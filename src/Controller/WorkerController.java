package Controller;

import Entity.Worker;
import Manager.WorkerManager;

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

    public void addWorker(Worker add){
        if(add.username != null && add.username != null) {
            wm.addWorker(add);
        }
    }

    public void removeWorker(Worker fired){
        if(wm.getWorkerByID(fired.workerID)!= null){
            removeWorker(fired);
        }
    }
}
