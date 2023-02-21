package com.example.pathfinding;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * Class that represents a "Tile" on the GUI.
 *
 * @author Daniel Banks
 * @version 1.0
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
     * Creates a node.
     * @param nodeRect the JavaFX Rectangle that's draw on the GUI
     */
    public Node(Rectangle nodeRect) {
        this.nodeRect = nodeRect;
        nodeRect.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Main.mousePressed) {
                    blockTile();
                }
            }
        });
    }

    /**
     * Resets the node back to the default colour
     */
    public void resetNode() {
        isBlocked = false;
        isVisited = false;
        isStartNode = false;
        isEndNode = false;
        nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.DEFAULT));
    }

    /**
     * Resets the node iff it's not a blocked node.
     */
    public void restStartStopNode() {
        isBlocked = false;
        isVisited = false;
        if (!isBlocked) {
            resetNode();
        }
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
}
