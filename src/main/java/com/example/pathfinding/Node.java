package com.example.pathfinding;

import com.example.pathfinding.Algorithms.AStar;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * Class that represents a "Tile" on the GUI.
 *
 * @author Daniel Banks
 * @version 1.3.1
 */
public class Node {

    /** Physical rectangle that's displayed on the GUI, */
    private final Rectangle nodeRect;

    /** bool to say if the node is a blocked node. */
    private boolean isBlocked = false;

    /** bool if the node has been visited by the pathfinding algorithm. */
    private boolean isVisited = false;

    /** bool to say if the node is the start node. */
    private boolean isStartNode = false;

    /** bool to say if the node is the end node. */
    private boolean isEndNode = false;

    /**
     * The previous node in the node graph.
     * (Isn't used in all pathfinding)
     */
    private Node previousNode;

    /**
     * Distance from start node
     * (Not used in all pathfinding)
     */
    private int distance = 0;

    /**
     * Creates a node.
     * @param nodeRect the JavaFX Rectangle that's draw on the GUI
     */
    public Node(Rectangle nodeRect) {
        this.nodeRect = nodeRect;
        nodeRect.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            if (Main.mousePressed) {
                blockTile();
            }
        });
    }

    /**
     * Resets the node back to the default colour.
     */
    public void resetNode() {
        isBlocked = false;
        isVisited = false;
        isStartNode = false;
        isEndNode = false;
        distance = 0;
        nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.DEFAULT));
    }

    /**
     * Resets the node iff it's not a blocked node.
     */
    public void restStartStopNode() {
        distance = 0;
        isVisited = false;
        if (!isBlocked) {
            resetNode();
        }
    }

    /**
     * Clears the route of a previous pathfinding algorithm.
     */
    public void clearRoute() {
        if (!isBlocked && !isEndNode && !isStartNode) {
            nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.DEFAULT));
        }
        distance = 0;
        isVisited = false;
    }

    /** Sets the colour of the node as a start point. */
    public void setStartNode() {
        isStartNode = true;
        nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.START_NODE));
    }

    /** Sets the colour of the node as the end point. */
    public void setEndNode() {
        isEndNode = true;
        nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.FINISH_POINT));
    }

    /**
     * Sets the tile/node as a blocked tile.
     */
    public void blockTile() {
        nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.BLOCKED));
        isBlocked = true;
    }

    /**
     * Gets if the tile/node is blocked.
     * @return if blocked
     */
    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * Gets if the tile/node is visited.
     * @return if visitied
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * Marks a node as visited by the pathfinding algorithm.
     */
    public void markVisited() {
        isVisited = true;
        if (!isStartNode && !isEndNode) {
            nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.DISCOVERED));
        }
    }

    /**
     * Marks a node as traversed by the pathfinding algorithm.
     */
    public void markDiscovered() {
        if (!isStartNode && !isEndNode) {
            nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.TRAVERSED));
        }
    }

    /**
     * Gets if the tile/node is the end node.
     * @return is end node
     */
    public boolean isEndNode() {
        return isEndNode;
    }

    /**
     * Gets if the node is the start node.
     * @return if node is start node
     */
    public boolean isStartNode() {
        return isStartNode;
    }

    /**
     * Sets the previous node.
     * @param node previous node
     */
    public void setPreviousNode(Node node) {
        previousNode = node;
    }

    /**
     * Gets the previous Node.
     * @return previous node
     */
    public Node getPreviousNode() {
        return previousNode;
    }

    /**
     * Sets distance to the start node
     * @param distance to start node
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Gets distance to the start node
     * @return distance to start node
     */
    public int getDistance() {
        return distance;
    }


    /**
     * Gets the 'weight' of a node.
     * Weight represents how close the node is to the item or the cost to start at that tile.
     * The lower, the better.
     * Higher weight means it's not worth as much to look at, and it's more of a last resort.
     *
     * Calculated by adding distance from root to distance to item.
     * @return double 'Weight' of the tile
     */
    public double getTotalWeight() {
        return  distance + AStar.getDistanceWeight() * Nodes.distanceToEnd(this);
    }
}
