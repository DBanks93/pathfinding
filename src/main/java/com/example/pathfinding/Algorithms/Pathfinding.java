package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Main;
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

    protected int nodesVisited = 0;

    /**
     * Starts the thread that'll complete the pathfinding search.
     */
    @Override
    public void run() {
        search();
    }

    /**
     * Adds a delay before traversing each node.
     * sleeps the pathfinding thread for a set time
     */
    protected void addSeedDelay() {
        long timeDelay = Main.getSpeedDelay();
        startTime += timeDelay;
        try {
            Thread.sleep(timeDelay);
        } catch (InterruptedException e) {
            System.out.println("Thread Interruption Error");
            e.printStackTrace();
        }
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

    /**
     * Adds the time taken to the display/GUI
     */
    protected void addTimeTaken() {
        Nodes.addTime(timeTaken);
    }

    /**
     * Adds the number of nodes visited to the GUI.
     */
    protected void setNodesVisited() {
        Nodes.addVisited(nodesVisited);
    }

    /**
     * Increments the number of nodes visited and displays it on the GUI.
     */
    protected void incNodesVisited() {
        nodesVisited++;
        setNodesVisited();
    }

    /**
     * Gets current time
     * @return current time (milliseconds)
     */
    private long getTime() {
        return System.currentTimeMillis();
    }
}
