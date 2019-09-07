import java.awt.image.BufferedImage;

/**
 * Class which represents chess pieces.
 */
public class Piece {

    // enum for types of pieces
    enum PieceType {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }

    private BufferedImage icon;
    private boolean isWhite;
    private PieceType type;
    private int row, col;

    /**
     * Creates a new chess piece.
     * @param row the row on the chess board this piece sits on
     * @param col the column on the chess board this piece sits on
     * @param isWhite whether this piece is white or black
     * @param type the type of piece, e.g. rook, pawn, etc
     */
    public Piece(int row, int col, boolean isWhite, PieceType type) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.type = type;
    }

    // getters and setters
    public BufferedImage getIcon() {
        return icon;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public PieceType getType() {
        return type;
    }

    public String getTypeAsString() {
        return type.toString();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return col;
    }

    public void setColumn(int col) {
        this.col = col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns all the possible locations this piece can move to. Does NOT check if other pieces are in the way.
     * @return an array of coordinates of valid move positions in the form: {{row1, col1}, {row2, col2}, ...}
     */
    /*
    public int[][] getValidLocations() {

    }
    */
}
