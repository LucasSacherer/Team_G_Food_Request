package Boundary2.sceneControllers;


import Controller2.MenuController;
import Controller2.ReportController;
import Entity2.*;
import Manager2.FoodLogManager;
import Manager2.ImageManager;
import Manager2.MenuItemManager;
import Manager2.NodeManager;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportsController {
    //** Controllers **//

    //** Managers **//
    ImageManager imageManager;
    FoodLogManager foodLogManager;
    MenuItemManager mim;
    MenuController menuController;
    //** Map **//
    ScrollPane scrollPane;
    ImageView imageView;
    Canvas canvas;
    GraphicsContext gc;
    Pane mapPane;
    ReportController rc;
    NodeManager nodeManager;
    private Label currentFloorNum;
    private JFXSlider zoomSlider;
    JFXTreeTableView<DensityNode> reportsTable;
    TreeTableColumn<DensityNode, String> locationRequestsColumn;
    TreeTableColumn<DensityNode, Integer> numberRequestsColumn;
    private TreeItem<DensityNode> root = new TreeItem<>();

    JFXTreeTableView<Slice> foodOrders;
    TreeTableColumn<Slice, String> menuFoodColumn;
    TreeTableColumn<Slice, Integer> menuFoodOrdersColumn;
    private TreeItem<Slice> pieroot = new TreeItem<>();

    PieChart orderItemsPieChart;
    private String currentFloor;

    private BarChart<String,Number> bc;
    private NumberAxis yAxis;
    private CategoryAxis xAxis;

    JFXTreeTableView<TypeData> typeTable;
    TreeTableColumn<TypeData, String> typeofOrder;
    TreeTableColumn<TypeData, Integer> numberofTypeOrders;
    private TreeItem<TypeData> barroot = new TreeItem<>();

    public ReportsController(ScrollPane scrollPane, ImageManager imageManager, FoodLogManager foodLogManager,
                             NodeManager nodeManager, JFXTreeTableView<DensityNode> reportsTable,
                             TreeTableColumn<DensityNode, String> locationRequestsColumn,
                             TreeTableColumn<DensityNode, Integer> numberRequestsColumn,
                             JFXTreeTableView<Slice> foodOrders, TreeTableColumn<Slice, String> menuFoodColumn,
                             TreeTableColumn<Slice, Integer> menuFoodOrdersColumn, PieChart orderItemsPieChart, Label currentFloorNum, JFXSlider zoomSlider,
                             BarChart<String,Number> bc, NumberAxis yAxis, CategoryAxis xAxis, MenuItemManager mim,
                             JFXTreeTableView<TypeData> typeTable,TreeTableColumn<TypeData,String> typeofOrder,TreeTableColumn<TypeData, Integer> numberofOrders){
        this.scrollPane = scrollPane;
        this.imageManager = imageManager;
        this.foodLogManager = foodLogManager;
        this.nodeManager = nodeManager;
        this.mim = mim;
        this.menuController = new MenuController(mim);
        rc  = new ReportController(foodLogManager,menuController);
        this.reportsTable = reportsTable;
        this.locationRequestsColumn = locationRequestsColumn;
        this.numberRequestsColumn = numberRequestsColumn;
        this.foodOrders = foodOrders;
        this.menuFoodColumn = menuFoodColumn;
        this.menuFoodOrdersColumn = menuFoodOrdersColumn;
        this.orderItemsPieChart = orderItemsPieChart;
        this.currentFloorNum = currentFloorNum;
        this.zoomSlider = zoomSlider;
        this.bc = bc;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.typeTable = typeTable;
        this.typeofOrder = typeofOrder;
        this.numberofTypeOrders = numberofOrders;
    }

    public void initialize(){
        initializeMap();
        initializeTable();
        initializePieTable();
        initializePieChart();
        initializeBarChart();
        initializrBarTable();
    }

    private void initializePieTable(){
        List<Slice> slices = rc.getPieSlices();
        for (Slice s: slices){
            pieroot.getChildren().add(new TreeItem<>(s));
        }
        menuFoodColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Slice, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getName()));
        menuFoodOrdersColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Slice, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getQty()));
        foodOrders.setRoot(pieroot);
        foodOrders.setShowRoot(false);
    }
    private void initializeTable(){
        List<DensityNode> nodes = rc.getRequestDensity();
        for (DensityNode densityNode: nodes){
            root.getChildren().add(new TreeItem<>(densityNode));
        }
        locationRequestsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<DensityNode, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));
        numberRequestsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<DensityNode, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getDensity()));
        reportsTable.setRoot(root);
        reportsTable.setShowRoot(false);
    }
    private void initializrBarTable(){
        List<TypeData> type = rc.getBarChartdata();
        for (TypeData t: type){
            barroot.getChildren().add(new TreeItem<>(t));
        }
        typeofOrder.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<TypeData, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getType()));
        numberofTypeOrders.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<TypeData, Integer> param) -> new ReadOnlyObjectWrapper(param.getValue().getValue().getAmount()));
        typeTable.setRoot(barroot);
        typeTable.setShowRoot(false);
    }
    private void initializeMap(){
        Group group = new Group();
        imageView = new ImageView();
        canvas = new Canvas();
        canvas.setHeight(3400);
        canvas.setWidth(5000);
        gc = canvas.getGraphicsContext2D();
        mapPane = new Pane();
        mapPane.getChildren().add(imageView);
        mapPane.getChildren().add(1,canvas);

        group.getChildren().add(mapPane);
        scrollPane.setContent(group);
        currentFloor = "G";
        imageView.setImage(imageManager.getImage(currentFloor));
        currentFloorNum.setText(currentFloor);
        mapPane.setScaleX(0.5);
        mapPane.setScaleY(0.5);
        scrollPane.setPannable(true);
        drawDensity();

    }
    private void initializePieChart(){
        List<Slice> slices = rc.getPieSlices();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(Slice s: slices){
            pieChartData.add(new PieChart.Data(s.getName(),s.getQty()));
        }
        orderItemsPieChart.setData(pieChartData);

    }

    private void initializeBarChart(){

        xAxis.setLabel("Type");
        yAxis.setLabel("# of Orders");

        List<TypeData> data = rc.getBarChartdata();
        XYChart.Series dataSeries1 = new XYChart.Series();
        List<String> typesUsed = new ArrayList<>();
        for(TypeData t: data){
            dataSeries1.getData().add(new XYChart.Data(t.getType(),t.getAmount()));
            typesUsed.add(t.getType());
        }
        if(!typesUsed.contains("Diabetic")){
            dataSeries1.getData().add(new XYChart.Data("Diabetic",0));
        }
        else if(!typesUsed.contains("Vegan")){
            dataSeries1.getData().add(new XYChart.Data("Vegan",0));
        }
        else if(!typesUsed.contains("Gluten Free")){
            dataSeries1.getData().add(new XYChart.Data("Gluten Free",0));
        }
        bc.setStyle("CHART_COLOR_1: #1e90ff;");
        bc.getData().add(dataSeries1);
        //get numbers of each category


    }

    private  void drawDensity(){
        List<DensityNode> nodes = rc.getRequestDensity();

        for (DensityNode dn: nodes){

            Node node = nodeManager.getNode(dn.getNodeID());
            if(node.getFloor().equals(currentFloor)) {
                int size = (dn.getDensity() * 20);
                gc.setFill(Color.BLACK);
                gc.fillOval(node.getXcoord() - (size / 2) - 1, node.getYcoord() - (size / 2) - 1, size + 5, size + 5);
                gc.setFill(Color.BLUEVIOLET);
                gc.fillOval(node.getXcoord() - (size / 2), node.getYcoord() - (size / 2), size, size);
            }
        }
    }

    public void reportsToHub() {}

    public void floorDown() throws IOException, SQLException {
        switch(currentFloor) {
            case "L2" :
                return;
            case "L1" :
                currentFloor = "L2";
                break;
            case "G" :
                currentFloor = "L1";
                break;
            case "1" :
                currentFloor = "G";
                break;
            case "2" :
                currentFloor = "1";
                break;
            case "3" :
                currentFloor = "2";
                break;
        }
        imageView.setImage(imageManager.getImage(currentFloor));
        currentFloorNum.setText(currentFloor);

        refreshCanvas();
    }
    private void refreshCanvas(){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        drawDensity();
    }
    public void floorUp() throws IOException, SQLException {
        switch (currentFloor) {
            case "3":
                return;
            case "L2":
                currentFloor = "L1";
                break;
            case "L1":
                currentFloor = "G";
                break;
            case "G":
                currentFloor = "1";
                break;
            case "1":
                currentFloor = "2";
                break;
            case "2":
                currentFloor = "3";
                break;
        }
        imageView.setImage(imageManager.getImage(currentFloor));
        currentFloorNum.setText(currentFloor);
        refreshCanvas();
    }
}
