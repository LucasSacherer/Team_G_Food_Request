package Boundary2;

import Boundary2.sceneControllers.MapDirectoryController;
import Boundary2.sceneControllers.StaffIntoPopupController;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class SceneSwitcher {

    private final String adminEditMenuLoc = "/Boundary2/fxml/adminEditMenu.fxml";
    private final String foodRequestHubLoc = "/Boundary2/fxml/foodRequestHub.fxml";
    private final String mapDirectoryLoc = "/Boundary2/fxml/mapDirectory.fxml";
    private final String reportsLoc = "/Boundary2/fxml/reports.fxml";
    private final String staffInfoPopupLoc = "/Boundary2/fxml/staffInfoPopup.fxml";
    private final String staffMenuOrderLoc = "/Boundary2/fxml/staffMenuOrder.fxml";


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
        stage.setScene(new Scene(root2, 1280, 720));
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

    public void makeFadeOut(Pane pane) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000000));
        fadeTransition.setNode(pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

    }
    public void prepareSlideMenuAnimation( Pane pane) {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), pane);
        openNav.setToX(0);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), pane);
//        button.setOnAction((ActionEvent evt)->{
//            if(pane.getTranslateX()!=0){
//                openNav.play();
//            }else{
//                closeNav.setToX(-(pane.getWidth()));
//                closeNav.play();
//            }
//        });
        openNav.play();
    }
}


