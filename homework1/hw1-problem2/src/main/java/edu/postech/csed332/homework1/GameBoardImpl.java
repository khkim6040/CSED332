package edu.postech.csed332.homework1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of GameBoard that implements the interface GameBoard.
 * <p>
 * TODO: implements all the unimplemented methods (marked with TODO)
 * NOTE: Do not modify the signature of public methods (which are used for GUI
 * and grading). But feel free to add new fields or new private methods as needed.
 */
class GameBoardImpl implements GameBoard {
    private final int width, height;
    private Map<Position, Set<Unit>> board;
    private int numMobsKilled;
    private int numMobsEscaped;
    // TODO add more fields to implement this class

    /**
     * Create a game board with a given width and height
     *
     * @param width  of this board
     * @param height of this board
     */
    public GameBoardImpl(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new HashMap<>();
        this.numMobsKilled = 0;
        this.numMobsEscaped = 0;
        // TODO add more statements if necessary
    }

    @Override
    public void placeUnit(Unit obj, Position p) {
        if(!isValidPosition(p)) {
            throw new IllegalArgumentException("Invalid Position");
        }

        Set<Unit> unitsAtPosition = board.getOrDefault(p, new HashSet<>());
        // increase 1 unit count of obj.getClass()
        unitsAtPosition.add(obj);
        board.put(p, unitsAtPosition);
    }

    @Override
    public void clear() {
        board.clear();
    }

    @Override
    public Set<Unit> getUnitsAt(Position p) {
        return board.getOrDefault(p, new HashSet<>());
    }

    @Override
    public Position getPosition(Unit obj) {
        for (Map.Entry<Position, Set<Unit>> entry : board.entrySet()) {
            if (entry.getValue().contains(obj)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void step() {
        // check if monster has escaped
        for(Monster monster: getMonsters()) {
            Position pos = monster.getPosition();
            if(pos.x() >= width-1) {
                numMobsEscaped++;
                removeUnit(monster, pos);
            }
        }
        // Attack Phase
        for(Tower tower: getTowers()) {
            Set<Monster> targets = tower.attack();
            numMobsKilled += targets.size();
            for(Monster target: targets) {
                // Delete target from board
                Position targetPosition = getPosition(target);
                removeUnit(target, targetPosition);
            }
        }
        // Move Phase
        for(Monster monster: getMonsters()) {
            Position oldPos = monster.getPosition();
            Position newPos = monster.move();
            removeUnit(monster, oldPos);
            placeUnit(monster, newPos);
        }
    }

    public void removeUnit(Unit unit, Position position) {
        Set<Unit> unitsAtPosition = board.getOrDefault(position, new HashSet<>());
        unitsAtPosition.remove(unit);
        board.put(position, unitsAtPosition);
    }

    @Override
    public boolean isValidBoard() {
//        (a) All units are in the boundaries.
        Set<Monster> monsters = getMonsters();
        for(Monster monster: monsters) {
            Position pos = monster.getPosition();
            if(!isValidPosition(pos)) {
                return false;
            }
        }
        Set<Tower> towers = getTowers();
        for(Tower tower: towers) {
            Position pos = tower.getPosition();
            if (!isValidPosition(pos)) {
                return false;
            }
        }
//        (b) Different ground units cannot be on the same tile.
//        (c) Different air units cannot be on the same tile.
        for(int x=0; x<getWidth(); x++) {
            for(int y=0; y<getHeight(); y++) {
                Position pos = new Position(x, y);
                Set<Unit> units = getUnitsAt(pos);
                int groundUnitsCount = 0;
                int airUnitsCount = 0;
                for(Unit unit: units) {
                    if(unit.isGround()) {
                        groundUnitsCount++;
                    }
                    else {
                        airUnitsCount++;
                    }
                }
                if(groundUnitsCount > 1 || airUnitsCount > 1) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public Set<Monster> getMonsters() {
        Set<Monster> monsters = new HashSet<>();
        for (Set<Unit> unitSet : board.values()) {
            for (Unit unit : unitSet) {
                if (unit instanceof Monster) {
                    monsters.add((Monster) unit);
                }
            }
        }
        return monsters;
    }

    @Override
    public Set<Tower> getTowers() {
        Set<Tower> towers = new HashSet<>();
        for (Set<Unit> unitSet : board.values()) {
            for (Unit unit : unitSet) {
                if (unit instanceof Tower) {
                    towers.add((Tower) unit);
                }
            }
        }
        return towers;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getNumMobsKilled() {
        return numMobsKilled;
    }

    @Override
    public int getNumMobsEscaped() {
        return numMobsEscaped;
    }
}
