package edu.touro.mco364;

public abstract class OthelloModel implements OthelloModelInterface {
    private Board board = new Board();

    @Override
    public CellState getCell(int row, int col) {
        return board.getCell(row, col);
    }

    @Override
    public boolean gameOver() {
        return board.isFull() || !(hasMove(CellState.WHITE) || hasMove(CellState.BLACK));
    }

    @Override
    public boolean hasMove(CellState state) {
        for(int i = 0; i < Board.GRID_SIZE; i++) {
            for(int j = 0; j < Board.GRID_SIZE; j++) {
                if(checkMoveValidity(new int[] {i, j}, state, false) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int countPieces(CellState state) {
        int counter = 0;
        for(int i = 0; i < Board.GRID_SIZE; i++) {
            for(int j = 0; j < Board.GRID_SIZE; j++) {
                if(board.getCell(i, j) == state)
                    counter++;
            }
        }
        return counter;
    }

    @Override
    public String tallyScore() {
        int black = countPieces(CellState.BLACK);
        int white = countPieces(CellState.WHITE);

        String blackWins = String.format("Black won with %d pieces to white's %d pieces!", black, white);
        String whiteWins = String.format("White won with %d pieces to black's %d pieces!", white, black);
        String tie = String.format("Tie! both players had %d pieces!", white);

        return black - white > 0 ? blackWins : black - white == 0 ? tie : whiteWins;
    }

    @Override
    public int checkMoveValidity(int[] input, CellState state, boolean shouldFlip) {
        if(input.length < 2)
            return 0;

        int row = input[0], col = input[1];

        if(!checkGridBounds(row, col) || board.getCell(row, col) != CellState.NONE)
            return 0;

        int flippedPieces = 0;

        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                flippedPieces += checkDirection(row, col, state, i, j, shouldFlip);
            }
        }

        return flippedPieces;
    }

    private int checkDirection(int row, int col, CellState state, int rowIncrement, int colIncrement, boolean shouldFlip) {
        int tempRow = row + rowIncrement;
        int tempCol = col + colIncrement;
        int flipCount = 0;

        if(!checkGridBounds(tempRow, tempCol) || board.getCell(tempRow, tempCol) == state || board.getCell(tempRow, tempCol) == CellState.NONE)
            return 0;

        tempRow += rowIncrement;
        tempCol += colIncrement;

        while(checkGridBounds(tempRow, tempCol)) {
            if(board.getCell(tempRow, tempCol) == CellState.NONE)
                return 0;

            if(board.getCell(tempRow, tempCol) == state) {
                while(tempRow != row || tempCol != col) {
                    tempRow -= rowIncrement;
                    tempCol -= colIncrement;
                    flipCount++;
                    if(shouldFlip)
                        board.setCell(tempRow, tempCol, state);
                }
                flipCount--;
                break;
            }

            tempRow += rowIncrement;
            tempCol += colIncrement;
        }

        return flipCount;
    }

    private boolean checkGridBounds(int row, int col) {
        return !(row < 0 || col < 0 || row >= Board.GRID_SIZE || col >= Board.GRID_SIZE);
    }
}