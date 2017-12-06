package Boundary.sceneControllers;

import Controller.DirectoryController;
import Entity.FoodRequest;
import Entity.Node;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class MapDirectoryController {

    private FoodRequestHubController foodRequestHubController;
    private DirectoryController directoryController;
    private Node destination, origin;

    @FXML
    private JFXTextField directoryOrigin, directoryDestination;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;

    public MapDirectoryController(DirectoryController directoryController) {
        this.directoryController = directoryController;
    }

    public void setMainSceneController(FoodRequestHubController m) {
        this.foodRequestHubController = m;
    }

    @FXML
    public void initialize() {
        initializeDirectory();
        initializeDirectoryListeners();
        origin = null; // directoryController.getDirectory().get("Elevators").get(0); //TODO we want this to be the cafeteria
    }

    @FXML
    private void directoryNavigate() throws IOException {
//        mainSceneController.navigate(origin, destination);
        Stage stage = (Stage) directoryOrigin.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void resetOrigin() {
//        origin = directoryController.getDefaultNode();
        directoryOrigin.clear();
    }

    @FXML
    private void setElevatorOrigin() {
        origin = (Node) elevatorDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setRestroomOrigin() {
        origin = (Node) restroomDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setStairsOrigin() {
        origin = (Node) stairsDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setDepartmentsOrigin() {
        origin = (Node) deptDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setInfoDesksOrigin() {
        origin = (Node) infoDeskDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setLabsOrigin() {
        origin = (Node) labDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setExitOrigin() {
        origin = (Node) exitDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setShopsOrigin() {
        origin = (Node) shopsDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setNonMedicalOrigin() {
        origin = (Node) nonMedical.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setConferenceOrigin() {
        origin = (Node) conferenceDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    private void initializeDirectory() {
        elevatorDir.setItems(directoryController.getDirectory().get("Elevators"));
        restroomDir.setItems(directoryController.getDirectory().get("Restrooms"));
        stairsDir.setItems(directoryController.getDirectory().get("Stairs"));
        labDir.setItems(directoryController.getDirectory().get("Departments"));
        deptDir.setItems(directoryController.getDirectory().get("Labs"));
        infoDeskDir.setItems(directoryController.getDirectory().get("Information Desks"));
        conferenceDir.setItems(directoryController.getDirectory().get("Conference Rooms"));
        exitDir.setItems(directoryController.getDirectory().get("Exits/Entrances"));
        shopsDir.setItems(directoryController.getDirectory().get("Shops, Food, Phones"));
        nonMedical.setItems(directoryController.getDirectory().get("Non-Medical Services"));
    }

    private void initializeDirectoryListeners(){
        elevatorDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) elevatorDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        restroomDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) restroomDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        stairsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) stairsDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        labDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) labDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        deptDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) deptDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        infoDeskDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) infoDeskDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        conferenceDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) conferenceDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        exitDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) exitDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        shopsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) shopsDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        nonMedical.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) nonMedical.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
    }
}