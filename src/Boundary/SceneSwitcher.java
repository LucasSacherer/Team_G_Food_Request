package Boundary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SceneSwitcher {

    private final String adminEditMenuLoc = "/boundary/fxml/adminEditMenu.fxml";
    private final String foodRequestHubLoc = "/boundary/fxml/foodRequestHub.fxml";
    private final String mapDirectoryLoc = "/boundary/fxml/mapDirectory.fxml";
    private final String reportsLoc = "/boundary/fxml/reports.fxml";
    private final String staffInfoPopupLoc = "/boundary/fxml/staffInfoPopup.fxml";
    private final String staffMenuOrderLoc = "/boundary/fxml/staffMenuOrder.fxml";

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

    public void toMapDirectory(GodController g, Pane from) throws IOException {
        switchScene(g, from, mapDirectoryLoc);
    }

    public void toReports(GodController g, Pane from) throws IOException {
        switchScene(g, from, reportsLoc);
    }

    public void toStaffInfoPopup(GodController g, Pane from) throws IOException {
        switchScene(g, from, staffInfoPopupLoc);
    }

    public void toStaffMenuOrder(GodController g, Pane from) throws IOException {
        switchScene(g, from, staffMenuOrderLoc);
    }
}

