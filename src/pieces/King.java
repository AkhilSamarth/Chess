package pieces;

import java.util.ArrayList;

public class King extends Piece {

    // used for castling
    private boolean hasMoved = false;

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }


    public King(int row, int col, boolean isWhite) {
        super(row, col, isWhite);

        // set icon
        loadIcon("king");
    }

    @Override
    public void updateValidLocations(Piece[][] pieces) {
        ArrayList<Integer[]> locations = new ArrayList<>();

        // go row by row
        for (int i = row - 1; i <= row + 1; i++) {
            // check bounds
            if (i < 0 || i >= 8) {
                continue;
            }

            // go through columns
            for (int j = col - 1; j <= col + 1; j++) {
                // check bounds
                if (j < 0 || j >= 8 || (i == row && j == col)) {
                    continue;
                }

                // add square
                locations.add(new Integer[]{i, j});
            }
        }

        // check if castling is possible
        if (!hasMoved) {
            boolean leftClear = true, rightClear = true;      // booleans to keep track of whether or not there's pieces between the king and the rook
            // check for clearance to the left, up to the rook
            for (int i = col - 1; i >= 1; i--) {
                if (pieces[row][i] != null) {
                    leftClear = false;
                    break;
                }
            }

            // check for clearance to the right, up to the rook
            for (int i = col + 1; i < pieces[row].length - 1; i++) {
                if (pieces[row][i] != null) {
                    rightClear = false;
                    break;
                }
            }

            // check if there is an unmoved rook in each direction
            if (leftClear && pieces[row][0] != null && pieces[row][0] instanceof  Rook && !((Rook) pieces[row][0]).hasMoved()) {
                // allow castling to the left
                locations.add(new Integer[]{row, col - 2});
            }

            // check if there is an unmoved rook in each direction
            if (rightClear && pieces[row][pieces[row].length - 1] != null && pieces[row][pieces[row].length - 1] instanceof  Rook && !((Rook) pieces[row][pieces[row].length - 1]).hasMoved()) {
                // allow castling to the right
                locations.add(new Integer[]{row, col + 2});
            }
        }

        removeFriendlyFireLocations(pieces, locations);

        validLocations = locations;
    }
}
