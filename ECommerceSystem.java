//Viet Hoang
//501108602

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.Scanner;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem {

    // Random number generator
    Random random = new Random();
    private Map<String, Product> products = new TreeMap<String, Product>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<ProductOrder> orders = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders = new ArrayList<ProductOrder>();

    // used to keep track of the carts each customer has (only maps customers that have items in their carts)
    private Map<String, Cart> cartsWithItems = new TreeMap<String, Cart>();

    // keeps track of the stats of products (the amount of orders each productId has)
    private Map<String, Integer> productOrderStats = new TreeMap<String, Integer>();

    // keeps track of all the ratings of all the products
    private Map<String, ArrayList<String>> productRatings = new TreeMap<String, ArrayList<String>>();

    // These variables are used to generate order numbers, customer id's, product id's
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;

    /**
     * Reads a txt file and formats it into a map
     *
     * @param fileName the name of the file
     * @return the map of all the products in [ProductId, Product] format
     * @throws FileNotFoundException file might be missing
     */
    private Map<String, Product> readProducts(String fileName) throws FileNotFoundException {

        Map<String, Product> lines = new TreeMap<String, Product>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNextLine()) {

            String firstLine = scanner.nextLine();
            String secondLine = scanner.nextLine();
            String thirdLine = scanner.nextLine();
            String fourthLine = scanner.nextLine();
            String fifthLine = scanner.nextLine();

            Product product;

            Product.Category category = Product.Category.valueOf(firstLine);
            String productName = secondLine;
            String productId = generateProductId();
            double price = Double.parseDouble(thirdLine);

            if (category == Product.Category.BOOKS) {

                String[] paperStocks = fourthLine.split(" ");
                int paperBackStock = Integer.parseInt(paperStocks[0]);
                int hardCoverStock = Integer.parseInt(paperStocks[1]);

                String[] bookInformation = fifthLine.split(":");

                String title = bookInformation[0];
                String author = bookInformation[1];

                Integer year = null;
                try {
                    year = Integer.parseInt(bookInformation[2]);

                    //some books might not have year
                } catch (Exception ignored) {

                } finally {
                    product = new Book(productName, productId, price, paperBackStock, hardCoverStock, title, author, year);
                }

            } else if (category == Product.Category.SHOES) {

                // black shoe sizes
                String[] blackShoeStocks = fourthLine.split(" ");

                // brown shoe sizes
                String[] brownShoeStocks = fifthLine.split(" ");

                int[] blackShoeSizes = new int[5];
                int[] brownShoeSizes = new int[5];
                for (int i = 0; i < 5; i++) {
                    blackShoeSizes[i] = Integer.parseInt(blackShoeStocks[i]);
                    brownShoeSizes[i] = Integer.parseInt(brownShoeStocks[i]);
                }

                product = new Shoes(productName, productId, price, blackShoeSizes[0], blackShoeSizes[1], blackShoeSizes[2], blackShoeSizes[3], blackShoeSizes[4],
                        brownShoeSizes[0], brownShoeSizes[1], brownShoeSizes[2], brownShoeSizes[3], brownShoeSizes[4]);
            } else {
                int stock = Integer.parseInt(fourthLine);
                product = new Product(productName, productId, price, stock, category);
            }

            lines.put(productId, product);
            productOrderStats.put(productId, 0);
            productRatings.put(productId, new ArrayList<String>());

        }
        scanner.close();
        return lines;
    }

    public ECommerceSystem() {

        try {
            products = readProducts("productsForTesting.txt");
            // productsForTesting.txt used for testing the:
            // -books with no year
            // -shoes
            // you can comment out the code above and uncomment this code below to test it out.
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("The txt file is not formatted correctly.\nThe program will now exit.");
            System.exit(1);
        }

        // Create some customers
        customers.add(new Customer(generateCustomerId(), "Inigo Montoya", "1 SwordMaker Lane, Florin"));
        customers.add(new Customer(generateCustomerId(), "Prince Humperdinck", "The Castle, Florin"));
        customers.add(new Customer(generateCustomerId(), "Andy Dufresne", "Shawshank Prison, Maine"));
        customers.add(new Customer(generateCustomerId(), "Ferris Bueller", "4160 Country Club Drive, Long Beach"));

    }

    private String generateOrderNumber() {

        return "" + orderNumber++;
    }

    private String generateCustomerId() {

        return "" + customerId++;
    }

    private String generateProductId() {

        return "" + productId++;
    }

    public void printAllProducts() {

        int start = 0;
        int pageSize = 4;
        int end = start + pageSize;
        int maxEnd = products.size() - 1; // assuming there are no gaps in the keys
        boolean exitLoop = false;
        Scanner scanner = new Scanner(System.in);
        String productID = "";

        while (!exitLoop) {
            for (int i = start; i <= Math.min(end, maxEnd); i++) {
                productID = String.valueOf(i + 700);
                if (products.containsKey(productID)) {
                    products.get(productID).print();
                }
            }
            System.out.println("\nCatalogue Page: " + (start / pageSize + 1));
            System.out.print("""
                    
                    Type 'n' for next page.
                    'p' for previous page.
                    A number to go to a specific page.
                    Any other key to stay on the current page.
                    >""");
            String input = scanner.nextLine().toLowerCase();
            switch (input) {
                case "n" -> {
                    if (end < maxEnd) {
                        start = end + 1;
                        end = Math.min(start + pageSize, maxEnd);
                    }
                }
                case "p" -> {
                    if (start > 0) {
                        end = start - 1;
                        start = Math.max(end - pageSize, 0);
                    }
                }
                case "" -> {
                    System.out.print("""
                    
                    Type 'n' for next page.
                    'p' for previous page.
                    A number to go to a specific page.
                    Any other key to stay on the current page.
                    >""");
                }
                default -> {
                    try {
                        int pageNum = Integer.parseInt(input);
                        if (pageNum >= 0 && pageNum <= (maxEnd + 1) / pageSize - 1) {
                            start = pageNum * (pageSize + 1);
                            end = Math.min(start + pageNum, maxEnd);
                        } else {
                            //MAKE AN ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
                            System.out.println("Invalid page number.");
                        }
                    } catch (NumberFormatException e) {
                        exitLoop = true;
                    }
                }
            }
        }

//        for (Product product : products.values())
//            product.print();
    }

    // Print all products that are books. See getCategory() method in class Product
    public void printAllBooks() {

        for (Product product : products.values()) {
            if (product.getCategory() == Product.Category.BOOKS) {
                product.print();
            }
        }
    }

    // Print all current orders
    public void printAllOrders() {

        for (ProductOrder product : orders) {
            product.print();
        }
    }

    // Print all shipped orders
    public void printAllShippedOrders() {

        for (ProductOrder product : shippedOrders) {
            product.print();
        }
    }

    // Print all customers
    public void printCustomers() {

        for (Customer customer : customers) {
            customer.print();
        }
    }

    /**
     * Given a customer id, print all the current orders and shipped orders for them (if any)
     *
     * @param customerId the id of the customer
     */
    public void printOrderHistory(String customerId) {

        // Make sure customer exists - check using customerId
        boolean exist = false;

        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                exist = true;
                break;
            }
        }
        // otherwise throws an execution
        if (!exist) {
            throw new UnknownCustomerException(customerId);
        }

        // Print current orders of this customer
        System.out.println("Current Orders of Customer " + customerId);
        // enter code here
        for (ProductOrder product : orders) {
            if (customerId.equals(product.getCustomer().getId())) {
                product.print();
            }
        }

        // Print shipped orders of this customer
        System.out.println("\nShipped Orders of Customer " + customerId);
        //enter code here
        for (ProductOrder productOrder : shippedOrders) {
            if (customerId.equals(productOrder.getCustomer().getId())) {
                productOrder.print();
            }
        }
    }

    /**
     * Used to order products.
     * BookOrShoe is used to keep track of whether ORDERBOOK or ORDERSHOES was used.
     *
     * @param productId            the id of the product
     * @param customerId           the id of the customer
     * @param productOptions       the product option
     * @param orderBookOrOrderShoe either book or shoe or neither
     */
    public void orderProduct(String productId, String customerId, String productOptions, String orderBookOrOrderShoe) {

        Product currentProduct;
        Customer currentCustomer = null;
        boolean exist = false;

        // First check to see if customer object with customerId exists in array list customers
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                currentCustomer = customer;
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new UnknownCustomerException(customerId);
        }

        // Check to see if product object with productId exists in the map of products
        exist = products.containsKey(productId);

        if (!exist) {
            throw new UnknownProductException(productId);
        }
        currentProduct = products.get(productId);

        // prevents book from being picked when using "ORDER" or "ORDERSHOES". (must use orderbook)
        if ((currentProduct.getCategory() == Product.Category.BOOKS)

                // the possibilities are only ORDER or ORDERSHOES
                && (orderBookOrOrderShoe == null || orderBookOrOrderShoe.equalsIgnoreCase("shoe"))) {

            throw new ImproperSelectCommandException("book", "\"ORDERBOOK\"");

        } // Prevent shoes from being picked when using "ORDER" or "ORDERBOOK" (must use ordershoes)
        else if (currentProduct.getCategory() == (Product.Category.SHOES)

                // the possibilities are only ORDER or ORDERBOOK
                && (orderBookOrOrderShoe == null || orderBookOrOrderShoe.equalsIgnoreCase("book"))) {

            throw new ImproperSelectCommandException("shoe", "\"ORDERSHOES\"");

        }  // when either ORDERBOOK or ORDERSHOES was used
        else if (orderBookOrOrderShoe != null) {

            // if ORDERBOOK was used but the product selected was not a book
            if (orderBookOrOrderShoe.equalsIgnoreCase("book") && !(currentProduct.getCategory() == (Product.Category.BOOKS))) {

                throw new ImproperSelectCommandException("non-book", "\"ORDER\"");

            } // if ORDERSHOES was used but the product selected was not a shoe
            else if (orderBookOrOrderShoe.equalsIgnoreCase("shoe") && !(currentProduct.getCategory() == Product.Category.SHOES)) {

                throw new ImproperSelectCommandException("non-shoe", "\"ORDER\"");
            }
        }

        // Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
        exist = currentProduct.validOptions(productOptions);

        if (currentProduct.getCategory() == (Product.Category.BOOKS)) {

            // otherwise throws an execution
            if (!exist) {
                throw new InvalidProductOptionsException("Book", productId, productOptions);
            }
        } else if (currentProduct.getCategory() == (Product.Category.SHOES)) {

            // can only be false if it is a shoe with an invalid option
            if (!exist) {
                throw new InvalidProductOptionsException("Shoes", productId, productOptions);
            }
        }

        // Check if the product has stock available (i.e. not 0)
        exist = (currentProduct.getStockCount(productOptions) > 0);
        if (!exist) {

            String outOptions = "";

            // to account for shoes and books so that it would display their specific options
            if (!productOptions.equalsIgnoreCase("")) {
                outOptions = " Option: " + productOptions;
            }

            // otherwise throws an execution
            throw new ProductOutOfStockException(productId, currentProduct.getName(), outOptions);
        }

        // Create a ProductOrder, (make use of generateOrderNumber() method above)
        // reduce stock count of product by 1 (see class Product and class Book)
        // Add to order list and return order number string
        currentProduct.reduceStockCount(productOptions);
        String numOrder = generateOrderNumber();

        // checks if the product has ever been ordered before. If not then make this its 1st order otherwise add 1)
        productOrderStats.put(productId, productOrderStats.get(productId) + 1);

        orders.add(new ProductOrder(numOrder, currentProduct, currentCustomer, productOptions));
        System.out.println("Order #" + numOrder);
    }

    /**
     * Create a new Customer object and add it to the list of customers
     *
     * @param name    the name of the customer
     * @param address the address of the customer
     */
    public void createCustomer(String name, String address) {

        // Check name parameter to make sure it is not null or "" otherwise throws an exception
        if (name == null || name.equals("")) {
            throw new InvalidCustomerNameException();
        }
        // checks the address to make sure its is not null or "" otherwise throws an exception
        if (address == null || address.equals("")) {
            throw new InvalidCustomerAddressException();
        }

        // Create a Customer object and add to array list
        customers.add(new Customer(generateCustomerId(), name, address));
    }

    /**
     * Ships an order
     *
     * @param orderNumber the order number
     */
    public void shipOrder(String orderNumber) {

        ProductOrder currentOrders = null;
        boolean exist = false;

        // Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderNumber().equals(orderNumber)) {
                exist = true;
                currentOrders = orders.get(i);
                shippedOrders.add(currentOrders);
                orders.remove(i);
                break;
            }
        }

        //throws an exception if the orderNumber isn't in orders
        if (!exist) {
            throw new UnknownOrderNumberException(orderNumber);
        }
        currentOrders.print();
    }

    /**
     * Cancel a specific order based on order number
     *
     * @param orderNumber or the order number
     */
    public void cancelOrder(String orderNumber) {

        boolean exist = false;

        // Check if order number exists first.
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderNumber().equals(orderNumber)) {
                exist = true;
                orders.remove(i);
                break;
            }
        }

        // otherwise throws an execution
        if (!exist) {
            throw new UnknownOrderNumberException(orderNumber);
        }
        // cancelOrder doesn't print anything since in the video, nothing was printed when an order was cancelled
    }

    /**
     * prints all the books by a certain author
     *
     * @param author the outhor
     */
    public void printBooksByAuthor(String author) {

        boolean exist = false;
        ArrayList<Book> booksByAuthor = new ArrayList<Book>();

        // used for checking if the author is found
        for (Product product : products.values()) {
            if ((product.getCategory() == Product.Category.BOOKS) && ((Book) product).getAuthor().equalsIgnoreCase(author)) {
                exist = true;
                booksByAuthor.add((Book) product);
            }
        }

        if (!exist) {
            throw new UnknownAuthorException(author);
        }

        // Sorts the books made by a certain author by year published (from old to newest, if it doesn't have a year
        // then assume it's less than any year)
        booksByAuthor.sort(new Comparator<Book>() {

            public int compare(Book o1, Book o2) {

                if (o1.getYear() == null && o2.getYear() == null) {
                    return 0;
                } else if (o1.getYear() == null && o2.getYear() != null) {
                    return -1;
                } else if (o1.getYear() != null && o2.getYear() == null) {
                    return 1;
                } else if (o1.getYear() < o2.getYear()) {
                    return -1;
                } else if (o1.getYear() > o2.getYear()) {
                    return 1;
                }
                return 0;
            }
        });

        System.out.println("Books by " + author + ": ");
        for (Book book : booksByAuthor) {
            String outP;
            if (book.getYear() == null) {
                outP = "N/A";
            } else {
                outP = String.valueOf(book.getYear());
            }
            System.out.printf("\nId: %-5s Title: %-35s Year Published: %-15s", book.getId(), book.getTitle(), outP);
        }
    }

    /**
     * Sort products by increasing price
     */
    public void printProductByPrice() {

        ArrayList<Product> productsByPrice = new ArrayList<Product>(products.values());

        productsByPrice.sort(new Comparator<Product>() {

            public int compare(Product o1, Product o2) {
                if (o1.getPrice() < o2.getPrice()) {
                    return -1;
                } else if (o1.getPrice() == o2.getPrice()) {
                    return 0;
                }
                return 1;
            }
        });

        for (Product product : productsByPrice) {
            product.print();
        }
    }

    /**
     * Sort products alphabetically by product name
     */
    public void printProductByName() {

        ArrayList<Product> productsByName = new ArrayList<Product>(products.values());

        productsByName.sort(new Comparator<Product>() {

            public int compare(Product o1, Product o2) {
                if (o1.getName().compareTo(o2.getName()) > 0) {
                    return 1;
                } else if (o1.getName().compareTo(o2.getName()) < 0) {
                    return -1;
                }
                // if it's a book then sort by book title
                else if (o1.getName().equalsIgnoreCase("book") && o2.getName().equalsIgnoreCase("book")) {

                    if (((Book) o1).getTitle().compareTo(((Book) o2).getTitle()) > 0) {
                        return 1;
                    }
                    if (((Book) o1).getTitle().compareTo(((Book) o2).getTitle()) < 0) {
                        return -1;
                    }
                }
                return 0;
            }
        });

        for (Product product : productsByName) {
            product.print();
        }

    }

    /**
     * Sort products alphabetically by product name
     */
    public void sortCustomersByName() {

        customers.sort(Customer::compareTo);
    }

    /**
     * Sort products alphabetically by product name
     *
     * @param productId          the ID of the product
     * @param customerId         the ID of the customer
     * @param productOptions     the product option
     * @param typeBookOrTypeShoe either book or shoe or neither
     */
    public void addToCart(String productId, String customerId, String productOptions, String typeBookOrTypeShoe) {

        Product currentProduct;
        Customer currentCustomer = null;
        boolean exist = false;

        // throws an exception if typeBookOrTypeShoe was not 1-3
        if (typeBookOrTypeShoe != null && typeBookOrTypeShoe.equals("Unknown")) {
            throw new UnknownProductTypeException();
        }

        // checks to see a customer with the customerId exist
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                currentCustomer = customer;
                exist = true;
                break;
            }
        }

        // otherwise throws an exception
        if (!exist) {
            throw new UnknownCustomerException(customerId);
        }

        //checks if a product with the productId exist
        exist = products.containsKey(productId);

        // otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }
        currentProduct = products.get(productId);

        // prevents book from being picked when using Type 1 (Non-Shoe and Non-Book) or Type 3 (Shoe). Must use Type 2 (Book)
        if ((currentProduct.getCategory() == Product.Category.BOOKS)

                // the possibilities are only Type 1 (Non-Shoe and Non-Book) or Type 3 (Shoe)
                && (typeBookOrTypeShoe == null || typeBookOrTypeShoe.equalsIgnoreCase("shoe"))) {

            throw new ImproperSelectCommandException("book", "Product Type Book [2]");

        } // Prevent shoes from being picked when using Type 1 (Non-Shoe and Non-Book) or Type 2 (Book). Must use Type 3 (Shoe)
        else if ((currentProduct.getCategory() == Product.Category.SHOES)

                // the possibilities are only Type 1 (Non-Shoe and Non-Book) or Type 2 (Book)
                && (typeBookOrTypeShoe == null || typeBookOrTypeShoe.equalsIgnoreCase("book"))) {

            throw new ImproperSelectCommandException("shoe", "Product Type Shoe [3]");

        } // when either Type 2 (Book) or Type 3 (Shoe) was used
        else if (typeBookOrTypeShoe != null) {

            // if Type 2 (Book) was used but the product selected was not a book
            if (typeBookOrTypeShoe.equalsIgnoreCase("book") && !(currentProduct.getCategory() == Product.Category.BOOKS)) {

                throw new ImproperSelectCommandException("non-book", "Product Type Non-Shoe and Non-Book [1]");

            } // if Type 3 (Shoe) was used but the product selected was not a shoe
            else if (typeBookOrTypeShoe.equalsIgnoreCase("shoe") && !(currentProduct.getCategory() == Product.Category.SHOES)) {

                throw new ImproperSelectCommandException("non-shoe", "Product Type Non-Shoe and Non-Book [1]");
            }
        }

        exist = currentProduct.validOptions(productOptions);
        // Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
        // See class Product and class Book for the method validOptions()
        if (currentProduct.getCategory() == (Product.Category.BOOKS)) {

            // can only be false if it is a book with an invalid option
            if (!exist) {
                throw new InvalidProductOptionsException("Book", productId, productOptions);
            }
        } else if (currentProduct.getCategory() == (Product.Category.SHOES)) {

            // can only be false if it is a shoe with an invalid option
            if (!exist) {
                throw new InvalidProductOptionsException("Shoes", productId, productOptions);
            }
        }

        // Check if the product has stock available (i.e. not 0)
        exist = (currentProduct.getStockCount(productOptions) > 0);
        if (!exist) {

            String outOptions = "";

            // to account for shoes and books so that it would display their specific options
            if (!productOptions.equalsIgnoreCase("")) {
                outOptions = " Option: " + productOptions;
            }
            throw new ProductOutOfStockException(productId, currentProduct.getName(), outOptions);
        }

        // adds the product to the cartArrayList and also
        currentCustomer.addItemToCustomerCart(currentProduct, productOptions, typeBookOrTypeShoe);

        // even if the customerId already exist in the map, it will just overwrite it with the same cartItemList
        // This is really only here for putting a new customerId into the map.
        cartsWithItems.put(customerId, currentCustomer.getCustomerCart());
        System.out.println("Product " + productId + " successfully added to cart of customerId " + customerId);
    }


    /**
     * removes the first instance of an item with the product ID from an existing cart.
     * the reason why this is the only boolean command is that I wanted to make it so that it would only print the
     * "success" msg only when the "REMCARTITEM" is used and not when removeRemoveFromCart() is called from
     * orderAllCartItemOfCustomer
     *
     * @param productId  the ID of the product
     * @param customerId the ID customer
     * @return whether an item could be removed from the cart
     */
    public boolean removeRemoveFromCart(String productId, String customerId) {

        boolean exist = false;

        //checks if a customer with customerId exists
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                exist = true;
                break;
            }
        }

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownCustomerException(customerId);
        }

        //checks if a product with productId exists
        exist = products.containsKey(productId);

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }

        //see if the customerId has a cart
        if (cartsWithItems.containsKey(customerId)) {

            ArrayList<CartItem> cartItemArrayList = cartsWithItems.get(customerId).getCartItemList();

            //checks if the cart of customerId has the product and if it does then it removes it
            for (CartItem item : cartItemArrayList) {
                if (item.getProduct().getId().equalsIgnoreCase(productId)) {

                    cartsWithItems.get(customerId).removeCartItem(item);

                    // if the product that got removed from the cart was the last product in the cart then, it removes the cart from the map entirely.
                    if (cartItemArrayList.size() == 0) {
                        cartsWithItems.remove(customerId);
                    }

                    // only removes the first instance of it
                    return true;
                }
            }

            // if the product isn't in customer's cart
            throw new ProductNotInCartException(productId, customerId);
        } else {
            // if the customer has no CartItems in their cart.
            throw new CustomerHasNoCartItemException(customerId);
        }
    }

    /**
     * prints all the items in a certain existing cart
     *
     * @param customerId the ID of a customer
     */
    public void printAllCartItemOfCustomer(String customerId) {

        boolean exist = false;

        //checks if a customer with customerId exists
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                exist = true;
                break;
            }
        }

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownCustomerException(customerId);
        }

        // checks if the customer actually has a cart
        if (cartsWithItems.containsKey(customerId)) {
            System.out.println("CustomerId " + customerId + " cart items");

            //prints all the items in the cart of the customer
            for (CartItem item : cartsWithItems.get(customerId).getCartItemList()) {
                item.getProduct().print();
            }
        } else {
            // if the customer has no CartItems in their cart.
            throw new CustomerHasNoCartItemException(customerId);
        }
    }

    /**
     * orders all the items in the cart of a customer and as well as empties the cart
     *
     * @param customerId the ID of a customer
     */
    public void orderAllCartItemOfCustomer(String customerId) {

        boolean exist = false;

        //checks if a customer with customerId exists
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                exist = true;
                break;
            }
        }

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownCustomerException(customerId);
        }

        if (cartsWithItems.containsKey(customerId)) {
            int cartSize = cartsWithItems.get(customerId).getCartItemList().size();
            ArrayList<CartItem> cartItemArrayList = cartsWithItems.get(customerId).getCartItemList();
            int itemCount = 1;

            for (int i = 0; i < cartSize; i++) {

                Product product = cartItemArrayList.get(i).getProduct();
                String productName = product.getName();
                String productId = product.getId();
                String productOptions = cartItemArrayList.get(i).getProductOptions();
                String orderBookOrOrderShoe = cartItemArrayList.get(i).getTypeBookOrTypeShoe();

                // to account for the scenario where multiple orders have been made to the same product to the point
                // where are more items in the cart then there are the stocks of the items and so it will eventually
                // get out of stock as each of the items are being ordered from the cart
                if (product.getStockCount(productOptions) <= 0) {

                    String outOptions = "";

                    // to account for shoes and books so that it would display their specific options
                    if (!productOptions.equalsIgnoreCase("")) {
                        outOptions = " Option: " + productOptions;
                    }

                    // to remove the item that has no stock (we won't have to worry about the user putting an item with
                    // no stock back into the cart since that's already taken care of in the addToCart method
                    removeRemoveFromCart(productId, customerId);
                    System.out.print("CartItem " + itemCount + " failed to order. ");
                    throw new ProductOutOfStockException(productId, productName, outOptions);
                }

                System.out.print("CartItem " + itemCount + " successfully ordered. ");
                orderProduct(productId, customerId, productOptions, orderBookOrOrderShoe);
                itemCount++;

                // removes the items from the carts ArrayList
                removeRemoveFromCart(productId, customerId);

                //this is here to update the queue of the ArrayList
                i--;
                cartSize--;
            }
        } else {
            // if the customer has no CartItems in their cart.
            throw new CustomerHasNoCartItemException(customerId);
        }
    }

    /**
     * prints the products order from the most to the least ordered
     */
    public void printProductOrderStats() {

        // makes an arrayList of the map of <String, Integer>
        ArrayList<Map.Entry<String, Integer>> productIDsByOrderStats = new ArrayList<>(productOrderStats.entrySet());

        // sorts the arrayList from greatest to the least orders
        productIDsByOrderStats.sort((o1, o2) -> {
            if (o1.getValue() < o2.getValue()) {
                return 1;
            } else if (o1.getValue() > o2.getValue()) {
                return -1;
            }
            return 0;
        });

        // goose from the sorted ArrayList of Ids and prints the product with the associated Ids.
        for (Map.Entry<String, Integer> statsMap : productIDsByOrderStats) {
            for (Product product : products.values()) {
                if (product.getId().equals(statsMap.getKey())) {
                    System.out.printf("\nProduct Name: %-25s Product Id: %-5s Ordered: %-5s", product.getName(), product.getId(), statsMap.getValue());
                    break;
                }
            }
        }
    }

    /**
     * private method used for calculating the average rating given a productId
     *
     * @param productId the ID of a product
     * @return the average rating of a product
     */
    private double averRatingFinder(String productId) {

        double aveRatings = 0;

        if (productRatings.get(productId).size() > 0) {

            // gets the value of the map (this being a string arraylist of all the ratings of a product)
            for (int i = 0; i < productRatings.get(productId).size(); i++) {
                aveRatings += Double.parseDouble(productRatings.get(productId).get(i));
            }
            aveRatings = aveRatings / productRatings.get(productId).size();
        }
        return aveRatings;
    }

    /**
     * adds one rating to a product
     *
     * @param productId the ID of a product
     * @param rating    the rating to be entered for the product
     */
    public void rateProduct(String productId, String rating) {

        // throws an exception if a rating between 1 and 5 was not entered or if rating was not a number
        try {
            if (Double.parseDouble(rating) > 5 || Double.parseDouble(rating) < 1) {
                throw new InvalidRatingException(rating);
            }
        } catch (Exception e) {
            throw new InvalidRatingException(rating);
        }


        // checks if the product exists
        boolean exist = products.containsKey(productId);

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }

        productRatings.get(productId).add(rating);
        System.out.println("Rating " + rating + " successfully added to product " + productId);
        System.out.println("Updated average rating is " + String.format("%.1f", averRatingFinder(productId)));
    }

    /**
     * displays the average rating of one product
     *
     * @param productId the ID of a product
     */
    public void ratingsOfOneProduct(String productId) {
        Product currentProduct;

        //checks if the product exists
        boolean exist = products.containsKey(productId);

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }
        currentProduct = products.get(productId);


        System.out.println("Product " + productId + " (" + currentProduct.getName() + ") currently has an average rating of " + String.format("%.1f", averRatingFinder(productId)));
    }

    /**
     * Prints all the products of a certain category with an average rating greater than or equal to a minimum rating
     * entered
     *
     * @param categoryInput the selected category for the products to be printed
     * @param minimumRating the minimum rating each product must have
     */
    public void printCategoryBaseOnMinimumRating(String categoryInput, String minimumRating) {

        double minimumAverageRating;
        Product.Category enumCategory = null;

        // throws an error if an improper category was selected.
        if (!"1234567".contains(categoryInput)) {
            throw new InvalidCategoryException(categoryInput);
        }

        // throws an exception if a rating between 0 and 5 was not entered or if rating was not a number
        try {
            minimumAverageRating = Double.parseDouble(minimumRating);
            if (minimumAverageRating > 5 || minimumAverageRating < 0) {
                throw new InvalidRatingException(minimumRating);
            }
        }

        // to account for the scenario in which the user enters a non-number
        catch (Exception e) {
            throw new InvalidRatingException(minimumRating);
        }

        // makes an arrayList of the map <String, ArrayList<String>>
        ArrayList<Map.Entry<String, ArrayList<String>>> categoryByAverageRatingsProducts = new ArrayList<Map.Entry<String, ArrayList<String>>>(productRatings.entrySet());

        //orders the Arraylist from greatest to least ratings (the bonus never said to do this)
        categoryByAverageRatingsProducts.sort((o1, o2) -> {
            if (averRatingFinder(o1.getKey()) < averRatingFinder(o2.getKey())) {
                return 1;
            } else if (averRatingFinder(o1.getKey()) > averRatingFinder(o2.getKey())) {
                return -1;
            }
            return 0;
        });


        switch (categoryInput) {
            case "1" -> enumCategory = Product.Category.GENERAL;
            case "2" -> enumCategory = Product.Category.CLOTHING;
            case "3" -> enumCategory = Product.Category.BOOKS;
            case "4" -> enumCategory = Product.Category.FURNITURE;
            case "5" -> enumCategory = Product.Category.COMPUTERS;
            case "6" -> enumCategory = Product.Category.SHOES;
            case "7" -> {

                // only when category is set to "all"
                for (Map.Entry<String, ArrayList<String>> ratingMap : categoryByAverageRatingsProducts) {
                    for (Product product : products.values()) {
                        if (ratingMap.getKey().equals(product.getId()) && averRatingFinder(ratingMap.getKey()) >= minimumAverageRating) {
                            System.out.printf("\nProduct Id: %-4s Category: %-10sProduct Name: %-25s Average Rating: %-5s",
                                    product.getId(), product.getCategory(), product.getName(), String.format("%.1f", averRatingFinder(product.getId())));
                            break;
                        }
                    }
                }
                // exits the method after it has printed all the products
                return;
            }
        }

        // used to print only the products of selected category that's not all categories
        for (Map.Entry<String, ArrayList<String>> ratingMap : categoryByAverageRatingsProducts) {
            for (Product product : products.values()) {
                if (ratingMap.getKey().equals(product.getId()) && averRatingFinder(ratingMap.getKey()) >= minimumAverageRating && enumCategory == product.getCategory()) {
                    System.out.printf("\nProduct Id: %-4s Category: %-10sProduct Name: %-25s Average Rating: %-5s",
                            product.getId(), product.getCategory(), product.getName(), String.format("%.1f", averRatingFinder(product.getId())));
                    break;
                }
            }
        }
    }
}

