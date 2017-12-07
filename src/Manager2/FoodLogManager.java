package Manager2;

import Database2.DatabaseGargoyle;
import Entity2.CartItem;
import Entity2.FoodLog;
import Entity2.FoodRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FoodLogManager implements EntityManager {
    private DatabaseGargoyle databaseGargoyle;
    private List<FoodLog> foodLog;

    public FoodLogManager(DatabaseGargoyle databaseGargoyle) {
        this.foodLog = new ArrayList<>();
        this.databaseGargoyle = databaseGargoyle;
    }

    /**
     * Updates the food log to be up to date with the database
     */
    public void update(){
        String foodName, nodeID;
        LocalDateTime timeCreated;
        foodLog.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODLOG");
        try {
            while (rs.next()) {
                foodName = rs.getString("FOODNAME");
                timeCreated = rs.getTimestamp("TIMECREATED").toLocalDateTime();
                nodeID = rs.getString("NODEID");
                foodLog.add(new FoodLog(foodName, timeCreated, nodeID));
            }
        }catch (SQLException ex){
            System.out.println("Failed to update the Food Log!");
            ex.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Adds a each menu item in the Food Request to the food log table
     * @param foodRequest
     */
    public void addFoodLog(FoodRequest foodRequest){
        for (CartItem item: foodRequest.getOrder()){
            for (int i = 0; i < item.getQuantity(); i++){
                databaseGargoyle.createConnection();
                databaseGargoyle.executeUpdateOnDatabase("INSERT INTO FOODLOG VALUES (" +
                        "'" + item.getFoodNameCart() + "', " +
                        "'" + Timestamp.valueOf(foodRequest.getTimeCreated()) + "', " +
                        "'" + foodRequest.getNode().getNodeID() + "')");
                databaseGargoyle.destroyConnection();
            }
        }
    }

    /**
     * Returns the food log
     * @return
     */
    public List<FoodLog> getFoodLog() {
        return foodLog;
    }
}
