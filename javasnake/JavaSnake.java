package javasnake;

import ui.GameWindow;
import utils.LibUtilities;

/**
 *
 * @author cristopher
 */
public class JavaSnake {
    public static void main(String[] args) {
        LibUtilities.loadPreferences(null);
        new GameWindow().showWindow();
    }
}
