package com.example.pathfinding;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Node {

    /** Physical rectangle that's displayed on the GUI, */
    private final Rectangle nodeRect;

    private boolean isBlocked = false;

    private boolean isVisited = false;

    private boolean isStartNode = false;

    private boolean isEndNode = false;

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

    public void restStartStopNode() {
        isBlocked = false;
        isVisited = false;
        if (!isBlocked) {
            resetNode();
        }
    }

    /** Sets the colour of the node as a start point */
    public void setStartNode() {
        isStartNode = true;
        nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.START_NODE));
    }

    /** Sets the colour of the node as the end point */
    public void setEndNode() {
        isEndNode = true;
        nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.FINISH_POINT));
    }

    public void blockTile() {
        nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.BLOCKED));
        isBlocked = true;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void markVisited() {
        isVisited = true;
        if (!isStartNode && !isEndNode) {
            nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.DISCOVERED));
        }
    }

    public void markDiscovered() {
        if (!isStartNode && !isEndNode) {
            nodeRect.setFill(Nodes.NODE_STATE_COLOURS.get(NodeState.TRAVERSED));
        }
    }

    public boolean isEndNode() {
        return isEndNode;
    }
}
