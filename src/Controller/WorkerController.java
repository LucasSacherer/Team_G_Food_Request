package Controller;

import Entity.Worker;
import Manager.WorkerManager;

import java.util.ArrayList;
import java.util.List;

public class WorkerController {
    WorkerManager wm;
    public WorkerController(WorkerManager wm){
        this.wm = wm;
    }

    public void modifyWorker(Worker modified){
        //check that it is a valid worker
        if(wm.getWorkerByID(modified.workerID)!= null) {
            wm.modifyWorker(modified);
        }
    }

    private String getUniqueID(String username) {
        wm.update();
        String firstNum, potential;
        for (int i = 0; i < 999; i++) {
            StringBuilder ID = new StringBuilder();
            if (i < 10) firstNum = "00" + i;
            else if (i < 100) firstNum = "0" + i;
            else firstNum = "" + i;
            ID.append("G");;
            ID.append(firstNum);
            potential = ID.toString();
            if (wm.getWorkerByID(potential) == null) return potential;
        }
        return null;
    }

    public void addWorker(String username){
        List<Worker> allworkers = getWorkers();
        Worker add = new Worker(getUniqueID(username),username);
        if(add.username != null && add.username != null) {
            wm.addWorker(add);
        }
    }

    public void removeWorker(Worker fired){
        if(wm.getWorkerByID(fired.workerID)!= null){
            wm.removeWorker(fired);
        }
    }

    public List<Worker> getWorkers(){
        return wm.getWorkers();
    }

    public Worker getWorkerbyName(String username){
        List<Worker> allWorkers = getWorkers();

        for (Worker w: allWorkers){
            if(w.username.equals(username)){
                return w;
            }
        }
        return null;
    }
    private List<String> getAllIds(){
        List<Worker> allWorkers = getWorkers();
        List<String> allIDs = new ArrayList<>();
        for (Worker w: allWorkers){
            allIDs.add(w.workerID);
        }
        return allIDs;
    }
}
