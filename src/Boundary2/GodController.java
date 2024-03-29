package Boundary2;

import Boundary2.sceneControllers.*;
import Controller2.*;
import Database2.DatabaseGargoyle;
import Entity2.*;
import Entity2.MenuItem;
import Entity2.Worker;
import Manager2.*;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.sql.SQLException;

public class GodController {
    /* Database Gargoyle */
    final private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    /* Managers */
    final private FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
    final private MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
    final private NodeManager nodeManager = new NodeManager(databaseGargoyle);
    final private  WorkerManager workerManager = new WorkerManager(databaseGargoyle);
    final private  FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager,
            workerManager, menuItemManager, foodLogManager);

    final private  WorkerLogManager workerLogManager = new WorkerLogManager(databaseGargoyle);
    
    ImageManager imageManager = new ImageManager();




    /* Entities */

    /* Controllers */
    CartController cartController = new CartController();
    DirectoryController directoryController = new DirectoryController(nodeManager);
    MenuController menuController = new MenuController(menuItemManager);
    RequestController requestController = new RequestController(foodRequestManager, workerLogManager, foodLogManager);
    SearchEngine searchEngine = new SearchEngine(nodeManager);
    WorkerController workerController = new WorkerController(workerManager);
    /* Scene Commandments */



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
    private JFXButton addWorker, cancelWorker, deleteWorker, editWorker, exportWorkers;

    @FXML
    private JFXTreeTableView<Worker> workersTable = new JFXTreeTableView<Worker>();

    @FXML
    private TreeTableColumn<Worker, String> workerIDColumn = new TreeTableColumn<Worker, String>();

    @FXML
    private TreeTableColumn<Worker, String> usernameColumn = new TreeTableColumn<Worker, String>();
    /* Menu Tab */
    @FXML
    private JFXTextField foodName, stockAvailable, calories, priceEditText;

    @FXML
    private JFXTextArea descriptionItem;

    @FXML
    private JFXButton addItem, cancelItem, deleteItem, editItem, exportItem;

    @FXML
    private JFXToggleButton vegan, diabetic, gluttenfree;

    @FXML
    private JFXTreeTableView<MenuItem> menuTable = new JFXTreeTableView<MenuItem>();

    @FXML
    private TreeTableColumn<MenuItem, String> foodNameColumn = new TreeTableColumn<MenuItem, String>();
    ;

    @FXML
    private TreeTableColumn<MenuItem, String> descriptionColumn = new TreeTableColumn<MenuItem, String>();

    @FXML
    private TreeTableColumn<MenuItem, Integer> stockAvailableColumn = new TreeTableColumn<MenuItem, Integer>();

    @FXML
    private TreeTableColumn<MenuItem, Integer> caloriesColumn = new TreeTableColumn<MenuItem, Integer>();

    @FXML
    private TreeTableColumn<MenuItem, String> veganColumn = new TreeTableColumn<MenuItem, String>();
    ;

    @FXML
    private TreeTableColumn<MenuItem, String> diabeticColumn = new TreeTableColumn<MenuItem, String>();
    ;

    @FXML
    private TreeTableColumn<MenuItem, String> gluttenFreeColumn = new TreeTableColumn<MenuItem, String>();
    ;
    @FXML
    private TreeTableColumn<MenuItem, Integer> priceEditColumn = new TreeTableColumn<>();

        /* Requests Tab */


    @FXML
    private JFXTextArea requestOrder;

    @FXML
    private JFXButton deleteRequest, deleteAllRequest, exportRequest;


    @FXML
    private JFXTreeTableView<FoodRequest> requestsTable = new JFXTreeTableView<FoodRequest>();

    @FXML
    private TreeTableColumn<FoodRequest, String> requestNameColumn = new TreeTableColumn<FoodRequest, String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> timeCreatedColumn = new TreeTableColumn<FoodRequest, String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> timeCompletedColumn = new TreeTableColumn<FoodRequest, String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> requestTypeColumn = new TreeTableColumn<FoodRequest, String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> descriptionRequestColumn = new TreeTableColumn<FoodRequest, String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> locationColumn = new TreeTableColumn<FoodRequest, String>();

    @FXML
    private TreeTableColumn<FoodRequest, String> assignedWorkerColumn = new TreeTableColumn<FoodRequest, String>();

    /* Request Hub */
    @FXML
    private Pane foodRequestHubPane;

    @FXML
    private JFXButton toAdminEdit, toStaffMenuOrder, toReports, assignButton, completeOrderButton;

    @FXML
    private JFXComboBox employeeToAssign;

    @FXML
    private JFXComboBox filterRequests =  new JFXComboBox();

    @FXML
    private JFXTreeTableView<FoodRequest> ordersAsssignTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> orderNameAssignColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> timeOrderedColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> descriptionAssignColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> locationAssignColumn = new TreeTableColumn<>();

    @FXML
    private JFXTextArea unassignedOrderInfo;

    @FXML
    private JFXTreeTableView<FoodRequest> assignedOrdersTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> orderNameAssignedColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> timeOrderedAssignedColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> locationAssignedColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> assignedEmployeeColumn = new TreeTableColumn<>();

    @FXML
    private JFXTextArea assignedOrdersInfo;



    /* Map Directory */

    //TODO

    /* Reports */
    @FXML
    private Pane reportPane;

    @FXML
    private JFXButton reportsBack;

    @FXML
    private PieChart userOrderCreatePieChart;

    @FXML
    private PieChart userOrderCompletePieChart;

    @FXML
    private Label currentFloorNum;

    @FXML
    private JFXSlider zoomSlider;

    @FXML
    private JFXButton floorDown, floorUp;

