package com.example.pathfinding;

import com.example.pathfinding.Algorithms.DFS;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Nodes {
    /** Number of nodes. */
    public static final int NO_NODES = 1000;

    /** Width (px) of the nodes */
    public static final int NODES_WIDTH = 20;

    /** Colours for all the states that a node will be in. */
    public static HashMap<NodeState, Color> NODE_STATE_COLOURS = new HashMap<>() {{
        put(NodeState.DEFAULT, Color.WHITE);
        put(NodeState.START_NODE, Color.BLUE);
        put(NodeState.DISCOVERED, Color.YELLOW);
        put(NodeState.TRAVERSED, Color.GREEN);
        put(NodeState.FINISH_POINT, Color.RED);
        put(NodeState.BLOCKED, Color.BLACK);
    }};

    /** Width of the nodes' pane. */
    private static final int NODES_PANE_WIDTH = (int) Math.sqrt(NO_NODES);

    /** All the nodes in the scene to be used for pathfinding. */
    private static final Node[][] nodes =
            new Node[NODES_PANE_WIDTH][NODES_PANE_WIDTH];

    private static final Random random = new Random();

    private static int[] startNode;

    private static int[] endNode;

    /**
     * initialises all the nodes.
     * @param nodesPane GridPane where the nodes will go
     */
    public static void initNodes(GridPane nodesPane) {
        Rectangle nodeRect;
        for (int y = 0; y < NODES_PANE_WIDTH; y++) {
            for (int x = 0; x < NODES_PANE_WIDTH; x++) {
                nodeRect = new Rectangle(x, y, NODES_WIDTH, NODES_WIDTH);
                nodeRect.setFill(NODE_STATE_COLOURS.get(NodeState.DEFAULT));
                nodeRect.setStrokeWidth(1);
                nodeRect.setStroke(Color.BLACK);
                nodes[x][y] = new Node(nodeRect);
                nodesPane.add(nodeRect, x, y);
            }
        }
    }

    public static void search(String search) {
        Thread searchThread;
        switch (search) {
            case "DFS":
                DFS dfs = new DFS(startNode, endNode);
                searchThread = new  Thread(dfs);
            default:
                DFS woop = new DFS(startNode, endNode);
                searchThread = new  Thread(woop);
        }

        searchThread.start();

    }

    public static void resetNodes(boolean resetAll) {
        for (Node[] nodesRow : nodes) {
            for (Node node : nodesRow) {
                if (resetAll) {
                    node.resetNode();
                } else {
                    node.restStartStopNode();
                }
            }
        }
    }

    public static void getRandomStartPoint() {
        startNode = new int[] {
                random.nextInt(NODES_PANE_WIDTH),
                random.nextInt(NODES_PANE_WIDTH)
        };
        nodes[startNode[0]][startNode[1]].setStartNode();
    }

    public static void getRandomEndPoint() {
        endNode = new int[] {
                random.nextInt(NODES_PANE_WIDTH),
                random.nextInt(NODES_PANE_WIDTH)
        };
        while (endNode == startNode) {
            endNode = new int[] {
                    random.nextInt(NODES_PANE_WIDTH),
                    random.nextInt(NODES_PANE_WIDTH)
            };
        }
        nodes[endNode[0]][endNode[1]].setEndNode();
    }

    public static Node getNode(int[] pos) {
        return nodes[pos[0]][pos[1]];
    }

    public static ArrayList<int[]> getNeighbours(int[] pos) {
        ArrayList<int[]> xNeighbours = new ArrayList<>();
        if (pos[0] != 0) {
            xNeighbours.add(new int[] {pos[0] -1, pos[1]});
        }
        if (pos[0] != NODES_PANE_WIDTH -1) {
            xNeighbours.add(new int[] {pos[0] +1, pos[1]});
        }
        if (pos[1] != 0) {
            xNeighbours.add(new int[] {pos[0], pos[1] -1});
        }
        if (pos[1] != NODES_PANE_WIDTH -1) {
            xNeighbours.add(new int[] {pos[0], pos[1] +1});
        }

        return xNeighbours;
    }
}
