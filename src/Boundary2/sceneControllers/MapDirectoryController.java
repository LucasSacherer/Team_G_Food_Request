package Boundary2.sceneControllers;

import Controller2.DirectoryController;
import Entity2.Node;
import Manager2.NodeManager;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.stage.Stage;


import java.io.IOException;

public class MapDirectoryController {
    private StaffMenuOrderController m;
    private DirectoryController directoryController;
    private ObservableList directoryList;

    @FXML
    private JFXComboBox browser;

    @FXML
    private JFXListView listView;


    public MapDirectoryController(DirectoryController directoryController, StaffMenuOrderController staffMenuOrderController) {
        this.directoryController = directoryController;
    }

    public void setMainSceneController(StaffMenuOrderController m) {
        this.m = m;
    }

    @FXML
    private void initialize() {
        directoryList = FXCollections.observableArrayList(directoryController.getDirectory().keySet());
        browser.setItems(directoryList);
        browser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listView.setItems(directoryController.getDirectory().get(browser.getSelectionModel().getSelectedItem()));
            }
        });
        browser.getSelectionModel().select(0);
        listView.setItems(directoryController.getDirectory().get(browser.getSelectionModel().getSelectedItem()));
    }


    @FXML
    private void setDirectoryDestination() throws IOException {
        if (!listView.getSelectionModel().isEmpty())
            m.setLocation((Node) listView.getSelectionModel().getSelectedItem());
        JFXPopup popup = (JFXPopup) browser.getScene().getWindow();
        popup.hide();
    }

}