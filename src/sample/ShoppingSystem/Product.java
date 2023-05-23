package sample.ShoppingSystem;

/*
 * class Product defines a product for sale by the system.
 *
 * A product belongs to one of the 5 categories below.
 *
 * Some products also have various options (e.g. size, color, format, style, ...). The options can affect
 * the stock count(s). In this generic class Product, product options are not used in get/set/reduce stockCount() methods
 *
 * Some products
 */
public class Product {

	protected enum Category {GENERAL, CLOTHING, BOOK, FURNITURE, COMPUTER, MOVIE, VIDEOGAME}

    private String name;
    private String sellerID;
    private String id;
    private Category category;
    private double price;
    private int stockCount;
    private String ratings;
    private int numberOfOrders;

    public Product(){
        this.name = "";
        this.sellerID = "";
        this.id = "";
        this.price = -1;
        this.stockCount = -1;
        this.category = null;
        this.ratings = "";
        this.numberOfOrders = 0;
    }

    public Product(String name, String sellerID, String id, double price, int stockCount, Category category, String ratings, int numberOfOrders) {

        this.name = name;
        this.sellerID = sellerID;
        this.id = id;
        this.price = price;
        this.stockCount = stockCount;
        this.category = category;
        this.ratings = ratings;
        this.numberOfOrders = numberOfOrders;
    }

    public Category getCategory() {

        return category;
    }

    public void setCategory(Category category) {

        this.category = category;
    }
    public String getCategoryName() {
        return category.name();
    }
    public Category strToCategory(String cat) {
        if (cat.equals("Book")) {
            return Category.BOOK;
        } else if (cat.equals("Clothing")) {
            return Category.CLOTHING;
        } else if (cat.equals("Computer")) {
            return Category.COMPUTER;
        } else if (cat.equals("Furniture")) {
            return Category.FURNITURE;
        }else if (cat.equals("Movie")) {
            return Category.MOVIE;
        } else if (cat.equals("General")) {
            return Category.GENERAL;
        } else if (cat.equals("Video Game")) {
            return Category.VIDEOGAME;
        }
        return null;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getSellerID() {

        return sellerID;
    }

    public void setSellerID(String sellerID) {

        this.sellerID = sellerID;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    /*
     * Return the number of items currently in stock for this product
     * Note: in this general class, the productOptions parameter is not used. It may be used
     * in subclasses.
     */
    public int getStockCount() {

        return stockCount;
    }

    /*
     * Set (or replenish) the number of items currently in stock for this product
     */
    public void setStockCount(int stockCount) {

        this.stockCount = stockCount;
    }

    /*
     * Reduce the number of items currently in stock for this product by 1 (called when a product has
     * been ordered by a customer)
     * Note: in this general class, the productOptions parameter is not used. It may be used
     * in subclasses.
     */
    public void reduceStockCount() {

        stockCount--;
    }

    public String getRatings() {

        return ratings;
    }

    public double getAverageRating(){
        if (getRatings().equals("") || getRatings().equals(" ")){
            return 0;
        }
        String[] ratingArray = getRatings().trim().split(" ");
        double totalRating = 0;
        for (String s : ratingArray) {
            totalRating += Integer.parseInt(s);
        }
        return totalRating / ratingArray.length;
    }

    public void setRatings(String ratings) {

        this.ratings = (this.ratings + " " + ratings).trim();
    }

    public int getNumberOfOrders() {

        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {

        this.numberOfOrders = numberOfOrders;
    }

    public String returnStrProductPrint() {

        return String.format("\nId: %-5s Category: %-9s Price: $%8.2f Name: %-30s Stock: %-5d", id, category, price, name, stockCount);
    }

    public String getData() {

        return getCategory().toString() + "\n" + getSellerID() + "\n" + getId() + "\n" + getName() + "\n" + getPrice() + "\n"
                + getStockCount() + "\n" + getRatings() + "\n" + getNumberOfOrders() + "\n";
    }

    /*
     * Two products are equal if they have the same product Id.
     * This method is inherited from superclass Object and overridden here
     */
    public boolean equals(Object other) {

        Product otherP = (Product) other;
        return this.id.equals(otherP.id);
    }
}
