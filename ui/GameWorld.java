package ui;

import game.GameModes;
import game.Snake;
import game.SnakeBody;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author cristopher
 */
public class GameWorld extends Canvas implements ComponentSetup {
    protected int width, height;
    protected int rWidth, rHeight;
    
    protected int gridWidth, gridHeight;
    protected int gridLength, gridLengthHalf;
    
    protected boolean paintGrid = false;
    
    protected Snake snake;
    protected Color snakeColor;
    
    public static ArrayList<Point2D.Float> food = new ArrayList<>();
    
    
    protected GameModes mode = GameModes.COLORFUL;
    protected boolean preview = true;

    
    protected Timer gameThread = new Timer(16, (Action) -> {
        updateSnake();
        render();
    });
    
    protected Timer foodGeneratorThread = new Timer(2000, (Action) -> {
        spawnFood();
    });
    
    
    public GameWorld(int width, int height) {
        this.width = width;
        this.height = height;
        
        gridLength = (int) (width / 50 * UIProperties.uiScale);
        gridLengthHalf = gridLength / 2;
        gridWidth = width / gridLength;
        gridHeight = height / gridLength;
        
        SnakeBody.gridLength = gridLength;
        SnakeBody.xGridLimit = gridWidth - 1;
        SnakeBody.yGridLimit = gridHeight - 1;
        
        snakeColor = UIProperties.APP_BG_COLOR;
        
        food.add(new Point2D.Float(width / 2 - gridLength, height - gridLength));
        snake = new Snake(snakeColor, 3, gridLength);
        snake.setAutomatic(true);
    }
    
    @Override
    public final void initUI() {
        createBufferStrategy(2);
        
        gameThread.start();
        foodGeneratorThread.start();
    }

    @Override
    public void updateUISize() {
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void updateUIFont() { }

    @Override
    public void updateUITheme() { }

    @Override
    public void updateUIColors() { }

    @Override
    public void setUseAppTheme(boolean useAppTheme) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setUseAppColor(boolean useAppColor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRoundCorners(boolean roundCorners) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPaintBorder(boolean paintBorder) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setPreferredSize(Dimension preferredSize) {
        width = preferredSize.width;
        height = preferredSize.height;
        
        preferredSize.width = (int) (preferredSize.width * UIProperties.uiScale);
        preferredSize.height = (int) (preferredSize.height * UIProperties.uiScale);
        
        rWidth = preferredSize.width;
        rHeight = preferredSize.height;
        
        super.setPreferredSize(preferredSize);
    }
    
    protected void updateSnake() {
        snake.move();
        if (preview)
            if (!food.isEmpty())
                snake.translateTo(food.get(0).x, food.get(0).y);
    }
    
    protected void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        Graphics2D g2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        paintWorld(g2D);

        g2D.dispose();
        bufferStrategy.show();
    }

    protected void paintWorld(Graphics2D g2D) {
        g2D.setColor(UIProperties.APP_BG);
        g2D.fillRect(0, 0, rWidth, rHeight);
        
        if (paintGrid) {
            g2D.setColor(Color.LIGHT_GRAY);
            g2D.drawRect(0, 0, rWidth, rHeight);
            for (int i = 0; i < rWidth; i += gridLength)
                g2D.drawLine(i, 0, i, rHeight);
            for (int i = 0; i < rHeight; i += gridLength)
                g2D.drawLine(0, i, rWidth, i);
        }
        
        g2D.setColor(Color.RED);
        for (Point2D.Float p : food)
            g2D.fill(new Ellipse2D.Float(p.x - 5, p.y - 5, 10, 10));
        
        snake.paintSnake(g2D);
    }

    public void stopWorld() {
        gameThread.stop();
        foodGeneratorThread.stop();
        
        food = new ArrayList<>();
        snake = null;
    }
    
    public void startWorld(Color snakeColor, boolean startThreads) {
        if (gameThread.isRunning() || foodGeneratorThread.isRunning())
            throw new IllegalStateException("This world is running already");
        
        food = new ArrayList<>();
        food.add(new Point2D.Float(width / 2 - gridLength, height - gridLength));
        snake = new Snake(snakeColor == null ? this.snakeColor : snakeColor, 1, gridLength);
        
        if (startThreads)
            initUI();
    }
    
    public void setPreview(boolean preview) {
        this.preview = preview;
        
        if (!preview)
            stopWorld();
    }

    private void spawnFood() {
        int xGrid = (int) (Math.random() * gridWidth) * gridLength - gridLengthHalf;
        int yGrid = (int) (Math.random() * gridHeight) * gridLength - gridLengthHalf;
        
        if (food.size() > 99)
            return;
        
        food.add(new Point2D.Float(xGrid, yGrid));
    }

    public void setSnakeColor(Color snakeColor) {
        this.snakeColor = snakeColor;
        snake.setColor(this.snakeColor);
    }
    
    public void setGameMode(GameModes mode) {
        if (mode == null)
            mode = this.mode;
        
        this.mode = mode;
        
        stopWorld();
        
        int gameThreadTime = 16;
        
        if (mode == GameModes.CLASSIC)
            gameThreadTime = 64;
        
        gameThread = new Timer(gameThreadTime, (Action) -> {
            updateSnake();
            render();
        });
        
        switch (mode) {
            case CLASSIC:
                startWorld(Color.GREEN, false);
                
                snake.setSpeed(100);
                snake.setAllowColorShift(false);
                snake.setAllowSpeedModifications(false);
            break;
            
            case COLORFUL:
                startWorld(snakeColor, false);
            break;
        }
        
        snake.setAutomatic(preview);
        
        initUI();
    }
}
