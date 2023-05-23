package sample.ShoppingSystem;

public class Movie extends Product {
    private String genre;
    private String length;

    public Movie(){
        super();
        this.genre = "";
        this.length = "";
    }

    public Movie(String name, String sellerID, String id, double price, int stock, String ratings, int orders, String genre, String length) {
        super(name, sellerID, id, price, stock, Category.MOVIE, ratings, orders);
        this.genre = genre;
        this.length = length;
    }

    public String getGenre() {

        return genre;
    }

    public void setGenre(String genre) {

        this.genre = genre;
    }

    public String getLength() {

        return length;
    }

    public void setLength(String length) {

        this.length = length;
    }

    /*
     * Return product information in super class and append VideoGame specific information genre and length
     */
    public String returnStrProductPrint() {

        return super.returnStrProductPrint() + String.format(" Genre: %-5s Length: %-12s", genre, length);
    }

    public String getData() {

        return getCategory().toString() + "\n" + getSellerID() + "\n" + getId() + "\n" + getName() + "\n" + getPrice() + "\n"
                + getStockCount() + "\n" + getRatings() + "\n" + getNumberOfOrders() + "\n" + getGenre() + "\n"
                + getLength() + "\n";
    }
}
