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
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;

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
    FoodRequestManager foodRequestManager = new FoodRequestManager( databaseGargoyle,  nodeManager,
             workerManager,  menuItemManager,  foodLogManager);

    /* Entities */

    /* Scene Switcher */
    SceneSwitcher sceneSwitcher = new SceneSwitcher();


    ///////////////////////
    /** FXML Attributes **/
    ///////////////////////
    /* Edit Menu */
    @FXML
    private Pane adminEditPane;

    @FXML
    private JFXButton editMenuBack;

        /* Worker Tab */
    @FXML
    private JFXTextField workerID,usernameId;

    @FXML
    private JFXButton addWorker, cancelWorker, deleteWorker, deleteAllWorkers, exportWorkers, confirmWorkers;

    @FXML
    private JFXTreeTableView<Worker> workersTable;

    @FXML
    private TreeTableColumn<Worker,String> workerIDColumn;

    @FXML
    private TreeTableColumn<Worker,String> usernameColumn;
        /* Menu Tab */
    @FXML
    private JFXTextField foodName, stockAvailable, calories;

    @FXML
    private JFXTextArea descriptionItem;

    @FXML
    private JFXButton addItem, cancelItem, deleteItem, deleteAllItem, exportItem, confirmMenu;

    @FXML
    private JFXToggleButton vegan, diabetic, gluttenFree;

    @FXML
    private JFXTreeTableView<MenuItem> menuTable;

    @FXML
    private TreeTableColumn<MenuItem,String> foodNameColumn;

    @FXML
    private TreeTableColumn<MenuItem,String> descriptionColumn;

    @FXML
    private TreeTableColumn<MenuItem,String> stockAvailableColumn;

    @FXML
    private TreeTableColumn<MenuItem,String> caloriesColumn;

        /* Requests Tab */
    @FXML
    private JFXTextField requestName,locationRequest,assignedWorker;

    @FXML
    private JFXComboBox requestTypeCombo;

    @FXML
    private JFXTextArea descriptionRequest,requestOrder;

    @FXML
    private JFXButton addRequest, cancelRequest, deleteRequest, deleteAllRequest, exportRequest, confirmRequests;

    @FXML
    private JFXTimePicker timeCreated, timeCompleted;

    @FXML
    private JFXTreeTableView<FoodRequest> requestsTable;

    @FXML
    private TreeTableColumn<FoodRequest,String> requestNameColumn;

    @FXML
    private TreeTableColumn<FoodRequest,String> timeCreatedColumn;

    @FXML
    private TreeTableColumn<FoodRequest,String> timeCompletedColumn;

    @FXML
    private TreeTableColumn<FoodRequest,String> requestTypeColumn;

    @FXML
    private TreeTableColumn<FoodRequest,String> descriptionRequestColumn;

    @FXML
    private TreeTableColumn<FoodRequest,String> locationColumn;

    @FXML
    private TreeTableColumn<FoodRequest,String> assignedWorkerColumn;

    /* Request Hub */
    @FXML
    private Pane foodRequestHubPane;

    @FXML
    private JFXButton toAdminEdit, toStaffMenuOrder, toReports, assignButton, completeOrder;

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

    //TODO

    /* Staff Menu Order */
    @FXML
    private Pane staffMenuOrderPane;

    @FXML
    private JFXButton menuOrderBack;

    /** Organize Functions by Scene **/



    @FXML
    private void staffMenuOrderToHub() throws IOException { sceneSwitcher.toFoodRequestHub(this, staffMenuOrderPane); }

    @FXML
    private void adminEditToHub() throws IOException { sceneSwitcher.toFoodRequestHub(this, adminEditPane); }

    @FXML
    private void reportsToHub() throws IOException { sceneSwitcher.toFoodRequestHub(this, reportPane); }

    @FXML
    private void hubToStaffMenuOrder() throws IOException { sceneSwitcher.toStaffMenuOrder(this, foodRequestHubPane); }

    @FXML
    private void hubToAdminEdit() throws IOException { sceneSwitcher.toAdminEditMenu(this, foodRequestHubPane); }

    @FXML
    private void hubToReports() throws IOException { sceneSwitcher.toReports(this, foodRequestHubPane); }


    /* Scene Commandments */
    AdminEditMenuController adminEditMenuController;
    FoodRequestHubController foodRequestHubController;
    MapDirectoryController mapDirectoryController;
    ReportsController reportsController;
    StaffIntoPopupController staffIntoPopupController;
    StaffMenuOrderController staffMenuOrderController;

    private void initializeAdminEditMenuScene(){
        adminEditMenuController = new AdminEditMenuController();
    }
    private void initializeFoodRequestHubScene(){
        foodRequestHubController = new FoodRequestHubController();
    }
    private void initializeMapDirectoryScene(){
        mapDirectoryController = new MapDirectoryController();
    }
    private void initializeReportsScene(){
        reportsController = new ReportsController();
    }
    private void initializeStaffIntoPopupScene(){
        staffIntoPopupController = new StaffIntoPopupController();
    }
    private void initializeStaffMenuOrderScene(){
        staffMenuOrderController = new StaffMenuOrderController();
    }



    ////////////////
    /* Main scene */
    ////////////////

}
