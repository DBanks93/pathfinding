package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Main;
import com.example.pathfinding.Node;
import com.example.pathfinding.Nodes;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

/**
 * Class to do a depth first search.
 * @author Daniel Banks
 * @version 1.0
 */
public class DFS implements Runnable {
    /** Stack of nodes that DFS has traversed. */
    private static final Stack<int[]> nodesStack = new Stack<>();

    /** Start Pos of the Search */
    private final int[] startPos;

    /** Finish Pos of the search */
    private final int[] finishPos;

    /**
     * Creates an instance of DFS.
     * @param startPos start position of the search
     * @param finishPos position the search algorithm is searching for
     */
    public DFS (int[] startPos, int[] finishPos) {
        this.startPos = startPos;
        this.finishPos = finishPos;
    }

    /**
     * Starts the thread that'll complete the DFS search.
     */
    @Override
    public void run() {
        search();
    }

    /**
     * Starts the search algorithm.
     * Is called from a thread
     */
    private void search() {
        nodesStack.push(startPos);
        Nodes.getNode(startPos).markVisited();
        searchRec(startPos);
    }

    /**
     * Recursively searches for the finish position.
     * (Is called within a thread)
     * @param nodePos current node it's visiting
     * @return if finish pos found in that "branch"
     */
    private boolean searchRec(int[] nodePos) {
        try {
            Thread.sleep(Main.getSpeedDelay());
        } catch (InterruptedException e) {
            System.out.println("Thread Interruption Error");
            e.printStackTrace();
        }

        if (Nodes.getNode(nodePos).isEndNode()) {
            return true;
        }
        ArrayList<int[]> neighbours = Nodes.getNeighbours(nodePos);
        if (neighbours.isEmpty()) {
            nodesStack.pop();
            return false;
        }
        Node neighbourNode;
        for (int[] neighbour : neighbours) {
            neighbourNode = Nodes.getNode(neighbour);
            if (!neighbourNode.isVisited() && !neighbourNode.isBlocked()) {
                nodesStack.push(neighbour);
                neighbourNode.markVisited();
                Nodes.addVisited();
                if (searchRec(neighbour)) {
                    neighbourNode.markDiscovered();
                    return true;
                }
            }
        }
        nodesStack.pop();
        return false;
    }
}
