package Entity2;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Worker extends RecursiveTreeObject<Worker> {
    public String workerID;
    public String username;

    public Worker(String workerID, String username) {
        this.workerID = workerID;
        this.username = username;
    }

    public String getWorkerID() {
        return workerID;
    }

    public String getUsername() {
        return username;
    }
}
