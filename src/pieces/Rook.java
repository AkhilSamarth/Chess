package pieces;

import java.util.ArrayList;

public class Rook extends Piece {

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    // used for castling
    private boolean hasMoved = false;

    public Rook(int row, int col, boolean isWhite) {
        super(row, col, isWhite);

        // set icon
        loadIcon("rook");
    }

    @Override
    public void updateValidLocations(Piece[][] pieces) {
        ArrayList<Integer[]> locations = new ArrayList<>();

        // loop in each of the four directions until an obstruction is found
        // going right
        for (int i = col + 1; i < 8; i++) {
            // add the location, and if it returns true (obstruction), break
            if (addBishopRookLocation(pieces, locations, row, i)) {
                break;
            }
        }
        // going left
        for (int i = col - 1; i >= 0; i--) {
            // add the location, and if it returns true (obstruction), break
            if (addBishopRookLocation(pieces, locations, row, i)) {
                break;
            }
        }
        // going down
        for (int i = row + 1; i < 8; i++) {
            // add the location, and if it returns true (obstruction), break
            if (addBishopRookLocation(pieces, locations, i, col)) {
                break;
            }
        }
        // going up
        for (int i = row - 1; i >= 0; i--) {
            // add the location, and if it returns true (obstruction), break
            if (addBishopRookLocation(pieces, locations, i, col)) {
                break;
            }
        }

        removeFriendlyFireLocations(pieces, locations);

        validLocations = locations;
    }
}
