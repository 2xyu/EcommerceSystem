//Viet Hoang
//501108602

/* A book IS A product that has additional information - e.g. title, author

 	 A book also comes in different formats ("Paperback", "Hardcover", "EBook")
 	 
 	 The format is specified as a specific "stock type" in get/set/reduce stockCount methods.

*/
public class Book extends Product {

    // Stock related information NOTE: inherited stockCount variable is used for EBooks
    int paperbackStock;
    int hardcoverStock;
    private String author;
    private String title;

    //year is set as Integer so that it can be null for books with no years.
    private Integer year;

    public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, Integer year) {

        // Make use of the constructor in the super class Product. Initialize additional Book instance variables.
        //Stock is set to one since we're assuming that there are infinite number of EBook copies
        super(name, id, price, 1, Category.BOOKS);
        this.paperbackStock = paperbackStock;
        this.hardcoverStock = hardcoverStock;
        this.title = title;
        this.author = author;
        this.year = year;
        // Set category to BOOKS
    }

    public Integer getYear() {

        return year;
    }

    public String getAuthor() {

        return author;
    }

    public String getTitle() {

        return title;
    }


    // Check if a valid format
    public boolean validOptions(String productOptions) {

        // check productOptions for "Paperback" or "Hardcover" or "EBook"
        // if it is one of these, return true, else return false
        return productOptions.equalsIgnoreCase("Paperback")
            || productOptions.equalsIgnoreCase("Hardcover")
            || productOptions.equalsIgnoreCase("EBook");
    }

    // Override getStockCount() in super class.
    public int getStockCount(String productOptions) {

        // Use the productOptions to check for (and return) the number of stock for "Paperback" etc
        if (productOptions.equalsIgnoreCase("Paperback")) {

            return paperbackStock;
        } else if (productOptions.equalsIgnoreCase("Hardcover")) {

            return hardcoverStock;
        } else if (productOptions.equalsIgnoreCase("EBook")) {

            return super.getStockCount("EBook");
        }

        // Use the variables paperbackStock and hardcoverStock at the top.
        // For "EBook", use the inherited stockCount variable.

        // this will realistically never reach here since in ECommerceSystem.java, it will always check if the
        // productOptions is valid first (ie if its "Paperback", "Hardcover", or "Ebook").
        // if the productOptions isn't on here then just return 0.
        return 0;
    }

    public void setStockCount(int stockCount, String productOptions) {

        // Use the productOptions to check for (and set) the number of stock for "Paperback" etc
        // Use the variables paperbackStock and hardcoverStock at the top.
        // For "EBook", set the inherited stockCount variable.

        // There is no Ebook since we're assuming that there are infinite number of EBook copies.
        if (productOptions.equalsIgnoreCase("Paperback")) {

            paperbackStock = stockCount;
        } else if (productOptions.equalsIgnoreCase("Hardcover")) {

            hardcoverStock = stockCount;
        }
    }

    /*
     * When a book is ordered, reduce the stock count for the specific stock type
     */
    public void reduceStockCount(String productOptions) {

        // Use the productOptions to check for (and reduce) the number of stock for "Paperback" etc
        // Use the variables paperbackStock and hardcoverStock at the top.
        // For "EBook", set the inherited stockCount variable.

        //Ebook stocks don't change since we're assuming that there are infinite number of EBook copies
        if (productOptions.equalsIgnoreCase("Paperback")) {

            this.paperbackStock--;
        } else if (productOptions.equalsIgnoreCase("Hardcover")) {

            this.hardcoverStock--;
        }
    }

    /*
     * Print product information in super class and append Book specific information title and author
     */
    public void print() {

        // Replace the line below.
        // Make use of the super class print() method and append the title and author info. See the video
        super.print();
        System.out.printf(" Book Title: %-35s Author: %-5s", title, author);
    }
}
