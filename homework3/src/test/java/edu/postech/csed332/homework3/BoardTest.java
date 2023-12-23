package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void testCellConstruction() {
        Cell cell = new Cell();
        for (int n = 1; n <= 9; n++)
            Assertions.assertTrue(cell.containsPossibility(n));
    }


    @Test
    void testAddPossibility() {
        Cell cell = new Cell();
        cell.removePossibility(1);
        Assertions.assertFalse(cell.containsPossibility(1));
        cell.addPossibility(1);
        Assertions.assertTrue(cell.containsPossibility(1));
    }


    @Test
    void testCellDisabled() {
        Cell cell = new Cell();
        for(int i = 1; i <= 8; i++)
            cell.removePossibility(i);
        assertFalse(cell.hasNoPossibility());

        var disabled = new AtomicBoolean(false);
        cell.addObserver((caller, arg) -> {
            if (arg instanceof ActivationEvent ae)
                disabled.set(!ae.activated());
        });
        cell.removePossibility(9);
        assertTrue(disabled.get());
        assertTrue(cell.hasNoPossibility());
    }

    @Test
    void testCellEnabled() {
        Cell cell = new Cell();
        for(int i = 1; i <= 9; i++)
            cell.removePossibility(i);
        assertTrue(cell.hasNoPossibility());

        var disabled = new AtomicBoolean(true);
        cell.addObserver((caller, arg) -> {
            if (arg instanceof ActivationEvent ae)
                disabled.set(!ae.activated());
        });
        cell.addPossibility(1);
        assertFalse(disabled.get());
        assertFalse(cell.hasNoPossibility());
    }

    @Test
    void testGameSetNumbers() {
        var board = new Board(GameInstanceFactory.createGameInstance());
        assertTrue(board.getCell(1,1).setNumber(5));
        assertTrue(board.getCell(4,4).setNumber(1));
        assertTrue(board.getCell(5,5).setNumber(9));
        assertTrue(board.getCell(6,6).hasNoPossibility());
        assertTrue(board.getCell(1,1).unsetNumber());
        assertTrue(board.getCell(6,6).hasNoPossibility());
        assertTrue(board.getCell(5,5).unsetNumber());
        assertFalse(board.getCell(6,6).hasNoPossibility());
    }

    @Test
    void testCellSetNumberShouldSetNumberWhenNumberIsUnsigned() {
        Cell cell = new Cell();
        assertTrue(cell.setNumber(5));
        assertTrue(cell.getNumber().isPresent());
        assertEquals(5, (int) cell.getNumber().get());
    }

    @Test
    void testCellSetNumberShouldNotSetNumberWhenNumberIsSigned() {
        Cell cell = new Cell();
        assertTrue(cell.setNumber(5));
        assertFalse(cell.setNumber(6));
    }

    @Test
    void testCellUnsetNumberShouldUnsetNumberWhenNumberIsSigned() {
        Cell cell = new Cell();
        assertTrue(cell.setNumber(5));
        assertTrue(cell.unsetNumber());
        assertFalse(cell.getNumber().isPresent());
    }

    @Test
    void testCellUnsetNumberShouldNotUnsetNumberWhenNumberIsUnsigned() {
        Cell cell = new Cell();
        assertFalse(cell.unsetNumber());
    }

    @Test
    void testCellAddGroup() {
        Cell cell = new Cell();
        Group group = new Group();
        cell.addGroup(group);
        assertTrue(cell.groups.contains(group));
    }

    @Test
    void testCellAddPossibilityShouldNotAddPossibilityWhenNumberIsSignedInAnotherGroup() {
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Group group = new Group();
        cell1.addGroup(group);
        cell2.addGroup(group);
        group.addCell(cell1);
        group.addCell(cell2);
        cell1.setNumber(5);
        cell2.addPossibility(5);
        assertFalse(cell2.containsPossibility(5));
    }

    @Test
    void testGroupAddCell() {
        Group group = new Group();
        Cell cell = new Cell();
        group.addCell(cell);
        assertTrue(group.cells.contains(cell));
    }

    @Test
    void testGroupIsAvailable() {
        Group group = new Group();
        Cell cell = new Cell();
        group.addCell(cell);
        assertTrue(group.isAvailable(5));
        cell.setNumber(5);
        assertFalse(group.isAvailable(5));
    }

    @Test
    void testGroupUpdate() {
        Group group = new Group();
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        group.addCell(cell1);
        group.addCell(cell2);
        cell1.addGroup(group);
        cell2.addGroup(group);
        assertTrue(cell1.containsPossibility(5));
        // If cell2 has a number 5, cell1 should not have 5 as a possibility
        cell2.setNumber(5);
        assertFalse(cell1.containsPossibility(5));
        // If cell2 does not have a number 5, cell1 should have 5 as a possibility
        cell2.unsetNumber();
        assertTrue(cell1.containsPossibility(5));
    }

    @Test
    void testBoardConstruction() {
        Board board = new Board(GameInstanceFactory.createGameInstance());
        assertEquals(9, board.cells.length);
        assertEquals(9, board.horizontalGroups.size());
        assertEquals(9, board.verticalGroups.size());
        assertEquals(9, board.blockGroups.size());
        assertEquals(2, board.diagonalGroups.size());
    }

    @Test
    void testBoardSetNumbers() {
        Board board = new Board(GameInstanceFactory.createGameInstance());
        assertTrue(board.getCell(1,1).setNumber(5));
        assertTrue(board.getCell(4,4).setNumber(1));
        assertTrue(board.getCell(5,5).setNumber(9));
        assertTrue(board.getCell(6,6).hasNoPossibility());
        assertTrue(board.getCell(1,1).unsetNumber());
        assertTrue(board.getCell(6,6).hasNoPossibility());
        assertTrue(board.getCell(5,5).unsetNumber());
        assertFalse(board.getCell(6,6).hasNoPossibility());
    }

    @Test
    void testBoardGetCell() {
        Board board = new Board(GameInstanceFactory.createGameInstance());
        Cell cell = board.getCell(1, 1);
        // According to GameInstanceFactory's example data,
        // Cell (1, 1) could only have 5 and 8 as possibilities.
        assertFalse(cell.containsPossibility(1));
        assertFalse(cell.containsPossibility(2));
        assertFalse(cell.containsPossibility(3));
        assertFalse(cell.containsPossibility(4));
        assertTrue(cell.containsPossibility(5));
        assertFalse(cell.containsPossibility(6));
        assertFalse(cell.containsPossibility(7));
        assertTrue(cell.containsPossibility(8));
        assertFalse(cell.containsPossibility(9));
    }
}
