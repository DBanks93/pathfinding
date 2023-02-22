package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Node;

import java.util.ArrayList;

/**
 * Abstract class for all the pathfinding algorithms.
 * Pathfinding is run as a separate thread
 *
 * @author Daniel Banks
 * @version 1.15
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
