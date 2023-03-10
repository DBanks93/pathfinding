package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Main;
import com.example.pathfinding.Node;
import com.example.pathfinding.Nodes;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to do a breath first search.
 * @author Daniel Banks
 * @version 1.5.1
 */
public class BFS extends Pathfinding {

    /** Queue of discovered nodes positions */
    private final Queue<int[]> nodeQueue = new LinkedList<>();

    /**
     * Starts the search algorithm.
     * Is called from a thread
     */
    @Override
    protected void search() {
        setStartTime();
        nodeQueue.add(Nodes.getStartNodePos());
        if (searchRec()) Nodes.getRoute();
        setEndTime();
    }

    /**
     * Recursively is called until the end node is found
     * @return if end node is found
     */
    private boolean searchRec() {
        if (stopped) {
            return false;
        }

        addSeedDelay();

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
                incNodesVisited();
                if (neighbourNode.isEndNode()) return true;
            }
        }
        return searchRec();
    }
}
