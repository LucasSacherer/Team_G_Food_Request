package Manager;

import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.MenuItem;
import Entity.Node;
import Entity.Worker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FoodRequestManager implements EntityManager{
    private List<FoodRequest> requests;
    private DatabaseGargoyle databaseGargoyle;
    private NodeManager nodeManager;
    private WorkerManager workerManager;
    private MenuItemManager menuItemManager;
    private FoodLogManager foodLogManager;

    public FoodRequestManager(DatabaseGargoyle databaseGargoyle, NodeManager nodeManager,
                              WorkerManager workerManager, MenuItemManager menuItemManager, FoodLogManager foodLogManager) {
        this.requests = new ArrayList<>();
        this.databaseGargoyle = databaseGargoyle;
        this.nodeManager = nodeManager;
        this.workerManager = workerManager;
        this.menuItemManager = menuItemManager;
        this.foodLogManager = foodLogManager;
    }

    /**
     * Updates requests to be up to date with the database
     */
    public void update(){
        String name, type, description, nodeID, workerID;
        LocalDateTime timeCreated, timeCompleted;
        Node node;
        Worker worker;
        List<MenuItem> order;
        requests.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODREQUEST");
        try {
            while (rs.next()){
                name = rs.getString("NAME");
                timeCreated = rs.getTimestamp("TIMECREATED").toLocalDateTime();
                timeCompleted = rs.getTimestamp("TIMECOMPLETED").toLocalDateTime();
                type = rs.getString("TYPE");
                description = rs.getString("DESCRIPTION");
                nodeID = rs.getString("NODEID");
                workerID = rs.getString("WORKERID");
                node = nodeManager.getNode(nodeID);
                if (workerID != null){
                    worker = workerManager.getWorkerByID(workerID);
                } else worker = null;
                order = getFoodOrders(name, Timestamp.valueOf(timeCreated));
                if(timeCreated.equals(timeCompleted)) {
                    requests.add(new FoodRequest(name, timeCreated, timeCompleted, type, description, node, worker, order));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get food requests from database!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Gets all the food orders in the database for the given request
     * @param requestName
     * @param timeCreated
     * @return
     */
    public ArrayList<MenuItem> getFoodOrders(String requestName, Timestamp timeCreated){
        ArrayList<MenuItem> result = new ArrayList<>();

        databaseGargoyle.createConnection();
        ResultSet orders = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODORDER " +
                "WHERE REQUESTNAME = '" + requestName + "' AND TIMECREATED = '" +timeCreated+ "'");
        try {
            while (orders.next()){
                result.add(menuItemManager.getMenuItemByName(orders.getString("FOODNAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        return result;
    }

    /**
     * Changes the completed time in the database for the given request, and removes the food
     *  orders attached to the request from the database
     * @param fReq
     */
    public void completeRequest(FoodRequest fReq){
        //Change the completed time of the database request
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE FOODREQUEST SET " +
                "TIMECOMPLETED = '" + Timestamp.valueOf(LocalDateTime.now()) + "' " +
                "WHERE NAME = '" + fReq.getName() + "' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'");
        databaseGargoyle.destroyConnection();

        //Remove all food orders of this request
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODORDER " +
                "WHERE REQUESTNAME = '" + fReq.getName() +"' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Adds the food request to the databaase, adds all its orders to the database, and
     *  changes the menu stock counts
     * @param fReq
     */
    public void addRequest(FoodRequest fReq){
        //Add the request to the FOODREQUEST table
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO FOODREQUEST VALUES (" +
                "'" + fReq.getName() + "'," +
                "'" + Timestamp.valueOf(fReq.getTimeCreated()) + "'," +
                "'" + Timestamp.valueOf(fReq.getTimeCreated()) + "'," +
                "'" + fReq.getType() + "'," +
                "'" + fReq.getDescription() + "'," +
                "'" + fReq.getNode().getNodeID() + "'," +
                "null)");
        databaseGargoyle.destroyConnection();

        //Add all food items to the FOODORDER table
        if (fReq.getOrder() != null) {
            for (MenuItem item : fReq.getOrder()) {
                databaseGargoyle.createConnection();
                databaseGargoyle.executeUpdateOnDatabase("INSERT INTO FOODORDER VALUES (" +
                        "'" + fReq.getName() + "','" + Timestamp.valueOf(fReq.getTimeCreated()) + "','" + item.getFoodName() + "')");
                databaseGargoyle.destroyConnection();
            }
        }

        //Update the Menu Item stock counts
        for (MenuItem item: fReq.getOrder()){
            menuItemManager.removeStock(item, 1);
        }

        //Add its order to the food log
        foodLogManager.addFoodLog(fReq);
    }

    /**
     * Reverts the changes to the menu stock counts, Removes all food orders for the given request,
     *  and deletes the request from the databasea
     * @param fReq
     */
    public void deleteRequest(FoodRequest fReq){
        //Update the Menu Item stock counts
        for (MenuItem item: fReq.getOrder()){
            menuItemManager.addStock(item, 1);
        }

        //Remove all food orders of this request from the database
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODORDER " +
                "WHERE REQUESTNAME = '" + fReq.getName() +"' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'");
        databaseGargoyle.destroyConnection();

        //Remove the request from the database
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODREQUEST " +
                "WHERE NAME = '" + fReq.getName() +"' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Returns the current list of requests
     * @return
     */
    public List<FoodRequest> getRequests() {
        return requests;
    }

    /**
     * Returns the list of all completed requests
     * @return
     */
    public List<FoodRequest> getCompleted(){
        return null;
    }

    /**
     * Assigns a worked to the specified FoodRequest
     * @param fReq The Food Request that you wish to assign a Worker to
     * @param worker The Worker you wish to assign to the Food Request
     */
    public void assignWorker(FoodRequest fReq, Worker worker){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE FOODREQUEST SET " +
                "WORKERID = '" + worker + "' " +
                "WHERE NAME = '" + fReq.getName() + "' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'");
        databaseGargoyle.destroyConnection();
    }
    /*
    public List<FoodRequest> getUnassignedRequests(String name){

    }
    public List<FoodRequest> getAssignedRequests(){

    }*/

    /**
     * Gets the food request according to the name given
     * @param foodName
     * @return
     */
    public FoodRequest getFoodRequest(String foodName){
        for (FoodRequest foodRequest: requests){
            if (foodRequest.getName().equals(foodName)){
                return foodRequest;
            }
        }
        return null;
    }
}
