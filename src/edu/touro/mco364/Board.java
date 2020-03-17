package edu.touro.mco364;

import java.util.Arrays;

public class Board {
    private CellState[][] board;
    public static final int GRID_SIZE = 8;

    public Board() {
        board = new CellState[GRID_SIZE][GRID_SIZE];

        for(int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], CellState.NONE);
        }
        board[3][3] = CellState.WHITE;
        board[3][4] = CellState.BLACK;
        board[4][4] = CellState.WHITE;
        board[4][3] = CellState.BLACK;
    }

    public CellState getCell(int row, int col) {
        return board[row][col];
    }

    public void setCell(int row, int col, CellState state) {
        board[row][col] = state;
    }

    public boolean isFull() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == CellState.NONE)
                    return false;
            }
        }
        return true;
    }
}