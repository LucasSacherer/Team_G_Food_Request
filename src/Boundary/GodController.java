package Boundary;

import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.MenuItem;
import Entity.Worker;
import Manager.*;
import com.jfoenix.controls.*;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private JFXTreeTableColumn<Worker,String> workerIDColumn;

    @FXML
    private JFXTreeTableColumn<Worker,String> usernameColumn;
        /* Menu Tab */
    @FXML
    private JFXTextField foodName,descriptionItem, stockAvailable, calories;

    @FXML
    private JFXButton addItem, cancelItem, deleteItem, deleteAllItem, exportItem, confirmMenu;

    @FXML
    private JFXToggleButton vegan, diabetic, gluttenFree;

    @FXML
    private JFXTreeTableView<MenuItem> menuTable;

    @FXML
    private JFXTreeTableColumn<MenuItem,String> foodNameColumn;

    @FXML
    private JFXTreeTableColumn<MenuItem,String> descriptionColumn;

    @FXML
    private JFXTreeTableColumn<MenuItem,String> stockAvailableColumn;

    @FXML
    private JFXTreeTableColumn<MenuItem,String> caloriesColumn;

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
    private JFXTreeTableColumn<FoodRequest,String> requestNameColumn;

    @FXML
    private JFXTreeTableColumn<FoodRequest,String> timeCreatedColumn;

    @FXML
    private JFXTreeTableColumn<FoodRequest,String> timeCompletedColumn;

    @FXML
    private JFXTreeTableColumn<FoodRequest,String> requestTypeColumn;

    @FXML
    private JFXTreeTableColumn<FoodRequest,String> descriptionRequestColumn;

    @FXML
    private JFXTreeTableColumn<FoodRequest,String> locationColumn;

    @FXML
    private JFXTreeTableColumn<FoodRequest,String> assignedWorkerColumn;

    /* Request Hub */
    @FXML
    private Pane foodRequestHubPane;

    @FXML
    private JFXButton toAdminEdit, toStaffMenuOrder, toReports;
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

    ////////////////
    /* Main scene */
    ////////////////

}
