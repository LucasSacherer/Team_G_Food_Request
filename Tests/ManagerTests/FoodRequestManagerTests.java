package ManagerTests;

import Database2.DatabaseGargoyle;
import Entity2.CartItem;
import Entity2.FoodRequest;
import Manager2.*;
import org.junit.Test;
import java.sql.Timestamp;
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
        assertEquals("Fruit", req.getOrder().get(0).getFoodNameCart());
        assertEquals("Cereal", req.getOrder().get(1).getFoodNameCart());
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

        ArrayList<CartItem> originalOrder = new ArrayList<>();
        originalOrder.add(new CartItem("Milk", 1));
        originalOrder.add(new CartItem("Cereal", 1));
        originalOrder.add(new CartItem("Fruit", 1));
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
        ArrayList<CartItem> order = new ArrayList<>();
        order.add(new CartItem("Pudding", 1));
        order.add(new CartItem("Chicken", 1));
        FoodRequest request = new FoodRequest("name", time.toLocalDateTime(), time.toLocalDateTime(),
                "type", "description", nodeManager.getNode("IHALL01303"),
                workerManager.getWorkerByID("worker1"), order);

        foodRequestManager.addRequest(request);

        FoodRequest testReq = foodRequestManager.getFoodRequest("name");
        assertEquals("type", testReq.getType());
        assertEquals("description", testReq.getDescription());
        assertEquals("IHALL01303", testReq.getNode().getNodeID());
        assertEquals("worker1", testReq.getAssignedWorker().getWorkerID());
        assertEquals(2, testReq.getOrder().size());
        assertEquals("Pudding", testReq.getOrder().get(0).getFoodNameCart());
        assertEquals("Chicken", testReq.getOrder().get(1).getFoodNameCart());

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
