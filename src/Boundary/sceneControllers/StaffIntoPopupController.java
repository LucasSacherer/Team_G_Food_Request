package Boundary.sceneControllers;

import Entity.MenuItem;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;

public class StaffIntoPopupController {

    @FXML
    private JFXTreeTableView<MenuItem> foodMenu;

    @FXML
    private TreeTableColumn<MenuItem, String> foodItemName;

    @FXML
    private TreeTableColumn<MenuItem, Integer> foodItemNutritionFacts;

    @FXML
    private TreeTableColumn<MenuItem, String> foodItemDescription;


    public StaffIntoPopupController(){}
}
