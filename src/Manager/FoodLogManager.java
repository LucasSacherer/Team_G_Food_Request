package Manager;

import Database.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.MenuItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodLogManager {
    private DatabaseGargoyle databaseGargoyle;

    public FoodLogManager(DatabaseGargoyle databaseGargoyle) {
        this.databaseGargoyle = databaseGargoyle;
    }

    /**
     * Adds a each menu item in the Food Request to the food log table
     * @param foodRequest
     */
    public void addFoodLog(FoodRequest foodRequest){
        for (MenuItem item: foodRequest.getOrder()){
            databaseGargoyle.createConnection();
            databaseGargoyle.executeUpdateOnDatabase("INSERT INTO FOODLOG VALUES (" +
                    "'" + foodRequest.getName() + "', " +
                    "'" + foodRequest.getTimeCreated() + "', " +
                    "'" + foodRequest.getNode().getNodeID() + "')");
            databaseGargoyle.destroyConnection();
        }
    }

    /**
     * Returns the number of times the given food name appears in the foodlogs
     * @param foodName
     * @return
     */
    public int getCountOfFood(String foodName){
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT COUNT(*) " +
                "FROM FOODLOG " +
                "WHERE FOODNAME = '" + foodName + "'");
        databaseGargoyle.destroyConnection();
        try {
            if (rs.next()){
                return rs.getInt(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
