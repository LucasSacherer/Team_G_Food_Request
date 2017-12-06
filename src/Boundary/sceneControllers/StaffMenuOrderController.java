package Boundary.sceneControllers;

import Controller.CartController;
import Controller.MenuController;
import Controller.RequestController;
import Controller.WorkerController;
import Database.DatabaseGargoyle;
import Entity.CartItem;
import Entity.FoodRequest;
import Entity.MenuItem;
import Entity.Node;
import Manager.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import sun.swing.MenuItemCheckIconFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class StaffMenuOrderController {
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    private NodeManager nodeManager = new NodeManager(databaseGargoyle);
    private FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
    private MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
    private WorkerManager workerManager = new WorkerManager(databaseGargoyle);
    private FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager,
            workerManager, menuItemManager, foodLogManager);

    /* Controllers */
    private MenuController menuController;
    private CartController cartController;
    private RequestController requestController;

    private JFXTextField selectQuantity;
    private JFXTextField menuItemOrder, itemPrice;
    private Label destination;
    private JFXTreeTableView<MenuItem> menuOrderTable;
    private JFXTreeTableView<CartItem> myOrderTable;
    private TreeTableColumn<MenuItem, String> foodItemColumn;
    private TreeTableColumn<CartItem,String> foodItemOrderColumn;
    private TreeTableColumn<MenuItem, Integer> priceColumn;
    private TreeTableColumn<CartItem,Integer> priceOrderColumn;

    private MenuItem item;
    private Node location;

    private CartItem cartItem;

    private TreeItem<MenuItem> menuRoot = new TreeItem<>();
    private TreeItem<CartItem> orderRoot = new TreeItem<>();
    private List<MenuItem> addedItems = new ArrayList<>();


    public StaffMenuOrderController(DatabaseGargoyle databaseGargoyle,
                                    NodeManager nodeManager, FoodLogManager foodLogManager, MenuItemManager menuItemManager, WorkerManager workerManager,
                                    FoodRequestManager foodRequestManager, JFXTextField selectQuantity, JFXTextField menuItemOrder, JFXTextField itemPrice,
                                    JFXTreeTableView<MenuItem> menuOrderTable, JFXTreeTableView<CartItem> myOrderTable,
                                    TreeTableColumn<MenuItem, String> foodItemColumn, TreeTableColumn<CartItem,String> foodItemOrderColumn,
                                    TreeTableColumn<MenuItem, Integer> priceColumn, TreeTableColumn<CartItem,Integer> priceOrderColumn,
                                    CartController cartController, RequestController requestController,Label destination) {
        this.databaseGargoyle = databaseGargoyle;
        this.nodeManager = nodeManager;
        this.foodLogManager = foodLogManager;
        this.menuItemManager = menuItemManager;
        this.workerManager = workerManager;
        this.foodRequestManager = foodRequestManager;
        this.selectQuantity = selectQuantity;
        this.menuItemOrder = menuItemOrder;
        this.itemPrice = itemPrice;
        this.menuOrderTable = menuOrderTable;
        this.myOrderTable = myOrderTable;
        this.foodItemColumn = foodItemColumn;
        this.foodItemOrderColumn = foodItemOrderColumn;
        this.priceColumn = priceColumn;
        this.priceOrderColumn = priceOrderColumn;
        this.cartController = cartController;
        this.requestController = requestController;
        this.destination = destination;
    }

    public void initialize(Label destination, MenuController menuController) {
        this.menuController = menuController;
        System.out.println(menuController);
        initializeMenuTable();
        initializeOrderTable();
        this.destination = destination;
    }

    private void initializeMenuTable() {

        for (MenuItem menuItem : menuController.getAvailableMenu()){
            menuRoot.getChildren().add(new TreeItem<>(menuItem));
        }

        foodItemColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getFoodName()));
        priceColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getPrice()));

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
            itemPrice.setText("" + selectedMenuItem.getValue().getPrice() + "$");

        }
    }


    private void initializeOrderTable() {

        for (CartItem cartItem : cartController.getItems() ) {
            orderRoot.getChildren().add(new TreeItem<>(cartItem));
        }
        foodItemOrderColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CartItem, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getFoodNameCart()));
        //TODO -> Return price instead of null
        priceOrderColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CartItem, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getQuantity()));

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


    public void addMenuItem() {
        TreeItem<MenuItem> selectedMenuItem = menuOrderTable.getSelectionModel().getSelectedItem();
        cartItem = new CartItem(selectedMenuItem.getValue().getFoodName(),Integer.parseInt(selectQuantity.getText()));
        cartController.addItemToCart(cartItem);
        orderRoot.getChildren().add(new TreeItem<>(cartItem));
        cancelMenuItem();
    }

    public void cancelMenuItem() {
        selectQuantity.setText(selectQuantity.getPromptText());
        menuItemOrder.setText(selectQuantity.getPromptText());
        itemPrice.setText(itemPrice.getPromptText());
        destination.setText("Destination");
    }

    public void checkoutRequest() {
        FoodRequest foodRequest = new FoodRequest(cartItem.getFoodNameCart(), LocalDateTime.now(),LocalDateTime.now(),"hello","This",
                nodeManager.getNode("GREST01201"),null,cartController.getItems());
        orderRoot.getChildren().clear();
        System.out.println(foodRequest.getName());
        System.out.println(foodRequest.getTimeCreated());
        System.out.println(foodRequest.getOrder());
        System.out.println(foodRequest.getNode());
        System.out.println(foodRequest.getAssignedWorker());
        System.out.println(foodRequest.getDescription());
        System.out.println(foodRequest.getType());
        System.out.println(foodRequest.getTimeCompleted());
        requestController.addRequest(foodRequest);
        cartController.clearItems();

        initializeOrderTable();
        cancelMenuItem();

    }

    public void deleteFoodItemFromCart() {
        TreeItem<CartItem> selectedMenuItem = myOrderTable.getSelectionModel().getSelectedItem();
        cartController.getItems().remove(selectedMenuItem);
        orderRoot.getChildren().remove(selectedMenuItem);
    }

    public void selectDietaryRestriction() {
    }

    public void setLocation(Node location) {
        this.location = location;
    }

    public void setLabelDestination(JFXTextField text){
        destination.setText(text.getText());
    }
}
