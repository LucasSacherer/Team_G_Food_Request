package Boundary2.sceneControllers;

import Controller2.DirectoryController;
import Entity2.Node;
import Manager2.NodeManager;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class MapDirectoryController {


    private FoodRequestHubController foodRequestHubController;
    private StaffMenuOrderController staffMenuOrderController;
    private NodeManager nodeManager;
    private DirectoryController directoryController;
    private Node destination, origin;

    @FXML
    private JFXTextField directoryOrigin, directoryDestination;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;

    public MapDirectoryController(DirectoryController directoryController, NodeManager nodeManager, StaffMenuOrderController staffMenuOrderController) {
        this.directoryController = directoryController;
        this.nodeManager = nodeManager;
        this.staffMenuOrderController = staffMenuOrderController;

    }

    public void setMainSceneController(FoodRequestHubController m) {
        this.foodRequestHubController = m;
    }

    @FXML
    public void initialize() {
        initializeDirectory();
        initializeDirectoryListeners();
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
            directoryDestination.setText(destination.getNodeID());
        });
        restroomDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) restroomDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.getNodeID());
        });
        stairsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) stairsDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.getNodeID());
        });
        labDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) labDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.getNodeID());
        });
        deptDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) deptDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.getNodeID());
        });
        infoDeskDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) infoDeskDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.getNodeID());
        });
        conferenceDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) conferenceDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.getNodeID());
        });
        exitDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) exitDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.getNodeID());
        });
        shopsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) shopsDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.getNodeID());
        });
        nonMedical.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) nonMedical.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.getNodeID());
        });
    }
    @FXML
    public void setDestination (){
        //staffMenuOrderController.setLocation(destination);
        StaffMenuOrderController.location = destination;
        //staffMenuOrderController.setLabelDestination(directoryDestination);
        StaffMenuOrderController.destination.setText(directoryDestination.getText());
        Stage stage = (Stage) directoryDestination.getScene().getWindow();
        stage.close();

    }
}