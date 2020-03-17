package edu.touro.mco364;

public interface OthelloModelInterface {
    CellState getCell(int row, int col);

    int countPieces(CellState state);

    boolean gameOver();

    boolean hasMove(CellState state);

    int checkMoveValidity(int[] input, CellState state, boolean shouldFlip);

    boolean playerMove(CellState state);

    String tallyScore();
}