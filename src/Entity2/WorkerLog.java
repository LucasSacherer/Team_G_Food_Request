package Entity2;

import java.time.LocalDateTime;

public class WorkerLog {
    private String requestName;
    private String workerID;
    private LocalDateTime timeCreated;
    private LocalDateTime timeCompleted;

    public WorkerLog(String requestName, String workerID, LocalDateTime timeCreated, LocalDateTime timeCompleted) {
        this.requestName = requestName;
        this.workerID = workerID;
        this.timeCreated = timeCreated;
        this.timeCompleted = timeCompleted;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getWorkerID() {
        return workerID;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public LocalDateTime getTimeCompleted() {
        return timeCompleted;
    }
}
