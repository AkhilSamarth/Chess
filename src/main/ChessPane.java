package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * JPanel to contain the chess game.
 */
public class ChessPane extends JPanel implements MouseListener {

    // colors of the squares on the board
    public static final Color BOARD_COLOR_LIGHT = new Color(255, 255, 240);     // ivory
    public static final Color BOARD_COLOR_DARK = new Color(90, 40, 10);          // dark brown

    // array which represents each piece and its location on the board
    private Piece[][] pieces = new Piece[8][8];

    // piece which is currently selected
    private Piece selectedPiece;

    // array which indicates if the location given by [row][col] can be moved to or not
    private boolean[][] movableSquares = new boolean[8][8];

    // size of each square
    private int squareWidth, squareHeight;

    // whether or not its white's turn, true at start
    private boolean isWhitesTurn = true;
    
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

                // update piece sizes
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (pieces[i][j] != null) {
                            pieces[i][j].updateIcon(squareWidth, squareHeight);
                        }
                    }
                }

                repaint();
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

        // update all pieces
        updatePieceLocations();
    }

    /**
     * Handles the drawing of the chess game.
     * @param g graphics context, provided by Swing
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // how much selection squares are scaled down compared to board squares
        final double SCALE_FACTOR = 0.8;

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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece current = pieces[i][j];
                if (current == null) {
                    continue;
                }

                BufferedImage icon = current.getIcon();

                // calculate coords relative to this square needed for centered icon
                int iconX = (squareWidth - icon.getWidth()) / 2;
                int iconY = (squareHeight - icon.getHeight()) / 2;

                // coordinates of current square
                int squareX = squareWidth * j;
                int squareY = squareHeight * i;

                // draw icon inside square
                g2.drawImage(icon, null, iconX + squareX, iconY + squareY);

                /* TODO: this code will be used when proper check detection is added
                // if this piece is giving check, highlight it with a yellow box
                if (current.isGivingCheck()) {
                    g2.setStroke(new BasicStroke(4));
                    g2.setColor(Color.YELLOW);
                    int[] squareCoords = getSelectionSquareCoords(i, j, SCALE_FACTOR);
                    g2.drawRect(squareCoords[0], squareCoords[1], squareCoords[2], squareCoords[3]);
                }
                */
            }
        }

        // highlight selectedPiece
        if (selectedPiece != null) {
            g2.setStroke(new BasicStroke(4));
            g2.setColor(Color.BLUE);
            int row = selectedPiece.getRow();
            int col = selectedPiece.getColumn();

            // get drawing info for selection square
            int[] squareInfo = getSelectionSquareCoords(row, col, SCALE_FACTOR);
            g2.drawRect(squareInfo[0], squareInfo[1], squareInfo[2], squareInfo[3]);

            // get and draw valid move locations of selected piece
            ArrayList<Integer[]> validMoves = selectedPiece.getValidLocations();
            for (Integer[] coord : validMoves) {
                // if there is a piece at this square, mark it red (attack square)
                if (pieces[coord[0]][coord[1]] != null) {
                    g2.setColor(Color.RED);
                } else {
                    g2.setColor(Color.GREEN);
                }

                // draw square
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
     * Update the movableSquares array, which is used by the mouseClicked method to determine movement.
     */
    private void updateMovableSquares() {
        // reset array each time
        movableSquares = new boolean[8][8];

        // if there's no piece selected, nothing to update
        if (selectedPiece == null) {
            return;
        }

        // get valid moves from piece and update array accordingly
        ArrayList<Integer[]> validLocations = selectedPiece.getValidLocations();
        for (Integer[] coord : validLocations) {
            movableSquares[coord[0]][coord[1]] = true;
        }
    }

    /**
     * Updates all the pieces with the current board configuration so that they have the correct valid moves.
     */
    private void updatePieceLocations() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    pieces[i][j].updateValidLocations(pieces);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * Captures mouse clicks and updates game accordingly.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // convert mouse x, mouse y to square on the board
        int mouseCol = e.getX() / squareWidth;
        int mouseRow = e.getY() / squareHeight;

        // if a piece is selected and a movable square is clicked, allow it to move/attack
        if (selectedPiece != null && movableSquares[mouseRow][mouseCol]) {
            // TODO: if attack, keep track of killed pieces

            int pieceRow = selectedPiece.getRow();
            int pieceCol = selectedPiece.getColumn();

            // if this is a king which is moving to a non-adjacent square, this is castling move
            if (selectedPiece instanceof King && mouseCol != pieceCol - 1 && mouseCol != pieceCol + 1) {
                // find the correct rook
                Piece rook;
                if (mouseCol < pieceCol) {
                    // left rook
                    rook = pieces[pieceRow][0];

                    // move rook
                    pieces[pieceRow][0] = null;
                    pieces[pieceRow][pieceCol - 1] = rook;
                    rook.setPosition(pieceRow, pieceCol - 1);
                } else {
                    // right rook
                    rook = pieces[pieceRow][pieces[pieceRow].length - 1];

                    // move rook
                    pieces[pieceRow][pieces[pieceRow].length - 1] = null;
                    pieces[pieceRow][pieceCol + 1] = rook;
                    rook.setPosition(pieceRow, pieceCol + 1);
                }
            }

            // move piece
            pieces[pieceRow][pieceCol] = null;
            pieces[mouseRow][mouseCol] = selectedPiece;
            selectedPiece.setPosition(mouseRow, mouseCol);

            // if this is a king or rook, let it know that it has moved
            if (selectedPiece instanceof King) {
                ((King) selectedPiece).setMoved(true);
            } else if (selectedPiece instanceof Rook) {
                ((Rook) selectedPiece).setMoved(true);
            }

            // if this piece is a pawn which has reached the end, promote it to a queen
            // TODO: allow player to select piece to promote to
            if (selectedPiece instanceof Pawn && (mouseRow == 0 || mouseRow == 7)) {
                // replace Pawn with new Queen
                pieces[mouseRow][mouseCol] = new Queen(mouseRow, mouseCol, selectedPiece.isWhite());
            }

            // deselect piece and update pieces and board
            selectedPiece = null;
            updateMovableSquares();
            updatePieceLocations();

            // switch turn
            isWhitesTurn = !isWhitesTurn;

        } else if (pieces[mouseRow][mouseCol] != null && pieces[mouseRow][mouseCol].isWhite() == isWhitesTurn) {    // select piece at current location, making sure piece is correct color
            selectedPiece = pieces[mouseRow][mouseCol];
            // update movableSquares
            updateMovableSquares();
        } else {
            // if neither of above cases are true, deselect piece
            selectedPiece = null;
            updateMovableSquares();
        }

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
