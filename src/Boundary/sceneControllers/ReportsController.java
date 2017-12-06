package Boundary.sceneControllers;


import Controller.ReportController;
import Entity.DensityNode;
import Entity.FoodLog;
import Entity.Node;
import Manager.FoodLogManager;
import Manager.ImageManager;
import Manager.NodeManager;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;

public class ReportsController {
    //** Controllers **//

    //** Managers **//
    ImageManager imageManager;
    FoodLogManager foodLogManager;
    //** Map **//
    ScrollPane scrollPane;
    ImageView imageView;
    Canvas canvas;
    GraphicsContext gc;
    Pane mapPane;
    ReportController rc;
    NodeManager nodeManager;


    public ReportsController(ScrollPane scrollPane, ImageManager imageManager, FoodLogManager foodLogManager, NodeManager nodeManager){
        this.scrollPane = scrollPane;
        this.imageManager = imageManager;
        this.foodLogManager = foodLogManager;
        this.nodeManager = nodeManager;
        rc  = new ReportController(foodLogManager);
    }

    public void initialize(){
        initializeMap();
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
        imageView.setImage(imageManager.getImage("1"));
        mapPane.setScaleX(0.5);
        mapPane.setScaleY(0.5);
        scrollPane.setPannable(true);
        List<DensityNode> nodes = rc.getRequestDensity();

        for (DensityNode dn: nodes){
            Node node = nodeManager.getNode(dn.getNodeID());
            int size = (dn.getDensity() * 20);
            gc.setFill(Color.BLUEVIOLET);
            gc.fillOval(node.getXcoord(),node.getYcoord(),size,size);
        }

    }

    public void reportsToHub() {}
}
