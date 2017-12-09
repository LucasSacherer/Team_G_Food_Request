package Boundary2.sceneControllers;

import Controller2.RequestController;
import Controller2.WorkerController;
import Entity2.CartItem;
import Entity2.FoodRequest;
import Entity2.Worker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import java.time.LocalDateTime;
import java.util.List;

public class FoodRequestHubController {

    private RequestController requestController;
    private WorkerController workerController;
    private JFXComboBox employeeToAssign, filterRequests;

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
                                    TreeTableColumn<FoodRequest, String> assignedEmployeeColumn, JFXTextArea assignedOrdersInfo, WorkerController workerController, JFXComboBox filterRequests) {

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
        this.filterRequests = filterRequests;
        filterRequests.getItems().add("None");


    }

    public void initialize() {
        initializeOrdersAssign();
        initializeOrdersAssigned();
        for (Worker worker : workerController.getWorkers()) {
            employeeToAssign.getItems().add(worker.getUsername());
        }
        for (Worker worker : workerController.getWorkers()) {
            filterRequests.getItems().add(worker.getUsername());
        }


    }

    private void initializeOrdersAssign() {
        for (FoodRequest foodRequest : requestController.getRequests()) {
            if (foodRequest.getAssignedWorker() == null) {
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
            List<CartItem> list = selectedFoodRequest.getValue().getOrder();
            String display = "";
            for (CartItem item : list) {
                display += item.toString();
            }
            unassignedOrderInfo.setText(display);
        }

    }

    private void initializeOrdersAssigned() {
        for (FoodRequest foodRequest : requestController.getRequests()) {
            if (foodRequest.getAssignedWorker() != null) {
                foodRequestAssignedRoot.getChildren().add(new TreeItem<>(foodRequest));
            }
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
            List<CartItem> list = selectedFoodRequest.getValue().getOrder();
            String display = "";
            for (CartItem item : list) {
                display += item.toString();
            }
            assignedOrdersInfo.setText(display);
        }
    }

    public void assignEmployee() {
        if (employeeToAssign.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "There is employee assigned");
            error.show();
        } else {
            TreeItem<FoodRequest> selectedFoodRequest = ordersAssignTable.getSelectionModel().getSelectedItem();
            //(String name, LocalDateTime timeCreated, LocalDateTime timeCompleted,
            //        String type, String description, Node node, Worker worker, List<CartItem> order)
            FoodRequest assignedFoodRequest = new FoodRequest(selectedFoodRequest.getValue().getName(), selectedFoodRequest.getValue().getTimeCreated(),
                    selectedFoodRequest.getValue().getTimeCompleted(), selectedFoodRequest.getValue().getType(), selectedFoodRequest.getValue().getDescription(),
                    selectedFoodRequest.getValue().getNode(), workerController.getWorkerbyName(employeeToAssign.getSelectionModel().getSelectedItem().toString()), selectedFoodRequest.getValue().getOrder());
            requestController.assignWorker(assignedFoodRequest, workerController.getWorkerbyName(employeeToAssign.getSelectionModel().getSelectedItem().toString()));
            foodRequestAssignRoot.getChildren().remove(selectedFoodRequest);
            foodRequestAssignedRoot.getChildren().add(new TreeItem<>(assignedFoodRequest));
        }
    }

    public void completeOrder() {
        if (assignedOrdersTable.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "There is no selected order to complete");
            error.show();
        } else

        {
            TreeItem<FoodRequest> selectedFoodRequest = assignedOrdersTable.getSelectionModel().getSelectedItem();
            FoodRequest completedFoodRequest = new FoodRequest(selectedFoodRequest.getValue().getName(), selectedFoodRequest.getValue().getTimeCreated(),
                    LocalDateTime.now(), selectedFoodRequest.getValue().getType(), selectedFoodRequest.getValue().getDescription(),
                    selectedFoodRequest.getValue().getNode(), selectedFoodRequest.getValue().getAssignedWorker(), selectedFoodRequest.getValue().getOrder());
            requestController.completeRequest(completedFoodRequest);
            foodRequestAssignedRoot.getChildren().remove(selectedFoodRequest);
            requestController.addRequest(completedFoodRequest);
        }
    }

    public void filterRequests() {
        if (filterRequests.getSelectionModel().getSelectedItem().toString() == "None"){
            foodRequestAssignedRoot.getChildren().clear();
            initializeOrdersAssigned();
        }else {

            String worker = filterRequests.getSelectionModel().getSelectedItem().toString();
            Worker filterWorker = workerController.getWorkerbyName(worker);
            foodRequestAssignedRoot.getChildren().clear();
            for (FoodRequest foodRequest : requestController.getRequestsByWorker(filterWorker)) {
                System.out.println(requestController.getRequestsByWorker(filterWorker));
                foodRequestAssignedRoot.getChildren().add(new TreeItem<>(foodRequest));
            }
        }
    }
}
