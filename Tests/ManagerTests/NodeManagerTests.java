package ManagerTests;

import Database.DatabaseGargoyle;
import Entity.Node;
import Manager.NodeManager;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NodeManagerTests {
    @Test
    public void checkUpdateNodes(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager manager = new NodeManager(databaseGargoyle);
        assertTrue(manager.getAllNodes().size() == 0);
        manager.update();
        assertFalse(manager.getAllNodes().size() == 0);
    }

    @Test
    public void testGetNode(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager manager = new NodeManager(databaseGargoyle);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.notifyManagers();

        assertEquals(manager.getNode("GHALL00601").getNodeID(),"GHALL00601");
        assertNull(manager.getNode(""));
        assertNull(manager.getNode(null));
    }
}
