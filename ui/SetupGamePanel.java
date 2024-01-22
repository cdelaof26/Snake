package ui;

import ui.enums.UIAlignment;

/**
 *
 * @author cristopher
 */
public class SetupGamePanel extends Panel {
    private final MainMenu mainMenu = new MainMenu(this);
    private final GameModeSelector gameModeSelector = new GameModeSelector(this);
    
    public final GameWindow container;
    
    public SetupGamePanel(GameWindow container) {
        super(400, 600);
        
        this.container = container;
        
//        mainMenu.setVisible(false);
        gameModeSelector.setVisible(false);
        
        add(mainMenu, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        add(gameModeSelector, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
    }
    
    public void showMainMenu() {
        mainMenu.setVisible(true);
        
        gameModeSelector.setVisible(false);
        
        repaint();
    }
    
    public void showGameModeSelector() {
        mainMenu.setVisible(false);
        
        gameModeSelector.setVisible(true);
        
        repaint();
    }
}
