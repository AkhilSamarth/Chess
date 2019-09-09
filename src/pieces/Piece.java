package pieces;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Class which represents chess pieces.
 */
public abstract class Piece {

    protected BufferedImage icon;
    protected boolean isWhite;
    protected int row, col;

    /**
     * Creates a new chess piece.
     * @param row the row on the chess board this piece sits on
     * @param col the column on the chess board this piece sits on
     * @param isWhite whether this piece is white or black
     */
    protected Piece(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    // getters and setters
    public BufferedImage getIcon() {
        return icon;
    }

    public boolean isWhite() {
        return isWhite;
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
     * @return an ArrayList of coordinates of valid move positions in the form: {{row1, col1}, {row2, col2}, ...}
     */
    public abstract ArrayList<Integer[]> getValidLocations();
}
