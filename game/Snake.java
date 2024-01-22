package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.Timer;
import ui.GameWorld;

/**
 *
 * @author cristopher
 */
public class Snake {
    protected SnakeBody head;
    
    protected ArrayList<SnakeBody> body = new ArrayList<>();
    protected ArrayList<Point2D> headPath = new ArrayList<>();
    
    private Color orgColor;
    private Color baseColor;
    private int colorMod = 0;
    private boolean allowColorShift = true;
    
    protected int gridLength;
    
    protected boolean automatic = false;
    protected double xNext = -1, yNext = -1;
    
    protected int detectionFoodRange;
    
    protected int growLimit = -1;
    
    private boolean allowSpeedModifications = true;
    private final Timer randomSpeedController = new Timer(1000, (Action) -> {
        head.setSpeed((int) (Math.random() * 200));
    });
    

    public Snake(Color baseColor, int detectionFoodRange, int gridLength) {
        this.baseColor = baseColor;
        this.orgColor = baseColor;
        
        this.gridLength = gridLength;        
        this.detectionFoodRange = this.gridLength * detectionFoodRange;
        
        head = new SnakeBody(true, SnakeBody.xGridLimit / 2 - 1.5, SnakeBody.yGridLimit / 2 - 1.5);
        head.setColor(baseColor);
    }
    
    public void paintSnake(Graphics2D g2D) {
        for (SnakeBody sb : body)
            sb.paintPart(g2D);
        
        head.paintPart(g2D);
    }
    
    public void setGeneralDirection(SnakeDirection generalDirection) {
        if (automatic)
            return;
        
        head.setDirection(generalDirection);
    }

    public void setAllowSpeedModifications(boolean allowSpeedModifications) {
        this.allowSpeedModifications = allowSpeedModifications;
    }
    
    public void setSpeed(int speed) {
        head.setSpeed(speed);
    }
    
    public void modifySpeed(boolean increase) {
        if (automatic || !allowSpeedModifications)
            return;
        
        head.modifySpeed(increase);
        
        for (SnakeBody sb : body)
            sb.modifySpeed(increase);
    }

    public void setGrowLimit(int growLimit) {
        if (growLimit < 1)
            growLimit = -1;
        
        this.growLimit = growLimit;
    }

    public void setAllowColorShift(boolean allowColorShift) {
        this.allowColorShift = allowColorShift;
    }
    
    public void shiftColor() {
        if ((int) (Math.random() * 100) % 2 == 0) {
            baseColor = baseColor.brighter();
            colorMod++;
        }
        if ((int) (Math.random() * 100) % 2 == 0) {
            baseColor = baseColor.darker();
            colorMod--;
        }

        if (Math.abs(colorMod) > 3) {
            colorMod = 0;
            baseColor = orgColor;
        }
    }
    
    public void setColor(Color c) {
        orgColor = c;
        head.setColor(c);
        
        for (SnakeBody sb : body) {
            shiftColor();
            sb.setColor(c);
        }
    }
    
    public void grow() {
        if (growLimit != -1 && body.size() > growLimit)
            return;
        
        if (allowColorShift)
            shiftColor();
        
        Point2D tailCoordinates;
        if (body.isEmpty())
            tailCoordinates = head.getGridLocation(false);
        else
            tailCoordinates = body.get(body.size() - 1).getGridLocation(false);
        
        headPath.add(tailCoordinates);
        SnakeBody p = new SnakeBody(false, tailCoordinates.getX(), tailCoordinates.getY());
        p.setColor(baseColor);
        body.add(p);
    }
    
    public void move() {
        head.movePart();
        Point2D hp = head.getAbsoluteLocation();
        
        if (automatic) {
            double xDifference = xNext - Math.round(hp.getX());
            double yDifference = yNext - Math.round(hp.getY());
            
            boolean closeInX = Math.abs(xDifference) < detectionFoodRange;
            boolean closeInY = Math.abs(yDifference) < detectionFoodRange;
            
            if (!closeInX) {
                if (xDifference > 0)
                    head.setDirection(SnakeDirection.EAST);
                else
                    head.setDirection(SnakeDirection.WEST);
            } else if (!closeInY) {
                if (yDifference > 0)
                    head.setDirection(SnakeDirection.SOUTH);
                else
                    head.setDirection(SnakeDirection.NORTH);
            }
        }
        
        if (!body.isEmpty()) {
            for (int i = 0, j = headPath.size() - 1; i < body.size(); i++, j--)
                body.get(i).setLocation(headPath.get(j));
            
            headPath.remove(0);
            headPath.add(head.getGridLocation(false));
        }
        
        for (int i = 0; i < GameWorld.food.size(); i++) {
//            System.out.println("hp = " + hp + ", compare " + GameWorld.food.get(i) + " distance " + hp.distance(GameWorld.food.get(i)));
            if (hp.distance(GameWorld.food.get(i)) <= detectionFoodRange) {
                GameWorld.food.remove(i);
                grow();
                break;
            }
        }
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
        if (automatic && allowSpeedModifications)
            randomSpeedController.start();
        else
            randomSpeedController.stop();
    }
    
    public void translateTo(double xGrid, double yGrid) {
        if (!automatic)
            throw new IllegalStateException("This snake is not in automatic mode");
        
        xNext = xGrid;
        yNext = yGrid;
    }
}
