package pieces;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public ArrayList<Integer[]> getValidLocations() {
        ArrayList<Integer[]> locations = new ArrayList<>();

        // loop through row and column
        for (int i = 0; i < 8; i++) {
            // add same row square
            if (col != i) {
                locations.add(new Integer[]{row, i});
            }
            // add same col square
            if (row != i) {
                locations.add(new Integer[]{i, col});
            }
        }

        return locations;
    }
}
