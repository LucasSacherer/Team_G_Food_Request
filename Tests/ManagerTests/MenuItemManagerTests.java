package ManagerTests;

import Database.DatabaseGargoyle;
import Entity.MenuItem;
import Manager.MenuItemManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MenuItemManagerTests {

    @Test
    public void testUpdateAndGetMenuItems(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);

        //Before the update, food log should be empty
        assertTrue(menuItemManager.getMenuItems().size() == 0);

        menuItemManager.update();

        //After the update, food log should not be empty
        assertFalse(menuItemManager.getMenuItems().size() == 0);
    }

    @Test
    public void testGetMenuItemByName() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.notifyManagers();

        assertEquals("Bowl of cereal and milk", menuItemManager.getMenuItemByName("Cereal").getDescription());
    }

    @Test
    public void testModifyMenuItem() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.notifyManagers();

        MenuItem originalItem = menuItemManager.getMenuItemByName("Cereal");
        MenuItem modifiedItem = new MenuItem("Cereal", "itS FucKINg gOOD", 2, 6000, true, true, true);

        menuItemManager.modifyMenuItem(modifiedItem);

        //Test that the values changed
        MenuItem entity = menuItemManager.getMenuItemByName("Cereal");
        assertEquals("itS FucKINg gOOD", entity.getDescription());
        assertEquals(2, entity.getStockAvailable());
        assertEquals(6000, entity.getCalories());
        assertEquals(true, entity.getVegan());
        assertEquals(true, entity.getDiabetic());
        assertEquals(true, entity.getDiabetic());

        //Revert changes and test that its better now
        menuItemManager.modifyMenuItem(originalItem);
        entity = menuItemManager.getMenuItemByName("Cereal");
        assertEquals("Bowl of cereal and milk", entity.getDescription());
        assertEquals(40, entity.getStockAvailable());
        assertEquals(200, entity.getCalories());
        assertEquals(false, entity.getVegan());
        assertEquals(true, entity.getDiabetic());
        assertEquals(false, entity.getGluttenFree());
    }
}
