package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Main;
import com.example.pathfinding.Node;
import com.example.pathfinding.Nodes;

import java.util.ArrayList;

/**
 * Class that'll perform the A Star path finding algorithm
 *
 * @author Daniel Banks
 * @version 1.1
 */
public class AStar extends Pathfinding {

    /** How much the distance to end node effects the weight of a node.
     * Is a factor of magnitude - will be timsed with distance to end
     */
    private static double distanceWeight = 1;

    /** Nodes who are the head of their sub-graph/branch. */
    private final ArrayList<Node> pathHeads = new ArrayList<>();

    /** Visited nodes */
    private final ArrayList<Node> visitedNodes = new ArrayList<>();

    /**
     * Sets the weight of the distance to the end node.
     * @param newDisWeight factor of magnitude for the weight
     */
    public static void setDistanceWeight(double newDisWeight) {
        distanceWeight = newDisWeight;
    }

    /**
     * Gets the weight of the distance to the end node.
     * @return factor of magnitude for the weight
     */
    public static double getDistanceWeight() {
        return distanceWeight;
    }

    /**
     * Starts the search algorithm.
     * Is called from a thread
     */
    @Override
    protected void search() {
        Node startNode = Nodes.getNode(Nodes.getStartNodePos());
        pathHeads.add(startNode);
        if (searchRec(startNode, 1)) Nodes.getRoute();
    }

    /**
     * Recursive search that is called until the end node is found.
     * @return if end node is found
     */
    private boolean searchRec(Node currentNode, int distance) {
        try {
            Thread.sleep(Main.getSpeedDelay());
        } catch (InterruptedException e) {
            System.out.println("Thread Interruption Error");
            e.printStackTrace();
        }

        if (currentNode.isEndNode()) {
            return true;
        }

        ArrayList<int[]> neighboursPos =
                Nodes.getNeighbours(Nodes.getNodePos(currentNode));

        if (neighboursPos.isEmpty()) {
            return false;
        }

        pathHeads.remove(0);

        for (int[] neighbourPos : neighboursPos) {
            Node neighbour = Nodes.getNode(neighbourPos);
            if (!neighbour.isBlocked() && (!neighbour.isVisited()
                    || neighbour.getDistance() > distance)) {
                neighbour.setPreviousNode(currentNode);
                neighbour.setDistance(distance);
                addList(pathHeads, neighbour);
                neighbour.markVisited();
            }
        }

        return searchRec(pathHeads.get(0), distance + 1);
    }


    /** Adds a node to given ArrayList in order making it a sorted array.
     * Nodes are compared by their distance to from the start point
     *
     * @param list List the node is added to
     * @param node node to be added to the arrayList
     */
    protected void addList(ArrayList<Node> list, Node node) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTotalWeight() > node.getTotalWeight()) {
                list.add(i, node);
                return;
            }
        }
        list.add(node);
    }
}
