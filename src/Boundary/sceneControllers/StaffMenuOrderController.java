package Boundary.sceneControllers;

import Database.DatabaseGargoyle;
import Entity.MenuItem;
import Entity.Node;
import Manager.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class StaffMenuOrderController {
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    private NodeManager nodeManager = new NodeManager(databaseGargoyle);
    private FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
    private MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
    private WorkerManager workerManager = new WorkerManager(databaseGargoyle);
    private FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager,
            workerManager, menuItemManager, foodLogManager);

    private JFXComboBox selectQuantityCombo;
    private JFXTextField menuItemOrder, itemPrice, destination;
    private JFXTreeTableView<MenuItem> menuOrderTable;
    private JFXTreeTableView<MenuItem> myOrderTable;
    private TreeTableColumn<MenuItem, String> foodItemColumn, foodItemOrderColumn;
    private TreeTableColumn<MenuItem, Integer> priceColumn, priceOrderColumn;

    private MenuItem item;

    private TreeItem<MenuItem> menuRoot = new TreeItem<>(item);
    private TreeItem<MenuItem> orderRoot = new TreeItem<>();
    private List<MenuItem> addedItems = new ArrayList<>();


    public StaffMenuOrderController(DatabaseGargoyle databaseGargoyle,
                                    NodeManager nodeManager, FoodLogManager foodLogManager, MenuItemManager menuItemManager, WorkerManager workerManager,
                                    FoodRequestManager foodRequestManager, JFXComboBox selectQuantityCombo, JFXTextField menuItemOrder, JFXTextField itemPrice, JFXTextField destination,
                                    JFXTreeTableView<MenuItem> menuOrderTable, JFXTreeTableView<MenuItem> myOrderTable,
                                    TreeTableColumn<MenuItem, String> foodItemColumn, TreeTableColumn<MenuItem, String> foodItemOrderColumn,
                                    TreeTableColumn<MenuItem, Integer> priceColumn, TreeTableColumn<MenuItem, Integer> priceOrderColumn) {
        this.databaseGargoyle = databaseGargoyle;
        this.nodeManager = nodeManager;
        this.foodLogManager = foodLogManager;
        this.menuItemManager = menuItemManager;
        this.workerManager = workerManager;
        this.foodRequestManager = foodRequestManager;
        this.selectQuantityCombo = selectQuantityCombo;
        this.menuItemOrder = menuItemOrder;
        this.itemPrice = itemPrice;
        this.destination = destination;
        this.menuOrderTable = menuOrderTable;
        this.myOrderTable = myOrderTable;
        this.foodItemColumn = foodItemColumn;
        this.foodItemOrderColumn = foodItemOrderColumn;
        this.priceColumn = priceColumn;
        this.priceOrderColumn = priceOrderColumn;
    }

    public void initialize() {
        initializeMenuTable();
        initializeOrderTable();

    }

    private void initializeMenuTable() {
        menuItemManager.update();

        for (MenuItem menuItem : menuItemManager.getMenuItems()) {
            menuRoot.getChildren().add(new TreeItem<MenuItem>(menuItem));
        }
        foodItemColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getFoodName()));
        //TODO -> Return price instead of null
        priceColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, Integer> param) -> null);

        menuOrderTable.setRoot(menuRoot);
        menuOrderTable.setShowRoot(false);
        menuOrderTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                onEdit();
            }
        });

    }

    private void onEdit() {
        if (menuOrderTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<MenuItem> selectedMenuItem = menuOrderTable.getSelectionModel().getSelectedItem();
            menuItemOrder.setText(selectedMenuItem.getValue().getFoodName());
            //TODO -> Return price instead of null
            itemPrice.setText("YOU HAVE NO PRICE");

        }
    }

    private void initializeOrderTable() {

        for (MenuItem menuItem : addedItems) {
            orderRoot.getChildren().add(new TreeItem<MenuItem>(menuItem));
        }
        foodItemOrderColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getFoodName()));
        //TODO -> Return price instead of null
        priceOrderColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, Integer> param) -> null);

        myOrderTable.setRoot(orderRoot);
        myOrderTable.setShowRoot(false);
        myOrderTable.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {
                onEditOrder();
            }
        });

    }

    private void onEditOrder() {
    }


    public void selectQuantity() {
    }

    public void addMenuItem() {
    }

    public void cancelMenuItem() {
    }

    public void checkoutRequest() {
    }

    public void destinationPopup() {
    }

    public void deleteFoodItemFromCart() {
    }

    public void selectDietaryRestriction() {
    }

    public void information() {
    }
}
