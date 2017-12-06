package Entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class CartItem extends RecursiveTreeObject<CartItem> {
    private String foodNameCart;
    private Integer quantity;


    public CartItem(String foodNameCart, Integer quantity){
        this.foodNameCart = foodNameCart;
        this.quantity = quantity;

    }

    public String getFoodNameCart() {
        return foodNameCart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setFoodNameCart(String foodNameCart) {
        this.foodNameCart = foodNameCart;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return foodNameCart + " X" + quantity + "\n";
    }
}
