package pieces;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public ArrayList<Integer[]> getValidLocations() {
        ArrayList<Integer[]> locations = new ArrayList<>();

        // loop through diagonals in front of and behind bishop
        int currentColFront = col + 1;
        int currentColBack = col - 1;
        int rowOffset = 1;
        while (currentColFront < 8 || currentColBack >= 0) {
            // if squares are in bound, add to list
            // front of bishop
            if (currentColFront < 8) {
                if (row + rowOffset < 8) {
                    locations.add(new Integer[]{row + rowOffset, currentColFront});
                }
                if (row - rowOffset >= 0) {
                    locations.add(new Integer[]{row - rowOffset, currentColFront});
                }
            }

            // behind bishop
            if (currentColBack >= 0) {
                if (row + rowOffset < 8) {
                    locations.add(new Integer[]{row + rowOffset, currentColBack});
                }
                if (row - rowOffset >= 0) {
                    locations.add(new Integer[]{row - rowOffset, currentColBack});
                }
            }
            currentColFront++;
            currentColBack--;
            rowOffset++;
        }

        return locations;
    }
}
