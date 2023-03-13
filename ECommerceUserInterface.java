//Viet Hoang
//501108602

import java.util.Scanner;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface {
    public static void main(String[] args) {
        // Create the system
        ECommerceSystem amazon = new ECommerceSystem();

        Scanner scanner = new Scanner(System.in);
        System.out.print(">");

        // Process keyboard actions
        while (scanner.hasNextLine()) {
            String action = scanner.nextLine().trim();

            try {
                if (action.equals("")) {
                    System.out.print("""
                            "Q" or "QUIT" to exit.
                            "PRODS" to display all products.
                            >""");
                }
                else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT")) {

                    // used to full quit so that it doesn't print the ">" in the "finally" of the try catch loop
                    // (since using return will cuz the code to still print the ">")
                    System.exit(0);
                }
                else if (action.equalsIgnoreCase("PRODS")) { // List all products for sale
                    amazon.printAllProducts();
                }
                else if (action.equalsIgnoreCase("BOOKS")) {   // List all books for sale

                    amazon.printAllBooks();
                }
                else if (action.equalsIgnoreCase("CUSTS")) {  // List all registered customers

                    amazon.printCustomers();
                }
                else if (action.equalsIgnoreCase("ORDERS")) { // List all current product orders

                    amazon.printAllOrders();
                }
                else if (action.equalsIgnoreCase("SHIPPED")) {    // List all orders that have been shipped

                    amazon.printAllShippedOrders();
                }
                else if (action.equalsIgnoreCase("NEWCUST")) {    // Create a new registered customer

                    String name = "";
                    String address = "";

                    System.out.print("Name: ");
                    if (scanner.hasNextLine()) {
                        name = scanner.nextLine().trim();
                    }

                    System.out.print("\nAddress: ");
                    if (scanner.hasNextLine()) {
                        address = scanner.nextLine().trim();
                    }

                    amazon.createCustomer(name, address);

                } // ship an order to a customer
                else if (action.equalsIgnoreCase("SHIP")) {

                    String orderNumber = "";

                    System.out.print("Order Number: ");
                    // Get order number from scanner
                    if (scanner.hasNextLine()) {
                        orderNumber = scanner.nextLine().trim();
                    }

                    // Ship order to customer
                    amazon.shipOrder(orderNumber);

                } // List all the current orders and shipped orders for this customer id
                else if (action.equalsIgnoreCase("CUSTORDERS")) {

                    String customerId = "";

                    System.out.print("Customer Id: ");
                    if (scanner.hasNextLine()) {
                        customerId = scanner.nextLine().trim();
                    }

                    // Print all current orders and all shipped orders for this customer
                    amazon.printOrderHistory(customerId);

                } // order a product for a certain customer
                else if (action.equalsIgnoreCase("ORDER")) {

                    String productId = "";
                    String customerId = "";

                    System.out.print("Product Id: ");
                    // Get product Id from scanner
                    if (scanner.hasNextLine()) {
                        productId = scanner.nextLine().trim();
                    }

                    System.out.print("\nCustomer Id: ");
                    // Get customer Id from scanner
                    if (scanner.hasNextLine()) {
                        customerId = scanner.nextLine().trim();
                    }

                    // Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem
                    // Print Order Number string returned from method in ECommerceSystem
                    amazon.orderProduct(productId, customerId, "", null);


                }  // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
                else if (action.equalsIgnoreCase("ORDERBOOK")) {

                    String productId = "";
                    String customerId = "";
                    String options = "";

                    System.out.print("Product Id: ");
                    // get product id
                    if (scanner.hasNextLine()) {
                        productId = scanner.nextLine().trim();
                    }

                    System.out.print("\nCustomer Id: ");
                    // get customer id
                    if (scanner.hasNextLine()) {
                        customerId = scanner.nextLine().trim();
                    }

                    System.out.print("\nFormat [Paperback Hardcover EBook]: ");
                    // get book forma and store in options string
                    if (scanner.hasNextLine()) {
                        options = scanner.nextLine().trim();
                    }

                    // Order product.
                    // Print order number string if order number is not null
                    amazon.orderProduct(productId, customerId, options, "book");

                }  // order shoes for a customer, provide size and color
                else if (action.equalsIgnoreCase("ORDERSHOES")) {

                    String productId = "";
                    String customerId = "";
                    String options = "";

                    System.out.print("Product Id: ");
                    // get product id
                    if (scanner.hasNextLine()) {
                        productId = scanner.nextLine().trim();
                    }

                    System.out.print("\nCustomer Id: ");
                    // get customer id
                    if (scanner.hasNextLine()) {
                        customerId = scanner.nextLine().trim();
                    }

                    System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
                    // get shoe size and store in options
                    if (scanner.hasNextLine()) {
                        options = "Size " + scanner.nextLine().trim();
                    }

                    System.out.print("\nColor: \"Black\" \"Brown\": ");
                    // get shoe color and append to options
                    if (scanner.hasNextLine()) {
                        options = options + " in " + scanner.nextLine().trim();
                    }

                    //order shoes
                    amazon.orderProduct(productId, customerId, options, "shoe");

                } // Cancel an existing order
                else if (action.equalsIgnoreCase("CANCEL")) {
                    String orderNumber = "";

                    System.out.print("Order Number: ");
                    // get order number from scanner
                    if (scanner.hasNextLine()) {
                        orderNumber = scanner.nextLine().trim();
                    }
                    //cancels the order
                    amazon.cancelOrder(orderNumber);
                }
                else if (action.equalsIgnoreCase("BooksByAuthor")) {

                    String author = "";

                    System.out.print("Author: ");
                    // gets author for scanner
                    if (scanner.hasNextLine()) {
                        author = scanner.nextLine().trim();
                    }
                    amazon.printBooksByAuthor(author);
                }
                else if (action.equalsIgnoreCase("ADDTOCART")) {
                    String productId = "";
                    String customerId = "";
                    String typeOfProduct = "";
                    String productOptions = "";

                    System.out.print("Product Id: ");
                    // get product id
                    if (scanner.hasNextLine()) {
                        productId = scanner.nextLine().trim();
                    }

                    System.out.print("\nCustomer Id: ");
                    // get customer id
                    if (scanner.hasNextLine()) {
                        customerId = scanner.nextLine().trim();
                    }

                    System.out.print("""

                            Product Type:
                            1) Non-Shoe and Non-Book
                            2) Book
                            3) Shoe
                            Select Product Type [1, 2, or 3]:\s""");
                    // get customer id
                    if (scanner.hasNextLine()) {
                        typeOfProduct = scanner.nextLine().trim();
                    }

                    // for non-shoes and non-nooks
                    switch (typeOfProduct) {
                        case "1" -> amazon.addToCart(productId, customerId, "", null);


                        //for book
                        case "2" -> {
                            System.out.print("\nFormat [Paperback Hardcover EBook]: ");
                            // get book forma and store in options string
                            if (scanner.hasNextLine()) {
                                productOptions = scanner.nextLine().trim();
                            }
                            amazon.addToCart(productId, customerId, productOptions, "book");
                        }

                        // for shoe
                        case "3" -> {
                            System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");

                            // get shoe size and store in options
                            if (scanner.hasNextLine()) {
                                productOptions = "Size " + scanner.nextLine().trim();
                            }
                            System.out.print("\nColor: \"Black\" \"Brown\": ");

                            // get shoe color and append to options
                            if (scanner.hasNextLine()) {
                                productOptions = productOptions + " in " + scanner.nextLine().trim();
                            }
                            amazon.addToCart(productId, customerId, productOptions, "shoe");
                        }
                        default -> amazon.addToCart("", "", "", "Unknown");
                    }
                }
                else if (action.equalsIgnoreCase("REMCARTITEM")) {
                    String productId = "";
                    String customerId = "";

                    System.out.print("Product Id: ");
                    // get product id
                    if (scanner.hasNextLine()) {
                        productId = scanner.nextLine().trim();
                    }

                    System.out.print("\nCustomer Id: ");
                    // get customer id
                    if (scanner.hasNextLine()) {
                        customerId = scanner.nextLine().trim();
                    }
                    boolean success = amazon.removeRemoveFromCart(productId, customerId);

                    if (success){
                        System.out.println("First instance of ProductId " + productId + " successfully removed from cart of customerId " + customerId);
                    }

                }
                else if (action.equalsIgnoreCase("PRINTCART")) {

                    String customerId = "";

                    System.out.print("\nCustomer Id: ");
                    // get customer id
                    if (scanner.hasNextLine()) {
                        customerId = scanner.nextLine().trim();
                    }
                    amazon.printAllCartItemOfCustomer(customerId);
                }
                else if (action.equalsIgnoreCase("ORDERITEMS")) {

                    String customerId = "";

                    System.out.print("\nCustomer Id: ");
                    // get customer id
                    if (scanner.hasNextLine()) {
                        customerId = scanner.nextLine().trim();
                    }

                    amazon.orderAllCartItemOfCustomer(customerId);
                }
                else if (action.equalsIgnoreCase("STATS")){
                    amazon.printProductOrderStats();

                }
                else if(action.equalsIgnoreCase("RateProd")){

                    String productId = "";
                    String rating = "";

                    System.out.print("Product Id: ");
                    // get product id
                    if (scanner.hasNextLine()) {
                        productId = scanner.nextLine().trim();
                    }

                    // can also rate as doubles (ie can give a rating of 2.5)
                    System.out.print("\nRating [1 to 5]: ");
                    // get customer id
                    if (scanner.hasNextLine()) {
                        rating = scanner.nextLine().trim();
                    }
                    amazon.rateProduct(productId, rating);
                }
                else if (action.equalsIgnoreCase("AvgRatingOfOneProd")){

                    String productId = "";
                    System.out.print("Product Id: ");
                    // get product id
                    if (scanner.hasNextLine()) {
                        productId = scanner.nextLine().trim();
                    }
                    amazon.ratingsOfOneProduct(productId);
                }
                else if (action.equalsIgnoreCase("PrintCategoryWithMinimumRating")){

                    String category = "";
                    String minimumRating = "";

                    System.out.print("""
                            Category:\s
                             1) GENERAL
                             2) CLOTHING
                             3) BOOKS
                             4) FURNITURE
                             5) COMPUTERS
                             6) SHOES
                             7) All Categories
                             Select [1, 2, 3, 4, 5, 6, or 7]:\s""");
                    // get Category id
                    if (scanner.hasNextLine()) {
                        category = scanner.nextLine().trim();
                    }

                    System.out.print("Minimum Rating [0 to 5]: ");
                    // get minimum rating
                    if (scanner.hasNextLine()) {
                        minimumRating = scanner.nextLine().trim();
                    }

                    amazon.printCategoryBaseOnMinimumRating(category, minimumRating);

                }
                else if (action.equalsIgnoreCase("PRINTBYPRICE")) { // sort products by price
                    amazon.printProductByPrice();

                }
                else if (action.equalsIgnoreCase("PRINTBYNAME")) { // sort products by name (alphabetic)
                    amazon.printProductByName();

                }
                else if (action.equalsIgnoreCase("SORTCUSTS")) { // sort customers by name (alphabetic)
                    amazon.sortCustomersByName();

                }
            }
            catch (RuntimeException e) {

                System.out.println(e.getMessage());
            }
            finally {
                System.out.print("\n>");
            }
        }
        scanner.close();
    }
}
