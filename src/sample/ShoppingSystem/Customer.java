package sample.ShoppingSystem;

public class Customer extends User {

    public Customer(String id, String name, String userName, String password, double balance, String address, Category category) {
        super(id, name, userName, password, balance, address, category);
    }

    public Customer() {

        super();
    }
}
