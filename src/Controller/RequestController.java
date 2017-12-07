package Controller;

import Entity.Worker;
import Manager.FoodLogManager;
import Manager.WorkerLogManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import Entity.FoodRequest;
import Manager.FoodRequestManager;


public class RequestController {
    FoodRequestManager fm;
    WorkerLogManager wm;
    FoodLogManager flm;

    public RequestController(FoodRequestManager fm, WorkerLogManager wm, FoodLogManager flm){
        this.fm = fm;
        this.wm = wm;
        this.flm = flm;
    }

    public void addRequest(FoodRequest fReq){
        //Check that cReq has a name and timeCompleted that is unique to all cleanUpRequests
        if (validateRequest(fReq)){
            fm.addRequest(fReq);

            //Add the request order to the foodLog
            flm.addFoodLog(fReq);
        }
    }

    protected boolean validateRequest(FoodRequest fReq){
        //Check that nothing in null other than worker
        if(fReq.getName()!= null&& fReq.getTimeCreated()!= null && fReq.getNode()!= null){
            return true;
        }else return false;
    }

    public List<FoodRequest> getRequests(){
        List<FoodRequest> requests = new ArrayList<>();
        requests.addAll(fm.getRequests());
        return requests;
    }

    public void deleteRequest(FoodRequest fReq) {
        //check that request exists
        if (fm.getFoodRequest(fReq.getName()) != null) {
            fm.deleteRequest(fReq);
        }

    }

    public void completeRequest(FoodRequest fReq){
        //check that request exists
        if (fm.getFoodRequest(fReq.getName()) != null){
            fm.completeRequest(fReq);

            //Add the completion to the Worker Log
            wm.addWorkerLog(fReq);
        }
    }

    /**
     * Returns a list of requests that are assigned to the specified worker
     * @param worker
     * @return
     */
    public List<FoodRequest> getRequestsByWorker(Worker worker){
        ArrayList<FoodRequest> results = new ArrayList<>();
        for (FoodRequest req: fm.getRequests()){
            if (req.getAssignedWorker().equals(worker)){
                results.add(req);
            }
        }
        return results;
    }

    public void assignWorker(FoodRequest fReq, Worker emp){
        fm.assignWorker(fReq,emp);
    }
}
