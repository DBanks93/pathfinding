package com.example.pathfinding.Algorithms;

/**
 * Abstract class for all the pathfinding algorithms.
 * Pathfinding is run as a separate thread
 *
 * @author Daniel Banks
 * @version 1.1.6
 */
public abstract class Pathfinding implements Runnable {

    /**
     * Starts the thread that'll complete the pathfinding search.
     */
    @Override
    public void run() {
        search();
    }

    /**
     * Starts the search algorithm.
     * Is called from a thread
     */
    protected abstract void search();
}
