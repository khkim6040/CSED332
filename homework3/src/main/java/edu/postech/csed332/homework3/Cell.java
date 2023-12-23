package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A cell that has a number and a set of possibilities. A cell may have a number of observers,
 * and notifies events to the observers.
 */
public class Cell extends Subject {
    //TODO: add private members variables and private methods as needed
    int number;
    Set<Integer> possibilities;
    List<Group> groups;
    /**
     * Creates an empty cell with a given type. Initially, no number is assigned.
     */
    public Cell() {
        //TODO: implement this
        number = 0;
        possibilities = new HashSet<>();
        for (int i = 1; i <= 9; i++) {
            possibilities.add(i);
        }
        groups  = new ArrayList<>();
    }

    /**
     * Returns the number of this cell.
     *
     * @return the number; Optional.empty() if no number assigned
     */
    @NotNull
    public Optional<Integer> getNumber() {
        //TODO: implement this
        if(number != 0) {
            return Optional.of(number);
        }
        return Optional.empty();
    }

    /**
     * Sets a number of this cell and notifies NumberEvent(number, true), provided that the cell has
     * previously no number and the given number to be set is in the set of possibilities.
     *
     * @param number the number
     * @return true if the number is set
     */
    public boolean setNumber(int number) {
        //TODO: implement this
        if(this.number == 0 && possibilities.contains(number)) {
            this.number = number;
            notifyObservers(new NumberEvent(number, true));
            return true;
        }
        return false;
    }

    /**
     * Removes the number of this cell and notifies NumberEvent(number, false), provided that the cell
     * has a number.
     *
     * @return true if the number is removed
     */
    public boolean unsetNumber() {
        //TODO: implement this
        if(this.number != 0) {
            int number = this.number;
            this.number = 0;
            notifyObservers(new NumberEvent(number, false));
            return true;
        }
        return false;
    }

    /**
     * Adds a group for this cell. This methods should also call addObserver(group).
     *
     * @param group a group
     */
    public void addGroup(@NotNull Group group) {
        addObserver(group);
        //TODO: implement this
        groups.add(group);
    }

    /**
     * Returns true if a given number is in the set of possibilities
     *
     * @param n a number
     * @return true if n is in the set of possibilities
     */
    public boolean containsPossibility(int n) {
        //TODO: implement this
        return possibilities.contains(n);
    }

    /**
     * Returns true if the cell has no possibility
     *
     * @return true if the set of possibilities is empty
     */
    public boolean hasNoPossibility() {
        //TODO: implement this
        return possibilities.isEmpty();
    }

    /**
     * Add the possibility of a given number, if possible, and notify ActivationEvent(true) if the set of
     * possibilities, previously empty, becomes non-empty. Also, if this number is already used by another
     * cell in the same group with this cell, the number cannot be added to the set of possibilities.
     *
     * @param number the number
     */
    public void addPossibility(int number) {
        //TODO: implement this
        for(Group g : groups) {
            if (!g.isAvailable(number)) {
                return;
            }
        }
        boolean flag = possibilities.isEmpty();
        possibilities.add(number);
        if(flag) {
            notifyObservers(new ActivationEvent(true));
        }
    }

    /*
     * Remove the possibility of a given number, and notify ActivationEvent(false) if the set of
     * possibilities becomes empty.
     *
     * @param number the number
     */
    public void removePossibility(int number) {
        //TODO: implement this
        if(possibilities.contains(number)) {
            possibilities.remove(number);
            if(possibilities.isEmpty()) {
                notifyObservers(new ActivationEvent(false));
            }
        }
    }
}
