package ui;

import game.GameModes;
import game.SnakeDirection;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import utils.LibUtilities;

/**
 *
 * @author cristopher
 */
public class GameWindow extends Window {
    protected int width = 1000, height = 600;
    
    private final GameWorld gameWorld = new GameWorld(width, height);
    protected SetupGamePanel mainMenu = new SetupGamePanel(this);
    
    private boolean initializated = false;
    
    public GameWindow() {
        super(1000, 600, true);
        
        initializated = true;
        
        setResizable(false);
        
        add(mainMenu);
        add(gameWorld);
        pack();
        
        mainMenu.setBounds(0, 0, mainMenu.width, mainMenu.height);
        
        gameWorld.initUI();
        
        addKeyBindings();
    }

    @Override
    public void updateUISize() {
        if (initializated) {
            mainMenu.updateUISize();
            gameWorld.updateUISize();
            
            gameWorld.setBounds(0, 0, gameWorld.getPreferredSize().width, gameWorld.getPreferredSize().height);
        }
        
        super.updateUISize();
    }
    
    public void setGameMode(GameModes mode) {
        gameWorld.setGameMode(mode);
    }
    
    public void updateSnakeColor(Color c) {
        gameWorld.setSnakeColor(c);
    }
    
    public void startGame() {
        remove(mainMenu);
        revalidate();
        repaint();
        
        gameWorld.setPreview(false);
        gameWorld.setGameMode(null);
    }
    
    private void addKeyBindings() {
        // Debug
//        LibUtilities.addKeyBindingTo(container, "Grow", "R", new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                gameWorld.snake.grow();
//            }
//        });
        
        LibUtilities.addKeyBindingTo(container, "Up", "UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.setGeneralDirection(SnakeDirection.NORTH);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "Down", "DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.setGeneralDirection(SnakeDirection.SOUTH);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "Left", "LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.setGeneralDirection(SnakeDirection.WEST);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "Right", "RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.setGeneralDirection(SnakeDirection.EAST);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "UpW", "W", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.setGeneralDirection(SnakeDirection.NORTH);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "DownS", "S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.setGeneralDirection(SnakeDirection.SOUTH);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "LeftA", "A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.setGeneralDirection(SnakeDirection.WEST);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "RightD", "D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.setGeneralDirection(SnakeDirection.EAST);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "Increase speed", "Q", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.modifySpeed(true);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "Decrease speed", "E", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWorld.snake.modifySpeed(false);
            }
        });
    }
}
