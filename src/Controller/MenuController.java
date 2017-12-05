package Controller;

import Entity.MenuItem;
import Manager.MenuItemManager;

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
          //call addMenuItem from mim
    }
}
