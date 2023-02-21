package com.example.pathfinding.Algorithms;

import com.example.pathfinding.Main;
import com.example.pathfinding.Node;
import com.example.pathfinding.Nodes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class DFS implements Runnable {
    private static final Stack<int[]> nodesStack = new Stack<>();
    private final int[] startPos;
    private final int[] finishPos;

    public DFS (int[] startPos, int[] finishPos) {
        this.startPos = startPos;
        this.finishPos = finishPos;
    }

    public void search() {
        nodesStack.push(startPos);
        Nodes.getNode(startPos).markVisited();
        searchRec(startPos, finishPos);
    }

    private boolean searchRec(int[] nodePos, int[] finishPos) {
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
                if (searchRec(neighbour, finishPos)) {
                    neighbourNode.markDiscovered();
                    return true;
                }
            }
        }
        nodesStack.pop();
        return false;
    }


    @Override
    public void run() {
        search();
    }
}
