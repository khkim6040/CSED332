package edu.postech.csed332.homework3;

import java.util.Optional;

/**
 * An X Sudoku puzzle
 */
public interface GameInstance {
    /**
     * Initial numbers (null if a cell is empty)
     *
     * @param i row index
     * @param j column index
     * @return the number in the i-th row of j-th column
     */
    Optional<Integer> getNumbers(int i, int j);
}
