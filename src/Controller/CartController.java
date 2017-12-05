package Controller;

import Entity.MenuItem;

import java.util.HashMap;

public class CartController {
    HashMap<MenuItem,Integer> cart = new HashMap<>();
    public CartController(){}

    public void addItemToCart(MenuItem item, int qty){
        if (cart.containsKey(item)){
            cart.put(item,cart.get(item)+qty);
        }
        else{
            cart.put(item,qty);
        }
    }

    public void clearItems(){
        cart.clear();
    }

    public HashMap<MenuItem,Integer> getItems(){
        return cart;
    }

}
