package Boundary.sceneControllers;

import Controller.CSVController;
import Controller.MenuController;
import Controller.RequestController;
import Controller.WorkerController;
import Database.DatabaseGargoyle;
import Entity.FileSelector;
import Entity.FoodRequest;
import Entity.MenuItem;
import Entity.Worker;
import Manager.*;
import com.jfoenix.controls.*;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import sun.reflect.generics.tree.Tree;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

public class AdminEditMenuController {
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
    private CSVController csvController = new CSVController(databaseGargoyle);
    final private FileSelector fileSelector = new FileSelector();

    /* Managers */
    private NodeManager nodeManager;
    private FoodLogManager foodLogManager;
    private MenuItemManager menuItemManager;
    private WorkerManager workerManager;
    private FoodRequestManager foodRequestManager;
    private WorkerLogManager workerLogManager;

    /* Controllers */
    private MenuController menuController;
    private WorkerController workerController;
    private RequestController requestController;

    /*Worker Tab */
    private JFXTextField username;
    private Label workerID;
    private JFXTreeTableView<Worker> workersTable;
    private TreeTableColumn<Worker,String> workerIDColumn;
    private TreeTableColumn<Worker,String> usernameColumn;

    /* Menu Tab */
    private JFXTextField foodName, stockAvailable, calories;
    private JFXTextField priceEditText;
    private JFXTextArea descriptionItem;
    private JFXToggleButton vegan, diabetic, gluttenfree;
    private JFXTreeTableView<MenuItem> menuTable;
    private TreeTableColumn<MenuItem,String> foodNameColumn;
    private TreeTableColumn<MenuItem,String> descriptionColumn;
    private TreeTableColumn<MenuItem,Integer> stockAvailableColumn;
    private TreeTableColumn<MenuItem,Integer> caloriesColumn;
    private TreeTableColumn<MenuItem,String> veganColumn;
    private TreeTableColumn<MenuItem,String> diabeticColumn;
    private TreeTableColumn<MenuItem,String> gluttenFreeColumn;
    private TreeTableColumn<MenuItem, Integer> priceEditColumn;

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
                                   FoodRequestManager foodRequestManager, WorkerLogManager workerLogManager, JFXTextField username, Label workerID, JFXTreeTableView<Worker> workersTable,
                                   TreeTableColumn<Worker,String> workerIDColumn, TreeTableColumn<Worker,String> usernameColumn,
                                   JFXTextField foodName,JFXTextField stockAvailable,JFXTextField calories,
                                   JFXTextArea descriptionItem, JFXToggleButton vegan,JFXToggleButton diabetic,JFXToggleButton gluttenfree, JFXTreeTableView<MenuItem> menuTable,
                                   TreeTableColumn<MenuItem,String> foodNameColumn, TreeTableColumn<MenuItem,String> descriptionColumn, TreeTableColumn<MenuItem,Integer> stockAvailableColumn,
                                   TreeTableColumn<MenuItem,Integer> caloriesColumn, JFXTextArea requestOrder,
                                   JFXTreeTableView<FoodRequest> requestsTable, TreeTableColumn<FoodRequest,String> requestNameColumn,
                                   TreeTableColumn<FoodRequest,String> timeCreatedColumn, TreeTableColumn<FoodRequest,String> timeCompletedColumn,
                                   TreeTableColumn<FoodRequest,String> requestTypeColumn, TreeTableColumn<FoodRequest,String> descriptionRequestColumn,
                                   TreeTableColumn<FoodRequest,String> locationColumn, TreeTableColumn<FoodRequest,String> assignedWorkerColumn,
                                   TreeTableColumn<MenuItem,String> veganColumn,TreeTableColumn<MenuItem,String> diabeticColumn,TreeTableColumn<MenuItem,String> gluttenFreeColumn,
                                    MenuController menuController, WorkerController workerController, RequestController requestController,TreeTableColumn<MenuItem, Integer> priceEditColumn,
                                   JFXTextField priceEditText){
        this.databaseGargoyle = databaseGargoyle;
        this.nodeManager = nodeManager;
        this.foodLogManager = foodLogManager;
        this.menuItemManager = menuItemManager;
        this.workerManager = workerManager;
        this.foodRequestManager = foodRequestManager;
        this.workerLogManager = workerLogManager;
        this.username = username;
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
        this.gluttenfree = gluttenfree;
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
        this.veganColumn = veganColumn;
        this.diabeticColumn = diabeticColumn;
        this.gluttenFreeColumn = gluttenFreeColumn;
        this.workerController = workerController;
        this.requestController = requestController;
        this.menuController = menuController;
        this.priceEditColumn = priceEditColumn;
        this.priceEditText = priceEditText;


    }

    public void initialize() {

        initializeMenuTab();
        initializeRequestsTab();
        initializeWorkerTab();

    }
    private void initializeMenuTab(){
        menuItemManager.update();

        for (MenuItem menuItem : menuItemManager.getMenuItems()){
            menuRoot.getChildren().add(new TreeItem<MenuItem>(menuItem));

        }
        foodNameColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> {
                    return new ReadOnlyStringWrapper(param.getValue().getValue().getFoodName());
                });
        descriptionColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getDescription()));
        stockAvailableColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getStockAvailable()));
        caloriesColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getCalories()));
        veganColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getVegan().toString()));
        diabeticColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getDiabetic().toString()));
        gluttenFreeColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, String> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getGluttenFree().toString()));
        priceEditColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<MenuItem, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getPrice()));


        menuTable.setRoot(menuRoot);
        menuTable.setShowRoot(false);
        menuTable.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {
                onEditMenu();
            }
        });

        }


    private void onEditMenu() {
        if (menuTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<MenuItem> selectedMenuItem = menuTable.getSelectionModel().getSelectedItem();
            foodName.setText(selectedMenuItem.getValue().getFoodName());
            stockAvailable.setText("" + selectedMenuItem.getValue().getStockAvailable());
            calories.setText("" + selectedMenuItem.getValue().getCalories());
            descriptionItem.setText(selectedMenuItem.getValue().getDescription());
            vegan.setSelected(selectedMenuItem.getValue().getVegan());
            diabetic.setSelected(selectedMenuItem.getValue().getDiabetic());
            gluttenfree.setSelected(selectedMenuItem.getValue().getGluttenFree());
            priceEditText.setText("" + selectedMenuItem.getValue().getPrice());
        }
    }
    public void addMenu() {
        TreeItem<MenuItem> selectedMenuItem = menuTable.getSelectionModel().getSelectedItem();
        MenuItem newMenuItem = new MenuItem(foodName.getText(),descriptionItem.getText(),
               Integer.parseInt(stockAvailable.getText()),Integer.parseInt(calories.getText()),
                vegan.isSelected(),diabetic.isSelected(),gluttenfree.isSelected(),Integer.parseInt(priceEditText.getText()));
        menuRoot.getChildren().add(new TreeItem<>(newMenuItem));
        clearMenu();

    }
    public void deleteMenu() {
        TreeItem<MenuItem> selectedMenuItem = menuTable.getSelectionModel().getSelectedItem();
        menuController.removeMenuItem(selectedMenuItem.getValue());
        menuRoot.getChildren().remove(selectedMenuItem);
        clearMenu();

    }
    public void editMenu() {
        TreeItem<MenuItem> selectedItem = menuTable.getSelectionModel().getSelectedItem();
        MenuItem modifiedItem = new MenuItem(foodName.getText(),descriptionItem.getText(),
                Integer.parseInt(stockAvailable.getText()),Integer.parseInt(calories.getText()),
                vegan.isSelected(),diabetic.isSelected(),gluttenfree.isSelected(),Integer.parseInt(priceEditText.getText()));
        menuController.modifyMenuItem(modifiedItem);

        menuRoot.getChildren().remove(selectedItem);
        menuRoot.getChildren().add(new TreeItem<>(modifiedItem));
        clearMenu();

    }

    public void clearMenu(){
        foodName.setText(foodName.getPromptText());
        descriptionItem.setText("");
        stockAvailable.setText(stockAvailable.getPromptText());
        calories.setText(stockAvailable.getPromptText());
        vegan.setSelected(false);
        diabetic.setSelected(false);
        gluttenfree.setSelected(false);
        priceEditText.setText(priceEditText.getPromptText());

    }
    private void initializeRequestsTab(){
        foodRequestManager.update();

        for (FoodRequest foodRequest : foodRequestManager.getRequests()){
            requestsRoot.getChildren().add(new TreeItem<>(foodRequest));
        }

        requestNameColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> {
                    return new ReadOnlyStringWrapper(param.getValue().getValue().getName());
                });
        timeCreatedColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
        timeCompletedColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCompleted().toString()));
        requestTypeColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getType()));
        descriptionRequestColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getDescription()));
        locationColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> {
                    if (param.getValue().getValue().getNode() == null){
                        return null;
                    }else
                    return new ReadOnlyStringWrapper(param.getValue().getValue().getNode().getLongName());
                });
        assignedWorkerColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> {
                    if (param.getValue().getValue().getAssignedWorker() == null){
                        return null;
                    }else
                        return new ReadOnlyObjectWrapper(param.getValue().getValue().getAssignedWorker().getWorkerID());
                });


        requestsTable.setRoot(requestsRoot);
        requestsTable.setShowRoot(false);
        requestsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {
                onEditRequests();
            }
        });

    }
    private void onEditRequests() {
        if (requestsTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<FoodRequest> selectedFoodRequest = requestsTable.getSelectionModel().getSelectedItem();
            requestOrder.setText(selectedFoodRequest.getValue().getOrder().toString());
        }

    }
    public void deleteRequest() {
        TreeItem<FoodRequest> selectedFoodRequest = requestsTable.getSelectionModel().getSelectedItem();
        requestController.deleteRequest(selectedFoodRequest.getValue());
        requestsRoot.getChildren().remove(selectedFoodRequest);
        cancelRequest();


    }
    public void deleteAllRequests() {
        requestsRoot.getChildren().clear();

    }
    public void cancelRequest() {
        requestOrder.setText("");

    }
    private void initializeWorkerTab(){
         workerManager.update();

        for (Worker worker : workerManager.getWorkers()){
            workerRoot.getChildren().add(new TreeItem<Worker>(worker));
        }
        workerIDColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Worker, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getWorkerID()));
        usernameColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Worker, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getUsername()));

        workersTable.setRoot(workerRoot);
        workersTable.setShowRoot(false);
        workersTable.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {
                onEditWorkers();
            }
        });

    }

    private void onEditWorkers() {
        if (workersTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<Worker> selectedWorker = workersTable.getSelectionModel().getSelectedItem();
            username.setText(selectedWorker.getValue().getUsername());
            workerID.setText(selectedWorker.getValue().getWorkerID());
        }
    }
    public void addWorker() {
        workerController.addWorker(username.getText());
        workerRoot.getChildren().add(new TreeItem<>(workerController.getWorkerbyName(username.getText())));
        clearWorker();

    }
    public void deleteWorker() {
        TreeItem<Worker> selectedWorker = workersTable.getSelectionModel().getSelectedItem();
        workerManager.removeWorker(selectedWorker.getValue());
        username.setText(username.getPromptText());
        workerID.setText("Worker ID");
        workerRoot.getChildren().remove(selectedWorker);
        clearWorker();

    }
    public void editWorker() {
        TreeItem<Worker> selectedWorker = workersTable.getSelectionModel().getSelectedItem();
//        if (username.getText() == null){
//            return new JFXAlert<>()
//        }
        Worker modifiedWorker = new Worker(selectedWorker.getValue().workerID,username.getText());
        workerController.modifyWorker(modifiedWorker);

        workerRoot.getChildren().remove(selectedWorker);
        workerRoot.getChildren().add(new TreeItem<>(modifiedWorker));
        clearWorker();

    }
    public void clearWorker(){
        username.setText(username.getPromptText());
        workerID.setText("Worker ID");

    }

    public void exportMenuItems() {
        String path = fileSelector.selectFile();
        try {
            csvController.saveMenuItems(path);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CSV File Populated");
            alert.setHeaderText(null);
            alert.setContentText("The CSV file " + path + " has been populated with all Nodes!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportWorkerLogs() {
        String path = fileSelector.selectFile();
        try {
            csvController.saveWorkerLogs(path);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CSV File Populated");
            alert.setHeaderText(null);
            alert.setContentText("The CSV file " + path + " has been populated with all Nodes!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportFoodLogs() {
        String path = fileSelector.selectFile();
        try {
            csvController.saveFoodLogs(path);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CSV File Populated");
            alert.setHeaderText(null);
            alert.setContentText("The CSV file " + path + " has been populated with all Nodes!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

