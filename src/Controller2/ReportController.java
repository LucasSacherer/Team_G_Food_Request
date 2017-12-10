package Controller2;
import Entity2.DensityNode;
import Entity2.FoodLog;
import Entity2.Slice;
import Entity2.TypeData;
import Manager2.FoodLogManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ReportController {

    FoodLogManager foodLogManager;
    MenuController mc;

    public ReportController(FoodLogManager flm,MenuController mc){
        this.foodLogManager = flm;
        this.mc = mc;
    }
    public List<Slice> getPieSlices(){
        return calcSlices();
    }

    public List<TypeData> getBarChartdata(){
        return calcBarChart();
    }

    private List<TypeData> calcBarChart(){
        List<TypeData> data = new ArrayList<>();
        List<Slice> orders = calcSlices();
        HashMap<String,Integer> typeOrders = new HashMap<>();
        for(Slice s: orders){
            String foodName = s.getName();
            List<String> types = mc.getTypes(foodName);
            for(String t: types){
                if (typeOrders.containsKey(t)){
                    typeOrders.put(t,typeOrders.get(t) + s.getQty());
                }
                else{
                    typeOrders.put(t,s.getQty());
                }
            }
        }

        List<String> allTypes = new ArrayList<>(typeOrders.keySet());

        for(String t : allTypes){
            TypeData type = new TypeData(t,typeOrders.get(t));
            data.add(type);
        }
        return data;
    }

    private HashMap<String,Integer> orders(List<FoodLog> foodLog){
        HashMap<String,Integer> ordered = new HashMap<>();
        for(FoodLog fl: foodLog){
            if(ordered.containsKey(fl.getFoodName())){
                ordered.put(fl.getFoodName(),ordered.get(fl.getFoodName())+1);
            }
            else{
                ordered.put(fl.getFoodName(),1);
            }
        }
        return ordered;
    }

    private List<Slice> calcSlices(){
        List<Slice> slices = new ArrayList<>();
        List<FoodLog> foodLog = foodLogManager.getFoodLog();
        HashMap<String,Integer> allOrders = orders(foodLog);
        Collection allValuesC = allOrders.values();
        List<Integer> allValues = new ArrayList<>();
        allValues.addAll(allValuesC);
        int sum = 0;
        for (int v: allValues){
            sum += v;
        }
        List<String> allFoods = new ArrayList<>(allOrders.keySet());
        //create slice objects
        for(String s: allFoods){
            double percent = (allOrders.get(s))/sum;
            Slice slice = new Slice(s,allOrders.get(s),percent);
            slices.add(slice);
        }
        return  slices;
    }

    public List<DensityNode> getRequestDensity(){
        List<DensityNode> densityNodes = new ArrayList<>();
        List<FoodLog> foodLog = foodLogManager.getFoodLog();
        HashMap<String,Integer> allRequests = requests(foodLog);
        int highest = 0;
        List<String> requestNames = new ArrayList<>(allRequests.keySet());
        for (String s: requestNames){
            if (allRequests.get(s) > highest){
                highest = allRequests.get(s);
            }
        }
        int levels = 5;
        int scale = 0;
        if(allRequests.size() < 5){
            scale = 1;
        }
        else{
            scale = highest/levels;
        }

        for (String s: requestNames){
            int amount = allRequests.get(s);
            int level = 0;
            if(amount <= scale){
                level = 1;
            }
            else if(amount <= (scale*2)){
                level = 2;
            }
            else if(amount <= (scale*3)){
                level = 3;
            }
            else if(amount <= (scale*4)){
                level = 4;
            }
            else{ level = 5;}

            DensityNode dn = new DensityNode(s,level);
            densityNodes.add(dn);
        }
        return densityNodes;
    }

    private HashMap<String,Integer> requests (List<FoodLog> foodLog){
        HashMap<String,Integer> ordered = new HashMap<>();
        for(FoodLog fl: foodLog){
            if(ordered.containsKey(fl.getNodeID())){
                ordered.put(fl.getNodeID(),ordered.get(fl.getNodeID())+1);
            }
            else{
                ordered.put(fl.getNodeID(),1);
            }
        }
        return ordered;
    }

}
