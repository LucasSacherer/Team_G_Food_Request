package ControllerTests;

import Controller.ReportController;
import Controller.RequestController;
import Controller.SearchEngine;
import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.MenuItem;
import Manager.*;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ReportControllerTest {

    @Test
    public void test1order(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        WorkerManager wm = new WorkerManager(databaseGargoyle);
        FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle,nodeManager,wm,menuItemManager,foodLogManager);
        WorkerLogManager wlm = new WorkerLogManager(databaseGargoyle);
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(wm);
        databaseGargoyle.attachManager(wlm);
        databaseGargoyle.attachManager(foodRequestManager);
        databaseGargoyle.notifyManagers();
        RequestController requestC = new RequestController(foodRequestManager,wlm,foodLogManager);
        MenuItem cake = new MenuItem("cake","chocolate cake", 3,50,true,false,false,3);
        /*String name, LocalDateTime timeCreated, LocalDateTime timeCompleted,
                       String type, String description, Node node, Worker worker, List<MenuItem> order*/
        ArrayList<MenuItem> originalOrder = new ArrayList<>();
        originalOrder.add(menuItemManager.getMenuItemByName("Milk"));
        originalOrder.add(menuItemManager.getMenuItemByName("Cereal"));
        originalOrder.add(menuItemManager.getMenuItemByName("Fruit"));
        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.000000000");

        int originalSize = foodLogManager.getFoodLog().size();
        FoodRequest foodrequest = new FoodRequest("test", time.toLocalDateTime(), time.toLocalDateTime(), "type", "description", nodeManager.getNode("GRETL03501"), null, originalOrder);

        requestC.addRequest(foodrequest);
        ReportController rc = new ReportController(foodLogManager);
        System.out.println(rc.getPieSlices());

    }

}