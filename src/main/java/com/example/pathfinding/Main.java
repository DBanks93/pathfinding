package com.example.pathfinding;

import com.example.pathfinding.Algorithms.AStar;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main class for program
 *
 * @author Daniel Banks
 * @version 1.3
 */
public class Main extends Application {

    /**
     * Window Width.
     */
    public static final int WIDTH = 1280;

    /**
     * Window Height.
     */
    public static final int HEIGHT = 720;

    public static boolean mousePressed = false; // NOT IMPLEMENTED YET

    /** Delay between each node being searched. */
    private static long speedDelay = 0;

    /**
     * Array of possible algorithms names
     */
    private static final String[] algorithms = {
            "DFS",
            "BFS",
            "Dijkstra's",
            "A*"
    };

    /** String for the number of visited nodes output*/
    private static final String VISITED_STRING = "Nodes Visited: %o";

    /** String for the distance output*/
    private static final String DISTANCE_STRING = "Distance:     %o";

    /** Main root/pane of the window. */
    private final GridPane root = new GridPane();

    /** Javafx scene. */
    private final Scene scene = new Scene(root, WIDTH, HEIGHT);

    /** Pane where the pathfinding will happen. */
    private final GridPane pathfindingPane = new GridPane();

    private final TilePane controlsMenuPane = new TilePane();
    private final VBox menuPane = new VBox(controlsMenuPane);
    private final Button resetButton = new Button("Reset");
    private final Button startButton = new Button("Setup");
    private final Button searchButton = new Button("Search");
    boolean canSearch = false;
    private final ChoiceBox<String> algSelect =
            new ChoiceBox<>(FXCollections.observableArrayList(algorithms));

    private final GridPane resultsPane = new GridPane();

    /** slider that controls the speed of the algorithm
     * value in slider is in milliseconds
     */
    private final Slider speedSlider = new Slider(0, 100, 0);
    private final Label speedLabel = new Label("Speed: ");

    private final Label vistiedNoLabel = new Label("Nodes Visited");

    private final Label distanceLabel = new Label("Distance: ");

    /**
     *  All the nodes that'll appear on the results nodes.
     *  (Only the nodes that appear for ALL pathfinding algorithms)
     */
    private final ArrayList<Node> resultNodes = new ArrayList<>() {{
        add(vistiedNoLabel);
        add(distanceLabel);
    }};

    /** Map of javaFx nodes to their position of the results grid. */
    HashMap<Node, int[]> resultsNodesPos = new HashMap<>() {{
        put(vistiedNoLabel, new int[] {1, 1});
        put(distanceLabel, new int[] {1, 2});
    }};

    private final Slider disWeightSlider = new Slider(0, 50, 0);
    private final Label disWeightLabel = new Label("Distance Weight: ");

    /** Javafx nodes that are used for the current selected algorithm */
    private final ArrayList<Node> algorithmFxNodes = new ArrayList<>();

    /** current selected pathfinding algorithm */
    private String algorithmSelected = "DFS";

    /**
     * Stats the GUI (JavaFx stage)
     * @param stage JavaFx Stage
     */
    @Override
    public void start(Stage stage) {
        Nodes.setMain(this);

        algSelect.setValue("DFS");

        resetButton.setOnAction(e -> reset());

        startButton.setOnAction(e -> setup());

        searchButton.setOnAction(e -> search());

        speedSlider.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        speedDelay = newValue.intValue() * 10L);

        disWeightSlider.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        AStar.setDistanceWeight(newValue.intValue() / 10.));

        algSelect.getSelectionModel().selectedIndexProperty()
                .addListener((observableValue, number, newNumber) -> {
            Nodes.clearRoute();
            algorithmSelected = algorithms[newNumber.intValue()];
            algUI();
        });

        speedSlider.setMinWidth(400);


        initPathFindingPane();
        initMenuPane();

        root.add(pathfindingPane, 0, 0);
        root.add(menuPane, 1, 0);


        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SHIFT) {
                mousePressed = true;
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.SHIFT) {
                mousePressed = false;
            }
        });

    }

    /**
     * Main method
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Sets the number of nodes visited on the GUI.
     * @param noVisited number of nodes visited by the path fining algorithm
     */
    public void setNodesVisited(int noVisited) {
        Platform.runLater(() -> vistiedNoLabel.setText(
                String.format(VISITED_STRING, noVisited)));
    }

    /**
     * Sets the path distance from the start to end node on the GUI.
     * @param distance distance of the path
     */
    public void setDistance(int distance) {
        Platform.runLater(() -> distanceLabel.setText(
                String.format(DISTANCE_STRING, distance)));
    }

    /**
     * Gets how long the delay between each node being traversed.
     * @return speedDelay
     */
    public static long getSpeedDelay() {
        return speedDelay;
    }

    /**
     * Initialises and adds all the relevant nodes to the nodes grid.
     */
    private void initPathFindingPane() {
        Nodes.initNodes(pathfindingPane);
    }

    /**
     * Initialises and adds all the relevant nodes to the menu pane.
     */
    private void initMenuPane() {
        speedLabel.setMinHeight(50);
        speedLabel.setMinWidth(50);
        resultsPane.add(speedLabel, 0, 0);
        resultsPane.add(speedSlider, 1, 0);
        menuPane.getChildren().add(resultsPane);
        controlsMenuPane.getChildren().add(startButton);
        controlsMenuPane.getChildren().add(resetButton);
        controlsMenuPane.getChildren().add(algSelect);
    }

    /**
     * resets the path finding.
     */
    private void reset() {

        Nodes.resetNodes(true);
        if (canSearch) {
            controlsMenuPane.getChildren().remove(searchButton);
            for (Node resultsNode : resultNodes) {
                resultsPane.getChildren().remove(resultsNode);
            }
            canSearch = false;
        }
    }

    /**
     * Sets up the nodes ready for a search to occur.
     * (Including the GUI)
     */
    private void setup() {
        Nodes.resetNodes(false);
        Nodes.getRandomStartPoint();
        Nodes.getRandomEndPoint();
        if (!canSearch) {
            controlsMenuPane.getChildren().add(searchButton);

            for (Node resultsNode : resultNodes) {
                int[] nodePos = resultsNodesPos.get(resultsNode);
                resultsPane.add(resultsNode, nodePos[0],
                        nodePos[1]);
            }
            //resultsPane.add(visitedLabel, 0, 1);
            //resultsPane.add(vistiedNoLabel, 1, 1);
            canSearch = true;
        }
    }

    /**
     * Starts a pathfinding Algorithm.
     */
    private void search() {
        Nodes.search(algorithmSelected);
    }

    /**
     * Adds all the JavaFx nodes that are relevant for each algorithm.
     */
    private void algUI() {
        for (Node fxNode : algorithmFxNodes) {
            resultsPane.getChildren().remove(fxNode);
        }
        algorithmFxNodes.clear();

        if (algorithmSelected.equals("A*")) {
            resultsPane.add(disWeightLabel, 0, 3);
            resultsPane.add(disWeightSlider, 1, 3);
            algorithmFxNodes.add(disWeightLabel);
            algorithmFxNodes.add(disWeightSlider);
        }
    }
}