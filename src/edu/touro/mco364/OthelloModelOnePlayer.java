package edu.touro.mco364;

public class OthelloModelOnePlayer extends OthelloModel {
    private CellState computerColor;
    private int[][] moveTracker = new int[Board.GRID_SIZE][Board.GRID_SIZE];

    public OthelloModelOnePlayer(CellState computer) {
        computerColor = computer;
    }

    @Override
    public boolean playerMove(CellState state) {
        if(state == computerColor) {
            computerMove();
            return false;
        }
        return true;
    }

    private void computerMove() {
        int maxFlip = 0;
        int[] bestMove = new int[] {-1, -1};
        for(int i = 0; i < moveTracker.length; i++) {
            for(int j = 0; j < moveTracker[i].length; j++) {
                int[] currentSpace = new int[] {i, j};
                int flipCount = checkMoveValidity(currentSpace, computerColor, false);
                if(flipCount > maxFlip) {
                    bestMove = currentSpace;
                    maxFlip = flipCount;
                }
            }
        }
        System.out.println(maxFlip + ": " + bestMove[0] + ", " + bestMove[1]);
        checkMoveValidity(bestMove, computerColor, true);
    }
}