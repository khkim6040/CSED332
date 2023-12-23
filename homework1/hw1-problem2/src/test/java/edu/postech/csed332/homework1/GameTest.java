package edu.postech.csed332.homework1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
public class GameTest {
    // Method to generate test parameters
    static Stream<Object[]> towerAndMobProvider() {
        GameBoard board = new GameBoardImpl(5, 3);
        return Stream.of(
                new Object[]{new GroundTower(board), new GroundMob(board)},
                new Object[]{new AirTower(board), new AirMob(board)}
        );
    }

    static Stream<Object[]> twoAirOrGroundUnitsProvider() {
        GameBoard board = new GameBoardImpl(5, 3);
        return Stream.of(
                new Object[]{new GroundTower(board), new GroundTower(board)},
                new Object[]{new GroundTower(board), new GroundMob(board)},
                new Object[]{new GroundMob(board), new GroundMob(board)},
                new Object[]{new AirTower(board), new GroundMob(board)},
                new Object[]{new AirTower(board), new AirTower(board)},
                new Object[]{new AirMob(board), new AirMob(board)}
        );
    }

    @Test
    void testGameBoard() {
        var board = new GameBoardImpl(5, 3);
        Assertions.assertEquals(5, board.getWidth());
        Assertions.assertEquals(3, board.getHeight());
    }

    @Test
    void testPlaceUnit() {
        var board = new GameBoardImpl(5, 3);
        var mob = new GroundMob(board);
        var pos = new Position(0, 0);

        board.placeUnit(mob, pos);
        Assertions.assertTrue(board.getUnitsAt(pos).contains(mob));
        Assertions.assertEquals(1, board.getNumMobs());
    }

    @Test
    void testPlaceUnitInvalid() {
        var board = new GameBoardImpl(5, 3);
        var mob = new GroundMob(board);
        var pos = new Position(5, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> board.placeUnit(mob, pos));
    }

    @Test
    void testPlaceTwoUnits() {
        var board = new GameBoardImpl(5, 3);
        var mob = new AirMob(board);
        var tower = new GroundTower(board);
        var position = new Position(0, 0);

        board.placeUnit(mob, position);
        board.placeUnit(tower, position);
        Assertions.assertTrue(board.getUnitsAt(position).contains(mob));
        Assertions.assertTrue(board.getUnitsAt(position).contains(tower));
        Assertions.assertEquals(1, board.getNumMobs());
        Assertions.assertEquals(1, board.getNumTowers());
    }

    @Test
    void testGroundAttack() {
        var board = new GameBoardImpl(5, 3);
        var tower = new GroundTower(board);
        var mob = new GroundMob(board);
        var pos1 = new Position(1, 1);
        var pos2 = new Position(1, 2);

        board.placeUnit(tower, pos1);
        board.placeUnit(mob, pos2);
        Assertions.assertTrue(tower.attack().contains(mob));
    }

    @ParameterizedTest
    @MethodSource("towerAndMobProvider")
    void testTowerShouldAttackWhenMobIsNearby(Tower tower, Monster mob) {
        var pos1 = new Position(1, 1);
        var pos2 = new Position(1, 2);
        tower.getBoard().placeUnit(tower, pos1);
        tower.getBoard().placeUnit(mob, pos2);

        Assertions.assertTrue(tower.attack().contains(mob));
    }

    @ParameterizedTest
    @MethodSource("towerAndMobProvider")
    void testTowerShouldNotAttackWhenMobIsFar(Tower tower, Monster mob) {
        var pos1 = new Position(1, 1);
        var pos2 = new Position(2, 2);
        tower.getBoard().placeUnit(tower, pos1);
        tower.getBoard().placeUnit(mob, pos2);

        Assertions.assertFalse(tower.attack().contains(mob));
    }


    @ParameterizedTest
    @MethodSource("twoAirOrGroundUnitsProvider")
    void testisValidBoardOnSamePlace(Unit unit1, Unit unit2) {
        var pos = new Position(1, 1);
        unit1.getBoard().placeUnit(unit1, pos);
        unit2.getBoard().placeUnit(unit2, pos);
        Assertions.assertFalse(unit1.getBoard().isValidBoard());
    }

    @Test
    void testIsValidBoardOnRightPlace() {
        GameBoard board = new GameBoardImpl(5, 3);
        Unit unit = new GroundTower(board);
        Position pos = new Position(1, 1);
        board.placeUnit(unit, pos);
        Assertions.assertTrue(board.isValidBoard());
    }

    @Test
    void testClear() {
        GameBoard board = new GameBoardImpl(5, 3);

        Unit unit1 = new GroundMob(board);
        Unit unit2 = new GroundTower(board);
        Position pos1 = new Position(1, 1);
        Position pos2 = new Position(2, 2);

        board.placeUnit(unit1, pos1);
        board.placeUnit(unit2, pos2);

        Assertions.assertFalse(board.getUnitsAt(pos1).isEmpty());
        Assertions.assertFalse(board.getUnitsAt(pos2).isEmpty());

        board.clear();

        Assertions.assertTrue(board.getUnitsAt(pos1).isEmpty());
        Assertions.assertTrue(board.getUnitsAt(pos2).isEmpty());
    }

    @Test
    void testRemoveUnit() {
        GameBoardImpl board = new GameBoardImpl(5, 3);
        Unit unit = new GroundMob(board);
        Position pos = new Position(1, 1);

        board.placeUnit(unit, pos);
        Assertions.assertFalse(board.getUnitsAt(pos).isEmpty());

        board.removeUnit(unit, pos);

        Assertions.assertTrue(board.getUnitsAt(pos).isEmpty());
    }
}
