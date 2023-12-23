package edu.postech.csed332.homework1;

/**
 * A position of a game board, given by a pair (x, y).
 * NOTE: do not modify this file!
 */
public record Position(int x, int y) {

    /**
     * Return a new position based on this position with respect to given differences.
     *
     * @param dx x-axis difference
     * @param dy y-axis difference
     * @return the new position
     */
    public Position getRelative(int dx, int dy) {
        return new Position(x() + dx, y() + dy);
    }

    /**
     * Return the distance between this position (x,y) and a given position (x',y').
     * The distance is defined by |x - x'| + |y - y'| (which is called L1 distance).
     *
     * @param p a position
     * @return the distance between this position and p
     */
    public int getDistance(Position p) {
        return Math.abs(p.x() - x()) + Math.abs(p.y() - y());
    }
}