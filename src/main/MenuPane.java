package main;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel for the menu.
 */
public class MenuPane extends JPanel {

    /**
     * Instantiates a panel with the given width and height.
     * @param width the width of the panel
     * @param height the height of the panel
     */
    public MenuPane(int width, int height) {
        // no need for layout manager since all drawing done manually
        super(null);
        setSize(width, height);

        setBackground(Color.BLUE);
    }
}