class UnknownCustomerException extends RuntimeException {

    public UnknownCustomerException() {
    }

    public UnknownCustomerException(String customerId) {

        super("Customer " + customerId + " not found.");
    }
}

class UnknownProductException extends RuntimeException {

    public UnknownProductException() {
    }

    public UnknownProductException(String productId) {

        super("Product " + productId + " not found.");
    }
}

class InvalidProductOptionsException extends RuntimeException {

    public InvalidProductOptionsException() {
    }

    public InvalidProductOptionsException(String bookOrShoe, String productId, String productOptions) {

        super("Product " + bookOrShoe + " Product Id: " + productId + " Invalid Option: " + productOptions + ".");
    }
}

class ProductOutOfStockException extends RuntimeException {

    public ProductOutOfStockException() {
    }

    public ProductOutOfStockException(String productId, String productName, String productOptions) {

        super("Product Id: " + productId + " Product Name: " + productName + productOptions + " is out of stock.");
    }
}

class InvalidCustomerNameException extends RuntimeException {

    public InvalidCustomerNameException() {

        super("Invalid customer name.");
    }
}

class InvalidCustomerAddressException extends RuntimeException {

    public InvalidCustomerAddressException() {

        super("Invalid customer address.");
    }
}

class UnknownOrderNumberException extends RuntimeException {

