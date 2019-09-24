package main;

import javax.swing.*;
import java.awt.*;

public class Main {

    // string constants for switching panels for cardLayout
    public static final String MENUPANE = "menu pane";
    public static final String CHESSPANE = "chess pane";

    public static JFrame frame;

    // initial size of panel, square of given side length
    public static final int INITIAL_SIZE = 960;

    // used for switching panels
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        // set up frame
        frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // panel to store cards
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(INITIAL_SIZE, INITIAL_SIZE));
        cardPanel.add(new MenuPane(INITIAL_SIZE, INITIAL_SIZE), MENUPANE);
        cardPanel.add(new ChessPane(INITIAL_SIZE, INITIAL_SIZE), CHESSPANE);

        // add cards to frame
        frame.add(cardPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // show menu
        showMenuPane();
    }

    /**
     * Displays the menu pane.
     */
    public static void showMenuPane() {
        cardLayout.show(cardPanel, MENUPANE);
    }

    /**
     * Displays the chess pane.
     */
    public static void showChessPane() {
        cardLayout.show(cardPanel, CHESSPANE);
    }
}
