package ControllerTests;

import Controller.CartController;
import Controller.MenuController;
import Controller.SearchEngine;
import Controller.WorkerController;
import Database.DatabaseGargoyle;
import Entity.MenuItem;
import Entity.Worker;
import Manager.FoodLogManager;
import Manager.MenuItemManager;
import Manager.NodeManager;
import Manager.WorkerManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerControllerTest {
    @Test
    public void testAddRemove(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.notifyManagers();

        WorkerController wc = new WorkerController(workerManager);

        Worker emp = new Worker("worker4", "worker4");
        //System.out.println(wc.getWorkers().size());
        wc.addWorker(emp);
        //System.out.println(wc.getWorkers().size());
        wc.removeWorker(emp);
        //System.out.println(wc.getWorkers().size());
        Worker modEmp = new Worker("worker4", "jim");
        wc.modifyWorker(modEmp);
        int fsize = wc.getWorkers().size();
        assertEquals(2,fsize);
    }
}