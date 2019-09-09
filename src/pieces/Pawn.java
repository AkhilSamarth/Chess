package pieces;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public ArrayList<Integer[]> getValidLocations() {
        return new ArrayList<>();
    }
}
