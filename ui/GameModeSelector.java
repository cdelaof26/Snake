package ui;

import game.GameModes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import ui.enums.ImageButtonArrangement;
import ui.enums.LabelType;
import ui.enums.UIAlignment;
import utils.LibUtilities;

/**
 *
 * @author cristopher
 */
public class GameModeSelector extends Panel {
    private final ImageButton backButton = new ImageButton(ImageButtonArrangement.ONLY_TINY_IMAGE);
    
    private final Label title = new Label(LabelType.NONE, "New game");
    
    private final Label modeSubtitle = new Label(LabelType.BODY, "Select the game mode");
    private final ImageButton classicMode = new ImageButton("Retro", false, ImageButtonArrangement.F_CENTER_TEXT_LEFT_IMAGE);
    private final ImageButton colorfulMode = new ImageButton("Colorful", false, ImageButtonArrangement.F_CENTER_TEXT_LEFT_IMAGE);
    
    private final Label colorPickerSubtitle = new Label(LabelType.BODY, "Select the snake color");
    
    private final JComponent colorUpdater = new JComponent() {
        @Override
        public void setBackground(Color fg) {
            if (container.container == null)
                return;
            
            container.container.updateSnakeColor(fg);
        }
    };
    private final ColorPicker snakeColorPicker = new ColorPicker(colorUpdater);
    
    private final ImageButton startGameButton = new ImageButton("Play colorful", false, ImageButtonArrangement.F_CENTER_TEXT_RIGHT_IMAGE);
    
    private boolean initializated = false;
    
    private final SetupGamePanel container;
    
    public GameModeSelector(SetupGamePanel container) {
        super(400, 600);
        this.container = container;
        
        initializated = true;
        
        initGameModeSelector();
    }

    private void initGameModeSelector() {
        classicMode.setPreferredSize(new Dimension(160, 40));
        colorfulMode.setPreferredSize(new Dimension(160, 40));
        startGameButton.setPreferredSize(new Dimension(160, 40));
        
        snakeColorPicker.setSelectedColor(UIProperties.APP_BG_COLOR, true);
        
        
        backButton.addActionListener((Action) -> {
            container.showMainMenu();
        });
        
        classicMode.addActionListener((Action) -> {
            container.container.setGameMode(GameModes.CLASSIC);
            showColorPicker(false);
            startGameButton.setText("Play retro");
        });
        
        colorfulMode.addActionListener((Action) -> {
            container.container.setGameMode(GameModes.COLORFUL);
            showColorPicker(true);
            startGameButton.setText("Play colorful");
        });
        
        startGameButton.addActionListener((Action) -> {
            container.container.startGame();
        });
        
        
        add(backButton, UIAlignment.WEST, UIAlignment.WEST, 10, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        
        add(title, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 100);
        
        add(modeSubtitle, this, title, UIAlignment.WEST, UIAlignment.WEST, 30, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(classicMode, modeSubtitle, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(colorfulMode, this, classicMode, UIAlignment.EAST, UIAlignment.EAST, -30, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        
        add(colorPickerSubtitle, classicMode, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 20);
        add(snakeColorPicker, this, colorPickerSubtitle, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 0);
        
        add(startGameButton, UIAlignment.EAST, UIAlignment.EAST, -10, UIAlignment.SOUTH, UIAlignment.SOUTH, -10);
    }
    
    @Override
    public void updateUISize() {
        if (initializated) {
            title.setFont(new Font(LibUtilities.getFontName(), Font.BOLD, 40));
        }
        
        super.updateUISize();
    }

//    @Override
//    public void updateUITheme() {
//        super.updateUITheme();
//        setBackground(new Color(255, 230, 230));
//    }
    
    private void showColorPicker(boolean b) {
        colorPickerSubtitle.setVisible(b);
        snakeColorPicker.setVisible(b);
        
        repaint();
    }
}