    public UnknownOrderNumberException() {
    }

    public UnknownOrderNumberException(String orderNumber) {

        super("Order " + orderNumber + " not found.");
    }
}

// The exceptions below here are extra added to account for the unmentioned errors in the pdf that may occur
class ImproperSelectCommandException extends RuntimeException {

    public ImproperSelectCommandException() {
    }

    public ImproperSelectCommandException(String bookOrShoe, String correctCommand) {
        super("An improper command was used to select is a " + bookOrShoe + ". Please use " + correctCommand + " instead.");
    }
}

class UnknownAuthorException extends RuntimeException {

    public UnknownAuthorException() {
    }

    public UnknownAuthorException(String author) {
        super("Author " + author + " not found");
    }
}

class UnknownProductTypeException extends RuntimeException {

    public UnknownProductTypeException() {
        super("An improper Product Type was entered. Please only select [1,2,3] instead.");
    }
}

class ProductNotInCartException extends RuntimeException {

    public ProductNotInCartException() {
    }

    public ProductNotInCartException(String productId, String customerId) {

        super("Product " + productId + " is not in the cart of customer " + customerId + ".");
    }
}

class CustomerHasNoCartItemException extends RuntimeException {

    public CustomerHasNoCartItemException() {
    }

    public CustomerHasNoCartItemException(String customerId) {
        super("Customer " + customerId + " has no items in their cart.");
    }
}

class InvalidRatingException extends RuntimeException {

    public InvalidRatingException() {
    }

    public InvalidRatingException(String rating) {

        super("Rating " + rating + " is invalid. Please only input a rating between 0 and 5");
    }
}

class InvalidCategoryException extends RuntimeException {

    public InvalidCategoryException() {
    }

    public InvalidCategoryException(String category) {

        super("Category " + category + " is invalid. Please only select [1,2,3,4,5,6,7] instead");
    }
}
