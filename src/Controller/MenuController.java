package Controller;

import Entity.MenuItem;
import Manager.MenuItemManager;

import java.util.List;

public class MenuController {
    MenuItemManager mim;
    public MenuController(MenuItemManager mim){
        this.mim = mim;
    }

    public List<MenuItem> getAvailableMenu(){
        return mim.getMenuItems();
    }

    public void modifyMenuItem(MenuItem modified){
        //check that is valid menuItem
        mim.modifyMenuItem(modified);
    }
}
