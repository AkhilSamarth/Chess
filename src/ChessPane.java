import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * JPanel to contain the chess game.
 */
public class ChessPane extends JPanel implements MouseListener {

    // colors of the squares on the board
    public static final Color BOARD_COLOR_LIGHT = new Color(255, 255, 240);
    public static final Color BOARD_COLOR_DARK = new Color(0, 0, 0);

    // array which represents each piece and its location on the board
    private Piece[][] pieces = new Piece[8][8];

    // piece which is currently selected
    private Piece selectedPiece;

    // size of each square
    private int squareWidth, squareHeight;
    
    /**
     * Instantiates a panel with the given width and height.
     * @param width width of panel
     * @param height height of panel
     */
    public ChessPane(int width, int height) {
        // no need for layout manager since all drawing done manually
        super(null);
        setSize(width, height);
        addMouseListener(this);

        squareWidth = getWidth() / 8;
        squareHeight = getHeight() / 8;

        // update square sizes when panel is resized
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                squareWidth = getWidth() / 8;
                squareHeight = getHeight() / 8;
            }
        });

        // add pieces in correct location to pieces array
        for (int i = 0; i < 8; i++) {
            // add pawns
            pieces[1][i] = new Pawn(1, i, false);
            pieces[6][i] = new Pawn(6, i, true);

            // determine which non-pawn pieces are in current column
            Piece darkPiece, lightPiece;
            switch (i) {
                case 0:
                case 7:
                    darkPiece = new Rook(0, i, false);
                    lightPiece = new Rook(7, i, true);
                    break;
                case 1:
                case 6:
                    darkPiece = new Knight(0, i, false);
                    lightPiece = new Knight(7, i, true);
                    break;
                case 2:
                case 5:
                    darkPiece = new Bishop(0, i, false);
                    lightPiece = new Bishop(7, i, true);
                    break;
                case 3:
                    darkPiece = new Queen(0, i, false);
                    lightPiece = new Queen(7, i, true);
                    break;
                case 4:
                    darkPiece = new King(0, i, false);
                    lightPiece = new King(7, i, true);
                    break;
                default:
                    // this shouldn't happen
                    darkPiece = new Pawn(0, i, false);
                    lightPiece = new Pawn(7, i, true);
                    break;
            }

            // add non-pawn piece
            pieces[0][i] = darkPiece;
            pieces[7][i] = lightPiece;

        }
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
        for (int i = 0; i < 8; i++) {
            // set color of first square on each row, light if even, dark if odd
            g2.setColor(i % 2 == 0 ? BOARD_COLOR_LIGHT : BOARD_COLOR_DARK);
            for (int j = 0; j < 8; j++) {
                g2.fillRect(j * squareWidth, i * squareHeight, squareWidth, squareHeight);

                // switch color
                if (g2.getColor().equals(BOARD_COLOR_LIGHT)) {
                    g2.setColor(BOARD_COLOR_DARK);
                } else {
                    g2.setColor(BOARD_COLOR_LIGHT);
                }
            }
        }

        // TODO: replace with proper piece drawing code
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece current = pieces[i][j];
                if (current == null) {
                    continue;
                }

                g2.setColor(current.isWhite() ? Color.GREEN : Color.RED);
                g2.setFont(new Font("Arial", Font.PLAIN, 32));
                g2.drawString(current.getClass().getName().substring(7), j * squareWidth + 20, i * squareHeight + 50);
            }
        }



        // highlight selectedPiece
        if (selectedPiece != null) {
            // how much selection squares are scaled down compared to board squares
            final double SCALE_FACTOR = 0.8;

            g2.setStroke(new BasicStroke(4));
            g2.setColor(Color.BLUE);
            int row = selectedPiece.getRow();
            int col = selectedPiece.getColumn();

            // get drawing info for selection square
            int[] squareInfo = getSelectionSquareCoords(row, col, SCALE_FACTOR);
            g2.drawRect(squareInfo[0], squareInfo[1], squareInfo[2], squareInfo[3]);

            // draw valid move locations
            g2.setColor(Color.GREEN);
            ArrayList<Integer[]> validMoves = new ArrayList<>();
            validMoves = getPossibleMoves(row, col);
            for (Integer[] coord : validMoves) {
                squareInfo = getSelectionSquareCoords(coord[0], coord[1], SCALE_FACTOR);
                g2.drawRect(squareInfo[0], squareInfo[1], squareInfo[2], squareInfo[3]);
            }
        }
    }

    /**
     * Helper method used to calculate the x, y, width, height of selection squares (squares that appear when a piece is clicked).
     * @param row row of square to be drawn
     * @param col column of square to be drawn
     * @param scale factor by which size of board square is multiplied (e.g. 0.5 = selection square will be half the size of board squares)
     * @return an int[] of the format: {x, y, width, height}
     */
    private int[] getSelectionSquareCoords(int row, int col, double scale) {
        // calculate size and position
        int width = (int) (squareWidth * scale);
        int height = (int) (squareHeight * scale);
        // offset of 0.5 * squareSize * (1 - SCALE_FACTOR) centers square inside larger square
        int x = (int) (col * squareWidth + 0.5 * squareWidth * (1 - scale));
        int y = (int) (row * squareHeight + 0.5 * squareHeight * (1 - scale));

        return new int[]{x, y, width, height};
    }

    /**
     * Figure out which locations a piece can actually move to, accounting for other pieces, etc.
     * @param row row of piece to check
     * @param col column of piece to check
     * @return ArrayList of coordinates in the form: {{row1, col1}, {row2, col2}, etc.}
     */
    private ArrayList<Integer[]> getPossibleMoves(int row, int col) {
        //TODO: implement this properly
        return pieces[row][col].getValidLocations();
    }

    /**
     * {@inheritDoc}
     * Captures mouse clicks and updates game accordingly.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // convert mouse x, mouse y to square on the board
        int boardX = e.getX() / squareWidth;
        int boardY = e.getY() / squareHeight;

        // set selectedPiece to piece at this location
        // its okay if its null
        selectedPiece = pieces[boardY][boardX];

        repaint();
    }

    // unused MouseListener methods
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
