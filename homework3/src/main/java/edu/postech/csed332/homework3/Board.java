package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * An X-Sudoku board
 */
public class Board {
    //TODO: add private member variables and private methods as needed
    ArrayList<Cell>[] cells = new ArrayList[9];
    List<Group> horizontalGroups = new ArrayList<>();
    List<Group> verticalGroups = new ArrayList<>();
    List<Group> blockGroups = new ArrayList<>();
    List<Group> diagonalGroups = new ArrayList<>();

    /**
     * Creates an X-Sudoku board
     *
     * @param game an initial configuration
     */
    Board(@NotNull GameInstance game) {
        //TODO: implement this
        for (int i = 0; i < 9; i++) {
            cells[i] = new ArrayList<>();
            horizontalGroups.add(new Group());
            verticalGroups.add(new Group());
            blockGroups.add(new Group());
        }
        diagonalGroups.add(new Group());
        diagonalGroups.add(new Group());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i].add(new Cell());
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int block = i / 3 * 3 + j / 3;
                boolean diagonal = (i == j);
                boolean antiDiagonal = (i + j == 8);
                Cell cell = cells[i].get(j);

                horizontalGroups.get(i).addCell(cell);
                cell.addObserver(horizontalGroups.get(i));
                cell.groups.add(horizontalGroups.get(i));
                verticalGroups.get(j).addCell(cell);
                cell.addObserver(verticalGroups.get(j));
                cell.groups.add(verticalGroups.get(j));
                blockGroups.get(block).addCell(cell);
                cell.addObserver(blockGroups.get(block));
                cell.groups.add(blockGroups.get(block));
                if(diagonal) {
                    diagonalGroups.get(0).addCell(cell);
                    cell.addObserver(diagonalGroups.get(0));
                    cell.groups.add(diagonalGroups.get(0));
                }
                if(antiDiagonal) {
                    diagonalGroups.get(1).addCell(cell);
                    cell.addObserver(diagonalGroups.get(1));
                    cell.groups.add(diagonalGroups.get(1));
                }
            }
        }

        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9 ; j++) {
                if (game.getNumbers(i, j).isPresent()) {
                    cells[i - 1].get(j - 1).setNumber(game.getNumbers(i, j).get());
                }
            }
        }
    }

    /**
     * Returns a cell in the i-th row of j-th column, where 1 <= i, j <= 9.
     *
     * @param i a row index
     * @param j a column index
     * @return a cell in the i-th row of j-th column
     */
    @NotNull
    Cell getCell(int i, int j) {
        //TODO: implement this
        return cells[i - 1].get(j - 1);
    }
}
