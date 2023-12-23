package edu.postech.csed332.homework1;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An air tower that can attack nearby air monsters within 1 tile. For example,
 * an air tower at (x,y) can attack all air monsters at (x-1, y), (x+1, y),
 * (x, y-1), and (x, y+1). The class AirTower implements the interface Tower.
 * <p>
 * TODO: implement all unimplemented methods.
 * Feel free to modify this file, e.g. add new fields or methods. If necessary,
 * you can define a new (abstract) superclass that this class inherits. In this case,
 * some methods can be moved to the abstract class.
 */
public class AirTower implements Tower {
    GameBoard board;

    public AirTower(GameBoard gameBoard) {
        this.board = gameBoard;
    }

    @Override
    public Set<Monster> attack() {
        // get tower's position
        Position towerPos = getPosition();
        // see if there are any monsters near at tower
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        Set<Monster> attackedMonsters = new HashSet<>();
        for(int i=0; i<4; i++) {
            int x = towerPos.x() + dx[i];
            int y = towerPos.y() + dy[i];
            if(x>=0 && y>=0 && x<board.getWidth() && y<board.getHeight()) {
                Position nearbyPos = new Position(x, y);
                Set<Unit> units = board.getUnitsAt(nearbyPos);
                for(Unit unit: units) {
                    if(unit instanceof AirMob) {
                        attackedMonsters.add((Monster)unit);
                    }
                }
            }
        }
        return attackedMonsters;
    }

    @Override
    public GameBoard getBoard() {
        return board;
    }
}
