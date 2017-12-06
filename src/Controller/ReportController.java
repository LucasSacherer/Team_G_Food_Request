package Controller;
import Entity.FoodLog;
import Entity.Slice;
import Manager.FoodLogManager;

import javax.swing.event.ListDataEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportController {

    FoodLogManager foodLogManager;
    public ReportController(FoodLogManager flm){
        this.foodLogManager = flm;
    }
    public List<Slice> getPieSlices(){
        List<Slice> slices = new ArrayList<>();
        List<FoodLog> foodLog = foodLogManager.getFoodLog();
        return  slices;
    }

    private HashMap<String,Integer> Orders(List<FoodLog> foodLog){
        HashMap<String,Integer> ordered = new HashMap<>();
        for(FoodLog fl: foodLog){
            if(ordered.containsKey(fl.getFoodName())){
                ordered.put(fl.getFoodName(),ordered.get(fl.getFoodName())+1);
            }
        }
        return ordered;
    }
}
