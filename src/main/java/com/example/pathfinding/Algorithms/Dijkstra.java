package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Main;
import com.example.pathfinding.Node;
import com.example.pathfinding.Nodes;

import java.util.ArrayList;

/**
 * Class to do a Dijkstra path finding algorithm.
 * @author Daniel Banks
 * @version 1.1
 */
public class Dijkstra extends Pathfinding {

    /** Nodes who are the head of their sub-graph/branch. */
    private final ArrayList<Node> pathsHeads = new ArrayList<>();

    /** Visted nodes */
    private final ArrayList<Node> visitedNodes = new ArrayList<>();

    /**
     * Starts the thread that'll complete Dijkstra's algorithm.
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
        Node startNode = Nodes.getNode(Nodes.getStartNodePos());
        pathsHeads.add(startNode);
        if (searchRec(startNode, 1)) Nodes.getRoute();
    }

    /**
     * Recursive search that is called until the end node is found.
     * @return if end node is found
     */
    protected boolean searchRec(Node currentNode, int distance) {
        try {
            Thread.sleep(Main.getSpeedDelay());
        } catch (InterruptedException e) {
            System.out.println("Thread Interruption Error");
            e.printStackTrace();
        }
        if (currentNode.isEndNode()) {
            return true;
        }
        pathsHeads.remove(0);
        currentNode.markVisited();
        ArrayList<int[]> neighbours = Nodes.getNeighbours(Nodes.getNodePos(currentNode));
        for (int[] neighbourPos : neighbours) {
            Node neighbourNode = Nodes.getNode(neighbourPos);
            if (!neighbourNode.isBlocked()
                    &&(!visitedNodes.contains(neighbourNode)
                    || neighbourNode.getDistance() > distance)) {
                neighbourNode.setDistance(distance);
                neighbourNode.setPreviousNode(currentNode);
                addList(visitedNodes, neighbourNode);
                addList(pathsHeads, neighbourNode);
            }
        }
        if (pathsHeads.isEmpty()) {
            return false;
        }
        return searchRec(pathsHeads.get(0), distance + 1);
    }
}
