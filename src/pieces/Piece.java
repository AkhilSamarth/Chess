package pieces;

import main.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class which represents chess pieces.
 */
public abstract class Piece {

    // can be used to scale icons independently of window size
    public static final double ICON_SCALE = 0.55;

    protected BufferedImage icon, sourceImage;
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
     * Updates and resizes the icon based on the given square sizes.
     * Should be called anytime the board is resized.
     * @param squareWidth width of chess squares after resize
     * @param squareHeight height of chess squares after resize
     */
    public void updateIcon(int squareWidth, int squareHeight) {
        int initialSquareSize = Main.INITIAL_SIZE / 8;

        // calculate what fraction of initial size new size is
        double scaleX = 1D * squareWidth / initialSquareSize;
        double scaleY = 1D * squareHeight / initialSquareSize;

        // calculate scaled sizes of original image
        int scaledWidth = (int) (sourceImage.getWidth() * scaleX * ICON_SCALE);
        int scaledHeight = (int) (sourceImage.getHeight() * scaleY * ICON_SCALE);

        // generate new image with the appropriate size and update icon
        Image scaledImg = sourceImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        // use scaled image to draw onto icon
        icon = new BufferedImage(scaledImg.getWidth(null), scaledImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = icon.createGraphics();
        g.drawImage(scaledImg, 0, 0, null);
        g.dispose();
    }

    /**
     * Returns all the possible locations this piece can move to.
     * @param pieces an array representing the board
     * @return an ArrayList of coordinates of valid move positions in the form: {{row1, col1}, {row2, col2}, ...}
     */
    public abstract ArrayList<Integer[]> getValidLocations(Piece[][] pieces);

    /**
     * Loads the icon for this piece.
     * @param pieceName the name of piece, such as "pawn", "rook", etc.
     */
    protected void loadIcon(String pieceName) {
        // try to load image
        try {
            // make sure pieceName is valid
            if (!(pieceName.equals("pawn") || pieceName.equals("bishop") || pieceName.equals("knight") || pieceName.equals("queen") || pieceName.equals("rook") || pieceName.equals("king"))) {
                throw new IllegalArgumentException("Invalid piece name" + pieceName + " passed.");
            }

            // generate path name based on piece
            String path = "res/piece_" + (isWhite ? "white" : "black") + "_" + pieceName + ".png";
            sourceImage = ImageIO.read(new File(path));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            // TODO: generate default image
        }

        // set icon based on initial square size
        int initialSquareSize = Main.INITIAL_SIZE / 8;
        updateIcon(initialSquareSize, initialSquareSize);
    }

    /**
     * Removes any locations at which a friendly piece is located.
     * Should be called at the end of getValidLocations().
     * @param pieces an array representing the board
     * @param locations the list of locations
     */
    protected void removeFriendlyFireLocations(Piece[][] pieces, ArrayList<Integer[]> locations) {
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
}
