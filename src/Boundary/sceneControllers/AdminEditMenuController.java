package Boundary.sceneControllers;

import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.MenuItem;
import Entity.Worker;
import Manager.*;
import com.jfoenix.controls.*;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class AdminEditMenuController {
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    private NodeManager nodeManager = new NodeManager(databaseGargoyle);
    private FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
    private MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
    private WorkerManager workerManager = new WorkerManager(databaseGargoyle);
    private FoodRequestManager foodRequestManager = new FoodRequestManager( databaseGargoyle,  nodeManager,
             workerManager,  menuItemManager,  foodLogManager);
    /*Worker Tab */
    private JFXTextField usernameId;
    private Label workerID;
    private JFXTreeTableView<Worker> workersTable;
    private TreeTableColumn<Worker,String> workerIDColumn;
    private TreeTableColumn<Worker,String> usernameColumn;
    /* Menu Tab */
    private JFXTextField foodName, stockAvailable, calories;
    private JFXTextArea descriptionItem;
    private JFXToggleButton vegan, diabetic, gluttenFree;
    private JFXTreeTableView<MenuItem> menuTable;
    private TreeTableColumn<MenuItem,String> foodNameColumn;
    private TreeTableColumn<MenuItem,String> descriptionColumn;
    private TreeTableColumn<MenuItem,Integer> stockAvailableColumn;
    private TreeTableColumn<MenuItem,Integer> caloriesColumn;
    /* Requests Tab */
    private JFXTextArea requestOrder;
    private JFXTreeTableView<FoodRequest> requestsTable;
    private TreeTableColumn<FoodRequest,String> requestNameColumn;
    private TreeTableColumn<FoodRequest,String> timeCreatedColumn;
    private TreeTableColumn<FoodRequest,String> timeCompletedColumn;
    private TreeTableColumn<FoodRequest,String> requestTypeColumn;
    private TreeTableColumn<FoodRequest,String> descriptionRequestColumn;
    private TreeTableColumn<FoodRequest,String> locationColumn;
    private TreeTableColumn<FoodRequest,String> assignedWorkerColumn;

    private TreeItem<FoodRequest> requestsRoot = new TreeItem<>();
    private TreeItem<MenuItem> menuRoot = new TreeItem<>();
    private TreeItem<Worker> workerRoot = new TreeItem<>();
    private FoodRequest foodRequest;
    private MenuItem menuItem;
    private Worker worker;

    public AdminEditMenuController(DatabaseGargoyle databaseGargoyle,NodeManager nodeManager,FoodLogManager foodLogManager,MenuItemManager menuItemManager,WorkerManager workerManager,
                                   FoodRequestManager foodRequestManager, JFXTextField usernameId, Label workerID, JFXTreeTableView<Worker> workersTable,
                                   TreeTableColumn<Worker,String> workerIDColumn, TreeTableColumn<Worker,String> usernameColumn,
                                   JFXTextField foodName,JFXTextField stockAvailable,JFXTextField calories,
                                   JFXTextArea descriptionItem, JFXToggleButton vegan,JFXToggleButton diabetic,JFXToggleButton gluttenFree, JFXTreeTableView<MenuItem> menuTable,
                                   TreeTableColumn<MenuItem,String> foodNameColumn, TreeTableColumn<MenuItem,String> descriptionColumn, TreeTableColumn<MenuItem,Integer> stockAvailableColumn,
                                   TreeTableColumn<MenuItem,Integer> caloriesColumn, JFXTextArea requestOrder,
                                   JFXTreeTableView<FoodRequest> requestsTable, TreeTableColumn<FoodRequest,String> requestNameColumn,
                                   TreeTableColumn<FoodRequest,String> timeCreatedColumn, TreeTableColumn<FoodRequest,String> timeCompletedColumn,
                                   TreeTableColumn<FoodRequest,String> requestTypeColumn, TreeTableColumn<FoodRequest,String> descriptionRequestColumn,
                                   TreeTableColumn<FoodRequest,String> locationColumn, TreeTableColumn<FoodRequest,String> assignedWorkerColumn){
        this.databaseGargoyle = databaseGargoyle;
        this.nodeManager = nodeManager;
        this.foodLogManager = foodLogManager;
        this.menuItemManager = menuItemManager;
        this.workerManager = workerManager;
        this.foodRequestManager = foodRequestManager;
        this.usernameId = usernameId;
        this.workerID = workerID;
        this.workersTable = workersTable;
        this.workerIDColumn = workerIDColumn;
        this.usernameColumn = usernameColumn;
        this.foodName = foodName;
        this.stockAvailable = stockAvailable;
        this.calories = calories;
        this.descriptionItem = descriptionItem;
        this.vegan = vegan;
        this.diabetic = diabetic;
        this.gluttenFree = gluttenFree;
        this.menuTable = menuTable;
        this.foodNameColumn = foodNameColumn;
        this.descriptionColumn = descriptionColumn;
        this.stockAvailableColumn = stockAvailableColumn;
        this.caloriesColumn = caloriesColumn;
        this.requestOrder = requestOrder;
        this.requestsTable = requestsTable;
        this.requestNameColumn = requestNameColumn;
        this.timeCreatedColumn = timeCreatedColumn;
        this.timeCompletedColumn = timeCompletedColumn;
        this.requestTypeColumn = requestTypeColumn;
        this.descriptionRequestColumn = descriptionRequestColumn;
        this.locationColumn = locationColumn;
        this.assignedWorkerColumn = assignedWorkerColumn;


    }

    public void initialize() {

        initializeMenuTab();
        initializeRequestsTab();
        initializeWorkerTab();

    }
    private void initializeMenuTab(){
//        menuItemManager.update();
//
//        for (MenuItem menuItem : menuItemManager.getMenuItems()){
//            menuRoot.getChildren().add(new TreeItem<MenuItem>(menuItem));
//
//        }
//        foodNameColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getFoodName()));
//        descriptionColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getDescription()));
//        stockAvailableColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<MenuItem, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getStockAvailable()));
//        caloriesColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<MenuItem, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getCalories()));
//
//        menuTable.setRoot(menuRoot);
//        menuTable.setShowRoot(true);


    }
    private void initializeRequestsTab(){
//        foodRequestManager.update();
//
//        for (FoodRequest foodRequest : foodRequestManager.getRequests()){
//            requestsRoot.getChildren().add(new TreeItem<FoodRequest>(foodRequest));
//        }
//        requestNameColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getName()));
//        timeCreatedColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
//        timeCompletedColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCompleted().toString()));
//        requestTypeColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getType()));
//        descriptionRequestColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getDescription()));
//        locationColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNode().getLongName()));
//        assignedWorkerColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getAssignedWorker().getWorkerID()));

    }
    private void initializeWorkerTab(){
//         workerManager.update();
//
//        for (Worker worker : workerManager.getWorkers()){
//            workerRoot.getChildren().add(new TreeItem<Worker>(worker));
//        }
//        workerIDColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<Worker, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getWorkerID()));
//        usernameColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<Worker, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getUsername()));
//
//        workersTable.setRoot(workerRoot);
//        workersTable.setShowRoot(true);

    }

}

