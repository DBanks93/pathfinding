package com.example.pathfinding;

import com.example.pathfinding.Algorithms.BFS;
import com.example.pathfinding.Algorithms.DFS;
import com.example.pathfinding.Algorithms.Dijkstra;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Class that stores all the nodes and performs operations upon them
 *
 * @author Daniel Banks
 * @version 1.2
 */
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

    /** Random */
    private static final Random random = new Random();

    /** Position of the start node. */
    private static int[] startNode;

    /** Position of the end node. */
    private static int[] endNode;

    /** Number of nodes visited */
    private static int nodesVisited = 0; // TODO: will be used in the gui

    private static int distance = -1; // TODO: will be used in the gui

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

    /**
     * Starts a search of the nodes based on a set Algorithm.
     * @param search Pathfinding algorithm name
     */
    public static void search(String search) {
        clearRoute();
        Thread searchThread = switch (search) {
            case "DFS" -> new Thread(new DFS());
            case "BFS" -> new Thread(new BFS());
            case "Dijkstra's" -> new Thread(new Dijkstra());
            default -> new Thread(new DFS());
        };
        searchThread.start();
    }

    /**
     * Resets all the nodes to default values.
     * @param resetAll if all nodes so be reset or all but blocked nodes
     */
    public static void resetNodes(boolean resetAll) {
        nodesVisited = 0;
        distance = -1;
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

    public static void clearRoute() {
        for (Node[] nodesRow : nodes) {
            for (Node node : nodesRow) {
                node.clearRoute();
            }
        }
    }

    /**
     * Creates a random start point.
     */
    public static void getRandomStartPoint() {
        startNode = new int[] {
                random.nextInt(NODES_PANE_WIDTH),
                random.nextInt(NODES_PANE_WIDTH)
        };
        nodes[startNode[0]][startNode[1]].setStartNode();
    }

    /**
     * Creates a random end point.
     */
    public static void getRandomEndPoint() {
        endNode = new int[] {
                random.nextInt(NODES_PANE_WIDTH -1),
                random.nextInt(NODES_PANE_WIDTH -1)
        };
        while (endNode == startNode) {
            endNode = new int[] {
                    random.nextInt(NODES_PANE_WIDTH -1),
                    random.nextInt(NODES_PANE_WIDTH -1)
            };
        }
        nodes[endNode[0]][endNode[1]].setEndNode();
    }

    /**
     * Gets a node
     * @param pos position of the node (int {x, y})
     * @return node
     */
    public static Node getNode(int[] pos) {
        return nodes[pos[0]][pos[1]];
    }

    /**
     * Gets the neighbours of a node
     * @param pos position of nodes to find neighbours (int {x, y})
     * @return ArrayList of positions (int {x, y})
     */
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

    /**
     * Increments the number of visited nodes
     */
    // TODO: will be used in the gui
    public static void addVisited() {
        ++nodesVisited;
        Main.setNodesVisited(nodesVisited);
    }

    /**
     * Gets the number of nodes visited
     * @return no. nodes visited
     */
    // TODO: will be used in the gui
    public static int noNodesVisited() {
        return nodesVisited;
    }

    /**
     * Gets the end node
     * @return node that's the end node
     */
    public static Node getEndNode() {
        return getNode(endNode);
    }

    /**
     * Gets the position of the start node/point
     * @return int[] {x, y}
     */
    public static int[] getStartNodePos() {
        return startNode;
    }

    public static int[] getNodePos(Node node) {
        for (int x = 0; x < NODES_PANE_WIDTH; x++) {
            for (int y = 0; y < NODES_PANE_WIDTH; y++) {
                if (node == nodes[x][y]) {
                    return new int[] {x, y};
                }
            }
        }
        return new int[] {0, 0};
    }

    /**
     * Shows the route path to get to the end node.
     * Traverses route in reversed order
     */
    public static void getRoute() {
        getRoute(getEndNode());
    }

    /**
     * Shows the route path to get to the end node.
     * Traverses route in reversed order
     * @param currentNode the last node in the route
     */
    private static void getRoute(Node currentNode) {
        if (!currentNode.isStartNode()) {
            currentNode.markDiscovered();
            getRoute(currentNode.getPreviousNode());
        }
    }
}
