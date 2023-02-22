package com.example.pathfinding;

import javafx.application.Application;
import javafx.collections.FXCollections;
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

/**
 * Main class for program
 *
 * @author Daniel Banks
 * @version 1.12
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

    private final Label visitedLabel = new Label("Nodes Visited: ");
    private final Label vistiedNoLabel = new Label("");

    private String algorithmSelected = "DFS";

    /**
     * Stats the GUI (JavaFx stage)
     * @param stage JavaFx Stage
     */
    @Override
    public void start(Stage stage) {
        algSelect.setValue("DFS");

        resetButton.setOnAction(e -> reset());

        startButton.setOnAction(e -> setup());

        searchButton.setOnAction(e -> search());

        speedSlider.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        speedDelay = newValue.intValue() * 10L);

        algSelect.getSelectionModel().selectedIndexProperty()
                .addListener((observableValue, number, newNumber) -> {
            Nodes.clearRoute();
            algorithmSelected = algorithms[newNumber.intValue()];
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

    // TODO: add number of nodes visited text field
    public void setNodesVisited(int noVisited) {
        vistiedNoLabel.setText(String.valueOf(noVisited));
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
            resultsPane.getChildren().remove(visitedLabel);
            resultsPane.getChildren().remove(vistiedNoLabel);
            canSearch = false;
        }
    }

    /**
     * Sets up the nodes ready for a search to occur.
     */
    private void setup() {
        Nodes.resetNodes(false);
        Nodes.getRandomStartPoint();
        Nodes.getRandomEndPoint();
        if (!canSearch) {
            controlsMenuPane.getChildren().add(searchButton);
            resultsPane.add(visitedLabel, 0, 1);
            resultsPane.add(vistiedNoLabel, 1, 1);
            canSearch = true;
        }
    }

    /**
     * Starts a pathfinding Algorithm.
     */
    private void search() {
        Nodes.search(algorithmSelected);
    }
}