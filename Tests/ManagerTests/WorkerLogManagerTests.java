package ManagerTests;

import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.MenuItem;
import Manager.*;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WorkerLogManagerTests {
    @Test
    public void testUpdate(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        WorkerLogManager workerLogManager = new WorkerLogManager(databaseGargoyle);

        //Before the update, food log should be empty
        assertTrue(workerLogManager.getWorkerLog().size() == 0);

        workerLogManager.update();

        //After the update, food log should not be empty
        assertFalse(workerLogManager.getWorkerLog().size() == 0);
    }

    @Test
    public void testAddWorkerLog(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager, workerManager, menuItemManager, foodLogManager);
        WorkerLogManager workerLogManager = new WorkerLogManager(databaseGargoyle);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(foodRequestManager);
        databaseGargoyle.attachManager(workerLogManager);
        databaseGargoyle.notifyManagers();

        ArrayList<MenuItem> originalOrder = new ArrayList<>();
        originalOrder.add(menuItemManager.getMenuItemByName("Milk"));
        originalOrder.add(menuItemManager.getMenuItemByName("Cereal"));
        originalOrder.add(menuItemManager.getMenuItemByName("Fruit"));
        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.000000000");

        //Before the addition, note how big the food log is
        int originalSize = workerLogManager.getWorkerLog().size();

        workerLogManager.addWorkerLog(new FoodRequest("test", time.toLocalDateTime(), time.toLocalDateTime(), "type", "description", nodeManager.getNode("GRETL03501"), workerManager.getWorkerByID("worker1"), originalOrder));

        //After addition, see that the size changed
        assertEquals(originalSize + 1, workerLogManager.getWorkerLog().size());

        //Revert changes
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM WORKERLOG WHERE REQUESTNAME = 'test' AND WORKERID = 'worker1'");
        assertEquals(originalSize, workerLogManager.getWorkerLog().size());


    }
}
