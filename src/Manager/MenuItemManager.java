package Manager;

import Database.DatabaseGargoyle;
import Entity.MenuItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemManager implements EntityManager {
    private DatabaseGargoyle databaseGargoyle;
    private List<MenuItem> menuItems;

    public MenuItemManager(DatabaseGargoyle databaseGargoyle) {
        this.databaseGargoyle = databaseGargoyle;
        this.menuItems = new ArrayList<>();
    }

    /**
     * Updates the list of menu items to be up to date with the database
     */
    public void update() {
        String foodName, description;
        int stockAvailable, calories;
        Boolean isVegan, isDiabetic, isGluttenFree;

        menuItems.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM MENUITEM");
        try {
            while (rs.next()){
                foodName = rs.getString("FOODNAME");
                description = rs.getString("DESCRIPTION");
                stockAvailable = rs.getInt("STOCKAVAILABLE");
                calories = rs.getInt("CALORIES");
                if (rs.getString("ISVEGAN").equalsIgnoreCase("true")){
                    isVegan = true;
                } else isVegan = false;
                if (rs.getString("ISDIABETIC").equalsIgnoreCase("true")){
                    isDiabetic = true;
                } else isDiabetic = false;
                if (rs.getString("ISGLUTTENFREE").equalsIgnoreCase("true")){
                    isGluttenFree = true;
                } else isGluttenFree = false;
                menuItems.add(new MenuItem(foodName, description, stockAvailable, calories, isVegan, isDiabetic, isGluttenFree));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get Menu Items from database!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Returns the menu item corresponding to the name given, null if not available (but it will be)
     * @param name
     * @return
     */
    public MenuItem getMenuItemByName(String name){
        for (MenuItem item: menuItems){
            if (item.getFoodName().equals(name)){
                return item;
            }
        }
        return null;
    }

    /**
     * returns the list of menu items
     * @return
     */
    public List<MenuItem> getMenuItems(){
        return menuItems;
    }

    /**
     * Modifies the menu item
     * @param modified
     */
    public void modifyMenuItem(MenuItem modified){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE MENUITEM SET " +
                "DESCRIPTION = '" + modified.getDescription() + "', " +
                "STOCKAVAILABLE = " + modified.getStockAvailable() + ", " +
                "CALORIES = " + modified.getCalories() + ", " +
                "ISVEGAN = '" + modified.getVegan().toString() + "', " +
                "ISDIABETIC = '" + modified.getDiabetic().toString() + "', " +
                "ISGLUTTENFREE = '" + modified.getGluttenFree().toString() + "' " +
                "WHERE FOODNAME = '" + modified.getFoodName() + "'");
        databaseGargoyle.destroyConnection();
    }
}
