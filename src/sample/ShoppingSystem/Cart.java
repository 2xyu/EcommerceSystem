package sample.ShoppingSystem;

import java.util.ArrayList;

public class Cart {

    private ArrayList<Product> cartItemList;

    public Cart(){

        cartItemList = new ArrayList<Product>();
    }

    public void addCartItem(Product item){

        getCartItemList().add(item);
    }

    public void removeCartItem(Product item){

        this.cartItemList.remove(item);
    }

    public ArrayList<Product> getCartItemList(){

        return this.cartItemList;
    }

    public void setCartItemList(ArrayList<Product> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public double getTotal(){
        double total = 0;

        for (Product product : cartItemList){
            total += product.getPrice();
        }
        return total;
    }


}
