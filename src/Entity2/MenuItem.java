package Entity2;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class MenuItem extends RecursiveTreeObject<MenuItem> {
    private String foodName;
    private String description;
    private int stockAvailable;
    private int calories;
    private Boolean isVegan;
    private Boolean isDiabetic;
    private Boolean isGluttenFree;
    private int price;

    public MenuItem(String foodName, String description, int stockAvailable,
                    int calories, Boolean isVegan, Boolean isDiabetic, Boolean isGluttenFree, int price) {
        this.foodName = foodName;
        this.description = description;
        this.stockAvailable = stockAvailable;
        this.calories = calories;
        this.isVegan = isVegan;
        this.isDiabetic = isDiabetic;
        this.isGluttenFree = isGluttenFree;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getDescription() {
        return description;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }

    public int getCalories() {
        return calories;
    }

    public Boolean getVegan() {
        return isVegan;
    }

    public Boolean getDiabetic() {
        return isDiabetic;
    }

    public Boolean getGluttenFree() {
        return isGluttenFree;
    }

    public int getPrice() { return price; }
}
