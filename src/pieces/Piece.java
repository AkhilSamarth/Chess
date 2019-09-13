package pieces;

import java.awt.*;
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
     * Removes any locations at which a friendly piece is located.
     * Should be called at the end of getValidLocations().
     * @param pieces an array representing the board
     * @param locations the list of locations
     */
    protected void preventFriendlyFire(Piece[][] pieces, ArrayList<Integer[]> locations) {
        // loop backwards to allow for safe removal of elements
        for (int i = locations.size() - 1; i >= 0; i--) {
            int currentRow = locations.get(i)[0];
            int currentCol = locations.get(i)[1];

            // check for friendly fire
            if (pieces[currentRow][currentCol] != null && pieces[currentRow][currentCol].isWhite() == isWhite) {
                locations.remove(i);
            }
        }
    }

    /**
     * Returns all the possible locations this piece can move to.
     * @param pieces an array representing the board
     * @return an ArrayList of coordinates of valid move positions in the form: {{row1, col1}, {row2, col2}, ...}
     */
    public abstract ArrayList<Integer[]> getValidLocations(Piece[][] pieces);
}
