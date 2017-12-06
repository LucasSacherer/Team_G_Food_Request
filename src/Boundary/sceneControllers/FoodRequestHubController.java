package Boundary.sceneControllers;

import Controller.RequestController;
import Controller.WorkerController;
import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.Worker;
import Manager.FoodRequestManager;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

public class FoodRequestHubController {

    private RequestController requestController;
    private WorkerController workerController;
    private JFXComboBox employeeToAssign = new JFXComboBox();

    private JFXTreeTableView<FoodRequest> ordersAssignTable;
    private TreeTableColumn<FoodRequest, String> orderNameAssignColumn;
    private TreeTableColumn<FoodRequest, String> timeOrderedColumn;
    private TreeTableColumn<FoodRequest, String> descriptionAssignColumn;
    private TreeTableColumn<FoodRequest, String> locationAssignColumn;
    private JFXTextArea unassignedOrderInfo;

    private JFXTreeTableView<FoodRequest> assignedOrdersTable;
    private TreeTableColumn<FoodRequest, String> orderNameAssignedColumn;
    private TreeTableColumn<FoodRequest, String> timeOrderedAssignedColumn;
    private TreeTableColumn<FoodRequest, String> locationAssignedColumn;
    private TreeTableColumn<FoodRequest, String> assignedEmployeeColumn;
    private JFXTextArea assignedOrdersInfo;

    private TreeItem<FoodRequest> foodRequestAssignRoot = new TreeItem<>();
    private TreeItem<FoodRequest> foodRequestAssignedRoot = new TreeItem<>();

    public FoodRequestHubController(RequestController requestController, JFXComboBox employeeToAssign, JFXTreeTableView<FoodRequest> ordersAssignTable,
                                    TreeTableColumn<FoodRequest, String> orderNameAssignColumn, TreeTableColumn<FoodRequest, String> timeOrderedColumn, TreeTableColumn<FoodRequest, String> descriptionAssignColumn,
                                    TreeTableColumn<FoodRequest, String> locationAssignColumn, JFXTextArea unassignedOrderInfo, JFXTreeTableView<FoodRequest> assignedOrdersTable, TreeTableColumn<FoodRequest, String> orderNameAssignedColumn,
                                    TreeTableColumn<FoodRequest, String> timeOrderedAssignedColumn, TreeTableColumn<FoodRequest, String> locationAssignedColumn,
                                    TreeTableColumn<FoodRequest, String> assignedEmployeeColumn, JFXTextArea assignedOrdersInfo, WorkerController workerController) {

        this.requestController = requestController;
        this.employeeToAssign = employeeToAssign;
        this.ordersAssignTable = ordersAssignTable;
        this.orderNameAssignedColumn = orderNameAssignedColumn;
        this.orderNameAssignColumn = orderNameAssignColumn;
        this.timeOrderedColumn = timeOrderedColumn;
        this.descriptionAssignColumn = descriptionAssignColumn;
        this.locationAssignColumn = locationAssignColumn;
        this.unassignedOrderInfo = unassignedOrderInfo;
        this.assignedOrdersTable = assignedOrdersTable;
        this.timeOrderedAssignedColumn = timeOrderedAssignedColumn;
        this.locationAssignedColumn = locationAssignedColumn;
        this.assignedEmployeeColumn = assignedEmployeeColumn;
        this.assignedOrdersInfo = assignedOrdersInfo;
        this.workerController = workerController;

    }

    public void initialize() {
        initializeOrdersAssign();
        initializeOrdersAssigned();

//        for (Worker worker : workerController.getWorkers()) {
//            employeeToAssign.getItems().add(worker);
//        }

    }

    private void initializeOrdersAssign() {
        for (FoodRequest foodRequest : requestController.getRequests()) {
            if (foodRequest.getAssignedWorker() == null){
                System.out.println("This is assigned");
            }else {
                foodRequestAssignRoot.getChildren().add(new TreeItem<>(foodRequest));
            }

        }

        orderNameAssignColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getName()));
        timeOrderedColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
        descriptionAssignColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getDescription()));
        locationAssignColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNode().getNodeID()));

        ordersAssignTable.setRoot(foodRequestAssignRoot);
        ordersAssignTable.setShowRoot(false);
        ordersAssignTable.setOnMouseClicked(event ->

    {
        if (event.getClickCount() > 1) {
            onEditAssignRequests();
        }
    });
}

    private void onEditAssignRequests() {
        if (ordersAssignTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<FoodRequest> selectedFoodRequest = ordersAssignTable.getSelectionModel().getSelectedItem();
            assignedOrdersInfo.setText(selectedFoodRequest.getValue().getOrder().toString());
        }

    }

    private void initializeOrdersAssigned() {
        for (FoodRequest foodRequest : requestController.getRequests()) {
//            if foodRequest.getAssignedWorker()
            foodRequestAssignedRoot.getChildren().add(new TreeItem<>(foodRequest));
        }

        orderNameAssignedColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getName()));
        timeOrderedAssignedColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
        locationAssignedColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNode().getNodeID()));
        assignedEmployeeColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedWorker().getUsername()));

        assignedOrdersTable.setRoot(foodRequestAssignedRoot);
        assignedOrdersTable.setShowRoot(false);
        assignedOrdersTable.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {
                onEditAssignedRequests();
            }
        });

    }

    private void onEditAssignedRequests() {
        if (assignedOrdersTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<FoodRequest> selectedFoodRequest = assignedOrdersTable.getSelectionModel().getSelectedItem();
            unassignedOrderInfo.setText(selectedFoodRequest.getValue().getOrder().toString());
        }

    }

    public void selectEmployeeToAssign() {

    }

    public void assignEmployee() {
    }

    public void completeOrder() {
    }
}
