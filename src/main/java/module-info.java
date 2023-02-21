module com.example.pathfinding {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pathfinding to javafx.fxml;
    exports com.example.pathfinding;
}