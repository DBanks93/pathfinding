package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Main;
import com.example.pathfinding.Node;
import com.example.pathfinding.Nodes;

import java.util.ArrayList;

public class Dijkstra implements Runnable {

    /** Array of nodes who are the head of a graph. */
    private final ArrayList<Node> pathsHeads = new ArrayList<>();

    /** Visited nodes. */
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
    private void search() {
        Node startNode = Nodes.getNode(Nodes.getStartNodePos());
        visitedNodes.add(startNode);
        pathsHeads.add(startNode);
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

        if (pathsHeads.isEmpty()) {
            return false;
        }

        pathsHeads.remove(0);
        currentNode.markVisited();
        ArrayList<int[]> neighbours = Nodes.getNeighbours(Nodes.getNodePos(currentNode));
        for (int[] neighbourPos : neighbours) {
            Node neighbourNode = Nodes.getNode(neighbourPos);
            if (!visitedNodes.contains(neighbourNode)
                    || neighbourNode.getDistance() > distance) {
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

    /** Adds a node to given ArrayList in order making it a sorted array.
     * Nodes are compared by their distance to from the start point
     *
     * @param list List the node is added to
     * @param node node to be added to the arrayList
     */
    private void addList(ArrayList<Node> list, Node node) {
        for (int i = 0; i < list.size(); i++) {
          if (list.get(i).getDistance() > node.getDistance()) {
              list.add(i, node);
              return;
          }
        }
        list.add(node);
    }
}
