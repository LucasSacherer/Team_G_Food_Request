package ControllerTests;

import Controller2.MenuController;
import Controller2.SearchEngine;
import Database2.DatabaseGargoyle;
import Manager2.FoodLogManager;
import Manager2.MenuItemManager;
import Manager2.NodeManager;
import org.junit.Test;
import Entity2.MenuItem;
import static org.junit.Assert.*;

public class MenuControllerTest {

    @Test
    public void additem() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.notifyManagers();
        SearchEngine se = new SearchEngine(nodeManager);
        MenuController mc = new MenuController(menuItemManager);
        int available = mc.getAvailableMenu().size();
        System.out.println(available);
        /*String foodName, String description, int stockAvailable,
                    int calories, Boolean isVegan, Boolean isDiabetic, Boolean isGluttenFree, int price*/
        MenuItem cake = new MenuItem("cake","chocolate cake", 3,50,true,false,false,3);
        mc.addMenuItem(cake);
        available = mc.getAvailableMenu().size();
        System.out.println(available);
        mc.removeMenuItem(cake);
        available = mc.getAvailableMenu().size();
        assertEquals(13,available);
    }
    @Test
    public void addBaditem() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.notifyManagers();
        SearchEngine se = new SearchEngine(nodeManager);
        MenuController mc = new MenuController(menuItemManager);
        int available1 = mc.getAvailableMenu().size();
        System.out.println(available1);
        /*String foodName, String description, int stockAvailable,
                    int calories, Boolean isVegan, Boolean isDiabetic, Boolean isGluttenFree, int price*/
        MenuItem cake = new MenuItem("cake","chocolate cake", 0,0,true,false,false,3);
        mc.addMenuItem(cake);
        int available2 = mc.getAvailableMenu().size();
        System.out.println(available2);
        assertEquals(available1,available2);
    }
    @Test
    public void addSameitem() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.notifyManagers();
        SearchEngine se = new SearchEngine(nodeManager);
        MenuController mc = new MenuController(menuItemManager);
        int available1 = mc.getAvailableMenu().size();
        System.out.println(available1);
        /*String foodName, String description, int stockAvailable,
                    int calories, Boolean isVegan, Boolean isDiabetic, Boolean isGluttenFree, int price*/
        MenuItem cake = new MenuItem("cake","chocolate cake", 5,4,true,false,false,3);
        mc.addMenuItem(cake);
        System.out.println(mc.getAvailableMenu().size());
        databaseGargoyle.notifyManagers();
        mc.addMenuItem(cake);
        int available2 = mc.getAvailableMenu().size();
        System.out.println(available2);
        assertEquals(14,available2);
    }



}