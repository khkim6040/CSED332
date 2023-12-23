package edu.postech.csed332.homework1;

import java.util.Set;

/**
 * A game board contains a set of units and a goal position. A game consists
 * of a sequence of rounds. In each round, all units perform their actions:
 * a monster can escape or move, and a tower can attack nearby monsters.
 * The following invariant conditions must be maintained throughout the game:
 * <p>
 * (a) The position of each unit is inside the boundaries.
 * (b) Different ground units cannot be on the same tile.
 * (c) Different air units cannot be on the same tile.
 * <p>
 * NOTE: do not modify this file!
 */
public interface GameBoard {

    /**
     * Place a unit in this board
     *
     * @param obj a unit to be placed
     * @param p   the position of obj
     * @throws IllegalArgumentException if p is outside the board.
     */
    void placeUnit(Unit obj, Position p) throws IllegalArgumentException;

    /**
     * Clear this game board
     */
    void clear();

    /**
     * Return a set of units at a given position
     *
     * @param p a position
     * @return the set of units at the position
     */
    Set<Unit> getUnitsAt(Position p);

    /**
     * Return the position of a given unit
     *
     * @param obj a unit
     * @return the position of obj
     */
    Position getPosition(Unit obj);

    /**
     * Proceed one iteration of a game
     */
    void step();

    /**
     * Check the following invariants:
     * (a) All units are in the boundaries.
     * (b) Different ground units cannot be on the same tile.
     * (c) Different air units cannot be on the same tile.
     *
     * @return true if there is no problem. false otherwise.
     */
    boolean isValidBoard();

    /**
     * Check if a given position is valid for this game board
     *
     * @param pos a position
     * @return true if pos is a valid position
     */
    default boolean isValidPosition(Position pos) {
        return 0 <= pos.x() && pos.x() < getWidth() && 0 <= pos.y() && pos.y() < getHeight();
    }

    /**
     * Return a set of all monsters in this board
     *
     * @return the set of all monsters
     */
    Set<Monster> getMonsters();

    /**
     * Return a set of all towers in this board
     *
     * @return the set of all towers
     */
    Set<Tower> getTowers();

    /**
     * Return the width of this board
     *
     * @return width
     */
    int getWidth();

    /**
     * Return the height of this board
     *
     * @return height
     */
    int getHeight();

    /**
     * Return the number of the monsters in this board
     *
     * @return the number of the monsters
     */
    default int getNumMobs() {
        return getMonsters().size();
    }

    /**
     * Return the number of the towers in this board
     *
     * @return the number of the towers
     */
    default int getNumTowers() {
        return getTowers().size();
    }

    /**
     * Return the number of the monsters removed so far in this game
     *
     * @return the number of the monsters removed
     */
    int getNumMobsKilled();

    /**
     * Return the number of the monsters escaped so far in this game
     *
     * @return the number of the monsters escaped
     */
    int getNumMobsEscaped();
}
