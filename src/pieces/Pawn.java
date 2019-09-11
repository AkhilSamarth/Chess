package pieces;

import java.util.ArrayList;

public class Pawn extends Piece {

    // whether or not this pawn is on its first move
    private boolean firstMove = true;

    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    // override setter to update firstMove
    @Override
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
        if (firstMove) {
            firstMove = false;
        }
    }

    @Override
    public ArrayList<Integer[]> getValidLocations() {
        ArrayList<Integer[]> locations = new ArrayList<>();

        // determine direction based on white and black
        if (isWhite) {
            // add square in front of pawn and ones to side (for attacks)
            locations.add(new Integer[]{row - 1, col});
            if (col - 1 >= 0) {
                locations.add(new Integer[]{row - 1, col - 1});
            }
            if (col + 1 < 8) {
                locations.add(new Integer[]{row - 1, col + 1});
            }

            // add extra square if first move
            if (firstMove) {
                locations.add(new Integer[]{row - 2, col});
            }
        } else {
            // repeat above with black
            locations.add(new Integer[]{row + 1, col});
            if (col - 1 >= 0) {
                locations.add(new Integer[]{row + 1, col - 1});
            }
            if (col + 1 < 8) {
                locations.add(new Integer[]{row + 1, col + 1});
            }

            // add extra square if first move
            if (firstMove) {
                locations.add(new Integer[]{row + 2, col});
            }
        }

        return locations;
    }
}
