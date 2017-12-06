package ManagerTests;

import Database2.DatabaseGargoyle;
import Entity2.Worker;
import Manager2.WorkerManager;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WorkerManagerTests {
    @Test
    public void testUpdate() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);

        //Before the update, food log should be empty
        assertTrue(workerManager.getWorkers().size() == 0);

        workerManager.update();

        //After the update, food log should not be empty
        assertFalse(workerManager.getWorkers().size() == 0);
    }

    @Test
    public void testAddAndRemoveWorker() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.notifyManagers();

        int originalSize = workerManager.getWorkers().size();
        Worker newWorker = new Worker("noob", "xX_n00b_Xx");

        //Before addition, it shouldnt be there
        databaseGargoyle.createConnection();
        ResultSet rs1 = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM WORKER WHERE WORKERID = 'noob'");
        try {
            assertFalse(rs1.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        workerManager.addWorker(newWorker);

        //After addition, it should be there
        assertEquals(originalSize + 1, workerManager.getWorkers().size());
        databaseGargoyle.createConnection();
        ResultSet rs2 = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM WORKER WHERE WORKERID = 'noob'");
        try {
            assertTrue(rs2.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        //After removal, it shouldn't be there
        workerManager.removeWorker(newWorker);
        assertEquals(originalSize, workerManager.getWorkers().size());
        databaseGargoyle.createConnection();
        ResultSet rs3 = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM WORKER WHERE WORKERID = 'noob'");
        try {
            assertFalse(rs3.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    @Test
    public void testModifyWorkerAndGetWorkerByID(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        WorkerManager workerManager = new WorkerManager(databaseGargoyle);
        databaseGargoyle.attachManager(workerManager);
        databaseGargoyle.notifyManagers();

        Worker original = workerManager.getWorkerByID("worker1");
        Worker modified = new Worker("worker1", "xX_n00b_Xx");

        //Before mod, username should be worker1Username
        assertEquals("worker1Username", workerManager.getWorkerByID("worker1").getUsername());

        workerManager.modifyWorker(modified);

        //After mod, username should be xX_n00b_Xx
        assertEquals("xX_n00b_Xx", workerManager.getWorkerByID("worker1").getUsername());

        //Revert changes
        workerManager.modifyWorker(original);
        assertEquals("worker1Username", workerManager.getWorkerByID("worker1").getUsername());
    }
}
