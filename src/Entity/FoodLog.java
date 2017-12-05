package Entity;

import java.time.LocalDateTime;

public class FoodLog {
    private String foodName;
    private LocalDateTime timeCreated;
    private String nodeID;

    public FoodLog(String foodName, LocalDateTime timeCreated, String nodeID) {
        this.foodName = foodName;
        this.timeCreated = timeCreated;
        this.nodeID = nodeID;
    }

    public String getFoodName() {
        return foodName;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public String getNodeID() {
        return nodeID;
    }
}
