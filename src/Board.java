import pieces.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents the chess board.
 */
public class Board {

    // row, col of array corresponds to row, col on board
    private Piece[][] pieces = new Piece[8][8];

    /**
     * Create a new chess board.
     */
    public Board() {
        /*
        // add pieces
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

        }*/

        pieces[0][0] = new Queen(0, 0, false);
        System.out.println(pieces[0][0].getValidLocations().size());
    }

    /**
     * Helper method used to draw board.
     * @param g graphics context which should be used to draw the board
     * @param lightColor the color of the lighter squares on the board
     * @param darkColor the color of the darker squares on the board
     * @param squareSize the size of each square on the board
     */
    public void drawBoard(Graphics2D g, Color lightColor, Color darkColor, int squareSize) {
        // draw squares, i = row, j = column
        for (int i = 0; i < 8; i++) {
            // set color of first square on each row, light if even, dark if odd
            g.setColor(i % 2 == 0 ? lightColor : darkColor);
            for (int j = 0; j < 8; j++) {
                g.fillRect(j * squareSize, i * squareSize, squareSize, squareSize);

                // switch color
                if (g.getColor().equals(lightColor)) {
                    g.setColor(darkColor);
                } else {
                    g.setColor(lightColor);
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

                g.setColor(current.isWhite() ? Color.GREEN : Color.RED);
                g.setFont(new Font("Arial", Font.PLAIN, 32));
                g.drawString(current.getClass().getName().substring(7), j * squareSize + 20, i * squareSize + 50);
            }
        }

        // TODO: testing for valid locs
        ArrayList<Integer[]> posArr = pieces[0][0].getValidLocations();

        for (Integer[] coord : posArr) {
            g.setColor(Color.BLUE);
            g.setStroke(new BasicStroke(4));
            g.drawRect(coord[1] * squareSize + 20, coord[0] * squareSize + 20, squareSize - 40, squareSize - 40);
        }
    }
}
