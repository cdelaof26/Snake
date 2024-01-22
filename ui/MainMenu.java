package ui;

import java.awt.Dimension;
import java.awt.Font;
import ui.enums.LabelType;
import ui.enums.UIAlignment;
import utils.LibUtilities;

/**
 *
 * @author cristopher
 */
public class MainMenu extends Panel {
    private final Label title = new Label(LabelType.NONE, "Mini Snake");
    private final ColorButton startGameButton = new ColorButton("Start game");
    private final ColorButton gamePreferencesButton = new ColorButton("Preferences");
    private final ColorButton uiPreferencesButton = new ColorButton("UI Preferences");
    
    private boolean initializated = false;
    
    private final SetupGamePanel container;
    
    public MainMenu(SetupGamePanel container) {
        super(300, 300);
        
        this.container = container;
        initMenu();
    }
    
    private void initMenu() {
        initializated = true;
        
        startGameButton.setUseOnlyAppColor(true);
        startGameButton.setPreferredSize(new Dimension(280, 50));
        startGameButton.setLabelType(LabelType.NONE);
        startGameButton.addActionListener((Action) -> {
            container.showGameModeSelector();
        });
        
        gamePreferencesButton.setUseAppTheme(true);
        
        add(title, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 50);
        add(startGameButton, title, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 30);
//        add(gamePreferencesButton, startGameButton, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 30);
        
        
        updateUIFont();
        updateUISize();
    }

    @Override
    public void updateUIFont() {
        if (initializated) {
            title.setFont(new Font(LibUtilities.getFontName(), Font.BOLD, 40));
            startGameButton.setFont(new Font(LibUtilities.getFontName(), Font.PLAIN, 20));
        }
        
        super.updateUIFont();
    }
}
