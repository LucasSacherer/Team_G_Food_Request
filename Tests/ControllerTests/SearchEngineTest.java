package ControllerTests;

import Controller.SearchEngine;
import Database.DatabaseGargoyle;
import Manager.FoodLogManager;
import Manager.FoodRequestManager;
import Manager.MenuItemManager;
import Manager.NodeManager;
import org.junit.Test;

public class SearchEngineTest {

    @Test
    public void teststring(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
    }
}
