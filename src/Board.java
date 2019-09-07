import java.awt.*;

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
        // add pieces
        for (int i = 0; i < 8; i++) {
            // add pawns
            pieces[1][i] = new Piece(1, i, false, Piece.PieceType.PAWN);
            pieces[6][i] = new Piece(6, i, true, Piece.PieceType.PAWN);

            // determine which non-pawn piece is on current column
            Piece.PieceType currentType;
            switch (i) {
                case 0:
                case 7:
                    currentType = Piece.PieceType.ROOK;
                    break;
                case 1:
                case 6:
                    currentType = Piece.PieceType.KNIGHT;
                    break;
                case 2:
                case 5:
                    currentType = Piece.PieceType.BISHOP;
                    break;
                case 3:
                    currentType = Piece.PieceType.QUEEN;
                    break;
                case 4:
                    currentType = Piece.PieceType.KING;
                    break;
                default:
                    // this shouldn't happen
                    currentType = Piece.PieceType.PAWN;
                    break;
            }

            // add non-pawn piece
            pieces[0][i] = new Piece(0, i, false, currentType);
            pieces[7][i] = new Piece(0, i, true, currentType);
        }
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
                g.setFont(new Font("Arial", Font.PLAIN, 24));
                g.drawString(current.getTypeAsString(), j * squareSize + 20, i * squareSize + 50);
            }
        }

    }
}
