package edu.postech.csed332.homework3;

import java.util.Optional;

class GameInstanceFactory {
    private final static Integer[][] numbers = new Integer[][]{
            {null, null, 9, 7, null, null, null, null, null},
            {null, 6, null, null, null, 1, null, null, 8},
            {3, null, 4, null, 6, null, null, null, 9},

            {null, null, null, null, null, 8, null, null, null},
            {null, null, 5, null, null, 7, null, 8, null},
            {null, null, null, null, 2, null, null, 5, null},

            {2, null, null, null, null, 6, 3, null, null},
            {1, null, null, null, null, 4, null, null, null},
            {null, 3, null, null, null, null, null, null, 7}
    };

    public static GameInstance createGameInstance() {
        return (i, j) -> Optional.ofNullable(numbers[i-1][j-1]);
    }

}
