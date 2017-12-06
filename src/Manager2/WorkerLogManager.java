package Manager2;

import Database2.DatabaseGargoyle;
import Entity2.FoodRequest;
import Entity2.WorkerLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkerLogManager implements EntityManager{
    private DatabaseGargoyle databaseGargoyle;
    private List<WorkerLog> workerLog;

    public WorkerLogManager(DatabaseGargoyle databaseGargoyle) {
        this.databaseGargoyle = databaseGargoyle;
        this.workerLog = new ArrayList<>();
    }

    /**
     * Updates the worker log to be up to date with the database
     */
    public void update(){
        String requestName, workerID;
        LocalDateTime timeCreated, timeCompleted;
        workerLog.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM WORKERLOG");
        try {
            while (rs.next()) {
                requestName = rs.getString("REQUESTNAME");
                workerID = rs.getString("WORKERID");
                timeCreated = rs.getTimestamp("TIMECREATED").toLocalDateTime();
                timeCompleted = rs.getTimestamp("TIMECOMPLETED").toLocalDateTime();
                workerLog.add(new WorkerLog(requestName, workerID, timeCreated, timeCompleted));
            }
        }catch (SQLException ex){
            System.out.println("Failed to update the Worker Log!");
            ex.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Adds the food request completion to the Worker Log
     * @param foodRequest
     */
    public void addWorkerLog(FoodRequest foodRequest){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO WORKERLOG VALUES (" +
                "'" + foodRequest.getName() + "', " +
                "'" + foodRequest.getAssignedWorker().getWorkerID() + "', " +
                "'" + Timestamp.valueOf(foodRequest.getTimeCreated()) + "', " +
                "'" + Timestamp.valueOf(LocalDateTime.now()) + "')");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Returns the updated worker log
     * @return
     */
    public List<WorkerLog> getWorkerLog(){
        return workerLog;
    }
}














