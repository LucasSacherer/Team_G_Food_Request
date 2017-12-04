package DatabaseTests;

import Database.DatabaseGargoyle;
import Manager.FoodRequestManager;
import Manager.MenuItemManager;
import Manager.NodeManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseGargoyleTests {
    //Test that the managers actually get added to the gargoyle's list
    @Test
    public void attachManagerTest(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nodeManager = new NodeManager(databaseGargoyle);

        //Before, the manager list should not contain the node manager
        assertEquals(0, databaseGargoyle.getManagers().size());

        databaseGargoyle.attachManager(nodeManager);

        //After, it should contain a manager
        assertEquals(1, databaseGargoyle.getManagers().size());
    }

    //Test that the managers actually get updated when notified
    @Test
    public void notifyManagersTest(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);

        //Before the notification, both managers should have empty lists
        assertTrue(nodeManager.getAllNodes().size() == 0);
        assertTrue(menuItemManager.getMenuItems().size() == 0);

        databaseGargoyle.notifyManagers();

        //After the notification, both managers should contain all their entities
        assertFalse(nodeManager.getAllNodes().size() == 0);
        assertFalse(menuItemManager.getMenuItems().size() == 0);
    }
}