//    @FXML
//    private JFXTreeTableView<?> userOrderFrequencyTable;
//
//    @FXML
//    private TreeTableColumn<?, ?> userNameFColumn;
//
//    @FXML
//    private TreeTableColumn<?, ?> userIDFColumn;
//
//    @FXML
//    private TreeTableColumn<?, ?> userOrderFColumn;
//
//    @FXML
//    private JFXTreeTableView<> userCreationFColumn;
//
//    @FXML
//    private TreeTableColumn<?, ?> userNameCColumn;
//
//    @FXML
//    private TreeTableColumn<?, ?> userIDCColumn;
//
//    @FXML
//    private TreeTableColumn<?, ?> userOrderCreatedCColumn1;
//
//    @FXML
//    private TreeTableColumn<?, ?> userOrderCompleteCColumn11;

    @FXML
    private PieChart itemFrequencyOrderPieChart;

    @FXML
    private PieChart itemRestrictionsPieChart;

    @FXML
    private JFXTreeTableView<?> itemFrequencyOrderTable;

    @FXML
    private TreeTableColumn<?, ?> itemNameFColumn;

    @FXML
    private TreeTableColumn<?, ?> itemDescriptionFColumn;

    @FXML
    private TreeTableColumn<?, ?> totalQuantityOrderedFColumn;

    @FXML
    private JFXTreeTableView<?> itemRestrictionsTable;

    @FXML
    private TreeTableColumn<?, ?> itemNameRColumn;

    @FXML
    private TreeTableColumn<?, ?> itemVeganRColumn;

    @FXML
    private TreeTableColumn<?, ?> itemDiabeticRColumn;

    @FXML
    private TreeTableColumn<?, ?> itemGlutenRColumn;

    @FXML
    private ImageView heatMapImageView;

    @FXML
    private Canvas heatMapCanvas;

    @FXML
    private JFXTreeTableView<?> requestReportTable;

    @FXML
    private TreeTableColumn<?, ?> orderNameColumn;

    @FXML
    private TreeTableColumn<?, ?> orderLocationColumn;

    @FXML
    private TreeTableColumn<?, ?> orderWorkerColumn;

    @FXML
    private TreeTableColumn<?, ?> orderDescriptionColumn;

    @FXML
    private TreeTableColumn<?, ?> orderTimeCreatedColumn;

    @FXML
    private TreeTableColumn<?, ?> orderTimeCompletedColumn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXTreeTableView<DensityNode> reportsTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<DensityNode, String> locationRequestsColumn = new JFXTreeTableColumn<>();

    @FXML
    private TreeTableColumn<DensityNode, Integer> numberRequestsColumn = new JFXTreeTableColumn<>();

    @FXML
    private JFXTreeTableView<Slice> foodOrders = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<Slice, String> menuFoodColumn = new JFXTreeTableColumn<>();

    @FXML
    private TreeTableColumn<Slice, Integer> menuFoodOrdersColumn = new JFXTreeTableColumn<>();



    @FXML
    private PieChart orderItemsPieChart = new PieChart();

    /* Staff Info Popup */
    @FXML
    private Pane foodInfoPane;

    @FXML
    private JFXTreeTableView<MenuItem> foodInfoTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<MenuItem, String> foodItemInfoColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<MenuItem, Integer> nutritionColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<MenuItem, String> foodInfoDescription = new TreeTableColumn<>();


    /* Staff Menu Order */
    @FXML
    private Pane staffMenuOrderPane;

    @FXML
    private JFXButton addMenuItemButton, cancelMenuItemButton, checkoutRequestButton, destinationPopupButton, deleteFoodItem, informationButton;

    @FXML
    private JFXTextField selectQuantity;

    @FXML
    private JFXTextField menuItemOrder, itemPrice;

    @FXML
    private JFXComboBox dietaryRestrictionsCombo = new JFXComboBox();

    @FXML
    private Label destination = new Label();

    @FXML
    private JFXTreeTableView<MenuItem> menuOrderTable = new JFXTreeTableView<>();

    @FXML
    private JFXTreeTableView<CartItem> myOrderTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<MenuItem, String> foodItemColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<CartItem, String> foodItemOrderColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<MenuItem, Integer> priceColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<CartItem, Integer> priceOrderColumn = new TreeTableColumn<>();

    AdminEditMenuController adminEditMenuController;
    FoodRequestHubController foodRequestHubController;
    ReportsController reportsController;
    StaffIntoPopupController staffIntoPopupController = new StaffIntoPopupController(menuController);
    StaffMenuOrderController staffMenuOrderController = new StaffMenuOrderController(databaseGargoyle,
            nodeManager, foodLogManager, menuItemManager, workerManager,
            foodRequestManager, selectQuantity, menuItemOrder, itemPrice,
            menuOrderTable, myOrderTable,
            foodItemColumn, foodItemOrderColumn,
            priceColumn, priceOrderColumn, cartController, requestController,destination,dietaryRestrictionsCombo);

    MapDirectoryController mapDirectoryController = new MapDirectoryController(directoryController, staffMenuOrderController);
    /* Scene Switcher */
    SceneSwitcher sceneSwitcher = new SceneSwitcher(staffIntoPopupController, mapDirectoryController);




    /**
     * Organize Functions by Scene
     **/

    /////////////////////////////
    /* Scene Switching Buttons */
    /////////////////////////////
    @FXML
    private void staffMenuOrderToHub() throws IOException {
        sceneSwitcher.toFoodRequestHub(this, staffMenuOrderPane);
        staffMenuOrderController.resetOnLeave();
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
        staffMenuOrderController.initialize(destination, menuController);

    }

    @FXML
    private void hubToAdminEdit() throws IOException {
        sceneSwitcher.toAdminEditMenu(this, foodRequestHubPane);
        adminEditMenuController.initialize();
    }

    @FXML
    private void hubToReports() throws IOException {
        sceneSwitcher.toReports(this, foodRequestHubPane);
        reportsController.initialize();
    }

    @FXML
    private void mapDirectoryPopup(ActionEvent event) throws IOException {
        toMapDirectoryPopup(dPane);
    }

    @FXML
    private void menuInfoPopup(ActionEvent event) throws IOException {
        sceneSwitcher.toMenuInfoPopup();
        staffIntoPopupController.initializeStaffInfo();
    }

    @FXML
    private BarChart<String,Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private JFXTreeTableView<TypeData> typeTable;

    @FXML
    private TreeTableColumn<TypeData,String> typeofOrder;

    @FXML
    private TreeTableColumn<TypeData,Integer> numberofTypeOrders;


    public void initialize() {
        initializeAdminEditMenuScene();
        initializeFoodRequestHubScene();
        initializeMapDirectoryScene();
        initializeReportsScene();
        initializeStaffIntoPopupScene();
        initializeStaffMenuOrderScene();
        initializeObservers();
        staffMenuOrderController.initialize(destination, menuController);
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
                locationColumn, assignedWorkerColumn, veganColumn, diabeticColumn, gluttenFreeColumn, menuController, workerController, requestController, priceEditColumn, priceEditText);
    }

    private void initializeFoodRequestHubScene() {
        foodRequestHubController = new FoodRequestHubController(requestController, employeeToAssign, ordersAsssignTable,
                orderNameAssignColumn, timeOrderedColumn, descriptionAssignColumn,
                locationAssignColumn, unassignedOrderInfo, assignedOrdersTable, orderNameAssignedColumn,
                timeOrderedAssignedColumn, locationAssignedColumn,
                assignedEmployeeColumn, assignedOrdersInfo, workerController,filterRequests);
        foodRequestHubController.initialize();
    }

    private void initializeMapDirectoryScene() {
        mapDirectoryController = new MapDirectoryController(directoryController, staffMenuOrderController);
    }

    private void initializeReportsScene() {
        reportsController = new ReportsController(scrollPane, imageManager,foodLogManager,nodeManager,reportsTable,locationRequestsColumn,numberRequestsColumn,
                foodOrders,menuFoodColumn,menuFoodOrdersColumn,orderItemsPieChart, currentFloorNum, zoomSlider,barChart,yAxis,xAxis,menuItemManager,
                typeTable,typeofOrder,numberofTypeOrders);
    }

    private void initializeStaffIntoPopupScene() {
//        staffIntoPopupController = new StaffIntoPopupController(foodInfoTable, foodItemInfoColumn,
//                nutritionColumn, foodInfoDescription,
//                databaseGargoyle, menuItemManager, menuController);

    }

    private void initializeStaffMenuOrderScene() {
        staffMenuOrderController = new StaffMenuOrderController(databaseGargoyle,
                nodeManager, foodLogManager, menuItemManager, workerManager,
                foodRequestManager, selectQuantity, menuItemOrder, itemPrice,
                menuOrderTable, myOrderTable,
                foodItemColumn, foodItemOrderColumn,
                priceColumn, priceOrderColumn,
                cartController, requestController, destination, dietaryRestrictionsCombo);
    }


    //////////////////////
    /* Food Request Hub */
    //////////////////////


    @FXML
    private void assignEmployee() {
        foodRequestHubController.assignEmployee();
    }

    @FXML
    private void completeOrder() {
        foodRequestHubController.completeOrder();
    }

    @FXML
    private void filterRequests() { foodRequestHubController.filterRequests();}

    /////////////
    /* Reports */
    /////////////

    @FXML
    private void floorUp() throws IOException, SQLException { reportsController.floorUp(); }

    @FXML
    private void floorDown() throws IOException, SQLException { reportsController.floorDown(); }


    ////////////////
    /* Menu Order */
    ////////////////

    @FXML
    private AnchorPane dPane;

    @FXML
    private void selectDietaryRestriction() {
        staffMenuOrderController.selectDietaryRestriction();
    }

    @FXML
    private void deleteFoodItemFromCart() {
        staffMenuOrderController.deleteFoodItemFromCart();
    }


    @FXML
    private void addMenuItem() {
        staffMenuOrderController.addMenuItem();
    }

    @FXML
    private void cancelMenuItem() {
        staffMenuOrderController.cancelMenuItem();
    }

    @FXML
    private void checkoutRequest() {
        staffMenuOrderController.checkoutRequest();
    }

    @FXML
    private void filterMenu() {staffMenuOrderController.selectDietaryRestriction();}

    //////////////////////
    /* Admin Edit */
    //////////////////////

    /*Worker Tab */
    @FXML
    private void removeWorker() {
        adminEditMenuController.deleteWorker();
    }

    @FXML
    private void clearWorker() {
        adminEditMenuController.clearWorker();
    }

    @FXML
    private void editWorker() {
        adminEditMenuController.editWorker();
    }

    @FXML
    private void addWorker() {
        adminEditMenuController.addWorker();
    }

    @FXML
    private void exportWorkers() {
        adminEditMenuController.exportWorkerLogs();
    }

    /*Request Tab */
    @FXML
    private void deleteRequest() {
        adminEditMenuController.deleteRequest();
    }

    @FXML
    private void deleteAllRequests() {
        adminEditMenuController.deleteAllRequests();
    }

    @FXML
    private void cancelRequest() {
        adminEditMenuController.cancelRequest();
    }

    @FXML
    private void exportRequests() {
        adminEditMenuController.exportFoodLogs();
    }

        /*Menu Tab */

    @FXML
    private void addMenuEditItem() {
        adminEditMenuController.addMenu();
    }

    @FXML
    private void removeMenuEditItem() {
        adminEditMenuController.deleteMenu();
    }

    @FXML
    private void cancelMenuEditItem() {
        adminEditMenuController.clearMenu();
    }

    @FXML
    private void editMenuEditItem() {
        adminEditMenuController.editMenu();
    }

    @FXML
    private void exportMenu() {
        adminEditMenuController.exportMenuItems();
    }


    void initializeObservers() {
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.attachManager(foodRequestManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.notifyManagers();
    }
//    @FXML
    public void toMapDirectoryPopup(AnchorPane dPane) throws IOException {
        JFXRippler rippler = new JFXRippler();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary2/fxml/mapDirectory.fxml"));
        loader.setController(mapDirectoryController);
        Region region = loader.load();
        dPane.getChildren().add(rippler);
        JFXPopup popup = new JFXPopup(region);
        mapDirectoryController.setMainSceneController(staffMenuOrderController);
        popup.show(rippler, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
    }



}


