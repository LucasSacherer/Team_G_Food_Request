package Controller;

import Entity.MenuItem;

import java.util.HashMap;

public class CartController {
    HashMap<MenuItem,Integer> cart = new HashMap<>();
    public CartController(){}

    public void addItemToCart(MenuItem item){
        if (cart.containsKey(item)){
            cart.put(item,cart.get(item)+1);
        }
        else{
            cart.put(item,1);
        }
    }
    public void clearItems(){
        cart.clear();
    }

    public HashMap<MenuItem,Integer> getItems(){
        return cart;
    }

}
