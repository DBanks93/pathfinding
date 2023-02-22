package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Node;

import java.util.ArrayList;

/**
 * Abstract class for all the pathfinding algorithms.
 * Pathfinding is run as a separate thread
 *
 * @author Daniel Banks
 * @version 1.1
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

    /** Adds a node to given ArrayList in order making it a sorted array.
     * Nodes are compared by their distance to from the start point
     *
     * @param list List the node is added to
     * @param node node to be added to the arrayList
     */
    protected void addList(ArrayList<Node> list, Node node) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDistance() > node.getDistance()) {
                list.add(i, node);
                return;
            }
        }
        list.add(node);
    }
}
