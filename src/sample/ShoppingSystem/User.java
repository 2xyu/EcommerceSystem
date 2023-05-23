package sample.ShoppingSystem;

/*
 *  class User defines a registered customer. It keeps track of the customer's name and address.
 *  A unique id is generated when a new customer is created.
 *
 *  Implement the Comparable interface and compare two Users based on name
 */
public class User implements Comparable<User> {

    protected enum Category {CUSTOMER, SELLER}

    private String id;
    private String name;
    private String userName;
    private String password;
    private double balance;
    private String shippingAddress;
    private Cart cart;
    private Category category;


    public User() {

        this.id = "";
        this.name = "";
        this.userName = "";
        this.password = "";
        this.balance = 0;
        this.shippingAddress = "";
        this.cart = new Cart();
        this.category = null;
    }
    public User(String id, String name, String userName, String password, double balance, String address, Category category) {

        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.balance = balance;
        this.shippingAddress = address;
        this.cart = new Cart();
        this.category = category;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public double getBalance() {

        return balance;
    }

    public void setBalance(double balance) {

        this.balance = balance;
    }

    public String getShippingAddress() {

        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {

        this.shippingAddress = shippingAddress;
    }

    public Cart getCart(){

        return cart;
    }

    public void setCart(Cart cart) {

        this.cart = cart;
    }

    public Category getCategory() {

        return category;
    }
    public void setCategory(Category category) {

        this.category = category;
    }
    
    public boolean isSeller() {

        return category == Category.SELLER;
    }

    public String returnStrUserPrint() {

        return String.format("\n%s ID: %3s Name: %-25s Address: %-35s", category, id, name, shippingAddress);
    }

    public String getData(){

        return getCategory() + "\n" + getId() + "\n" + getName() + "\n" + getUserName() + "\n" + getPassword() + "\n"
                + getBalance() + "\n" + getShippingAddress() + "\n";
    }

    public boolean equals(Object other) {

        User otherC = (User) other;
        return this.id.equals(otherC.id);
    }

    public int compareTo(User other) {

        if (this.getName().compareTo(other.getName()) == 0) {
            return 0;
        } else if (this.getName().compareTo(other.getName()) < 0) {
            return -1;
        }
        return 1;
    }
}
