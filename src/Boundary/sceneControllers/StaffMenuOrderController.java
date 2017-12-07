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
import javafx.scene.control.*;
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
    private JFXComboBox dietaryRestrictionsCombo;
    private Label destination;
    private JFXTreeTableView<MenuItem> menuOrderTable;
    private JFXTreeTableView<CartItem> myOrderTable;
    private TreeTableColumn<MenuItem, String> foodItemColumn;
    private TreeTableColumn<CartItem,String> foodItemOrderColumn;
    private TreeTableColumn<MenuItem, Integer> priceColumn;
    private TreeTableColumn<CartItem,Integer> priceOrderColumn;

    private MenuItem item;
    private Node location;
    private Integer lostStock;

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
                                    CartController cartController, RequestController requestController,Label destination,JFXComboBox dietaryRestrictionsCombo) {
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
        this.dietaryRestrictionsCombo = dietaryRestrictionsCombo;
        dietaryRestrictionsCombo.getItems().add("Vegan");
        dietaryRestrictionsCombo.getItems().add("Diabetic");
        dietaryRestrictionsCombo.getItems().add("Gluten Free");
        dietaryRestrictionsCombo.getItems().add("None");
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
        cartController.getItems().clear();
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
        if (menuOrderTable.getSelectionModel().getSelectedItem().getValue().getStockAvailable() < Integer.parseInt(selectQuantity.getText())) {
            cancelMenuItem();
            Alert error = new Alert(Alert.AlertType.ERROR, "There is not enough stock for this Menu Item ");
            error.show();
        } else {
            TreeItem<MenuItem> selectedMenuItem = menuOrderTable.getSelectionModel().getSelectedItem();
            lostStock = Integer.parseInt(selectQuantity.getText());
            MenuItem modifiedItem = new MenuItem(selectedMenuItem.getValue().getFoodName(), selectedMenuItem.getValue().getDescription(),
                    selectedMenuItem.getValue().getStockAvailable() - lostStock, selectedMenuItem.getValue().getCalories(),
                    selectedMenuItem.getValue().getVegan(), selectedMenuItem.getValue().getDiabetic(), selectedMenuItem.getValue().getGluttenFree(), selectedMenuItem.getValue().getPrice());
            menuController.modifyMenuItem(modifiedItem);
            cartItem = new CartItem(modifiedItem.getFoodName(), Integer.parseInt(selectQuantity.getText()));
            cartController.addItemToCart(cartItem);
            orderRoot.getChildren().add(new TreeItem<>(cartItem));
            cancelMenuItem();
        }
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
        requestController.addRequest(foodRequest);
        cartController.clearItems();

        initializeOrderTable();
        cancelMenuItem();

    }

    public void deleteFoodItemFromCart() {
        TreeItem<CartItem> selectedMenuItem = myOrderTable.getSelectionModel().getSelectedItem();
        MenuItem modifiedItem = menuItemManager.getMenuItemByName(selectedMenuItem.getValue().getFoodNameCart());
        System.out.println(lostStock);
        MenuItem newModifiedItem = new MenuItem(modifiedItem.getFoodName(),modifiedItem.getDescription(),
                modifiedItem.getStockAvailable() + lostStock,modifiedItem.getCalories(),
                modifiedItem.getVegan(),modifiedItem.getDiabetic(), modifiedItem.getGluttenFree(),modifiedItem.getPrice());
        menuController.modifyMenuItem(newModifiedItem);
        cartController.getItems().remove(selectedMenuItem);
        orderRoot.getChildren().remove(selectedMenuItem);
        lostStock = 0;
    }

    public void selectDietaryRestriction() {
        String foodFilter = dietaryRestrictionsCombo.getSelectionModel().getSelectedItem().toString();
        menuRoot.getChildren().clear();
        if ( foodFilter == "Vegan"){
            for (MenuItem menuItem : menuController.getVegan())
                menuRoot.getChildren().add(new TreeItem<>(menuItem));

        }else if (foodFilter == "Diabetic"){
            for (MenuItem menuItem : menuController.getDiabetic())
                menuRoot.getChildren().add(new TreeItem<>(menuItem));

        }else if (foodFilter == "Gluten Free"){
            for (MenuItem menuItem : menuController.getGlutenFree())
                menuRoot.getChildren().add(new TreeItem<>(menuItem));

        }else if (foodFilter == "None"){
            for (MenuItem menuItem : menuController.getAvailableMenu())
                menuRoot.getChildren().add(new TreeItem<>(menuItem));

        }

    }

    public void setLocation(Node location) {
        this.location = location;
    }

    public void setLabelDestination(JFXTextField text){
        destination.setText(text.getText());
    }
}
