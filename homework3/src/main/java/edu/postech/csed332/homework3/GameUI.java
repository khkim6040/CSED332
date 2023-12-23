package edu.postech.csed332.homework3;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    private static final int unitSize = 10;
    private static final int width = 45;
    private static final int height = 45;

    final JFrame top;

    public GameUI(Board board) {
        top = new JFrame("X-Sudoku");
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top.setLayout(new GridLayout(3, 3));

        var dim = new Dimension(unitSize * width, unitSize * height);
        top.setMinimumSize(dim);
        top.setPreferredSize(dim);

        createCellUI(board);

        top.pack();
        top.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var board = new Board(GameInstanceFactory.createGameInstance());
            new GameUI(board);
        });
    }

    private void createCellUI(Board board) {
        JTextField[][] cells = new JTextField[9][9];
        JPanel[][] squares = new JPanel[3][3];

        //TODO: implement this. Create cells and squares, to add them to top, and to
        // define layouts for them.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean isDiagonal = (i == j) || (i + j == 8);
                Cell cell = board.getCell(i + 1, j + 1);
                CellUI cellUI = new CellUI(cell, isDiagonal);
                cells[i][j] = cellUI;
                top.add(cells[i][j]);

                int squareRow = i / 3;
                int squareCol = j / 3;
                if (squares[squareRow][squareCol] == null) {
                    squares[squareRow][squareCol] = new JPanel();
                    squares[squareRow][squareCol].setLayout(new GridLayout(3, 3));
                }
                squares[squareRow][squareCol].add(cellUI);
                top.add(squares[squareRow][squareCol]);

            }
        }
    }

}
