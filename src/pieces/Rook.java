package pieces;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(int row, int col, boolean isWhite) {
        super(row, col, isWhite);

        // set icon
        loadIcon("rook");
    }

    @Override
    public ArrayList<Integer[]> getValidLocations(Piece[][] pieces) {
        ArrayList<Integer[]> locations = new ArrayList<>();

        // loop in each of the four directions until an obstruction is found
        // going right
        for (int i = col + 1; i < 8; i++) {
            locations.add(new Integer[]{row, i});

            if (pieces[row][i] != null) {
                break;
            }
        }
        // going left
        for (int i = col - 1; i >= 0; i--) {
            locations.add(new Integer[]{row, i});

            if (pieces[row][i] != null) {
                break;
            }
        }
        // going down
        for (int i = row + 1; i < 8; i++) {
            locations.add(new Integer[]{i, col});

            if (pieces[i][col] != null) {
                break;
            }
        }
        // going up
        for (int i = row - 1; i >= 0; i--) {
            locations.add(new Integer[]{i, col});

            if (pieces[i][col] != null) {
                break;
            }
        }

        removeFriendlyFireLocations(pieces, locations);

        return locations;
    }
}
