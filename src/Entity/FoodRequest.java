package Entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.time.LocalDateTime;
import java.util.List;

public class FoodRequest extends RecursiveTreeObject<FoodRequest> {
    private String name;
    private LocalDateTime timeCreated;
    private LocalDateTime timeCompleted;
    private String type;
    private String description;
    private Node node;

    private Worker assignedWorker;
    private List<MenuItem> order;

    public FoodRequest(String name, LocalDateTime timeCreated, LocalDateTime timeCompleted,
                       String type, String description, Node node, Worker worker, List<MenuItem> order) {
        this.name = name;
        this.timeCreated = timeCreated;
        this.timeCompleted = timeCompleted;
        this.type = type;
        this.description = description;
        this.node = node;
        this.assignedWorker = worker;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public LocalDateTime getTimeCompleted() {
        return timeCompleted;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Node getNode() {
        return node;
    }

    public Worker getAssignedWorker() {
        return assignedWorker;
    }

    public List<MenuItem> getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Food:    " + name + "    " + timeCreated.getMonth() + " " + timeCreated.getDayOfMonth() + " " + timeCreated.getHour() + ":" + timeCreated.getMinute() + ":" + timeCreated.getSecond();
    }
}
