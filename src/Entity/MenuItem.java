package Entity;

public class MenuItem {
    private String foodName;
    private String description;
    private int stockAvailable;
    private int calories;
    private Boolean isVegan;
    private Boolean isDiabetic;
    private Boolean isGluttenFree;

    public MenuItem(String foodName, String description, int stockAvailable,
                    int calories, Boolean isVegan, Boolean isDiabetic, Boolean isGluttenFree) {
        this.foodName = foodName;
        this.description = description;
        this.stockAvailable = stockAvailable;
        this.calories = calories;
        this.isVegan = isVegan;
        this.isDiabetic = isDiabetic;
        this.isGluttenFree = isGluttenFree;
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
}
