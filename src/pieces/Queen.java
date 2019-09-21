package pieces;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(int row, int col, boolean isWhite) {
        super(row, col, isWhite);

        // set icon
        loadIcon("queen");
    }

    @Override
    public void updateValidLocations(Piece[][] pieces) {
        isGivingCheck = false;

        // create a fake bishop and rook and combine their locations
        Rook rook = new Rook(row, col, isWhite);
        Bishop bishop = new Bishop(row, col, isWhite);

        // update locations of rook and bishop
        rook.updateValidLocations(pieces);
        bishop.updateValidLocations(pieces);

        isGivingCheck = rook.isGivingCheck() || bishop.isGivingCheck();

        ArrayList<Integer[]> locations = rook.getValidLocations();
        locations.addAll(bishop.getValidLocations());

        // friendly fire already prevented by Rook and Bishop

        validLocations = locations;
    }
}
