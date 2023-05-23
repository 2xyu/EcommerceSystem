package sample.ShoppingSystem;

import java.util.ArrayList;

public class Seller extends User {

    private ArrayList<Product> sellerProductList;
    private ArrayList<ProductOrdered> orderRequests;

    public Seller(){
        super();
        this.sellerProductList = new ArrayList<Product>();
        this.orderRequests = new ArrayList<ProductOrdered>();
    }

    public Seller(String id, String name, String userName, String password, double balance, String address, Category category, ArrayList<Product> sellerProductList, ArrayList<ProductOrdered> orderRequests) {
        super(id, name, userName, password, balance, address, category);
        this.sellerProductList = sellerProductList;
        this.orderRequests = orderRequests;
    }

    public ArrayList<Product> getSellerProductList() {

        return sellerProductList;
    }

    public void setSellerProductList(ArrayList<Product> sellerProductList) {

        this.sellerProductList = sellerProductList;
    }

    public void addSellerProduct(Product product){

        sellerProductList.add(product);
    }
    public void removeSellerProduct(Product product){

        sellerProductList.remove(product);
    }

    public ArrayList<ProductOrdered> getOrderRequests() {

        return orderRequests;
    }
    public void addOrderRequests(ProductOrdered productOrdered){

        orderRequests.add(productOrdered);
    }
    public void removeOrderRequests(ProductOrdered productOrdered){

        orderRequests.remove(productOrdered);
    }

}
