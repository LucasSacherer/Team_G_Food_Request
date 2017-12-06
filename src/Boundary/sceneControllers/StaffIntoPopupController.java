package Boundary.sceneControllers;

import Controller.MenuController;
import Database.DatabaseGargoyle;
import Entity.MenuItem;
import Manager.MenuItemManager;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

public class StaffIntoPopupController {
    private JFXTreeTableView<MenuItem> foodInfoTable;
    private TreeTableColumn<MenuItem, String> foodItemInfoColumn;
    private TreeTableColumn<MenuItem, Integer> nutritionColumn;
    private TreeTableColumn<MenuItem, String> foodInfoDescription;
    private MenuController menuController;

    DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
    MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);

    TreeItem<MenuItem> foodInfoRoot = new TreeItem<>();


    public StaffIntoPopupController(JFXTreeTableView<MenuItem> foodInfoTable, TreeTableColumn<MenuItem, String> foodItemInfoColumn,
                                    TreeTableColumn<MenuItem, Integer> nutritionColumn, TreeTableColumn<MenuItem, String> foodInfoDescription,
                                    DatabaseGargoyle databaseGargoyle, MenuItemManager menuItemManager, MenuController menuController) {
        this.foodInfoTable = foodInfoTable;
        this.foodItemInfoColumn = foodItemInfoColumn;
        this.nutritionColumn = nutritionColumn;
        this.foodInfoDescription = foodInfoDescription;
        this.databaseGargoyle = databaseGargoyle;
        this.menuItemManager = menuItemManager;
        this.menuController = menuController;
    }


    public void initializeStaffInfo() {

        for (MenuItem menuItem : menuController.getAvailableMenu()) {
            foodInfoRoot.getChildren().add(new TreeItem<MenuItem>(menuItem));

        }
        foodItemInfoColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getFoodName()));
        nutritionColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getCalories()));
        foodInfoDescription.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getDescription()));


        foodInfoTable.setRoot(foodInfoRoot);
        foodInfoTable.setShowRoot(false);

    }
}

