package sample.ShoppingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem {

    private Map<String, Product> products = new TreeMap<String, Product>();
    private Map<String, User> users = new TreeMap<String, User>();
    private Map<String, ProductOrdered> orders = new TreeMap<String, ProductOrdered>();
    private Map<String, ProductOrdered> shippedOrders = new TreeMap<String, ProductOrdered>();
    private Map<String, Cart> cartsWithItems = new TreeMap<String, Cart>();
    public String productFile = "products.txt";
    public String userFile = "users.txt";
    public String orderFile = "orders.txt";
    public String shippedOrderFile = "shippedOrders.txt";

    // These variables are used to generate order numbers, customer id's, product id's
    private int orderNumber = 500;
    private int userId = 900;
    private int productId = 700;

    /**
     * Reads a txt file and formats it into a map
     *
     * @param productFile the name of the product file
     * @return the map of all the products in [ProductId, Product] format
     * @throws FileNotFoundException file might be missing
     */
    private Map<String, Product> readProducts(String productFile) throws FileNotFoundException {

        Map<String, Product> lines = new TreeMap<String, Product>();
        Scanner scanner = new Scanner(new File(productFile));

        while (scanner.hasNextLine()) {

            String categoryLine = scanner.nextLine();
            String sellerIDLine = scanner.nextLine();
            String productIDLine = scanner.nextLine();
            String nameLine = scanner.nextLine();
            String priceLine = scanner.nextLine();
            String stockLine = scanner.nextLine();
            String ratingsLine = scanner.nextLine();
            String numberOfOrdersLine = scanner.nextLine();

            Product product;
            Product.Category category = Product.Category.valueOf(categoryLine);
            double price = Double.parseDouble(priceLine);
            int stock = Integer.parseInt(stockLine);
            int numOrders = Integer.parseInt(numberOfOrdersLine);

            if (category == Product.Category.BOOK) {
                String authorLine = scanner.nextLine();
                String yearPublishLine = scanner.nextLine();

                product = new Book(nameLine, sellerIDLine, productIDLine, price, stock, ratingsLine,  numOrders, authorLine, yearPublishLine);
            }
            else if (category == Product.Category.VIDEOGAME) {
                String ESRBRatingLine = scanner.nextLine();
                String genreLine = scanner.nextLine();

                product = new VideoGame(nameLine, sellerIDLine, productIDLine, price, stock, ratingsLine, numOrders, ESRBRatingLine, genreLine);
            }
            else if (category == Product.Category.MOVIE){
                String genreLine = scanner.nextLine();
                String lengthLine = scanner.nextLine();

                product = new Movie(nameLine, sellerIDLine, productIDLine, price, stock, ratingsLine, numOrders, genreLine, lengthLine);
            }

            else {

                product = new Product(nameLine, sellerIDLine, productIDLine, price, stock, category, ratingsLine, numOrders);
            }

            lines.put(productIDLine, product);
        }
        scanner.close();
        return lines;
    }
    /**
     *
     * @param userFile the name of the user file
     * @return the map the all users in [UserID, User] format
     * @throws FileNotFoundException file might be missing
     */
    private Map<String, User> readUsers(String userFile) throws FileNotFoundException {
        Map<String, User> lines = new TreeMap<String, User>();
        Scanner scanner = new Scanner(new File(userFile));

        while (scanner.hasNextLine()) {

            String categoryLine = scanner.nextLine();
            String userID = scanner.nextLine();
            String nameLine = scanner.nextLine();
            String userNameLine = scanner.nextLine();
            String passwordLine = scanner.nextLine();
            String balanceLine = scanner.nextLine();
            String addressLine = scanner.nextLine();

            User user;
            User.Category category = User.Category.valueOf(categoryLine);
            double balance = Double.parseDouble(balanceLine);

            if (category == User.Category.SELLER) {
                ArrayList<Product> sellerProductList = new ArrayList<>();
                ArrayList<ProductOrdered> orderRequests = new ArrayList<>();

                for (Product product : products.values()){
                    if (product.getSellerID().equals(userID)){
                        sellerProductList.add(product);
                    }
                }
                for (ProductOrdered productOrdered : orders.values()){
                    if (productOrdered.getProduct().getSellerID().equals(userID)){
                        orderRequests.add(productOrdered);
                    }
                }

                user = new Seller(userID, nameLine, userNameLine, passwordLine, balance, addressLine, category, sellerProductList, orderRequests);
            }
            else {

                user = new Customer(userID, nameLine, userNameLine, passwordLine, balance, addressLine, category);
            }
            lines.put(userID, user);
        }
        scanner.close();
        return lines;
    }
    /**
     * @param orderFile file of orders
     * @return map of the orders
     * @throws FileNotFoundException file might not exists
     */
    private Map<String, ProductOrdered> readOrders(String orderFile) throws FileNotFoundException {
        Map<String, ProductOrdered> lines = new TreeMap<String, ProductOrdered>();
        Scanner scanner = new Scanner(new File(orderFile));

        while (scanner.hasNextLine()){
            String orderNumberLine = scanner.nextLine();
            String userIdLine = scanner.nextLine();
            String sellerIdLine = scanner.nextLine();
            String productIdLine = scanner.nextLine();

            ProductOrdered productOrdered;
            productOrdered = new ProductOrdered(orderNumberLine, products.get(productIdLine), users.get(userIdLine));
            lines.put(orderNumberLine, productOrdered);
        }
        scanner.close();
        return lines;
    }
    private Map<String, ProductOrdered> readShippedOrders(String shippedOrderFile) throws FileNotFoundException {
        Map<String, ProductOrdered> lines = new TreeMap<String, ProductOrdered>();
        Scanner scanner = new Scanner(new File(shippedOrderFile));

        while (scanner.hasNextLine()){
            String orderNumberLine = scanner.nextLine();
            String userIdLine = scanner.nextLine();
            String sellerIdLine = scanner.nextLine();
            String productIdLine = scanner.nextLine();

            ProductOrdered productOrdered;
            productOrdered = new ProductOrdered(orderNumberLine, products.get(productIdLine), users.get(userIdLine));
            lines.put(orderNumberLine, productOrdered);
        }
        scanner.close();
        return lines;
    }

    public void readAllFiles() throws IOException {

        products = readProducts(productFile);
        orders = readOrders(orderFile);
        shippedOrders = readShippedOrders(shippedOrderFile);

    }
    public void readAllFiles(String userId) throws IOException {
        boolean exist = users.containsKey(userId);
        if (!exist) {
            throw new UnknownUserException(userId);
        }
        ArrayList<Product> temp = users.get(userId).getCart().getCartItemList();
        products = readProducts(productFile);
        users = readUsers(userFile);
        orders = readOrders(orderFile);
        shippedOrders = readShippedOrders(shippedOrderFile);
        users.get(userId).getCart().setCartItemList(temp);
    }

    /**
     * @param inPutFile the input file
     * @return the entire content of the file in a big String
     * @throws IOException file might be missing
     */
    private String readFile(String inPutFile) throws IOException {
        return new String(Files.readAllBytes(Paths.get(inPutFile)));
    }

    /**
     * @param outPutFile the file being written to
     * @param fileContents the content being written to the file
     * @throws IOException file might be missing
     */
    private void writeFile(String outPutFile, String fileContents) throws IOException {
        Files.write(Paths.get(outPutFile), fileContents.getBytes());
    }

    public ECommerceSystem() {

        try {
            products = readProducts(productFile);
            users = readUsers(userFile);
            orders = readOrders(orderFile);
            shippedOrders = readShippedOrders(shippedOrderFile);
        }
        catch (IOException e) {
            System.out.println("File not found");
            System.exit(1);
        }
        catch (Exception e) {
            System.out.println("The txt file is not formatted correctly.\nThe program will now exit.");
            System.exit(1);
        }
    }

    private int generateOrderNumber() {

        return orderNumber++;
    }
    private int generateUserID() {

        return userId++;
    }
    private int generateProductId() {

        return productId++;
    }

    // returns all the order requests to a seller
    public ArrayList<ProductOrdered> returnAllOrderRequestsForSeller(String sellerId) throws IOException {
        readAllFiles(sellerId);
        boolean exist = users.containsKey(sellerId);
        if (!exist) {
            throw new UnknownUserException(sellerId);
        }

        exist = users.get(sellerId).getCategory().equals(User.Category.SELLER);
        if (!exist){
            throw new UserNotSellerException(sellerId, "request their orders");
        }

        return ((Seller) users.get(sellerId)).getOrderRequests();
    }

    public ArrayList<Product> returnMyProducts(String sellerId) throws IOException {
        readAllFiles(sellerId);
        boolean exist = users.containsKey(sellerId);
        if (!exist) {
            throw new UnknownUserException(sellerId);
        }

        exist = users.get(sellerId).getCategory().equals(User.Category.SELLER);
        if (!exist){
            throw new UserNotSellerException(sellerId, "display their products");
        }
        ArrayList<Product> myProduct = new ArrayList<>();
        for (Product product : (new ArrayList<>(products.values()))){
            if (product.getSellerID().equals(sellerId)){
                myProduct.add(product);
            }
        }
        return myProduct;
    }

    public ArrayList<ProductOrdered> returnOrderHistory(String userId) throws IOException {
        readAllFiles(userId);
        // Make sure customer exists - check using userId
        boolean exist = users.containsKey(userId);
        ArrayList<ProductOrdered> orderHistory = new ArrayList<>();

        // otherwise throws an execution
        if (!exist) {
            throw new UnknownUserException(userId);
        }

        for (ProductOrdered product : orders.values()) {
            if (userId.equals(product.getUser().getId())) {
                orderHistory.add(product);
            }
        }
        readAllFiles(userId);
        return orderHistory;
    }

    public ArrayList<ProductOrdered> returnShipHistory(String userId) throws IOException {
        readAllFiles(userId);
        boolean exist = users.containsKey(userId);
        ArrayList<ProductOrdered> shipHistory = new ArrayList<>();

        // otherwise throws an execution
        if (!exist) {
            throw new UnknownUserException(userId);
        }
        for (ProductOrdered product : shippedOrders.values()) {
            if (userId.equals(product.getUser().getId())) {
                shipHistory.add(product);
            }
        }
        readAllFiles(userId);
        return shipHistory;
    }

    public ProductOrdered orderProduct(String productId, String userId) throws IOException {
        readAllFiles(userId);
        Product currentProduct;
        Seller currentSeller;
        String oldDataUser = "";
        String newDataUser = "";
        String oldDataSeller = "";
        String newDataSeller = "";
        String tempFileContent = "";
        User currentUser = null;
        boolean exist = false;

        // First check to see if customer object with userId exists in array list customers
        if (users.containsKey(userId)){
            currentUser = users.get(userId);
            exist = true;
        }
        if (!exist) {
            throw new UnknownUserException(userId);
        }

        // Check to see if product object with productId exists in the map of products
        exist = products.containsKey(productId);

        if (!exist) {
            throw new UnknownProductException(productId);
        }
        currentProduct = products.get(productId);
        currentSeller = (Seller) users.get(currentProduct.getSellerID());


        // Check if the product has stock available (i.e. not 0)
        exist = (currentProduct.getStockCount() > 0);
        if (!exist) {

            throw new ProductOutOfStockException(productId, currentProduct.getName());
        }
        oldDataUser = currentProduct.getData();
        tempFileContent = readFile(productFile);

        currentProduct.reduceStockCount();

        // Create a product object and add to Map
        String numOrder = String.format("%05d", generateOrderNumber());
        while (orders.containsKey(numOrder) || shippedOrders.containsKey(numOrder)) {
            numOrder = String.format("%05d", generateOrderNumber());
        }

        // increases the order number of a product
        currentProduct.setNumberOfOrders(currentProduct.getNumberOfOrders() + 1);

        newDataUser = currentProduct.getData();
        tempFileContent = tempFileContent.replace(oldDataUser, newDataUser).replaceAll("\r", "");
        writeFile(productFile, tempFileContent);

        orders.put(numOrder, (new ProductOrdered(numOrder, currentProduct, currentUser)));
        oldDataUser = readFile(orderFile) + orders.get(numOrder).getData();
        writeFile(orderFile, oldDataUser);

        oldDataUser = currentUser.getData();
        oldDataSeller = currentSeller.getData();

        currentUser.setBalance((currentUser.getBalance() * 100 - currentProduct.getPrice() * 100) / 100);
        currentSeller.setBalance((currentSeller.getBalance() * 100 + currentProduct.getPrice() * 100) / 100);

        newDataUser = currentUser.getData();
        newDataSeller = currentSeller.getData();

        tempFileContent = readFile(userFile).replace(oldDataUser, newDataUser).
                replace(oldDataSeller, newDataSeller).replaceAll("\r", "");
        writeFile(userFile, tempFileContent);

        readAllFiles(userId);
        return orders.get(numOrder);
    }

    public User createUser(String name, String userName, String password, double balance, String address, String category) throws IOException {
        readAllFiles();
        String newData = "";
        User newUser;
        // Check name parameter to make sure it is not null or "" otherwise throws an exception
        if (name == null || name.equals("")) {
            throw new InvalidNameException();
        }
        // Check name userName to make sure it is not null or "" otherwise throws an exception
        if (userName == null || userName.equals("")) {
            throw new InvalidUsernameException();
        }
        // checks the password to make sure its is not null or "" otherwise throws an exception
        if (password == null || password.equals("")) {
            throw new InvalidUserPasswordException();
        }
        // checks the address to make sure its is not null or "" otherwise throws an exception
        if (address == null || address.equals("")) {
            throw new InvalidUserAddressException();
        }
        if (balance < 0){
            throw new NegativeUserBalanceException();
        }

        ArrayList<User> userArrayList = new ArrayList<>(users.values());
        for (User user : userArrayList){
            if (user.getUserName().equals(userName)){
                return null;
            }
        }

        // Create a User object and add to Map
        String newUserId = String.format("%05d", generateUserID());
        while (users.containsKey(newUserId)) {
            newUserId = String.format("%05d", generateUserID());
        }
        if (category.equals("1")){
            newUser = new Customer();
            newUser.setCategory(User.Category.CUSTOMER);
            users.put(newUserId, newUser);
        }
        else if (category.equals("2")){
            newUser = new Seller();
            newUser.setCategory(User.Category.SELLER);
            users.put(newUserId, newUser);
        }
        else {
            throw new InvalidCategoryException();
        }
        newUser.setId(newUserId);
        newUser.setName(name);
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setBalance(balance);
        newUser.setShippingAddress(address);

        String fileContent = readFile(userFile);

        newData = users.get(newUserId).getData();

        fileContent += newData;
        writeFile(userFile, fileContent);
        return newUser;
    }

    public ArrayList<ProductOrdered> shipOrders(String userId, String productId, int amount) throws IOException {
        readAllFiles(userId);
        ArrayList<ProductOrdered> shippedOrdersArrayList = new ArrayList<>();
        Product currentProduct;
        String tempFileContent;
        StringBuilder newData = new StringBuilder();
        ProductOrdered currentOrder;
        boolean exist;
        int curOrders = 0;

        exist = users.containsKey(userId);
        if (!exist) {
            throw new UnknownUserException(userId);
        }

        exist = users.get(userId).getCategory() == User.Category.SELLER;
        if (!exist) {
            throw new UserNotSellerException(userId, "ship orders");
        }

        //checks if a product with the productId exist
        exist = products.containsKey(productId);

        // otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }
        currentProduct = products.get(productId);

        for (ProductOrdered productOrdered : orders.values()){
            if (productOrdered.getProduct().equals(currentProduct)){
                curOrders++;
            }
        }
        exist = curOrders != 0;

        //throws an exception if the productId isn't in orders
        if (!exist) {
            throw new UnknownProductException(productId);
        }

        exist = amount <= curOrders;

        // accounts for the curOrders as well
        if (!exist){
            throw new InvalidAmountException(amount, currentProduct.getName(), -1, curOrders);
        }

        int cur = 0;
        ArrayList<ProductOrdered> temp = new ArrayList<ProductOrdered>(orders.values());
        for (int i = 0; i < amount; i++){
            for (int j = cur; j < orders.size(); j++){
                if (temp.get(j).getProduct().getId().equals(productId)){
                    newData.append(temp.get(j).getData());
                    currentOrder = temp.get(j);
                    shippedOrders.put(temp.get(j).getOrderNumber(), currentOrder);
                    orders.remove(temp.get(j).getOrderNumber());
                    temp.remove(j);
                    shippedOrdersArrayList.add(currentOrder);
                    cur = j;
                    break;
                }
            }
        }
        tempFileContent = readFile(shippedOrderFile) + newData;
        writeFile(shippedOrderFile, tempFileContent);

        tempFileContent = readFile(orderFile).replace(newData, "").replaceAll("\r", "");
        writeFile(orderFile, tempFileContent);
        readAllFiles(userId);
        return shippedOrdersArrayList;
    }

    public ProductOrdered returnCancelOrder(String userId, String orderNumber) throws IOException {
        readAllFiles(userId);
        boolean exist = false;
        Product currentProduct;
        String oldOrderData = "";
        String oldProductData = "";
        String newProductData = "";
        String tempFileContent = "";

        exist = orders.containsKey(orderNumber);
        if (!exist) {
            throw new UnknownOrderNumberException(orderNumber);
        }
        ProductOrdered productOrdered = orders.get(orderNumber);

        exist = productOrdered.getUser().getId().equals(userId);
        if (!exist) {
            throw new OrderDoesNotBelongToUserException(orderNumber, userId);
        }

        oldOrderData += productOrdered.getData();
        currentProduct = products.get(productOrdered.getProduct().getId());
        oldProductData = currentProduct.getData();
        currentProduct.setStockCount(currentProduct.getStockCount() + 1);
        currentProduct.setNumberOfOrders(currentProduct.getNumberOfOrders() - 1);

        orders.remove(productOrdered.getOrderNumber());
        newProductData = currentProduct.getData();

        tempFileContent = readFile(orderFile).replace(oldOrderData, "").replaceAll( "\r", "");
        writeFile(orderFile, tempFileContent);

        tempFileContent = readFile(productFile).replace(oldProductData, newProductData).replaceAll("\r", "");
        writeFile(productFile, tempFileContent);

        readAllFiles(userId);
        return productOrdered;
    }

    public ArrayList<Product> returnProductByPrice() throws IOException {
        readAllFiles();
        ArrayList<Product> productsByPrice = new ArrayList<Product>(products.values());

        productsByPrice.sort(new Comparator<Product>() {

            public int compare(Product o1, Product o2) {
                if (o1.getPrice() < o2.getPrice()) {
                    return -1;
                }
                else if (o1.getPrice() > o2.getPrice()) {
                    return 1;
                }
                else {
                    if (o1.getId().compareTo(o2.getId()) < 0){
                        return -1;
                    }
                    else if (o1.getId().compareTo(o2.getId()) > 0){
                        return 1;
                    }
                    return 0;
                }
            }
        });
        readAllFiles();
        return productsByPrice;
    }

    public ArrayList<Product> returnProductByName() throws IOException {
        readAllFiles();
        ArrayList<Product> productsByName = new ArrayList<Product>(products.values());

        productsByName.sort((o1, o2) -> {
            if (o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase()) > 0) {
                return 1;
            }
            else if (o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase()) < 0) {
                return -1;
            }
            else {
                if (o1.getId().compareTo(o2.getId()) < 0){
                    return -1;
                }
                else if (o1.getId().compareTo(o2.getId()) > 0){
                    return 1;
                }
                return 0;
            }
        });
        readAllFiles();
        return productsByName;

    }

    public void addToCart(String productId, String userId, int amount) throws IOException {
        readAllFiles(userId);
        Product currentProduct;
        User currentUser = null;
        boolean exist = users.containsKey(userId);

        // checks to see a customer with the userId exist
        if (users.containsKey(userId)){
            currentUser = users.get(userId);
        }

        // otherwise throws an exception
        if (!exist) {
            throw new UnknownUserException(userId);
        }

        //checks if a product with the productId exist
        exist = products.containsKey(productId);

        // otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }
        currentProduct = products.get(productId);

        exist = amount > 0 && amount <= currentProduct.getStockCount();

        if (!exist){
            throw new InvalidAmountException(amount, currentProduct.getName(), currentProduct.getStockCount());
        }

        // Check if the product has stock available (i.e. not 0)
        exist = (currentProduct.getStockCount() > 0);
        if (!exist) {

            throw new ProductOutOfStockException(productId, currentProduct.getName());
        }

        // adds the product to the cartArrayList and also
        for (int i = 0; i < amount; i++) {
            currentUser.getCart().addCartItem(currentProduct);
        }
        // even if the userId already exist in the map, it will just overwrite it with a new cartItemList
        // This is really only here for putting a new userId into the map.
        cartsWithItems.put(userId, currentUser.getCart());
        readAllFiles(userId);
    }

    public boolean removeRemoveFromCart(String productId, String userId) throws IOException {
        readAllFiles(userId);
        boolean exist = users.containsKey(userId);

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownUserException(userId);
        }

        //checks if a product with productId exists
        exist = products.containsKey(productId);

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }

        //see if the userId has a cart
        if (cartsWithItems.containsKey(userId)) {

            ArrayList<Product> cartItemArrayList = cartsWithItems.get(userId).getCartItemList();

            //checks if the cart of userId has the product and if it does then it removes it
            for (Product item : cartItemArrayList) {
                if (item.getId().equalsIgnoreCase(productId)) {

                    cartsWithItems.get(userId).removeCartItem(item);

                    // if the product that got removed from the cart was the last product in the cart then, it removes the cart from the map entirely.
                    if (cartItemArrayList.size() == 0) {
                        cartsWithItems.remove(userId);
                    }

                    readAllFiles(userId);
                    // only removes the first instance of it
                    return true;
                }
            }

            readAllFiles(userId);
            // if the product isn't in customer's cart
            throw new ProductNotInCartException(productId, userId);
        }
        else {
            // if the customer has no CartItems in their cart.
            throw new UserHasNoItemsInCartException(userId);
        }
    }

    public ArrayList<Product> returnAllCartItemOfCustomer(String userId) throws IOException {
        readAllFiles(userId);
        boolean exist = users.containsKey(userId);

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownUserException(userId);
        }

        // checks if the customer actually has a cart
        if (cartsWithItems.containsKey(userId)) {
            readAllFiles(userId);
            return cartsWithItems.get(userId).getCartItemList();
        }
        else {
            // if the customer has no product in their cart.
            readAllFiles(userId);
            return new ArrayList<Product>();
        }
    }

    public ArrayList<ProductOrdered> returnStrOrderAllCartItemOfCustomer(String userId) throws IOException {
        readAllFiles(userId);
        boolean exist = users.containsKey(userId);
        User currentUser = users.get(userId);
        double userBalance = currentUser.getBalance();
        double totalCost = currentUser.getCart().getTotal();
        ArrayList<ProductOrdered> productOrderedArrayList = new ArrayList<>();

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownUserException(userId);
        }

        exist = totalCost <= userBalance;
        if (!exist) {
            throw new UserDoesNotHaveEnoughMoneyException(userId, totalCost, userBalance);
        }


        if (cartsWithItems.containsKey(userId)) {
            int cartSize = cartsWithItems.get(userId).getCartItemList().size();
            ArrayList<Product> cartItemArrayList = cartsWithItems.get(userId).getCartItemList();

            for (int i = 0; i < cartSize; i++) {
                Product product = cartItemArrayList.get(i);
                String productName = product.getName();
                String productId = product.getId();

                // to account for the scenario where multiple orders have been made to the same product to the point
                // where are more items in the cart then there are the stocks of the items and so it will eventually
                // get out of stock as each of the items are being ordered from the cart
                if (product.getStockCount() <= 0) {

                    // to remove the item that has no stock (we won't have to worry about the user putting an item with
                    // no stock back into the cart since that's already taken care of in the addToCart method
                    removeRemoveFromCart(productId, userId);
                    if (currentUser.getCart().getCartItemList().isEmpty()){
                        cartsWithItems.remove(userId);
                    }
                    throw new ProductOutOfStockException(productId, productName);
                }
                productOrderedArrayList.add(orderProduct(productId, userId));


                // removes the items from the carts ArrayList
                removeRemoveFromCart(productId, userId);
                if (currentUser.getCart().getCartItemList().isEmpty()){
                    cartsWithItems.remove(userId);
                }

                //this is here to update the queue of the ArrayList
                i--;
                cartSize--;
            }
        }
        else {
            // if the customer has no CartItems in their cart.
            throw new UserHasNoItemsInCartException(userId);
        }
        readAllFiles(userId);

        return productOrderedArrayList;
    }

    public double rateProduct(String userId, String productId, String rating) throws IOException {
        readAllFiles(userId);
        Product currentProduct;
        boolean exist = false;
        String oldData = "";
        String tempFileContent = "";
        String newData = "";

        for (ProductOrdered productOrdered : new ArrayList<>(shippedOrders.values())){
            if (productOrdered.getUser().getId().equals(userId)){
                exist = true;
                break;
            }
        }
        if (!exist){
            throw new UserCantRateProductTheyHaveNotGotException();
        }

        exist = users.containsKey(userId);
        //otherwise throws an exception
        if (!exist) {
            throw new UnknownUserException(userId);
        }

        //checks if a product with the productId exist
        exist = products.containsKey(productId);

        // otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }
        currentProduct = products.get(productId);

        // throws an exception if a rating between 1 and 5 was not entered or if rating was not a number
        try {
            if (Double.parseDouble(rating) > 5 || Double.parseDouble(rating) < 1) {
                throw new InvalidRatingException(rating);
            }
        }
        catch (Exception e) {
            throw new InvalidRatingException(rating);
        }
        oldData = currentProduct.getData();
        currentProduct.setRatings(rating);
        newData = currentProduct.getData();

        // checks if the product exists
        exist = products.containsKey(productId);

        //otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }
        tempFileContent = readFile(productFile).replace(oldData, newData).replaceAll("\r", "");
        writeFile(productFile, tempFileContent);
        readAllFiles(userId);
        return currentProduct.getAverageRating();
    }

    public ArrayList<Product> returnCategoryBaseOnMinimumRating(String categoryInput, String minimumRating) throws IOException {
        readAllFiles();
        double minimumAverageRating;
        boolean loopAlready = false;
        Product.Category enumCategory = null;

        // throws an error if an improper category was selected.
        if (!"12345678".contains(categoryInput) && categoryInput.length() != 1) {
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

        ArrayList<Product> categoryByAverageRatingsProducts = new ArrayList<>(products.values());
        ArrayList<Product> productBeingReturned = new ArrayList<>();

        categoryByAverageRatingsProducts.sort(new Comparator<Product>() {
            public int compare(Product o1, Product o2) {
                double r1 = o1.getAverageRating();
                double r2 = o2.getAverageRating();
                String c1 = o1.getCategory().toString();
                String c2 = o2.getCategory().toString();
                if (c1.compareTo(c2) > 0){
                    return 1;
                }
                else if (c1.compareTo(c2) < 0){
                    return -1;
                }
                else {
                    if (r1 < r2) {
                        return 1;
                    }
                    else if (r1 > r2) {
                        return -1;
                    }
                    else {
                        if (o1.getId().compareTo(o2.getId()) < 0){
                            return -1;
                        }
                        else if (o1.getId().compareTo(o2.getId()) > 0){
                            return 1;
                        }
                        return 0;
                    }
                }
            }
        });
        readAllFiles();
        switch (categoryInput) {
            case "1" -> enumCategory = Product.Category.GENERAL;
            case "2" -> enumCategory = Product.Category.CLOTHING;
            case "3" -> enumCategory = Product.Category.BOOK;
            case "4" -> enumCategory = Product.Category.FURNITURE;
            case "5" -> enumCategory = Product.Category.COMPUTER;
            case "6" -> enumCategory = Product.Category.MOVIE;
            case "7" -> enumCategory = Product.Category.VIDEOGAME;
            case "8" -> {

                // only when category is set to "all"
                productBeingReturned.addAll(categoryByAverageRatingsProducts);
                // exits the method after it has printed all the products
                loopAlready = true;
            }
        }

        if (!loopAlready) {
            // used to get only the products of selected category that's not all categories
            for (Product product : categoryByAverageRatingsProducts) {
                if (product.getCategory() == enumCategory) {
                    productBeingReturned.add(product);
                }
            }
        }
        return productBeingReturned;
    }

    public void removeProduct(String productId, String userId) throws IOException {
        readAllFiles(userId);
        Product currentProduct;
        String oldData = "";
        String tempFileContent = "";
        String newData = "";
        User currentUser;
        boolean exist;

        exist = products.containsKey(productId);

        // otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }
        currentProduct = products.get(productId);

        exist = users.containsKey(userId);
        //otherwise throws an exception
        if (!exist) {
            throw new UnknownUserException(userId);
        }
        currentUser = users.get(userId);
        oldData = currentProduct.getData();
        tempFileContent = readFile(productFile);


        if (currentUser instanceof Seller) {
            if (((Seller) currentUser).getSellerProductList().contains(currentProduct)) {
                ((Seller) currentUser).removeSellerProduct(currentProduct);
                newData = "";
                tempFileContent = tempFileContent.replace(oldData, newData).replaceAll("\r", "");
                writeFile(productFile, tempFileContent);
                readAllFiles(userId);
            }
            else{
                readAllFiles(userId);
                throw new SellerDoesNotSellThisProuct();
            }
        }
        else {
            readAllFiles(userId);
            throw new UserNotSellerException(userId, "remove products");
        }
    }

    public Product updateProduct(String productId, String userId) throws IOException {
        readAllFiles(userId);
        Product currentProduct;
        User currentUser;
        boolean exist;

        exist = products.containsKey(productId);

        // otherwise throws an exception
        if (!exist) {
            throw new UnknownProductException(productId);
        }
        currentProduct = products.get(productId);

        exist = users.containsKey(userId);
        //otherwise throws an exception
        if (!exist) {
            throw new UnknownUserException(userId);
        }
        currentUser = users.get(userId);
        if (currentUser instanceof Seller) {
            if (((Seller) currentUser).getSellerProductList().contains(currentProduct)) {
                ArrayList<Product> sellerProductList = ((Seller) currentUser).getSellerProductList();
                for (Product product : sellerProductList) {
                    if (product.equals(currentProduct)) {
                        return currentProduct;
                    }
                }
            }
            else {
                throw new SellerDoesNotSellThisProuct();
            }
        }
        throw new UserNotSellerException(userId, "update products");
    }
    public void updateProduct(Product product, String newName, double newPrice, int newStock) throws IOException {
        updateProduct(product, newName, newPrice, newStock, "", "");
    }
    public void updateProduct(Product product, String newName, double newPrice, int newStock, String o1, String o2) throws IOException {
        readAllFiles();
        String oldData = "";
        String tempFileContent = "";
        String newData = "";

        if (newName.equals("")){
            throw new InvalidProductNameException();
        }
        if (newPrice < 0){
            throw new InvalidPriceException();
        }
        if (newStock < 0){
            throw new InvalidStockException();
        }

        oldData = product.getData();
        tempFileContent = readFile(productFile);

        if (product.getCategory() == Product.Category.BOOK){

            if (o1.equals("")){
                throw new InvalidAuthorNameException();
            }
            if (!o2.matches("[0-9][0-9][0-9][0-9]")){
                throw new InvalidBookYearException();
            }

            Book temp = (Book) product;
            temp.setName(newName);
            temp.setPrice(newPrice);
            temp.setStockCount(newStock);
            temp.setAuthor(o1);
            temp.setYear(o2);
        }
        else if (product.getCategory() == Product.Category.MOVIE) {

            if (o1.equals("")){
                throw new InvalidGenreException();
            }
            if (!o2.matches("[0-9][0-9]hours :[0-9][0-9]minutes")){
                throw new InvalidMovieLengthException();
            }

            Movie temp = (Movie) product;
            temp.setName(newName);
            temp.setPrice(newPrice);
            temp.setStockCount(newStock);
            temp.setGenre(o1);
            temp.setLength(o2);
        }
        else if (product.getCategory() == Product.Category.VIDEOGAME) {

            if (o1.equals("")){
                throw new InvalidGenreException();
            }
            if (o2.equals("")){
                throw new InvalidESRBRatingException();
            }

            VideoGame temp = (VideoGame) product;
            temp.setName(newName);
            temp.setPrice(newPrice);
            temp.setStockCount(newStock);
            temp.setGenre(o1);
            temp.setESRBRating(o2);
        }
        else {
            product.setName(newName);
            product.setPrice(newPrice);
            product.setStockCount(newStock);
        }
        newData = product.getData();
        tempFileContent = tempFileContent.replace(oldData, newData).replaceAll("\r", "");
        writeFile(productFile, tempFileContent);
        readAllFiles();
    }

    public void addProduct(String userId) throws IOException {
        readAllFiles(userId);
        User currentUser;
        boolean exist;

        exist = users.containsKey(userId);
        //otherwise throws an exception
        if (!exist) {
            throw new UnknownUserException(userId);
        }
        currentUser = users.get(userId);
        if (currentUser instanceof Seller) {
            return;
        }
        throw new UserNotSellerException(userId, "add products");
    }
    public String addProduct(Product.Category category, String userId, String newName, double newPrice, int newStock) throws IOException {
        return addProduct(category, userId, newName, newPrice, newStock, "", "");
    }
    public String addProduct(Product.Category category, String userId, String newName, double newPrice, int newStock, String o1, String o2) throws IOException {
        readAllFiles(userId);
        String tempFileContent;
        String newData;

        if (newName.equals("")){
            throw new InvalidProductNameException();
        }
        if (newPrice < 0){
            throw new InvalidPriceException();
        }
        if (newStock < 0){
            throw new InvalidStockException();
        }

        tempFileContent = readFile(productFile);
        Product tempProduct;

        if (category == Product.Category.BOOK){

            if (o1.equals("")){
                throw new InvalidAuthorNameException();
            }
            if (!o2.matches("[0-9]+")){
                throw new InvalidBookYearException();
            }

            tempProduct = new Book();
            ((Book) tempProduct).setAuthor(o1);
            ((Book) tempProduct).setYear(o2);
        }
        else if (category == Product.Category.MOVIE) {

            if (o1.equals("")){
                throw new InvalidGenreException();
            }
            if (!o2.matches("[0-9][0-9]hours :[0-9][0-9]minutes")){
                throw new InvalidMovieLengthException();
            }

            tempProduct = new Movie();
            ((Movie) tempProduct).setGenre(o1);
            ((Movie) tempProduct).setLength(o2);
        }
        else if (category == Product.Category.VIDEOGAME) {

            if (o1.equals("")){
                throw new InvalidGenreException();
            }
            if (o2.equals("")){
                throw new InvalidESRBRatingException();
            }

            tempProduct = new VideoGame();
            ((VideoGame) tempProduct).setGenre(o1);
            ((VideoGame) tempProduct).setESRBRating(o2);
        }
        else {
            tempProduct = new Product();
        }
        tempProduct.setCategory(category);
        tempProduct.setName(newName);
        tempProduct.setPrice(newPrice);
        tempProduct.setSellerID(userId);
        tempProduct.setStockCount(newStock);

        // Create a product object and add to Map
        String temp = String.format("%05d", generateProductId());
        while (products.containsKey(temp)) {
            temp = String.format("%05d", generateProductId());
        }
        tempProduct.setId(temp);

        newData = tempProduct.getData();
        tempFileContent += newData;
        writeFile(productFile, tempFileContent);
        readAllFiles(userId);
        return temp;
    }

    public User userLogin(String userName, String password){
        ArrayList<User> userArrayList = new ArrayList<>(users.values());
        for (User user : userArrayList){
            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }

    public void updateAccountUsername(String userId, String username) throws IOException {
        readAllFiles(userId);
        // Check name userName to make sure it is not null or "" otherwise throws an exception
        if (username == null || username.equals("")) {
            throw new InvalidUsernameException();
        }

        ArrayList<User> userArrayList = new ArrayList<>(users.values());
        for (User user : userArrayList){
            if (user.getUserName().equals(username)){
                throw new UserAlreadyExistsException(username);
            }
        }
        String oldData = users.get(userId).getData();
        users.get(userId).setUserName(username);
        String newData = users.get(userId).getData();
        String tempFileContent = readFile(userFile).replace(oldData, newData).replaceAll("\r", "");
        writeFile(userFile, tempFileContent);
        readAllFiles(userId);
    }
    public void updateAccountPassword(String userId, String password) throws IOException {
        readAllFiles(userId);
        // checks the password to make sure its is not null or "" otherwise throws an exception
        if (password == null || password.equals("")) {
            throw new InvalidUserPasswordException();
        }
        String oldData = users.get(userId).getData();
        users.get(userId).setPassword(password);
        String newData = users.get(userId).getData();
        String tempFileContent = readFile(userFile).replace(oldData, newData).replaceAll("\r", "");
        writeFile(userFile, tempFileContent);
        readAllFiles(userId);
    }
    public void updateAccountName(String userId, String name) throws IOException {
        readAllFiles(userId);
        // Check name parameter to make sure it is not null or "" otherwise throws an exception
        if (name == null || name.equals("")) {
            throw new InvalidNameException();
        }
        String oldData = users.get(userId).getData();
        users.get(userId).setName(name);
        String newData = users.get(userId).getData();
        String tempFileContent = readFile(userFile).replace(oldData, newData).replaceAll("\r", "");
        writeFile(userFile, tempFileContent);
        readAllFiles(userId);
    }
    public void updateAccountBalance(String userId, double balance) throws IOException {
        readAllFiles(userId);
        if (balance < 0){
            throw new NegativeUserBalanceException();
        }
        String oldData = users.get(userId).getData();
        users.get(userId).setBalance(balance);
        String newData = users.get(userId).getData();
        String tempFileContent = readFile(userFile).replace(oldData, newData).replaceAll("\r", "");
        writeFile(userFile, tempFileContent);
        readAllFiles(userId);
    }
    public void updateAccountShippingAddress(String userId, String shippingAddress) throws IOException {
        readAllFiles(userId);
        // checks the address to make sure its is not null or "" otherwise throws an exception
        if (shippingAddress == null || shippingAddress.equals("")) {
            throw new InvalidUserAddressException();
        }
        String oldData = users.get(userId).getData();
        users.get(userId).setShippingAddress(shippingAddress);
        String newData = users.get(userId).getData();
        String tempFileContent = readFile(userFile).replace(oldData, newData).replaceAll("\r", "");
        writeFile(userFile, tempFileContent);
        readAllFiles(userId);
    }
}