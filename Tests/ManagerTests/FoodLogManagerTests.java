package ManagerTests;

import Database2.DatabaseGargoyle;
import Entity2.CartItem;
import Entity2.FoodRequest;
import Manager2.*;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FoodLogManagerTests {

    @Test
    public void testUpdateAndGetFoodLog() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);

        //Before the update, food log should be empty
        assertTrue(foodLogManager.getFoodLog().size() == 0);

        foodLogManager.update();

        //After the update, food log should not be empty
        assertFalse(foodLogManager.getFoodLog().size() == 0);
    }

    @Test
    public void testAddFoodLog(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.notifyManagers();

        ArrayList<CartItem> originalOrder = new ArrayList<>();
        originalOrder.add(new CartItem("Milk", 1));
        originalOrder.add(new CartItem("Cereal", 1));
        originalOrder.add(new CartItem("Fruit", 1));
        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.000000000");

        //Before the addition, note how big the food log is
        int originalSize = foodLogManager.getFoodLog().size();

        foodLogManager.addFoodLog(new FoodRequest("test", time.toLocalDateTime(), time.toLocalDateTime(), "type", "description", nodeManager.getNode("GRETL03501"), null, originalOrder));

        //After addition, confirm that it is in the food log and the database
        assertEquals(originalSize + 3, foodLogManager.getFoodLog().size());
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODLOG WHERE " +
                "FOODNAME = 'Milk' AND " +
                "TIMECREATED = '" + time + "' AND " +
                "NODEID = 'GRETL03501'");
        try {
            assertTrue(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
        databaseGargoyle.createConnection();
        ResultSet rs2 = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODLOG WHERE " +
                "FOODNAME = 'Cereal' AND " +
                "TIMECREATED = '" + time + "' AND " +
                "NODEID = 'GRETL03501'");
        try {
            assertTrue(rs2.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
        databaseGargoyle.createConnection();
        ResultSet rs3 = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODLOG WHERE " +
                "FOODNAME = 'Fruit' AND " +
                "TIMECREATED = '" + time + "' AND " +
                "NODEID = 'GRETL03501'");
        try {
            assertTrue(rs3.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        //Remove it from the database
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODLOG WHERE " +
                "FOODNAME = 'Milk' AND " +
                "TIMECREATED = '" + time + "' AND " +
                "NODEID = 'GRETL03501'");
        databaseGargoyle.destroyConnection();
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODLOG WHERE " +
                "FOODNAME = 'Cereal' AND " +
                "TIMECREATED = '" + time + "' AND " +
                "NODEID = 'GRETL03501'");
        databaseGargoyle.destroyConnection();
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODLOG WHERE " +
                "FOODNAME = 'Fruit' AND " +
                "TIMECREATED = '" + time + "' AND " +
                "NODEID = 'GRETL03501'");
        databaseGargoyle.destroyConnection();
        assertEquals(originalSize, foodLogManager.getFoodLog().size());
    }
}
