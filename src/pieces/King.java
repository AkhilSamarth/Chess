package pieces;

import java.util.ArrayList;

public class King extends Piece {

    public King(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public ArrayList<Integer[]> getValidLocations() {
        return new ArrayList<Integer[]>();
    }
}
