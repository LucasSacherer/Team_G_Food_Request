package Entity;

public class Worker {
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
