package ManagerTests;

import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Manager.*;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FoodLogManagerTests {
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
        assertTrue(req.getOrder().contains("Milk"));
        assertTrue(req.getOrder().contains("Cereal"));
    }

    @Test
    public void testUpdateRequest() {
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
        ArrayList<String> originalOrder = new ArrayList<>();
        originalOrder.add("cheeseburger");
        originalOrder.add("cheeseburger");
        originalOrder.add("lasagna");
        ArrayList<String> newOrder = new ArrayList<>();
        newOrder.add("ham");
        newOrder.add("turkey");

        FoodRequest originalReq = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type1", "description1", nodeManager.getNode("GRETL03501"), workerManager.getWorkerByID("worker1"), originalOrder);
        FoodRequest newReq = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type2", "description2", nodeManager.getNode("WELEV00ML2"), workerManager.getWorkerByID("worker2"), newOrder);
        foodManager.updateRequest(newReq);
        FoodRequest testReq = foodManager.getFoodRequest("food1");
        assertEquals("type2", testReq.getType());
        assertEquals("description2", testReq.getDescription());
        assertEquals("WELEV00ML2", testReq.getNode().getNodeID());
        assertEquals("admin2", testReq.getUser().getUserID());
        assertTrue(testReq.getOrder().contains("ham"));

        //Revert update
        foodManager.updateRequest(originalReq);
        FoodRequest testReq2 = foodManager.getFoodRequest("food1");
        assertEquals("type1", testReq2.getType());
        assertEquals("description1", testReq2.getDescription());
        assertEquals("GRETL03501", testReq2.getNode().getNodeID());
        assertEquals("admin1", testReq2.getUser().getUserID());
        assertTrue(testReq2.getOrder().contains("cheeseburger"));
    }

    @Test
    public void testCompleteRequest() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nm = new NodeManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle);
        FoodManager foodManager = new FoodManager(databaseGargoyle, nm,um);
        databaseGargoyle.attachManager(nm);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(foodManager);
        databaseGargoyle.notifyManagers();

        ArrayList<String> originalOrder = new ArrayList<>();
        originalOrder.add("cheeseburger");
        originalOrder.add("cheeseburger");
        originalOrder.add("lasagna");
        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.000000000");

        //Test that before completing the request, the times are equal
        FoodRequest testReq1 = foodManager.getFoodRequest("food1");
        assertEquals(testReq1.getTimeCreated(), testReq1.getTimeCompleted());
        assertTrue(testReq1.getOrder().contains("lasagna"));

        //Complete the request, and then check that it has been completed and removed from the list
        FoodRequest completedRequest = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type1", "description1", nm.getNode("GRETL03501"), um.getUser("admin1"), originalOrder);
        foodManager.completeRequest(completedRequest);
        assertEquals(null, foodManager.getFoodRequest("food1"));

        //Revert changes
        FoodRequest original = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type1", "description1", nm.getNode("GRETL03501"), um.getUser("admin1"), originalOrder);
        foodManager.updateRequest(original);
    }

    @Test
    public void testAddDeleteRequest() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nm = new NodeManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle);
        FoodManager foodManager = new FoodManager(databaseGargoyle, nm,um);
        databaseGargoyle.attachManager(nm);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(foodManager);
        databaseGargoyle.notifyManagers();

        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        ArrayList<String> order = new ArrayList<>();
        order.add("food1");
        order.add("food2");
        FoodRequest request = new FoodRequest("name", time.toLocalDateTime(),
                null, "type", "description", nm.getNode("IHALL01303"), um.getUser("admin1"), order);
        foodManager.addRequest(request);
        FoodRequest testReq = foodManager.getFoodRequest("name");
        assertEquals("type", testReq.getType());
        assertEquals("description", testReq.getDescription());
        assertEquals("IHALL01303", testReq.getNode().getNodeID());
        assertEquals("admin1", testReq.getUser().getUserID());
        assertEquals(2, testReq.getOrder().size());
        assertTrue(testReq.getOrder().contains("food2"));

        //Revert changes
        foodManager.deleteRequest(request);
        assertEquals(null, foodManager.getFoodRequest("name"));
    }

    @Test
    public void testGetCompleted() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nm = new NodeManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle);
        FoodManager foodManager = new FoodManager(databaseGargoyle, nm,um);
        databaseGargoyle.attachManager(nm);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(foodManager);
        databaseGargoyle.notifyManagers();

        ArrayList<FoodRequest> completed = foodManager.getCompleted();
        assertTrue(completed.get(0).getName().equals("food2"));
    }

    @Test
    public void testGetRequestsBy() {
        //Run testCompleteRequest first!
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        UserManager userManager = new UserManager(databaseGargoyle);
        FoodManager fManager = new FoodManager(databaseGargoyle, nodeManager,userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(fManager);
        databaseGargoyle.notifyManagers();


        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        ArrayList<String> order = new ArrayList<>();
        order.add("food1");
        order.add("food2");
        FoodRequest request = new FoodRequest("name", time.toLocalDateTime(),
                time.toLocalDateTime(), "type", "description", nodeManager.getNode("IHALL01303"), userManager.getUser("admin1"), order);

        fManager.deleteRequest(request);

        fManager.addRequest(request);

        List<FoodRequest> requestsByUser = fManager.getRequestsBy(userManager.getUser("admin1"));

        System.out.println(fManager.getRequests());
        System.out.println(requestsByUser);

        assertTrue(requestsByUser.size() == 2);
        assertTrue(requestsByUser.get(0).getUser().getUserID().equals("admin1"));

        requestsByUser = fManager.getRequestsBy(userManager.getUser("staff1"));

        System.out.println(requestsByUser);

        assertTrue(requestsByUser.size() == 0);

        fManager.deleteRequest(request);
    }
}
