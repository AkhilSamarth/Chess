package pieces;

import java.util.ArrayList;

public class King extends Piece {

    public King(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public ArrayList<Integer[]> getValidLocations() {
        ArrayList<Integer[]> locations = new ArrayList<>();

        // go row by row
        for (int i = row - 1; i <= row + 1; i++) {
            // check bounds
            if (row < 0 || row >= 8) {
                continue;
            }

            // go through columns
            for (int j = col - 1; j <= col + 1; j++) {
                // check bounds
                if (col < 0 || col >= 8 || (i == row && j == col)) {
                    continue;
                }

                // add square
                locations.add(new Integer[]{i, j});
            }
        }

        return locations;
    }
}
