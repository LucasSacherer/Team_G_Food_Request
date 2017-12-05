package Controller;

import Entity.MenuItem;

import java.util.HashMap;

public class CartController {
    HashMap<String,Integer> cart = new HashMap<>();
    public CartController(){}

    public void addItemToCart(MenuItem item, int qty){
        if (cart.containsKey(item.getFoodName())){
            cart.put(item.getFoodName(),cart.get(item)+qty);
        }
        else{
            cart.put(item.getFoodName(),qty);
        }
    }

    public void clearItems(){
        cart.clear();
    }

    public HashMap<String,Integer> getItems(){
        return cart;
    }

}
