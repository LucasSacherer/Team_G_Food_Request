package ControllerTests;

import Controller.CartController;
import Controller.MenuController;
import Controller.SearchEngine;
import Database.DatabaseGargoyle;
import Entity.MenuItem;
import Manager.FoodLogManager;
import Manager.MenuItemManager;
import Manager.NodeManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartControllerTest {

    @Test
    public void testAdd(){
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
        CartController cc = new CartController();
        System.out.println(cc.getItems().size());
        MenuItem cake = new MenuItem("cake","chocolate cake", 3,50,true,false,false,3);
        cc.addItemToCart(cake, 3);
        System.out.println(cc.getItems().size());
        int qty = cc.getItems().get(cake.getFoodName());
        System.out.println(qty);
    }
}