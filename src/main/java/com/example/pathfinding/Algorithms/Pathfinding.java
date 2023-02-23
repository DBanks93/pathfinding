package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Nodes;

/**
 * Abstract class for all the pathfinding algorithms.
 * Pathfinding is run as a separate thread
 *
 * @author Daniel Banks
 * @version 1.2
 */
public abstract class Pathfinding implements Runnable {
    /** How long the algorithm takes*/
    protected long timeTaken = 0;

    /** Time the algorithm starts */
    protected long startTime = 0;

    /** Time the algorithm finishes */
    protected long endTime = 0;

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

    /** Sets the time taken by the pathfinding algorithm (milliseconds). */
    protected void setTimeTaken() {
        timeTaken = endTime - startTime;
        addTimeTaken();
    }

    /** Sets the start time */
    protected void setStartTime() {
        startTime = getTime();
    }

    /** Sets the end time */
    protected void setEndTime() {
        endTime = getTime();
        setTimeTaken();
    }

    protected void addTimeTaken() {
        Nodes.addTime(timeTaken);
    }

    /**
     * Gets current time
     * @return current time (milliseconds)
     */
    private long getTime() {
        return System.currentTimeMillis();
    }
}
