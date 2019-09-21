package pieces;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(int row, int col, boolean isWhite) {
        super(row, col, isWhite);

        // set icon
        loadIcon("bishop");
    }

    @Override
    public void updateValidLocations(Piece[][] pieces) {
        ArrayList<Integer[]> locations = new ArrayList<>();
        isGivingCheck = false;

        // loop through each of the four diagonals until an obstruction is reached
        int currentRow = row + 1;
        int currentCol = col + 1;
        // going bottom right
        while (currentCol < 8 && currentRow < 8) {
            locations.add(new Integer[]{currentRow, currentCol});

            if (pieces[currentRow][currentCol] != null) {
                isGivingCheck |= isPieceKing(pieces[currentRow][currentCol]);
                break;
            }

            currentRow++;
            currentCol++;
        }

        currentRow = row - 1;
        currentCol = col + 1;
        // going top right
        while (currentCol < 8 && currentRow >= 0) {
            locations.add(new Integer[]{currentRow, currentCol});

            if (pieces[currentRow][currentCol] != null) {
                isGivingCheck |= isPieceKing(pieces[currentRow][currentCol]);
                break;
            }

            currentRow--;
            currentCol++;
        }

        currentRow = row + 1;
        currentCol = col - 1;
        // going bottom left
        while (currentCol >= 0 && currentRow < 8) {
            locations.add(new Integer[]{currentRow, currentCol});

            if (pieces[currentRow][currentCol] != null) {
                isGivingCheck |= isPieceKing(pieces[currentRow][currentCol]);
                break;
            }

            currentRow++;
            currentCol--;
        }

        currentRow = row - 1;
        currentCol = col - 1;
        // going top left
        while (currentCol >= 0 && currentRow >= 0) {
            locations.add(new Integer[]{currentRow, currentCol});

            if (pieces[currentRow][currentCol] != null) {
                isGivingCheck |= isPieceKing(pieces[currentRow][currentCol]);
                break;
            }

            currentRow--;
            currentCol--;
        }


        removeFriendlyFireLocations(pieces, locations);

        validLocations = locations;
    }
}
