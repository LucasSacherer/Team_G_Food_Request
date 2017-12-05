package Boundary;

import Boundary.sceneControllers.*;
import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.MenuItem;
import Entity.Worker;
import Manager.*;
import com.jfoenix.controls.*;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class GodController {
    /* Database Gargoyle */
    final private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    /* Managers */
    FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
    MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
    NodeManager nodeManager = new NodeManager(databaseGargoyle);
    WorkerManager workerManager = new WorkerManager(databaseGargoyle);
    FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager,
            workerManager, menuItemManager, foodLogManager);
    WorkerLogManager workerLogManager = new WorkerLogManager(databaseGargoyle);



    /* Entities */

    /* Scene Switcher */
    SceneSwitcher sceneSwitcher = new SceneSwitcher();


    ///////////////////////
    /**
     * FXML Attributes
     **/
    ///////////////////////
    /* Edit Menu */
    @FXML
    private Pane adminEditPane;

    @FXML
    private JFXButton editMenuBack;

    /* Worker Tab */
    @FXML
    private JFXTextField username;

    @FXML
    private Label workerID;

    @FXML
    private JFXButton addWorker, cancelWorker, deleteWorker, deleteAllWorkers, exportWorkers;

    @FXML
    private JFXTreeTableView<Worker> workersTable = new JFXTreeTableView<Worker>();

    @FXML
    private TreeTableColumn<Worker, String> workerIDColumn = new TreeTableColumn<Worker,String>();

    @FXML
    private TreeTableColumn<Worker, String> usernameColumn = new TreeTableColumn<Worker,String>();
    /* Menu Tab */
    @FXML
    private JFXTextField foodName, stockAvailable, calories;

    @FXML
    private JFXTextArea descriptionItem;

    @FXML
    private JFXButton addItem, cancelItem, deleteItem, deleteAllItem, exportItem;

    @FXML
    private JFXToggleButton vegan, diabetic, gluttenfree;

    @FXML
    private JFXTreeTableView<MenuItem> menuTable = new JFXTreeTableView<MenuItem>();

    @FXML
    private TreeTableColumn<MenuItem, String> foodNameColumn = new TreeTableColumn<MenuItem,String>();;

    @FXML
    private TreeTableColumn<MenuItem, String> descriptionColumn = new TreeTableColumn<MenuItem,String>();

    @FXML
    private TreeTableColumn<MenuItem, Integer> stockAvailableColumn = new TreeTableColumn<MenuItem,Integer>();

    @FXML
    private TreeTableColumn<MenuItem, Integer> caloriesColumn = new TreeTableColumn<MenuItem,Integer>();

    @FXML
    private TreeTableColumn<MenuItem, String> veganColumn = new TreeTableColumn<MenuItem,String>();;

    @FXML
    private TreeTableColumn<MenuItem, String> diabeticColumn = new TreeTableColumn<MenuItem,String>();;

    @FXML
    private TreeTableColumn<MenuItem, String> gluttenFreeColumn = new TreeTableColumn<MenuItem,String>();;

        /* Requests Tab */


    @FXML
    private JFXTextArea requestOrder;

    @FXML
    private JFXButton deleteRequest, deleteAllRequest, exportRequest;


    @FXML
    private JFXTreeTableView<FoodRequest> requestsTable = new JFXTreeTableView<FoodRequest>();

    @FXML
    private TreeTableColumn<FoodRequest, String> requestNameColumn = new TreeTableColumn<FoodRequest,String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> timeCreatedColumn = new TreeTableColumn<FoodRequest,String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> timeCompletedColumn = new TreeTableColumn<FoodRequest,String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> requestTypeColumn = new TreeTableColumn<FoodRequest,String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> descriptionRequestColumn = new TreeTableColumn<FoodRequest,String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> locationColumn = new TreeTableColumn<FoodRequest,String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> assignedWorkerColumn = new TreeTableColumn<FoodRequest,String>();

    /* Request Hub */
    @FXML
    private Pane foodRequestHubPane;

    @FXML
    private JFXButton toAdminEdit, toStaffMenuOrder, toReports, assignButton, completeOrderButton;

    @FXML
    private JFXListView ordersToAssign, assignOrderItems, ordersAssigned, assignedOrderItems;

    @FXML
    private JFXComboBox employeeToAssign;


    /* Map Directory */

    //TODO

    /* Reports */
    @FXML
    private Pane reportPane;

    @FXML
    private JFXButton reportsBack;

    /* Staff Info Popup */



    /* Staff Menu Order */
    @FXML
    private Pane staffMenuOrderPane;

    @FXML
    private JFXButton addMenuItemButton, cancelMenuItemButton, checkoutRequestButton,destinationPopupButton,deleteFoodItem, informationButton;

    @FXML
    private JFXComboBox selectQuantityCombo;

    @FXML
    private JFXTextField menuItem,itemPrice, destination;

    @FXML
    private TreeTableView<MenuItem> hubMenuTable;

    @FXML
    private TreeTableView<MenuItem> orderTable;

    @FXML
    private TreeTableColumn<MenuItem,String> foodItemColumn, foodItemOrderColumn;

    @FXML
    private TreeTableColumn<MenuItem,Integer> priceColumn, priceOrderColumn;



    /** Organize Functions by Scene **/

    /////////////////////////////
    /* Scene Switching Buttons */
    /////////////////////////////


    @FXML
    private void staffMenuOrderToHub() throws IOException {
        sceneSwitcher.toFoodRequestHub(this, staffMenuOrderPane);
    }

    @FXML
    private void adminEditToHub() throws IOException {
        sceneSwitcher.toFoodRequestHub(this, adminEditPane);
    }

    @FXML
    private void reportsToHub() throws IOException {
        sceneSwitcher.toFoodRequestHub(this, reportPane);
    }

    @FXML
    private void hubToStaffMenuOrder() throws IOException {
        sceneSwitcher.toStaffMenuOrder(this, foodRequestHubPane);
    }

    @FXML
    private void hubToAdminEdit() throws IOException {
        sceneSwitcher.toAdminEditMenu(this, foodRequestHubPane);
        adminEditMenuController.initialize();
    }

    @FXML
    private void hubToReports() throws IOException {
        sceneSwitcher.toReports(this, foodRequestHubPane);
    }


    /* Scene Commandments */
    AdminEditMenuController adminEditMenuController;
    FoodRequestHubController foodRequestHubController;
    MapDirectoryController mapDirectoryController;
    ReportsController reportsController;
    StaffIntoPopupController staffIntoPopupController;
    StaffMenuOrderController staffMenuOrderController;

    public void initialize(){
        initializeAdminEditMenuScene();
        initializeFoodRequestHubScene();
        initializeMapDirectoryScene();
        initializeReportsScene();
        initializeStaffIntoPopupScene();
        initializeStaffMenuOrderScene();
        initializeObservers();
    }


    private void initializeAdminEditMenuScene() {
        adminEditMenuController = new AdminEditMenuController(databaseGargoyle, nodeManager, foodLogManager, menuItemManager, workerManager,
                foodRequestManager, workerLogManager, username, workerID, workersTable,
                workerIDColumn, usernameColumn,
                foodName, stockAvailable, calories,
                descriptionItem, vegan, diabetic, gluttenfree, menuTable, foodNameColumn, descriptionColumn, stockAvailableColumn,
                caloriesColumn, requestOrder,
                requestsTable, requestNameColumn,
                timeCreatedColumn, timeCompletedColumn,
                requestTypeColumn, descriptionRequestColumn,
                locationColumn, assignedWorkerColumn,veganColumn,diabeticColumn,gluttenFreeColumn);
    }

    private void initializeFoodRequestHubScene() {
        foodRequestHubController = new FoodRequestHubController();
    }

    private void initializeMapDirectoryScene() {
        mapDirectoryController = new MapDirectoryController();
    }

    private void initializeReportsScene() {
        reportsController = new ReportsController();
    }

    private void initializeStaffIntoPopupScene() {
        staffIntoPopupController = new StaffIntoPopupController();
    }

    private void initializeStaffMenuOrderScene() {
        staffMenuOrderController = new StaffMenuOrderController();
    }


    //////////////////////
    /* Food Request Hub */
    //////////////////////

    @FXML
    private void selectEmployeeToAssign() {
        foodRequestHubController.selectEmployeeToAssign();
    }

    @FXML
    private void assignEmployee() {
        foodRequestHubController.assignEmployee();
    }
    @FXML
    private void completeOrder(){ foodRequestHubController.completeOrder();}

    ////////////////
    /* Menu Order */
    ////////////////

    @FXML
    private void selectDietaryRestriction() { staffMenuOrderController.selectDietaryRestriction(); }

    @FXML
    private void deleteFoodItemFromCart(){ staffMenuOrderController.deleteFoodItemFromCart();}

    @FXML
    private void selectQuantity(){ staffMenuOrderController.selectQuantity();}

    @FXML
    private void addMenuItem(){ staffMenuOrderController.addMenuItem();}

    @FXML
    private void cancelMenuItem(){ staffMenuOrderController.cancelMenuItem();}

    @FXML
    private void checkoutRequest(){ staffMenuOrderController.checkoutRequest();}

    @FXML
    private void destinationPopup(){ staffMenuOrderController.destinationPopup();}

    @FXML
    private void information(){ staffMenuOrderController.information();}

    @FXML
    void handleButtonAction (ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/staffInfoPopup.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Menu Info");
            stage.setScene(new Scene(root1, 1280, 720));
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load new window");
        }
    }
    void initializeObservers(){
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.attachManager(foodRequestManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.notifyManagers();
    }

}
