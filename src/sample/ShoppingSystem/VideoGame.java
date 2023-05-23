package sample.ShoppingSystem;

public class VideoGame extends Product {
    private String ESRBRating;
    private String genre;


    public VideoGame(){
        super();
        this.ESRBRating = "";
        this.genre = "";
    }

    public VideoGame(String name, String sellerID, String id, double price, int stock, String ratings, int orders, String ESRBRating, String genre) {
        super(name, sellerID, id, price, stock, Category.VIDEOGAME, ratings, orders);
        this.ESRBRating = ESRBRating;
        this.genre = genre;
    }

    public String getESRBRating() {

        return ESRBRating;
    }

    public void setESRBRating(String ESRBRating) {

        this.ESRBRating = ESRBRating;
    }

    public String getGenre() {

        return genre;
    }

    public void setGenre(String genre) {

        this.genre = genre;
    }

    /*
     * Return product information in super class and append VideoGame specific information ESRBRating and genre
     */
    public String returnStrProductPrint() {

        return super.returnStrProductPrint() + String.format(" ESRB Rating: %-5s Genre: %-10s", ESRBRating, genre);
    }

    public String getData() {

        return getCategory().toString() + "\n" + getSellerID() + "\n" + getId() + "\n" + getName() + "\n" + getPrice()
                + "\n" + getStockCount() + "\n" + getRatings() + "\n" + getNumberOfOrders() + "\n" + getESRBRating()
                + "\n" + getGenre() + "\n";
    }
}
