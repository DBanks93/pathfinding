package com.example.pathfinding;

import com.example.pathfinding.Algorithms.DFS;
import com.example.pathfinding.Algorithms.SortingAlg;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.IOException;

public class Main extends Application {

    /**
     * Window Width.
     */
    public static final int WIDTH = 1280;

    /**
     * Window Height.
     */
    public static final int HEIGHT = 720;

    public static boolean mousePressed = false;

    private static long speedDelay = 0;

    /**
     * Main root/pane of the window.
     */
    private final GridPane root = new GridPane();

    /**
     * Javafx scene.
     */
    private final Scene scene = new Scene(root, WIDTH, HEIGHT);

    /**
     * Pane where the pathfinding will happen
     */
    private final GridPane pathfindingPane = new GridPane();

    private final TilePane controlsMenuPane = new TilePane();
    private final VBox menuPane = new VBox(controlsMenuPane);
    private final Button resetButton = new Button("Reset");
    private final Button startButton = new Button("Start");
    private final Button searchButton = new Button("Search");
    boolean canSearch = false;
    private final ChoiceBox<String> algSelect =
            new ChoiceBox<String>(FXCollections.observableArrayList("DFS",
                    "BFS",
                    "Dijkstra's",
                    "A*"));

    private final GridPane resultsPane = new GridPane();

    private final Slider speedSlider = new Slider(0, 150, 0);

    @Override
    public void start(Stage stage) throws IOException {
        algSelect.setValue("DFS");

        resetButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        reset();
                    }
                }
        );

        startButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        setup();
                    }
                }
        );

        searchButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        search();
                    }
                }
        );

        speedSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        speedDelay = newValue.intValue() * 10;
                    }
                });

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

    public static void main(String[] args) {
        launch();
    }


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
        resultsPane.add(new Label("Speed:"), 0, 0);
        resultsPane.add(speedSlider, 1, 0);
        menuPane.getChildren().add(resultsPane);
        controlsMenuPane.getChildren().add(startButton);
        controlsMenuPane.getChildren().add(resetButton);
        controlsMenuPane.getChildren().add(algSelect);
    }

    /**
     * resets the path finding
     */
    private void reset() {
        Nodes.resetNodes(true);
        if (canSearch) {
            controlsMenuPane.getChildren().remove(searchButton);
            canSearch = false;
        }
    }

    private void setup() {
        Nodes.resetNodes(false);
        Nodes.getRandomStartPoint();
        Nodes.getRandomEndPoint();
        if (!canSearch) {
            controlsMenuPane.getChildren().add(searchButton);
            canSearch = true;
        }
    }

    private void search() {
        Nodes.search("DFS");
    }
}