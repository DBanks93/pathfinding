package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Main;
import com.example.pathfinding.Node;
import com.example.pathfinding.Nodes;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class to do a depth first search.
 * @author Daniel Banks
 * @version 1.4.2
 */
public class DFS extends Pathfinding {
    /** Stack of nodes that DFS has traversed. */
    private static final Stack<int[]> nodesStack = new Stack<>();

    /**
     * Starts the search algorithm.
     * Is called from a thread
     */
    @Override
    protected void search() {
        setStartTime();
        int[] startPos = Nodes.getStartNodePos();
        nodesStack.push(startPos);
        Nodes.getNode(startPos).markVisited();
        searchRec(startPos);
        Nodes.addDistance();
        setEndTime();
    }

    /**
     * Recursively searches for the finish position.
     * (Is called within a thread)
     * @param nodePos current node it's visiting
     * @return if finish pos found in that "branch"
     */
    private boolean searchRec(int[] nodePos) {
        addSeedDelay();
        if (stopped) {
            return false;
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
                incNodesVisited();
                if (searchRec(neighbour)) {
                    neighbourNode.markDiscovered();
                    Nodes.incDistance();
                    return true;
                }
            }
        }
        nodesStack.pop();
        return false;
    }
}
