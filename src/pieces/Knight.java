package pieces;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(int row, int col, boolean isWhite) {
        super(row, col, isWhite);

        // set icon
        loadIcon("knight");
    }

    @Override
    public ArrayList<Integer[]> getValidLocations(Piece[][] pieces) {
        ArrayList<Integer[]> locations = new ArrayList<>();

        // the eight possible locations of knight
        final int[][] LOCS_TO_CHECK = {{row+2, col+1}, {row+2, col-1},
                {row-2, col+1}, {row-2, col-1}, {row+1, col+2}, {row-1, col+2},
                {row+1, col-2}, {row-1, col-2}};

        // loop through the possible locations and add if in bounds
        for (int i = 0; i < LOCS_TO_CHECK.length; i++) {
            int rowToCheck = LOCS_TO_CHECK[i][0];
            int colToCheck = LOCS_TO_CHECK[i][1];
            if (rowToCheck < 0 || rowToCheck >= 8 || colToCheck < 0 || colToCheck >=8) {
                continue;
            }

            locations.add(new Integer[]{rowToCheck, colToCheck});
        }

        removeFriendlyFireLocations(pieces, locations);

        return locations;
    }
}
