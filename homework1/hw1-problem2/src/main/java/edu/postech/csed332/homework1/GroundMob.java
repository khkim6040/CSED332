package edu.postech.csed332.homework1;

import java.util.*;
import java.util.stream.Stream;
import java.util.random.*;

/**
 * A ground monster that moves towards the goal position of the board while satisfying
 * the game board invariants. The class GroundMob implements the interface Monster.
 * <p>
 * TODO: implement all unimplemented methods.
 * Feel free to modify this file, e.g. add new fields or methods. If necessary,
 * you can define a new (abstract) superclass that this class inherits. In this case,
 * some methods can be moved to the abstract class.
 *
 * @see GameBoard#isValidBoard1
 */
public class GroundMob implements Monster {
    GameBoard board;
    // Priority of moving: x+1, y, x-1
    int[] dx = {1, 0, 0, -1};
    int[] dy = {0, -1, 1, 0};
    public GroundMob(GameBoard gameBoard) {
        this.board = gameBoard;
    }

    @Override
    public boolean isGround() {
        return true;
    }

    @Override
    public GameBoard getBoard() {
        return board;
    }

    @Override
    public Position move() {
        Position curPos = getPosition();
        List<Position> possibleMoves = new ArrayList<>();
        boolean isTowardMovePossible = false;
        for(int i=0; i<4; i++) {
            Position newPos = new Position(curPos.x()+dx[i], curPos.y()+dy[i]);
            if(isMovable(newPos)) {
                if(i==0) {
                    isTowardMovePossible = true;
                }
                possibleMoves.add(newPos);
            }
        }

        if(!possibleMoves.isEmpty()) {
            if(isTowardMovePossible && Math.random()<=0.6) {
                return possibleMoves.get(0);
            }
            else{
                Random rand = new Random();
                int randomIndex = rand.nextInt(possibleMoves.size());
                return possibleMoves.get(randomIndex);
            }
        }

        return getPosition();
    }

    public boolean isMovable(Position pos) {
        // check if pos is valid position
        if(!board.isValidPosition(pos)) {
            return false;
        }
        // check if any ground units are already there
        Set<Unit> UnitsOnPos = board.getUnitsAt(pos);
        for(Unit unit: UnitsOnPos) {
            if(unit.isGround()) {
                return false;
            }
        }
        // check if GroundTower is on neabyPos
        for(int i=0; i<4; i++) {
            Position nearbyPos = new Position(pos.x()+dx[i], pos.y()+dy[i]);
            Set<Unit> nearbyUnits = board.getUnitsAt(nearbyPos);
            for(Unit unit: nearbyUnits) {
                if(unit instanceof GroundTower) {
                    return false;
                }
            }
        }
        return true;
    }
}
