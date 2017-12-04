package Manager;

import Database.DatabaseGargoyle;
import Entity.Worker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerManager implements EntityManager{
    private List<Worker> workers;
    private DatabaseGargoyle databaseGargoyle;

    public WorkerManager(DatabaseGargoyle databaseGargoyle) {
        this.workers = new ArrayList<>();
        this.databaseGargoyle = databaseGargoyle;
    }

    /**
     * Updates the list of workers to be up to date with the database
     */
    public void update() {
        String workerID, userName;
        workers.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM WORKER");
        try {
            while (rs.next()){
                workerID = rs.getString("WORKERID");
                userName = rs.getString("USERNAME");
                workers.add(new Worker(workerID, userName));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get workers from database!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Adds a worker to the database
     * @param newWorker
     */
    public void addWorker(Worker newWorker){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO WORKER VALUES (" +
                "'" + newWorker.getWorkerID() +"'," +
                "'" + newWorker.getUsername()+"')");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Removes a worker from the database
     * @param oldWorker
     */
    public void removeWorker(Worker oldWorker){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM WORKER WHERE workerID = '" + oldWorker.getWorkerID() + "'");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Modifies the given user's username
     * @param modifiedWorker
     */
    public void modifyWorker(Worker modifiedWorker){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE WORKER SET " +
                "USERNAME = '" + modifiedWorker.getUsername() + "' " +
                "WHERE WORKERID = '" + modifiedWorker.getWorkerID() + "'");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Returns a worker with the given workerID, null if not found
     * @param workerID
     * @return
     */
    public Worker getWorkerByID(String workerID){
        for (Worker worker: workers){
            if (worker.getWorkerID().equals(workerID)){
                return worker;
            }
        }
        return null;
    }
}
