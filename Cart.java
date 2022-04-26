//Viet Hoang
//501108602

import java.util.ArrayList;

public class Cart {

    private ArrayList<CartItem> cartItemList;

    public Cart(){

        cartItemList = new ArrayList<CartItem>();
    }

    public void addCartItem(CartItem item){

        cartItemList.add(item);
    }

    public void removeCartItem(CartItem item){

        cartItemList.remove(item);
    }

    public ArrayList<CartItem> getCartItemList(){

        return cartItemList;
    }
}
