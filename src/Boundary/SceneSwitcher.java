package Boundary;

import Boundary.sceneControllers.MapDirectoryController;
import Boundary.sceneControllers.StaffIntoPopupController;
import Controller.DirectoryController;
import Database.DatabaseGargoyle;
import Manager.NodeManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    private final String adminEditMenuLoc = "/boundary/fxml/adminEditMenu.fxml";
    private final String foodRequestHubLoc = "/boundary/fxml/foodRequestHub.fxml";
    private final String mapDirectoryLoc = "/boundary/fxml/mapDirectory.fxml";
    private final String reportsLoc = "/boundary/fxml/reports.fxml";
    private final String staffInfoPopupLoc = "/boundary/fxml/staffInfoPopup.fxml";
    private final String staffMenuOrderLoc = "/boundary/fxml/staffMenuOrder.fxml";


    private final StaffIntoPopupController staffInfoPopupController;

    private final MapDirectoryController mapDirectoryController;

    public SceneSwitcher(StaffIntoPopupController staffIntoPopupController, MapDirectoryController mapDirectoryController) {
        this.staffInfoPopupController = staffIntoPopupController;
        this.mapDirectoryController = mapDirectoryController;
    }

//

    public void switchScene(GodController g, Pane from, String to) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(to));
        loader.setController(g);
        AnchorPane mainScene = (AnchorPane) loader.load();
        Scene primaryScene = from.getScene();
        primaryScene.setRoot(mainScene);
    }

    public void toAdminEditMenu(GodController g, Pane from) throws IOException {
        switchScene(g, from, adminEditMenuLoc);
    }

    public void toFoodRequestHub(GodController g, Pane from) throws IOException {
        switchScene(g, from, foodRequestHubLoc);
    }

//    public void toMapDirectory(GodController g, Pane from) throws IOException {
//        switchScene(g, from, mapDirectoryLoc);
//    }

    public void toReports(GodController g, Pane from) throws IOException {
        switchScene(g, from, reportsLoc);
    }

//    public void toStaffInfoPopup(GodController g, Pane from) throws IOException {
//        switchScene(g, from, staffInfoPopupLoc);
//    }

    public void toStaffMenuOrder(GodController g, Pane from) throws IOException {
        switchScene(g, from, staffMenuOrderLoc);
    }

    public void toMapDirectoryPopup( ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mapDirectoryLoc));
        fxmlLoader.setController(mapDirectoryController);
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Directory");
        stage.setScene(new Scene(root2, 600, 300));
        stage.show();
    }

    public void toMenuInfoPopup( ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(staffInfoPopupLoc));
        fxmlLoader.setController(staffInfoPopupController);
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Menu Info");
        stage.setScene(new Scene(root1, 1280, 720));
        stage.show();
    }
}

