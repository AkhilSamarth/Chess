import javax.swing.*;
import java.awt.*;

/**
 * JPanel for the chess game. Draws the board, pieces, etc.
 */
public class ChessPane extends JPanel {

    // colors of the squares on the board
    public static final Color BOARD_COLOR_LIGHT = new Color(255, 255, 240);
    public static final Color BOARD_COLOR_DARK = new Color(0, 0, 0);

    private Board board;

    /**
     * Instantiates a panel with the given width and height.
     * @param length height and width of each side
     */
    public ChessPane(int length) {
        // no need for layout manager since all drawing done manually
        super(null);
        setSize(length, length);

        board = new Board();
    }

    /**
     * Handles the drawing of the chess game.
     * @param g graphics context, provided by Swing
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // cast to Graphics2D for more flexibility
        Graphics2D g2 = (Graphics2D) g;

        // draw board
        board.drawBoard(g2, BOARD_COLOR_LIGHT, BOARD_COLOR_DARK, getWidth() / 8);
    }
}
