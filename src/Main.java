import javax.swing.*;
import java.awt.*;

public class Main {

    // string constants for switching panels for cardLayout
    public static final String MENUPANE = "menu pane";
    public static final String CHESSPANE = "chess pane";

    public static JFrame frame;

    // initial size of panel
    private static int initialSize = 960;

    // used for switching panels
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        // set up frame
        frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // panel to store cards
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(initialSize, initialSize));
        cardPanel.add(new MenuPane(initialSize, initialSize), MENUPANE);
        cardPanel.add(new ChessPane(initialSize), CHESSPANE);

        // add cards to frame
        frame.add(cardPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // TODO: show menu initially
        showChessPane();
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
