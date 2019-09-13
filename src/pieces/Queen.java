package pieces;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public ArrayList<Integer[]> getValidLocations(Piece[][] pieces) {
        // create a fake bishop and rook and combine their locations
        Rook rook = new Rook(row, col, isWhite);
        Bishop bishop = new Bishop(row, col, isWhite);

        ArrayList<Integer[]> locations = rook.getValidLocations(pieces);
        locations.addAll(bishop.getValidLocations(pieces));

        // friendly fire already prevented by Rook and Bishop

        return locations;
    }
}
