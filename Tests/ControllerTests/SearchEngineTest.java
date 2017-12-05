package ControllerTests;

import Controller.SearchEngine;
import Database.DatabaseGargoyle;
import Entity.Node;
import Manager.FoodLogManager;
import Manager.FoodRequestManager;
import Manager.MenuItemManager;
import Manager.NodeManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SearchEngineTest {

    @Test
    public void testCafe(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.notifyManagers();
        SearchEngine se = new SearchEngine(nodeManager);
        List<Node> answer = (se.Search("Cafe"));
        List<String> names = new ArrayList<>();
        for(Node n: answer){
            names.add(n.getShortName());
        }

        System.out.println(names);



    }
}
