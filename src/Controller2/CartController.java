package Controller2;

import Entity2.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartController {
    ArrayList<CartItem> cartItems = new ArrayList<>();
    public CartController(){}

    public void addItemToCart(CartItem item){
        boolean added = false;
        for (CartItem c : cartItems){
            if (c.getFoodNameCart().equals(item.getFoodNameCart())){
                c.setQuantity(c.getQuantity()+item.getQuantity());
                added = true;
            }
        }

        if (!added)
            cartItems.add(item);
        /*
        if (cartItems.contains(item)){
            item.setQuantity(item.getQuantity() + 1);
        }
        else{
            cartItems.add(item);
        }*/
    }
    public CartItem getItemFromCart (String foodName){
        for (CartItem c : cartItems){
            if (c.getFoodNameCart().equals(foodName)){
                return c;
            }
        }
        return null;
    }

    public void clearItems(){
        cartItems.clear();

    }

    public List<CartItem> getItems(){
        return cartItems;
    }

}
