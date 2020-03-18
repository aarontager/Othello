package edu.touro.mco364;

import javax.swing.*;
import java.awt.*;

public class OthelloGUI extends JFrame {
    private JPanel top = new JPanel(), left = new JPanel(),
            right = new JPanel(), boardPanel = new JPanel();
    private JLabel bottom = new JLabel("Black's turn!");
    private OthelloModelInterface game;
    private JButton[][] buttonArray = new JButton[Board.GRID_SIZE][Board.GRID_SIZE];
    private JOptionPane gameOverPane = new JOptionPane();
    private Color background = Color.decode("#008000");
    private CellState turn = CellState.BLACK;

    public OthelloGUI(OthelloModelInterface model) {
        setSize(800, 800);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game = model;

        initializeBoard();
        setBorder();

        setVisible(true);
    }

    private void initializeBoard() {
        if(!game.playerMove(turn)){
            turn = CellState.WHITE;
            bottom.setText("White's turn!");
        }
        boardPanel.setBackground(background);
        boardPanel.setLayout(new GridLayout(8, 8, 4, 4));
        boardPanel.setBorder(BorderFactory.createLineBorder(background, 10));
        addButtons();
        add(boardPanel, BorderLayout.CENTER);
    }

    private void setBorder() {
        top.setPreferredSize(new Dimension(800, 50));
        top.setBackground(Color.BLACK);
        add(top, BorderLayout.PAGE_START);

        bottom.setPreferredSize(new Dimension(800, 50));
        bottom.setBackground(Color.BLACK);
        bottom.setHorizontalAlignment(SwingConstants.CENTER);
        bottom.setOpaque(true);
        bottom.setForeground(Color.WHITE);
        add(bottom, BorderLayout.PAGE_END);

        left.setPreferredSize(new Dimension(50, 700));
        left.setBackground(Color.BLACK);
        add(left, BorderLayout.LINE_START);

        right.setPreferredSize(new Dimension(50, 700));
        right.setBackground(Color.BLACK);
        add(right, BorderLayout.LINE_END);
    }

    private void addButtons() {
        for(int i = 0; i < Board.GRID_SIZE; i++) {
            for(int j = 0; j < Board.GRID_SIZE; j++) {
                final int row = i, col = j;
                buttonArray[i][j] = new JButton();
                buttonArray[i][j].setBackground(game.getCell(i, j).getColor());
                buttonArray[i][j].addActionListener(e -> makeMove(row, col));
                boardPanel.add(buttonArray[i][j]);
            }
        }
    }

    private void makeMove(int row, int col) {
        if(game.checkMoveValidity(new int[] {row, col}, turn, true) > 0) {
            turn = (turn == CellState.WHITE) ? CellState.BLACK : CellState.WHITE;
        }
        updateBoard(turn);
    }

    private void updateBoard(CellState state) {
        setColors();

        if(game instanceof OthelloModelOnePlayer)
            SwingUtilities.invokeLater(() -> checkComputerMove(state));
    }

    private void checkComputerMove(CellState state) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!game.playerMove(state)) {
            turn = (turn == CellState.WHITE) ? CellState.BLACK : CellState.WHITE;
            setColors();
        }

        if(!checkGameOver()) {
            while(!game.hasMove(state)) {
                turn = (turn == CellState.WHITE) ? CellState.BLACK : CellState.WHITE;
                state = turn;
            }
        }
    }

    private void setColors() {
        for(int i = 0; i < Board.GRID_SIZE; i++) {
            for(int j = 0; j < Board.GRID_SIZE; j++) {
                buttonArray[i][j].setBackground(game.getCell(i, j).getColor());
            }
        }
        String turnText;
        turnText = (turn == CellState.BLACK) ? "Black's turn!" : "White's turn!";
        bottom.setText(turnText);
    }

    private boolean checkGameOver() {
        boolean over = game.gameOver();
        if(over) {
            gameOverPane.showMessageDialog(this, game.tallyScore());
        }
        return over;
    }

    public static void main(String[] args) {
//        OthelloModelInterface run = new OthelloModelOnePlayer(CellState.WHITE);
        OthelloModelInterface run = new OthelloModelTwoPlayer();
        new OthelloGUI(run);
    }
}