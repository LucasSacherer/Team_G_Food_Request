package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import Entity.FoodRequest;
import Manager.FoodRequestManager;


public class RequestController {
    FoodRequestManager fm;

    public RequestController(FoodRequestManager fm){
        this.fm = fm;
    }

    public void addRequest(FoodRequest fReq){
        //Check that cReq has a name and timeCompleted that is unique to all cleanUpRequests
        if (validateRequest(fReq)){
            fm.addRequest(fReq);
        }
    }
    protected boolean validateRequest(FoodRequest fReq){
        //Check that nothing in null other than worker
        if(fReq.getName()!= null&& fReq.getTimeCreated()!= null && fReq.getNode()!= null){
            return true;
        }else return false;
    }
    public ObservableList<FoodRequest> getRequests(){
        ObservableList requests =  FXCollections.observableArrayList();
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
        }

    }

    public void assignWorker(){}
}
