package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Main;
import com.example.pathfinding.Node;
import com.example.pathfinding.Nodes;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to do a breath first search.
 * @author Daniel Banks
 * @version 1.3
 */
public class BFS extends Pathfinding {

    /** Queue of discovered nodes positions */
    private final Queue<int[]> nodeQueue = new LinkedList<>();

    /**
     * Starts the thread that'll complete the BFS search.
     */
    @Override
    public void run() {
        search();
    }

    /**
     * Starts the search algorithm.
     * Is called from a thread
     */
    @Override
    protected void search() {
        nodeQueue.add(Nodes.getStartNodePos());
        if (searchRec()) Nodes.getRoute();
    }

    /**
     * Recursively is called until the end node is found
     * @return if end node is found
     */
    private boolean searchRec() {
        try {
            Thread.sleep(Main.getSpeedDelay());
        } catch (InterruptedException e) {
            System.out.println("Thread Interruption Error");
            e.printStackTrace();
        }
        if (nodeQueue.isEmpty()) {
            return false;
        }
        int[] currentPos = nodeQueue.remove();

        if (Nodes.getNode(currentPos).isEndNode()) {
            return true;
        }
        for (int[] neighbourPos : Nodes.getNeighbours(currentPos)) {
            Node neighbourNode = Nodes.getNode(neighbourPos);
            if (!neighbourNode.isVisited() && !neighbourNode.isBlocked()) {
                nodeQueue.add(neighbourPos);
                neighbourNode.markVisited();
                neighbourNode.setPreviousNode(Nodes.getNode(currentPos));
                if (neighbourNode.isEndNode()) return true;
            }
        }
        return searchRec();
    }
}
