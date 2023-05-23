package sample.ShoppingSystem;

public class Book extends Product {

    private String author;
    private String year;

    public Book(){
        super();
        this.author = "";
        this.year = "";
    }

    public Book(String name, String sellerID, String id, double price, int stock, String ratings, int orders, String author, String year) {

        // Make use of the constructor in the super class Product. Initialize additional Book instance variables.
        super(name, sellerID, id, price, stock, Category.BOOK, ratings, orders);
        this.author = author;
        this.year = year;
        // Set category to BOOKS
    }
    public String getAuthor() {

        return author;
    }
    public void setAuthor(String author) {

        this.author = author;
    }

    public String getYear() {

        return year;
    }

    public void setYear(String year) {

        this.year = year;
    }

    /*
     * Return product information in super class and append Book specific information title and author
     */
    public String returnStrProductPrint() {

        return super.returnStrProductPrint() + String.format(" Author: %-15s Year Published: %-5s",author, year);
    }

    public String getData() {

        return getCategory().toString() + "\n" + getSellerID() + "\n" + getId() + "\n" + getName() + "\n" + getPrice() + "\n"
                + getStockCount() + "\n" + getRatings() + "\n" + getNumberOfOrders() + "\n" + getAuthor() + "\n"
                + getYear() + "\n";
    }
}
