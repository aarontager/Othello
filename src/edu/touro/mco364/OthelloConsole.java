package edu.touro.mco364;

import java.util.Scanner;

public class OthelloConsole {
    private static OthelloModelInterface game;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeGame();
        printBoard();

        while(!game.gameOver()) {

            if(game.hasMove(CellState.BLACK)) {
                move(CellState.BLACK);
                printBoard();
            }

            if(game.hasMove(CellState.WHITE)) {
                move(CellState.WHITE);
                printBoard();
            }
        }

        System.out.println(game.tallyScore());
    }

    private static void initializeGame() {
        System.out.println("Would you like to play 1 player or 2?");
        try {
            int input = scanner.nextInt();
            if(input < 1 || input > 2) {
                System.out.println("Invalid input! please input either 1 or 2!");
                initializeGame();
                return;
            }
            game = input == 1 ? new OthelloModelOnePlayer(CellState.BLACK) : new OthelloModelTwoPlayer();
        }
        catch(Exception e) {
            System.out.println("Invalid input! please input either 1 or 2!");
            scanner.nextLine();
            initializeGame();
            return;
        }
    }

    private static void printBoard() {

        System.out.println("Current Board State:");
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println("  ┏━━━┳━━━┳━━━┳━━━┳━━━┳━━━┳━━━┳━━━┓");
        for(int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ┃ ");
            for(int j = 0; j < 8; j++) {
                System.out.print(game.getCell(i, j).toString() + " ┃ ");
            }
            System.out.println();
            if(i < 7) System.out.println("  ┣━━━╋━━━╋━━━╋━━━╋━━━╋━━━╋━━━╋━━━┫");
        }
        System.out.println("  ┗━━━┻━━━┻━━━┻━━━┻━━━┻━━━┻━━━┻━━━┛");
    }

    private static void move(CellState state) {
        if(game.playerMove(state)) {
            int[] desiredMove = getPlayerMove(state);
            while(game.checkMoveValidity(desiredMove, state, true) <= 0) {
                System.out.print("Invalid move! ");
                desiredMove = getPlayerMove(state);
            }
        }
    }

    private static int[] getPlayerMove(CellState state) {
        System.out.print(state + ", please input your next move: ");
        String input = scanner.next().toUpperCase();

        if(input.length() < 2)
            return new int[] {-1, -1};

        int[] parsedInput = new int[] {input.charAt(1) - '1', input.charAt(0) - 'A'}; //Adjust for unicode values
        return parsedInput;
    }
}