package pieces;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public ArrayList<Integer[]> getValidLocations() {
        return new ArrayList<>();
    }
}
