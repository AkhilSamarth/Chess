package pieces;

import java.util.ArrayList;

public class Pawn extends Piece {

    // TODO: allow for promotions

    // whether or not this pawn is on its first move
    private boolean firstMove = true;

    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite);

        // set icon
        loadIcon("pawn");
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
    public ArrayList<Integer[]> getValidLocations(Piece[][] pieces) {
        ArrayList<Integer[]> locations = new ArrayList<>();

        // determine direction based on white and black
        if (isWhite) {
            // add square in front of pawn if unobstructed
            if (pieces[row - 1][col] == null) {
                locations.add(new Integer[]{row - 1, col});
            }

            // add diagonals if there's a piece there
            if (col - 1 >= 0 && pieces[row - 1][col - 1] != null) {
                locations.add(new Integer[]{row - 1, col - 1});
            }
            if (col + 1 < 8 && pieces[row - 1][col + 1] != null) {
                locations.add(new Integer[]{row - 1, col + 1});
            }

            // add extra square in front if first move
            if (firstMove && pieces[row - 2][col] == null) {
                locations.add(new Integer[]{row - 2, col});
            }
        } else {
            // repeat above with black
            // add square in front of pawn if unobstructed
            if (pieces[row + 1][col] == null) {
                locations.add(new Integer[]{row + 1, col});
            }

            // add diagonals if there's a piece there
            if (col - 1 >= 0 && pieces[row + 1][col - 1] != null) {
                locations.add(new Integer[]{row + 1, col - 1});
            }
            if (col + 1 < 8 && pieces[row + 1][col + 1] != null) {
                locations.add(new Integer[]{row + 1, col + 1});
            }

            // add extra square in front if first move
            if (firstMove && pieces[row + 2][col] == null) {
                locations.add(new Integer[]{row + 2, col});
            }
        }

        removeFriendlyFireLocations(pieces, locations);

        return locations;
    }
}
