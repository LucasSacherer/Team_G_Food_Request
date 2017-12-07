package ControllerTests;

import Controller2.WorkerController;
import Database2.DatabaseGargoyle;
import Entity2.Worker;
import Manager2.FoodLogManager;
import Manager2.MenuItemManager;
import Manager2.NodeManager;
import Manager2.WorkerManager;
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
        System.out.println(wc.getWorkers().size());
        wc.addWorker(emp.username);
        System.out.println(wc.getWorkers().size());
        Worker fired = wc.getWorkerbyName("worker4");
        wc.removeWorker(fired);
        System.out.println(wc.getWorkers().size());
        Worker modEmp = new Worker("worker4", "jim");
        wc.modifyWorker(modEmp);
        int fsize = wc.getWorkers().size();
        assertEquals(2,fsize);
    }
}