package sample.ShoppingSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ECommerceUserInterface {
    public static void main(String[] args) {
        String red = "\u001B[31m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        boolean shopIsOn = true;

        while (shopIsOn) {

            // Create the system
            ECommerceSystem onlineShoppingSystem = new ECommerceSystem();

            boolean userIn = true;
            User currentUser = new User();
            System.out.print("""
                            Options
                            1) Login.
                            2) Create Account.
                            3) Display Products.
                            4) Quit System.
                            Select [1, 2, 3, or 4]:\s""");
            while (userIn) {
                String action = scanner.nextLine().trim();
                try {

                    if (action.equalsIgnoreCase("1")) {
                        String userName = "";
                        String password = "";
                        System.out.print("User Name: ");
                        if (scanner.hasNextLine()) {
                            userName = scanner.nextLine();
                        }

                        System.out.print("Password: ");
                        if (scanner.hasNextLine()) {
                            password = scanner.nextLine();
                        }
                        currentUser = onlineShoppingSystem.userLogin(userName, password);
                        System.out.println("Login Success");
                        break;
                    }
                    else if (action.equalsIgnoreCase("2")) {    // register a new user

                        String category = "";
                        String name = "";
                        String userName = "";
                        String password = "";
                        String retypePassWord = "";
                        double balance = 0;
                        String address = "";

                        System.out.print("""
                                Category:\s
                                1) CUSTOMER
                                2) SELLER
                                Select [1 or 2]:\s""");
                        if (scanner.hasNextLine()) {
                            category = scanner.nextLine().trim();
                        }

                        System.out.print("Name: ");
                        if (scanner.hasNextLine()) {
                            name = scanner.nextLine().trim();
                        }

                        System.out.print("Username: ");
                        if (scanner.hasNextLine()) {
                            userName = scanner.nextLine();
                        }

                        System.out.print("Password: ");
                        if (scanner.hasNextLine()) {
                            password = scanner.nextLine();
                        }
                        System.out.print("Retype your password: ");
                        if (scanner.hasNextLine()) {
                            retypePassWord = scanner.nextLine().trim();
                        }
                        while (!password.equals(retypePassWord)) {
                            System.out.println("Retyped password is not the same try again.");

                            System.out.print("Password: ");
                            if (scanner.hasNextLine()) {
                                password = scanner.nextLine();
                            }
                            System.out.print("Retype your password: ");
                            if (scanner.hasNextLine()) {
                                retypePassWord = scanner.nextLine().trim();
                            }
                        }
                        System.out.print("Balance: ");
                        if (scanner.hasNextLine()) {
                            balance = Double.parseDouble(scanner.nextLine().trim());
                        }

                        System.out.print("Shipping Address: ");
                        if (scanner.hasNextLine()) {
                            address = scanner.nextLine().trim();
                        }

                        onlineShoppingSystem.createUser(name, userName, password, balance, address, category);
                        System.out.println("New user created");
                        break;
                    }
                    else if (action.equalsIgnoreCase("3")) { // List all products for sale
                        String option = "";
                        ArrayList<Product> productsArrayList;
                        System.out.print("""
                            Options
                            1) Display Product by Price.
                            2) Display Product by Name.
                            3) Display Product by Category.
                            Select [1, 2, or 3]:\s""");
                        if (scanner.hasNextLine()) {
                            option = scanner.nextLine().trim();
                        }

                        if (option.equalsIgnoreCase("1")){
                            productsArrayList = onlineShoppingSystem.returnProductByPrice();
                        }
                        else if (option.equalsIgnoreCase("2")){
                            productsArrayList = onlineShoppingSystem.returnProductByName();
                        }
                        else if (option.equalsIgnoreCase("3")) {
                            productsArrayList = onlineShoppingSystem.returnCategoryBaseOnMinimumRating("8", "0");
                        }
                        else {
                            System.out.println("Not valid option.");
                            continue;
                        }
                        int start = 0;
                        int pageSize = 5 - 1;
                        int end = start + pageSize;
                        int maxEnd = productsArrayList.size() - 1; // assuming there are no gaps in the keys
                        boolean exitLoop = false;

                        while (!exitLoop) {
                            for (int i = start; i <= Math.min(end, maxEnd); i++) {
                                System.out.print(productsArrayList.get(i).returnStrProductPrint());
                            }
                            System.out.println("\nCatalogue Page: " + (start / pageSize + 1));
                            System.out.print("""
                                    'n' for next page.
                                    'p' for previous page.
                                    A number to go to a specific page.
                                    'q' to quit searching.
                                    Any other key to stay on the current page.
                                    >""");
                            String input = scanner.nextLine().trim().toLowerCase();
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
                                            'q' to quit searching.
                                            Any other key to stay on the current page.
                                            >""");
                                }
                                default -> {
                                    try {
                                        int pageNum = Integer.parseInt(input) - 1;
                                        if (pageNum >= 0 && pageNum < (maxEnd + 1) / (pageSize + 1)) {
                                            start = pageNum * (pageSize + 1);
                                            end = Math.min(start + pageSize, maxEnd);
                                        } else {
                                            System.out.println("\u001B[31m" + "Invalid page number." + "\u001B[0m");
                                        }
                                    } catch (NumberFormatException e) {
                                        if (input.equalsIgnoreCase("q")) {
                                            exitLoop = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if (action.equalsIgnoreCase("4")) {

                        System.exit(0);
                    }
                }
                catch (RuntimeException | IOException e) {
                    System.out.println(red + e.getMessage() + reset);
                }
                System.out.print("""
                            Options
                            1) Login.
                            2) Create Account.
                            3) Display Products.
                            4) Quit System.
                            Select [1, 2, 3, or 4]:\s""");
            }
            System.out.println(currentUser.returnStrUserPrint());
            System.out.print("""
                    Type ? to see selections.
                    >""");
            // Process keyboard actions
            while (scanner.hasNextLine()) {
                String action = scanner.nextLine().trim();
                try {
                    if (action.equalsIgnoreCase("LOGOUT")) {

                        // used to log out.
                        break;
                    }
                    else if (action.equalsIgnoreCase("DisplayProds")) { // List all products for sale
                        String option = "";
                        ArrayList<Product> productsArrayList;
                        System.out.print("""
                            Options
                            1) Display Product by Price.
                            2) Display Product by Name.
                            3) Display Product by Category.
                            Select [1, 2, or 3]:\s""");
                        if (scanner.hasNextLine()) {
                            option = scanner.nextLine().trim();
                        }

                        if (option.equalsIgnoreCase("1")){
                            productsArrayList = onlineShoppingSystem.returnProductByPrice();
                        }
                        else if (option.equalsIgnoreCase("2")){
                            productsArrayList = onlineShoppingSystem.returnProductByName();
                        }
                        else if (option.equalsIgnoreCase("3")) {
                            productsArrayList = onlineShoppingSystem.returnCategoryBaseOnMinimumRating("8", "0");
                        }
                        else {
                            System.out.println("Not valid option.");
                            break;
                        }
                        int start = 0;
                        int pageSize = 5 - 1;
                        int end = start + pageSize;
                        int maxEnd = productsArrayList.size() - 1; // assuming there are no gaps in the keys
                        boolean exitLoop = false;

                        while (!exitLoop) {
                            for (int i = start; i <= Math.min(end, maxEnd); i++) {
                                System.out.print(productsArrayList.get(i).returnStrProductPrint());
                            }
                            System.out.println("\nCatalogue Page: " + (start / pageSize + 1));
                            System.out.print("""
                                    'n' for next page.
                                    'p' for previous page.
                                    A number to go to a specific page.
                                    'q' to quit searching.
                                    Any other key to stay on the current page.
                                    >""");
                            String input = scanner.nextLine().trim().toLowerCase();
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
                                            'q' to quit searching.
                                            Any other key to stay on the current page.
                                            >""");
                                }
                                default -> {
                                    try {
                                        int pageNum = Integer.parseInt(input) - 1;
                                        if (pageNum >= 0 && pageNum < (maxEnd + 1) / (pageSize + 1)) {
                                            start = pageNum * (pageSize + 1);
                                            end = Math.min(start + pageSize, maxEnd);
                                        } else {
                                            System.out.println("\u001B[31m" + "Invalid page number." + "\u001B[0m");
                                        }
                                    } catch (NumberFormatException e) {
                                        if (input.equalsIgnoreCase("q")) {
                                            exitLoop = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if (action.equalsIgnoreCase("myOrderReq")) { // List all current product orders requests for a seller

                        ArrayList<ProductOrdered> orders = onlineShoppingSystem.returnAllOrderRequestsForSeller(currentUser.getId());
                        for (ProductOrdered ordered : orders) {
                            System.out.print(ordered.returnStrProductOrdered());
                        }
                    }
                    else if (action.equalsIgnoreCase("myProds")){ // List all the products from a seller (the current user)

                        ArrayList<Product> products = onlineShoppingSystem.returnMyProducts(currentUser.getId());
                        for (Product product : products){
                            System.out.print(product.returnStrProductPrint());
                        }
                    }

                    else if (action.equalsIgnoreCase("SHIP")) {

                        String productId = "";
                        int amount = 0;
                        String strOutPut = "";

                        System.out.print("Product ID: ");
                        // Get order number from scanner
                        if (scanner.hasNextLine()) {
                            productId = scanner.nextLine().trim();
                        }

                        System.out.print("Amount: ");
                        // gets amount of products
                        if (scanner.hasNextLine()) {
                            amount = Integer.parseInt(scanner.nextLine().trim());
                        }

                        // Ship order to customer
                        ArrayList<ProductOrdered> shippedOrdersArrayList = onlineShoppingSystem.shipOrders(currentUser.getId(), productId, amount);
                        for (ProductOrdered productOrdered : shippedOrdersArrayList){
                            System.out.print(productOrdered.returnStrProductOrdered() + " successfully shipped.");
                        }

                    } // List all the current orders and shipped orders for this customer id
                    else if (action.equalsIgnoreCase("MyOrderHistory")) {


                        // Print all current orders and all shipped orders for this customer
                        ArrayList<ProductOrdered> orders = onlineShoppingSystem.returnOrderHistory(currentUser.getId());
                        ArrayList<ProductOrdered> shippedOrders = onlineShoppingSystem.returnShipHistory( currentUser.getId());

                        // Print current orders of this customer
                        System.out.println(red + "Current Orders of User " + currentUser.getId() + reset);

                        for (ProductOrdered product : orders) {

                            System.out.print(product.returnStrProductOrdered());
                        }
                        System.out.println();
                        // Print shipped orders of this customer
                        System.out.println(red + "\nShipped Orders of User " + currentUser.getId() + reset);

                        for (ProductOrdered productOrdered : shippedOrders) {

                            System.out.print(productOrdered.returnStrProductOrdered());
                        }
                    }
                    else if (action.equalsIgnoreCase("CANCEL")) {
                        String orderNumber = "";

                        System.out.print("Order Number: ");
                        // get order number from scanner
                        if (scanner.hasNextLine()) {
                            orderNumber = scanner.nextLine().trim();
                        }
                        //cancels the order
                        ProductOrdered productOrdered = onlineShoppingSystem.returnCancelOrder(currentUser.getId(), orderNumber);
                        System.out.print(productOrdered.returnStrProductOrdered() + " successfully cancelled.");
                    }
                    else if (action.equalsIgnoreCase("ADDTOCART")) {
                        String productId = "";
                        int amount = 0;

                        System.out.print("Product Id: ");
                        // get product id
                        if (scanner.hasNextLine()) {
                            productId = scanner.nextLine().trim();
                        }

                        System.out.print("Amount: ");
                        // gets amount of products
                        if (scanner.hasNextLine()) {
                            amount = Integer.parseInt(scanner.nextLine().trim());
                        }

                        onlineShoppingSystem.addToCart(productId, currentUser.getId(), amount);
                        System.out.println(amount + "x | Product " + productId + " successfully added to cart of userId " + currentUser.getId());
                    }
                    else if (action.equalsIgnoreCase("REMCARTITEM")) {
                        String productId = "";

                        System.out.print("Product Id: ");
                        // get product id
                        if (scanner.hasNextLine()) {
                            productId = scanner.nextLine().trim();
                        }

                        boolean success = onlineShoppingSystem.removeRemoveFromCart(productId, currentUser.getId());

                        if (success) {
                            System.out.println("First instance of ProductId " + productId
                                    + " successfully removed from cart of userId " + currentUser.getId());
                        }
                    }
                    else if (action.equalsIgnoreCase("PRINTCART")) {

                        ArrayList<Product> products = onlineShoppingSystem.returnAllCartItemOfCustomer(currentUser.getId());
                        System.out.println("your cart has the following items:");

                        //prints all the items in the cart of the customer
                        for (Product product : products) {
                            System.out.print(product.returnStrProductPrint());
                        }

                    }
                    else if (action.equalsIgnoreCase("OrderCart")) {

                        ArrayList<ProductOrdered> productOrderedArrayList = onlineShoppingSystem.returnStrOrderAllCartItemOfCustomer(currentUser.getId());
                        int itemCount = 1;
                        for (ProductOrdered productOrdered : productOrderedArrayList){
                            System.out.print("\n" + "Product " + itemCount + " successfully ordered. " + productOrdered.returnStrProductOrdered().substring(1) + " successfully ordered. ");
                            itemCount++;
                        }
                    }
                    else if (action.equalsIgnoreCase("RateProd")) {

                        String productId = "";
                        String rating = "";

                        System.out.print("Product Id: ");
                        // get product id
                        if (scanner.hasNextLine()) {
                            productId = scanner.nextLine().trim();
                        }

                        // can also rate as doubles (ie can give a rating of 2.5)
                        System.out.print("Rating [1 to 5]: ");
                        // get customer id
                        if (scanner.hasNextLine()) {
                            rating = scanner.nextLine().trim();
                        }
                        double result = onlineShoppingSystem.rateProduct(currentUser.getId(), productId, rating);
                        System.out.println("Rating " + rating + " successfully added to product " + productId);
                        System.out.println("Updated average rating is " + String.format("%.1f", result));
                    }
                    else if (action.equalsIgnoreCase("DisplayCategoryWithMinimumRating")) {

                        String categoryInput = "";
                        String minimumRating = "";

                        System.out.print("""
                                Category:\s
                                 1) GENERAL
                                 2) CLOTHING
                                 3) BOOKS
                                 4) FURNITURE
                                 5) COMPUTERS
                                 6) MOVIES
                                 7) VIDEOGAMES
                                 8) All Categories
                                 Select [1, 2, 3, 4, 5, 6, 7 or 8]:\s""");
                        // get Category id
                        if (scanner.hasNextLine()) {
                            categoryInput = scanner.nextLine().trim();
                        }

                        System.out.print("Minimum Rating [0 to 5]: ");
                        // get minimum rating
                        if (scanner.hasNextLine()) {
                            minimumRating = scanner.nextLine().trim();
                        }

                        ArrayList<Product> categoryByAverageRatingsProducts = onlineShoppingSystem.returnCategoryBaseOnMinimumRating(categoryInput, minimumRating);
                        for (Product product: categoryByAverageRatingsProducts) {
                            double avg = product.getAverageRating();
                            System.out.printf("\nId: %-4s Category: %-10sProduct Name: %-40s Average Rating: %-5s",
                                    product.getId(), product.getCategory(), product.getName(), String.format("%.1f", avg));
                        }
                    }
                    else if (action.equalsIgnoreCase("remProduct")) {

                        String productId = "";

                        System.out.print("Product Id: ");
                        // get product id
                        if (scanner.hasNextLine()) {
                            productId = scanner.nextLine().trim();
                        }

                        onlineShoppingSystem.removeProduct(productId, currentUser.getId());
                        System.out.println("Product " + productId + " successfully removed from seller " + currentUser.getId());
                    }
                    else if (action.equalsIgnoreCase("updateProduct")) {

                        String productId = "";

                        System.out.print("Product Id: ");

                        if (scanner.hasNextLine()) {
                            productId = scanner.nextLine().trim();
                        }

                        Product product = onlineShoppingSystem.updateProduct(productId, currentUser.getId());

                        System.out.println(product.returnStrProductPrint());
                        System.out.print("\nOld product " + productId);

                        String newName = "";
                        double newPrice = 0;
                        int newStock = 0;

                        System.out.print("\nNew name: ");

                        if (scanner.hasNextLine()) {
                            newName = scanner.nextLine().trim();
                        }

                        System.out.print("New price: ");

                        if (scanner.hasNextLine()) {
                            newPrice = Double.parseDouble(scanner.nextLine().trim());
                        }

                        System.out.print("New stock: ");

                        if (scanner.hasNextLine()) {
                            newStock = Integer.parseInt(scanner.nextLine().trim());
                        }

                        if (product.getCategory() == Product.Category.BOOK) {
                            String newAuthor = "";
                            String newYearPublished = "";

                            System.out.print("New author: ");

                            if (scanner.hasNextLine()) {
                                newAuthor = scanner.nextLine().trim();
                            }

                            System.out.print("New year-published: ");

                            if (scanner.hasNextLine()) {
                                newYearPublished = scanner.nextLine().trim();
                            }

                            onlineShoppingSystem.updateProduct(product, newName, newPrice, newStock, newAuthor, newYearPublished);
                        }
                        else if (product.getCategory() == Product.Category.MOVIE) {
                            String newGenre = "";
                            String newLength = "";

                            System.out.print("New genre: ");

                            if (scanner.hasNextLine()) {
                                newGenre = scanner.nextLine().trim();
                            }

                            System.out.print("New length: ");

                            if (scanner.hasNextLine()) {
                                newLength = scanner.nextLine().trim();
                            }
                            onlineShoppingSystem.updateProduct(product, newName, newPrice, newStock, newGenre, newLength);
                        }
                        else if (product.getCategory() == Product.Category.VIDEOGAME) {

                            String newGenre = "";
                            String newESRBRating = "";

                            System.out.print("New genre: ");

                            if (scanner.hasNextLine()) {
                                newGenre = scanner.nextLine().trim();
                            }

                            System.out.print("New length: ");

                            if (scanner.hasNextLine()) {
                                newESRBRating = scanner.nextLine().trim();
                            }
                            onlineShoppingSystem.updateProduct(product, newName, newPrice, newStock, newGenre, newESRBRating);
                        }
                        else {
                            onlineShoppingSystem.updateProduct(product, newName, newPrice, newStock);
                        }

                        System.out.println("Product " + productId + " successfully updated product from seller " + currentUser.getId());
                    }
                    else if (action.equalsIgnoreCase("addProduct")) {

                        onlineShoppingSystem.addProduct(currentUser.getId());

                        Product.Category category = null;
                        String newName = "";
                        double newPrice = 0;
                        int newStock = 0;
                        String productId = "";

                        System.out.print("\nProduct Category: ");

                        if (scanner.hasNextLine()) {
                            category = Product.Category.valueOf(scanner.nextLine().trim());
                        }

                        System.out.print("Product name: ");

                        if (scanner.hasNextLine()) {
                            newName = scanner.nextLine().trim();
                        }

                        System.out.print("Product price: ");

                        if (scanner.hasNextLine()) {
                            newPrice = Double.parseDouble(scanner.nextLine().trim());
                        }

                        System.out.print("Product stock: ");

                        if (scanner.hasNextLine()) {
                            newStock = Integer.parseInt(scanner.nextLine().trim());
                        }

                        if (category == Product.Category.BOOK) {
                            String newAuthor = "";
                            String newYearPublished = "";

                            System.out.print("New author: ");

                            if (scanner.hasNextLine()) {
                                newAuthor = scanner.nextLine().trim();
                            }

                            System.out.print("New year-published: ");

                            if (scanner.hasNextLine()) {
                                newYearPublished = scanner.nextLine().trim();
                            }

                            productId = onlineShoppingSystem.addProduct(category, currentUser.getId(), newName, newPrice, newStock, newAuthor, newYearPublished);
                        }
                        else if (category == Product.Category.MOVIE) {
                            String newGenre = "";
                            String newLength = "";

                            System.out.print("New genre: ");

                            if (scanner.hasNextLine()) {
                                newGenre = scanner.nextLine().trim();
                            }

                            System.out.print("New length: ");

                            if (scanner.hasNextLine()) {
                                newLength = scanner.nextLine().trim();
                            }
                            productId = onlineShoppingSystem.addProduct(category, currentUser.getId(), newName, newPrice, newStock, newGenre, newLength);
                        }
                        else if (category == Product.Category.VIDEOGAME) {

                            String newGenre = "";
                            String newESRBRating = "";

                            System.out.print("New genre: ");

                            if (scanner.hasNextLine()) {
                                newGenre = scanner.nextLine().trim();
                            }

                            System.out.print("New length: ");

                            if (scanner.hasNextLine()) {
                                newESRBRating = scanner.nextLine().trim();
                            }
                            productId = onlineShoppingSystem.addProduct(category, currentUser.getId(), newName, newPrice, newStock, newGenre, newESRBRating);
                        }
                        else {
                            productId = onlineShoppingSystem.addProduct(category, currentUser.getId(), newName, newPrice, newStock);
                        }

                        System.out.println("New product " + productId + " successfully added by seller " + currentUser.getId());
                    }
                    else if (action.equalsIgnoreCase("updateAccount")){
                        String name = "";
                        String userName = "";
                        String password = "";
                        double balance = 0;
                        String shippingAddress = "";
                        System.out.print("""
                            Update Account options:
                            1) Username
                            2) Password
                            3) Name
                            4) Balance
                            5) Shipping shippingAddress
                            6) Quit updating Account
                            Select [1, 2, 3, 4, 5, or 6]:\s""");
                        while (true){
                            String option = scanner.nextLine();
                            if (option.equalsIgnoreCase("1")){
                                System.out.print("Username: ");
                                if (scanner.hasNextLine()) {
                                    userName = scanner.nextLine().trim();
                                }
                                onlineShoppingSystem.updateAccountUsername(currentUser.getId(), userName);
                                System.out.println("Username updated");
                            }
                            else if (option.equalsIgnoreCase("2")){

                                System.out.print("Password: ");
                                if (scanner.hasNextLine()) {
                                    password = scanner.nextLine();
                                }
                                onlineShoppingSystem.updateAccountPassword(currentUser.getId(), password);
                                System.out.println("Password updated");
                            }
                            else if (option.equalsIgnoreCase("3")){

                                System.out.print("Name: ");
                                if (scanner.hasNextLine()) {
                                    name = scanner.nextLine().trim();
                                }
                                onlineShoppingSystem.updateAccountName(currentUser.getId(), name);
                                System.out.println("Name updated");
                            }
                            else if (option.equalsIgnoreCase("4")){
                                System.out.print("Balance: ");
                                if (scanner.hasNextLine()) {
                                    balance = Double.parseDouble(scanner.nextLine().trim());
                                }
                                onlineShoppingSystem.updateAccountBalance(currentUser.getId(), balance);
                                System.out.println("Balance updated");
                            }
                            else if (option.equalsIgnoreCase("5")){
                                System.out.print("Shipping Address: ");
                                if (scanner.hasNextLine()) {
                                    shippingAddress = scanner.nextLine().trim();
                                }
                                onlineShoppingSystem.updateAccountShippingAddress(currentUser.getId(), shippingAddress);
                                System.out.println("Shipping Address updated");
                            }
                            else if (option.equalsIgnoreCase("6")){
                                break;
                            }
                            System.out.print("""
                            Update Account options:
                            1) Username
                            2) Password
                            3) Name
                            4) Balance
                            5) Shipping shippingAddress
                            6) Quit updating Account
                            Select [1, 2, 3, 4, 5, or 6]:\s""");
                        }

                    }
                    else if (action.equalsIgnoreCase("?")) {

                        System.out.print(
                                red +"LOGOUT " + reset + "to logout.\n" +
                                red + "DisplayProds " + reset + "to display all products.\n" +
                                red + "myOrderReq " + reset + "to display all current product orders requests for a seller (sellers only.\n" +
                                red + "myProds " + reset + "do display all products that you are selling (sellers only.\n" +
                                red + "SHIP " + reset + "to ship an order.\n" +
                                red + "MyOrderHistory " + reset + "to print the order history of a certain customer.\n" +
                                red + "CANCEL " + reset + "to cancel an order.\n" +
                                red + "ADDTOCART " + reset + "to add a product to customer cart.\n" +
                                red + "REMCARTITEM " + reset + "to remove a product from customer cart.\n" +
                                red + "PRINTCART " + reset + "to display all products in a customer cart.\n" +
                                red + "OrderCart " + reset + "to order all products in a customer cart.\n" +
                                red + "RateProd " + reset + "to rate a product.\n" +
                                red + "DisplayCategoryWithMinimumRating " + reset + "to display the products of a certain category with a certain minimum rating.\n" +
                                red + "remProduct " + reset + "to remove a product (sellers only).\n" +
                                red + "updateProduct " + reset + "to update a product (sellers only).\n" +
                                red + "addProduct " + reset + "to update a product (sellers only).\n" +
                                red + "updateAccount " + reset + "to update/change the account info.\n" +
                                red + "\nTHE COMMANDS ARE NOT CASE SENSITIVE.\n" + reset);
                    }
                }
                catch (RuntimeException | IOException e) {

                    System.out.println(e.getMessage());
                }
                System.out.println(currentUser.returnStrUserPrint());
                System.out.print("""
                        Type ? to see selections.
                        >""");
            }
        }
        scanner.close();
    }
}
