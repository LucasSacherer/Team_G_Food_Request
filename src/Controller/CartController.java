package Controller;

import Entity.CartItem;
import Entity.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartController {
    ArrayList<CartItem> cartItems = new ArrayList<>();
    public CartController(){}

    public void addItemToCart(CartItem item){
        if (cartItems.contains(item.getFoodNameCart())){
            item.setQuantity(item.getQuantity() + 1);
        }
        else{
            cartItems.add(item);
        }
    }
    public void getItemFromCart (String foodName){}

    public void clearItems(){
        cartItems.clear();

    }

    public List<CartItem> getItems(){
        return cartItems;
    }

}
