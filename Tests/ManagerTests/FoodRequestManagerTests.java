package ManagerTests;

import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.MenuItem;
import Manager.*;
import org.junit.Test;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FoodRequestManagerTests {
    @Test
    public void getFoodOrdersTest() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager, workerManager, menuItemManager, foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(foodRequestManager);
        databaseGargoyle.notifyManagers();

        FoodRequest foodRequest = foodRequestManager.getFoodRequest("food1");
        assertEquals(3, foodRequestManager.getFoodOrders("food1", Timestamp.valueOf(foodRequest.getTimeCreated())).size());
    }

    @Test
    public void testUpdateRequests() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager, workerManager, menuItemManager, foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(foodRequestManager);
        databaseGargoyle.notifyManagers();

        //Check if the Food Request is there before update
        assertEquals(null, foodRequestManager.getFoodRequest("food2"));

        //Check if the user is there after update
        FoodRequest req = foodRequestManager.getFoodRequest("food1");
        assertEquals("food1", req.getName());
        assertEquals("type1", req.getType());
        assertEquals("description1", req.getDescription());
        assertEquals("GRETL03501", req.getNode().getNodeID());
        assertEquals("worker1", req.getAssignedWorker().getWorkerID());
        assertEquals(3, req.getOrder().size());
        assertTrue(req.getOrder().contains(menuItemManager.getMenuItemByName("Milk")));
        assertTrue(req.getOrder().contains(menuItemManager.getMenuItemByName("Cereal")));
    }

    @Test
    public void testCompleteRequest() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager, workerManager, menuItemManager, foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(foodRequestManager);
        databaseGargoyle.notifyManagers();

        ArrayList<MenuItem> originalOrder = new ArrayList<>();
        originalOrder.add(menuItemManager.getMenuItemByName("Milk"));
        originalOrder.add(menuItemManager.getMenuItemByName("Cereal"));
        originalOrder.add(menuItemManager.getMenuItemByName("Fruit"));
        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.000000000");

        //Test that before completing the request, the times are equal
        FoodRequest testReq1 = foodRequestManager.getFoodRequest("food1");
        assertEquals(testReq1.getTimeCreated(), testReq1.getTimeCompleted());

        //Complete the request, and then check that it has been completed and removed from the list
        FoodRequest completedRequest = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type1", "description1", nodeManager.getNode("GRETL03501"), workerManager.getWorkerByID("worker1"), originalOrder);
        foodRequestManager.completeRequest(completedRequest);
        assertEquals(null, foodRequestManager.getFoodRequest("food1"));

        //Revert changes
        FoodRequest original = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type1", "description1", nodeManager.getNode("GRETL03501"), workerManager.getWorkerByID("worker1"), originalOrder);
        foodRequestManager.revertFoodRequest(original);
        assertEquals(foodRequestManager.getFoodRequest("food1").getTimeCreated(), foodRequestManager.getFoodRequest("food1").getTimeCompleted());
        assertEquals(3, foodRequestManager.getFoodRequest("food1").getOrder().size());
    }

    @Test
    public void testAddDeleteRequest() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager, workerManager, menuItemManager, foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(foodRequestManager);
        databaseGargoyle.notifyManagers();

        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.000000000");
        ArrayList<MenuItem> order = new ArrayList<>();
        order.add(menuItemManager.getMenuItemByName("Pudding"));
        order.add(menuItemManager.getMenuItemByName("Chicken"));
        FoodRequest request = new FoodRequest("name", time.toLocalDateTime(), time.toLocalDateTime(),
                "type", "description", nodeManager.getNode("IHALL01303"),
                workerManager.getWorkerByID("worker1"), order);

        foodRequestManager.addRequest(request);

        FoodRequest testReq = foodRequestManager.getFoodRequest("name");
        assertEquals("type", testReq.getType());
        assertEquals("description", testReq.getDescription());
        assertEquals("IHALL01303", testReq.getNode().getNodeID());
        assertEquals(null, testReq.getAssignedWorker());
        assertEquals(2, testReq.getOrder().size());
        assertTrue(testReq.getOrder().contains(menuItemManager.getMenuItemByName("Pudding")));
        assertTrue(testReq.getOrder().contains(menuItemManager.getMenuItemByName("Chicken")));

        //Revert changes
        foodRequestManager.deleteRequest(request);
        assertEquals(null, foodRequestManager.getFoodRequest("name"));
    }

    @Test
    public void testGetCompleted() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        FoodRequestManager foodRequestManager = new FoodRequestManager(databaseGargoyle, nodeManager, workerManager, menuItemManager, foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.attachManager(foodRequestManager);
        databaseGargoyle.notifyManagers();

        List<FoodRequest> completed = foodRequestManager.getCompleted();
        assertTrue(completed.get(0).getName().equals("food2"));
    }
}
