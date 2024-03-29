package ControllerTests;

import Controller2.SearchEngine;
import Database2.DatabaseGargoyle;
import Entity2.Node;
import Manager2.FoodLogManager;
import Manager2.MenuItemManager;
import Manager2.NodeManager;
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

    @Test
    public void testLowerCafe(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.notifyManagers();
        SearchEngine se = new SearchEngine(nodeManager);
        List<Node> answer = (se.Search("cafe"));
        List<String> names = new ArrayList<>();
        for(Node n: answer){
            names.add(n.getShortName());
        }

        System.out.println(names);

    }

    @Test
    public void twice(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        FoodLogManager foodLogManager = new FoodLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle);
        MenuItemManager menuItemManager = new MenuItemManager(databaseGargoyle);
        databaseGargoyle.attachManager(foodLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(menuItemManager);
        databaseGargoyle.notifyManagers();
        SearchEngine se = new SearchEngine(nodeManager);
        List<Node> answer = (se.Search("cafe"));
        List<String> names = new ArrayList<>();
        for(Node n: answer){
            names.add(n.getShortName());
        }

        System.out.println(names);


        List<Node> answer2 = (se.Search("ATM"));
        List<String> names2 = new ArrayList<>();
        for(Node n: answer2){
            names2.add(n.getShortName());
        }

        System.out.println(names2);
    }
}
