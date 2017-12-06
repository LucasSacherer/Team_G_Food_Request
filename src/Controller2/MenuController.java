package Controller2;

import Entity2.MenuItem;
import Manager2.MenuItemManager;

import java.util.ArrayList;
import java.util.List;

public class MenuController {
    MenuItemManager mim;
    public MenuController(MenuItemManager mim){
        this.mim = mim;
    }

    public List<MenuItem> getAvailableMenu(){
        List<MenuItem> items = mim.getMenuItems();
        List<MenuItem> avaiable = new ArrayList<>();
        for(MenuItem mi: items){
            if (mi.getStockAvailable() != 0){
                avaiable.add(mi);
            }
        }
        return avaiable;
    }

    public List<MenuItem> getDiabetic(){
        List<MenuItem> items = mim.getMenuItems();
        List<MenuItem> diabetic = new ArrayList<>();
        for(MenuItem mi:items){
            if(mi.getDiabetic()){
                diabetic.add(mi);
            }
        }
        return diabetic;
    }

    public List<MenuItem> getvegan(){
        List<MenuItem> items = mim.getMenuItems();
        List<MenuItem> vegan = new ArrayList<>();
        for(MenuItem mi:items){
            if(mi.getVegan()){
                vegan.add(mi);
            }
        }
        return vegan;
    }

    public List<MenuItem> getGultenFree(){
        List<MenuItem> items = mim.getMenuItems();
        List<MenuItem> gf = new ArrayList<>();
        for(MenuItem mi:items){
            if(mi.getGluttenFree()){
                gf.add(mi);
            }
        }
        return gf;
    }

    public void modifyMenuItem(MenuItem modified){
        //check that is valid menuItem
        if(mim.getMenuItemByName(modified.getFoodName()) != null) {
            mim.modifyMenuItem(modified);
        }
    }

    public void addMenuItem(MenuItem newItem){
        if(mim.getMenuItemByName(newItem.getFoodName()) != null){
            //promt user that already exists
            System.out.println("already exists");
        }
        else if(newItem.getFoodName().contains(",") || (newItem.getDescription().contains(","))){
            //prompt user to remove commas
        }
        else if((newItem.getStockAvailable()==0) || (newItem.getStockAvailable() >= 2147483647)){
            //promt user to type in valid stock
        }
        else if((newItem.getPrice()==0) || (newItem.getPrice() >= 2147483647)){
            //prompt user ot type in valid price
        }
        else{
            //call addMenuItem from mim
            mim.addMenuItem(newItem);
        }
    }

    public void removeMenuItem(MenuItem delete){
        if(mim.getMenuItemByName(delete.getFoodName()) != null){
            mim.removeMenuItem(delete);
        }
    }
}
