package sample.ShoppingSystem;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ECommerceSystemTest {

    //test case 1
    @Test
    @Order(2)
    void returnMyProductsTester() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();
        ArrayList<Product> expected = new ArrayList<>();
        expected.add(new Product("Acer Laptop", "00970", "00700", 989.00, 99, Product.Category.COMPUTER, "5", 4));
        expected.add(new Product("Apex Desk", "00970", "00701", 1378.0, 12, Product.Category.FURNITURE, "4", 1));
        expected.add(new Book("Ahm Gonna Make You Learn", "00970", "00702", 45.0, 41, "", 4, "A. Einstein", "2021"));
        expected.add(new Product("DadBod Jeans", "00970", "00703", 24.0, 50, Product.Category.CLOTHING, "5 2", 37));
        expected.add(new Product("Polo High Socks", "00970", "00704", 5.0, 199, Product.Category.CLOTHING, "4 2 5", 91));
        expected.add(new Product("Tightie Whities", "00970", "00705", 15.0, 99, Product.Category.CLOTHING, "4 2", 5));
        expected.add(new Book("How to Fool Your Prof", "00970", "00706", 35.0, 11, "1 5 4", 11, "D. Umbast", "1997"));
        expected.add(new Book("How to Escape from Prison", "00970", "00707", 45.0, 24, "4", 5, "A. Fugitive", "1963"));
        expected.add(new Book("How to Teach Programming", "00970", "00708", 45.0, 12, "4 5 5 5", 32, "A. Einstein", "2001"));
        expected.add(new Product("Rock Hammer", "00970", "00709", 10.0, 22, Product.Category.GENERAL, "", 0));
        expected.add(new Book("Ahm Gonna Make You Learn More", "00970", "00710", 45.0, 322, "4 2 3 4 1", 52, "A. Einstein", "2022"));
        expected.add(new Book("Sir Gawain & The Green Knight", "00970", "00711", 15.0, 16, "4 2 1", 7, "J.R.R. Tolkien", "1925"));
        expected.add(new Book("The Book of Lost Tales, Part I", "00970", "00712", 22.99, 12, "", 0, "J.R.R. Tolkien", "1983"));
        expected.add(new Book("Smith of Wootton Major", "00970", "00713", 14.84, 19, "", 0, "J.R.R. Tolkien", "1990"));
        expected.add(new Book("The Hobbit", "00970", "00714", 14.5, 17, "", 0, "J.R.R. Tolkien", "1966"));
        expected.add(new Book("The Fellowship of the Ring", "00970", "00715", 10.99, 32, "4", 13, "J.R.R. Tolkien", "1997"));
        expected.add(new Product("A pack of pens", "00970", "00716", 12.0, 5, Product.Category.GENERAL, "", 0));
        expected.add(new Product("An ugly sweater", "00970", "00717", 35.0, 1, Product.Category.CLOTHING, "", 0));
        expected.add(new VideoGame("Halo 3", "00970", "00718", 59.99, 4, "2 5 5", 7, "M", "FPS"));
        expected.add(new Movie("Shrek", "00970", "00719", 30.0, 51, "5 5 5 5 5", 12, "Comedy", "01hours :30minutes"));
        expected.add(new Book("this book", "00970", "00720", 43.23, 0, "", 0, "this Author", "1999"));

        // test case 1.1
        for (int i = 0; i < 20; i++){
            assertEquals(expected.get(i).getName(), shop.returnMyProducts("00970").get(i).getName());
            assertEquals(expected.get(i).getSellerID(), shop.returnMyProducts("00970").get(i).getSellerID());
            assertEquals(expected.get(i).getId(), shop.returnMyProducts("00970").get(i).getId());
            assertEquals(expected.get(i).getPrice(), shop.returnMyProducts("00970").get(i).getPrice());
            assertEquals(expected.get(i).getStockCount(), shop.returnMyProducts("00970").get(i).getStockCount());
            assertEquals(expected.get(i).getCategory(), shop.returnMyProducts("00970").get(i).getCategory());
            assertEquals(expected.get(i).getRatings(), shop.returnMyProducts("00970").get(i).getRatings());
            assertEquals(expected.get(i).getNumberOfOrders(), shop.returnMyProducts("00970").get(i).getNumberOfOrders());
        }

        // test case 1.2
        assertThrows(UserNotSellerException.class, () -> shop.returnMyProducts("00904"));
    }

    //test case 2
    @Test
    @Order(3)
    void returnAllOrderRequestsForSellerAtStart() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();
        ArrayList<ProductOrdered> expected = new ArrayList<>();

        //test case 2.1
        assertEquals(expected, shop.returnAllOrderRequestsForSeller("00970"));

        //test case 2.2
        assertThrows(UserNotSellerException.class, () -> shop.returnMyProducts("00904"));
    }

    //test case 3
    @Test
    @Order(4)
    void returnOrderHistoryAtStart() throws IOException{
        ECommerceSystem shop = new ECommerceSystem();
        ArrayList<ProductOrdered> expected = new ArrayList<>();

        //test case 3.1
        assertEquals(expected, shop.returnOrderHistory("00970"));

        //test case 3.2
        assertEquals(expected, shop.returnOrderHistory("00904"));

        //test cases 3.3
        assertThrows(UnknownUserException.class, () -> shop.returnOrderHistory("99999"));
    }

    //test case 4
    @Test
    @Order(5)
    void returnShipHistoryAtStart() throws IOException{
        ECommerceSystem shop = new ECommerceSystem();
        ArrayList<ProductOrdered> expected = new ArrayList<>();

        //test case 4.1
        assertEquals(expected, shop.returnShipHistory("00970"));

        //test case 4.2
        assertEquals(expected, shop.returnShipHistory("00904"));

        //test case 4.3
        assertThrows(UnknownUserException.class, () -> shop.returnShipHistory("99999"));
    }

    //test case 5
    @Test
    @Order(6)
    void orderProductOneProduct() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();
        ProductOrdered productOrdered = new ProductOrdered("00500", null, null);

        //test case 5.1
        assertEquals(productOrdered, shop.orderProduct("00704", "00904"));

        //test case 5.2
        assertThrows(UnknownUserException.class, () -> shop.orderProduct("00704", "99999"));

        //test case 5.3
        assertThrows(UnknownProductException.class, () -> shop.orderProduct("77777", "00904"));

        //test case 5.4
        assertThrows(ProductOutOfStockException.class, () -> shop.orderProduct("00720", "00904"));
    }

    //test case 6
    @Test
    @Order(7)
    void returnAllOrderRequestsForSellerOneProduct() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();
        ArrayList<ProductOrdered> expected = new ArrayList<>();
        expected.add( new ProductOrdered("00500", null, null));

        //test case 6.1
        assertEquals(expected.get(0).getOrderNumber(), shop.returnAllOrderRequestsForSeller("00970").get(0).getOrderNumber());

        //test case 6.2
        assertThrows(UserNotSellerException.class, () -> shop.returnMyProducts("00904"));
    }

    //test case 7
    @Test
    @Order(8)
    void returnOrderHistoryOneProduct() throws IOException{
        ECommerceSystem shop = new ECommerceSystem();
        ArrayList<ProductOrdered> expected = new ArrayList<>();

        //test case 7.1
        assertEquals(expected, shop.returnOrderHistory("00970"));

        expected.add(new ProductOrdered("00500", null, null));

        //test case 7.2
        assertEquals(expected.get(0).getOrderNumber(), shop.returnOrderHistory("00904").get(0).getOrderNumber());
    }

    //test case 8
    @Test
    @Order(9)
    void createUserOneUser() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();

        //test case 8.1
        assertThrows(InvalidNameException.class, () -> shop.createUser("", "username", "password", 1, "address", "1"));

        //test case 8.2
        assertThrows(InvalidUsernameException.class, () -> shop.createUser("name", "", "password", 1, "address", "1"));

        //test case 8.3
        assertThrows(InvalidUserPasswordException.class, () -> shop.createUser("name", "username", "", 1, "address", "1"));

        //test case 8.4
        assertThrows(NegativeUserBalanceException.class, () -> shop.createUser("name", "username", "password", -1, "address", "1"));

        //test case 8.5
        assertThrows(InvalidUserAddressException.class, () -> shop.createUser("name", "username", "password", 1, "", "1"));

        //test case 8.6
        assertNull(shop.createUser("a name", "v22", "pass", 1, "here", "1"));

        //test case 8.7
        User user = new User("00907", "a name", "a username", "a password", 1, "an address", User.Category.CUSTOMER);
        User createdUser = shop.createUser("a name", "a username", "a password", 1, "an address", "1");
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getUserName(), createdUser.getUserName());
        assertEquals(user.getPassword(), createdUser.getPassword());
        assertEquals(user.getBalance(), createdUser.getBalance());
        assertEquals(user.getShippingAddress(), createdUser.getShippingAddress());
        assertEquals(user.getCategory(), createdUser.getCategory());
    }

    //test case 9
    @Test
    @Order(10)
    void shipOrdersOneShip() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();

        ArrayList<ProductOrdered> shippedOrders = new ArrayList<>();

        //test case 9.1
        assertThrows(UnknownProductException.class, () -> shop.shipOrders("00970", "77777", 1));

        //test case 9.2
        assertThrows(UnknownUserException.class, () -> shop.shipOrders("99999", "00704", 1));

        //test case 9.3
        assertThrows(UserNotSellerException.class, () -> shop.shipOrders("00904", "00704", 1));

        //test case 9.4
        assertThrows(InvalidAmountException.class, () -> shop.shipOrders("00970", "00704", 2));

        shippedOrders.add(new ProductOrdered("00500", null, null));

        //test case 9.5
        assertEquals(shippedOrders.get(0).getOrderNumber(), shop.shipOrders("00970", "00704", 1).get(0).getOrderNumber());
    }

    //test case 10
    @Test
    @Order(11)
    void returnCancelOrderOneProduct() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();
        ProductOrdered productOrdered = new ProductOrdered("00501", null, null);

        //test case 10.1
        assertEquals(productOrdered, shop.orderProduct("00704", "00904"));

        //test case 10.2
        assertThrows(UnknownOrderNumberException.class, () -> shop.returnCancelOrder("00904", "55555"));

        //test case 10.3
        assertThrows(OrderDoesNotBelongToUserException.class, () -> shop.returnCancelOrder("00900", "00501"));

        //test case 10.4
        assertEquals(productOrdered.getOrderNumber(), shop.returnCancelOrder("00904", "00501").getOrderNumber());
    }

    //test case 11
    @Test
    @Order(12)
    void returnProductByPrice() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();
        ArrayList<Product> expected = new ArrayList<>();
        expected.add(new Product("Polo High Socks", "00970", "00704", 5.0, 198, Product.Category.CLOTHING, "4 2 5", 92));
        expected.add(new Product("Rock Hammer", "00970", "00709", 10.0, 22, Product.Category.GENERAL, "", 0));
        expected.add(new Book("The Fellowship of the Ring", "00970", "00715", 10.99, 32, "4", 13, "J.R.R. Tolkien", "1997"));
        expected.add(new Product("A pack of pens", "00970", "00716", 12.0, 5, Product.Category.GENERAL, "", 0));
        expected.add(new Book("The Hobbit", "00970", "00714", 14.5, 17, "", 0, "J.R.R. Tolkien", "1966"));
        expected.add(new Book("Smith of Wootton Major", "00970", "00713", 14.84, 19, "", 0, "J.R.R. Tolkien", "1990"));
        expected.add(new Product("Tightie Whities", "00970", "00705", 15.0, 99, Product.Category.CLOTHING, "4 2", 5));
        expected.add(new Book("Sir Gawain & The Green Knight", "00970", "00711", 15.0, 16, "4 2 1", 7, "J.R.R. Tolkien", "1925"));
        expected.add(new Book("The Book of Lost Tales, Part I", "00970", "00712", 22.99, 12, "", 0, "J.R.R. Tolkien", "1983"));
        expected.add(new Product("DadBod Jeans", "00970", "00703", 24.0, 50, Product.Category.CLOTHING, "5 2", 37));
        expected.add(new Movie("Shrek", "00970", "00719", 30.0, 51, "5 5 5 5 5", 12, "Comedy", "01hours :30minutes"));
        expected.add(new Book("How to Fool Your Prof", "00970", "00706", 35.0, 11, "1 5 4", 11, "D. Umbast", "1997"));
        expected.add(new Product("An ugly sweater", "00970", "00717", 35.0, 1, Product.Category.CLOTHING, "", 0));
        expected.add(new Book("this book", "00970", "00720", 43.23, 0, "", 0, "this Author", "1999"));
        expected.add(new Book("Ahm Gonna Make You Learn", "00970", "00702", 45.0, 41, "", 4, "A. Einstein", "2021"));
        expected.add(new Book("How to Escape from Prison", "00970", "00707", 45.0, 24, "4", 5, "A. Fugitive", "1963"));
        expected.add(new Book("How to Teach Programming", "00970", "00708", 45.0, 12, "4 5 5 5", 32, "A. Einstein", "2001"));
        expected.add(new Book("Ahm Gonna Make You Learn More", "00970", "00710", 45.0, 322, "4 2 3 4 1", 52, "A. Einstein", "2022"));
        expected.add(new VideoGame("Halo 3", "00970", "00718", 59.99, 4, "2 5 5", 7, "M", "FPS"));
        expected.add(new Product("Acer Laptop", "00970", "00700", 989.00, 99, Product.Category.COMPUTER, "5", 4));
        expected.add(new Product("Apex Desk", "00970", "00701", 1378.0, 12, Product.Category.FURNITURE, "4", 1));

        //test case 11.1
        for (int i = 0; i < 20; i++){
            assertEquals(expected.get(i).getName(), shop.returnProductByPrice().get(i).getName());
            assertEquals(expected.get(i).getSellerID(), shop.returnProductByPrice().get(i).getSellerID());
            assertEquals(expected.get(i).getId(), shop.returnProductByPrice().get(i).getId());
            assertEquals(expected.get(i).getPrice(), shop.returnProductByPrice().get(i).getPrice());
            assertEquals(expected.get(i).getStockCount(), shop.returnProductByPrice().get(i).getStockCount());
            assertEquals(expected.get(i).getCategory(), shop.returnProductByPrice().get(i).getCategory());
            assertEquals(expected.get(i).getRatings(), shop.returnProductByPrice().get(i).getRatings());
            assertEquals(expected.get(i).getNumberOfOrders(), shop.returnProductByPrice().get(i).getNumberOfOrders());
        }
    }

    //test case 12
    @Test
    @Order(13)
    void returnProductByName() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();
        ArrayList<Product> expected = new ArrayList<>();
        expected.add(new Product("A pack of pens", "00970", "00716", 12.0, 5, Product.Category.GENERAL, "", 0));
        expected.add(new Product("Acer Laptop", "00970", "00700", 989.00, 99, Product.Category.COMPUTER, "5", 4));
        expected.add(new Book("Ahm Gonna Make You Learn", "00970", "00702", 45.0, 41, "", 4, "A. Einstein", "2021"));
        expected.add(new Book("Ahm Gonna Make You Learn More", "00970", "00710", 45.0, 322, "4 2 3 4 1", 52, "A. Einstein", "2022"));
        expected.add(new Product("An ugly sweater", "00970", "00717", 35.0, 1, Product.Category.CLOTHING, "", 0));
        expected.add(new Product("Apex Desk", "00970", "00701", 1378.0, 12, Product.Category.FURNITURE, "4", 1));
        expected.add(new Product("DadBod Jeans", "00970", "00703", 24.0, 50, Product.Category.CLOTHING, "5 2", 37));
        expected.add(new VideoGame("Halo 3", "00970", "00718", 59.99, 4, "2 5 5", 7, "M", "FPS"));
        expected.add(new Book("How to Escape from Prison", "00970", "00707", 45.0, 24, "4", 5, "A. Fugitive", "1963"));
        expected.add(new Book("How to Fool Your Prof", "00970", "00706", 35.0, 11, "1 5 4", 11, "D. Umbast", "1997"));
        expected.add(new Book("How to Teach Programming", "00970", "00708", 45.0, 12, "4 5 5 5", 32, "A. Einstein", "2001"));
        expected.add(new Product("Polo High Socks", "00970", "00704", 5.0, 198, Product.Category.CLOTHING, "4 2 5", 92));
        expected.add(new Product("Rock Hammer", "00970", "00709", 10.0, 22, Product.Category.GENERAL, "", 0));
        expected.add(new Movie("Shrek", "00970", "00719", 30.0, 51, "5 5 5 5 5", 12, "Comedy", "01hours :30minutes"));
        expected.add(new Book("Sir Gawain & The Green Knight", "00970", "00711", 15.0, 16, "4 2 1", 7, "J.R.R. Tolkien", "1925"));
        expected.add(new Book("Smith of Wootton Major", "00970", "00713", 14.84, 19, "", 0, "J.R.R. Tolkien", "1990"));
        expected.add(new Book("The Book of Lost Tales, Part I", "00970", "00712", 22.99, 12, "", 0, "J.R.R. Tolkien", "1983"));
        expected.add(new Book("The Fellowship of the Ring", "00970", "00715", 10.99, 32, "4", 13, "J.R.R. Tolkien", "1997"));
        expected.add(new Book("The Hobbit", "00970", "00714", 14.5, 17, "", 0, "J.R.R. Tolkien", "1966"));
        expected.add(new Book("this book", "00970", "00720", 43.23, 0, "", 0, "this Author", "1999"));
        expected.add(new Product("Tightie Whities", "00970", "00705", 15.0, 99, Product.Category.CLOTHING, "4 2", 5));

        //test case 12.1
        for (int i = 0; i < 20; i++){
            assertEquals(expected.get(i).getName(), shop.returnProductByName().get(i).getName());
            assertEquals(expected.get(i).getSellerID(), shop.returnProductByName().get(i).getSellerID());
            assertEquals(expected.get(i).getId(), shop.returnProductByName().get(i).getId());
            assertEquals(expected.get(i).getPrice(), shop.returnProductByName().get(i).getPrice());
            assertEquals(expected.get(i).getStockCount(), shop.returnProductByName().get(i).getStockCount());
            assertEquals(expected.get(i).getCategory(), shop.returnProductByName().get(i).getCategory());
            assertEquals(expected.get(i).getRatings(), shop.returnProductByName().get(i).getRatings());
            assertEquals(expected.get(i).getNumberOfOrders(), shop.returnProductByName().get(i).getNumberOfOrders());
        }
    }

    //test case 13
    @Test
    @Order(14)
    void returnProductByCategory() throws IOException{
        ECommerceSystem shop = new ECommerceSystem();
        ArrayList<Product> expected = new ArrayList<>();
        expected.add(new Book("How to Teach Programming", "00970", "00708", 45.0, 12, "4 5 5 5", 32, "A. Einstein", "2001"));
        expected.add(new Book("How to Escape from Prison", "00970", "00707", 45.0, 24, "4", 5, "A. Fugitive", "1963"));
        expected.add(new Book("The Fellowship of the Ring", "00970", "00715", 10.99, 32, "4", 13, "J.R.R. Tolkien", "1997"));
        expected.add(new Book("How to Fool Your Prof", "00970", "00706", 35.0, 11, "1 5 4", 11, "D. Umbast", "1997"));
        expected.add(new Book("Ahm Gonna Make You Learn More", "00970", "00710", 45.0, 322, "4 2 3 4 1", 52, "A. Einstein", "2022"));
        expected.add(new Book("Sir Gawain & The Green Knight", "00970", "00711", 15.0, 16, "4 2 1", 7, "J.R.R. Tolkien", "1925"));
        expected.add(new Book("Ahm Gonna Make You Learn", "00970", "00702", 45.0, 41, "", 4, "A. Einstein", "2021"));
        expected.add(new Book("The Book of Lost Tales, Part I", "00970", "00712", 22.99, 12, "", 0, "J.R.R. Tolkien", "1983"));
        expected.add(new Book("Smith of Wootton Major", "00970", "00713", 14.84, 19, "", 0, "J.R.R. Tolkien", "1990"));
        expected.add(new Book("The Hobbit", "00970", "00714", 14.5, 17, "", 0, "J.R.R. Tolkien", "1966"));
        expected.add(new Book("this book", "00970", "00720", 43.23, 0, "", 0, "this Author", "1999"));
        expected.add(new Product("Polo High Socks", "00970", "00704", 5.0, 198, Product.Category.CLOTHING, "4 2 5", 92));
        expected.add(new Product("DadBod Jeans", "00970", "00703", 24.0, 50, Product.Category.CLOTHING, "5 2", 37));
        expected.add(new Product("Tightie Whities", "00970", "00705", 15.0, 99, Product.Category.CLOTHING, "4 2", 5));
        expected.add(new Product("An ugly sweater", "00970", "00717", 35.0, 1, Product.Category.CLOTHING, "", 0));
        expected.add(new Product("Acer Laptop", "00970", "00700", 989.00, 99, Product.Category.COMPUTER, "5", 4));
        expected.add(new Product("Apex Desk", "00970", "00701", 1378.0, 12, Product.Category.FURNITURE, "4", 1));
        expected.add(new Product("Rock Hammer", "00970", "00709", 10.0, 22, Product.Category.GENERAL, "", 0));
        expected.add(new Product("A pack of pens", "00970", "00716", 12.0, 5, Product.Category.GENERAL, "", 0));
        expected.add(new Movie("Shrek", "00970", "00719", 30.0, 51, "5 5 5 5 5", 12, "Comedy", "01hours :30minutes"));
        expected.add(new VideoGame("Halo 3", "00970", "00718", 59.99, 4, "2 5 5", 7, "M", "FPS"));

        //test case 13.1
        for (int i = 0; i < 20; i++){
            assertEquals(expected.get(i).getName(), shop.returnCategoryBaseOnMinimumRating("8", "0").get(i).getName());
            assertEquals(expected.get(i).getSellerID(), shop.returnCategoryBaseOnMinimumRating("8", "0").get(i).getSellerID());
            assertEquals(expected.get(i).getId(), shop.returnCategoryBaseOnMinimumRating("8", "0").get(i).getId());
            assertEquals(expected.get(i).getPrice(), shop.returnCategoryBaseOnMinimumRating("8", "0").get(i).getPrice());
            assertEquals(expected.get(i).getStockCount(), shop.returnCategoryBaseOnMinimumRating("8", "0").get(i).getStockCount());
            assertEquals(expected.get(i).getCategory(), shop.returnCategoryBaseOnMinimumRating("8", "0").get(i).getCategory());
            assertEquals(expected.get(i).getRatings(), shop.returnCategoryBaseOnMinimumRating("8", "0").get(i).getRatings());
            assertEquals(expected.get(i).getNumberOfOrders(), shop.returnCategoryBaseOnMinimumRating("8", "0").get(i).getNumberOfOrders());
        }
    }

    //test case 14
    @Test
    @Order(15)
    void addToCartTwoProduct() {
        ECommerceSystem shop = new ECommerceSystem();

        //test case 14.1
        assertThrows(UnknownProductException.class, () -> shop.addToCart("77777", "00904", 2));

        //test case 14.2
        assertThrows(UnknownUserException.class, () -> shop.addToCart("00704", "99999", 2));

        //test case 14.3
        assertThrows(InvalidAmountException.class, () -> shop.addToCart("00704", "00904", -1));

        //test case 14.4
        assertThrows(InvalidAmountException.class, () -> shop.addToCart("00720", "00904", 1));

        //test case 14.5
        assertDoesNotThrow( () -> shop.addToCart("00704", "00904", 2));
    }

    //test case 15
    @Test
    @Order(16)
    void removeRemoveFromCartOneProduct() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();

        //test case 15.1
        assertThrows(UnknownProductException.class, () -> shop.removeRemoveFromCart("77777", "00904"));

        //test case 15.2
        assertThrows(UnknownUserException.class, () -> shop.removeRemoveFromCart("00704", "99999"));

        //test case 15.3
        assertThrows(UserHasNoItemsInCartException.class, () -> shop.removeRemoveFromCart("00704", "00904"));

        //test case 15.4
        shop.addToCart("00704", "00904", 2);
        assertThrows(ProductNotInCartException.class, () -> shop.removeRemoveFromCart("00705", "00904"));

        //test case 15.5
        assertDoesNotThrow( () -> shop.removeRemoveFromCart("00704", "00904"));
        assertDoesNotThrow( () -> shop.removeRemoveFromCart("00704", "00904"));

        //test case 15.6
        assertThrows(UserHasNoItemsInCartException.class, () -> shop.removeRemoveFromCart("00704", "00904"));
    }

    //test case 16
    @Test
    @Order(17)
    void returnAllCartItemOfCustomerOneProduct() throws IOException{
        ECommerceSystem shop = new ECommerceSystem();

        //test case 16.1
        assertEquals(new ArrayList<Product>(),shop.returnAllCartItemOfCustomer("00904"));

        //test case 16.2
        shop.addToCart("00704", "00904", 2);
        ArrayList<Product> expected = new ArrayList<>();
        expected.add(new Product("Polo High Socks", "00970", "00704", 5.0, 198, Product.Category.CLOTHING, "4 2 5", 92));
        expected.add(new Product("Polo High Socks", "00970", "00704", 5.0, 198, Product.Category.CLOTHING, "4 2 5", 92));
        for (int i = 0; i < 2; i++){
            assertEquals(expected.get(i).getName(), shop.returnAllCartItemOfCustomer("00904").get(i).getName());
            assertEquals(expected.get(i).getSellerID(), shop.returnAllCartItemOfCustomer("00904").get(i).getSellerID());
            assertEquals(expected.get(i).getId(), shop.returnAllCartItemOfCustomer("00904").get(i).getId());
            assertEquals(expected.get(i).getPrice(), shop.returnAllCartItemOfCustomer("00904").get(i).getPrice());
            assertEquals(expected.get(i).getStockCount(), shop.returnAllCartItemOfCustomer("00904").get(i).getStockCount());
            assertEquals(expected.get(i).getCategory(), shop.returnAllCartItemOfCustomer("00904").get(i).getCategory());
            assertEquals(expected.get(i).getRatings(), shop.returnAllCartItemOfCustomer("00904").get(i).getRatings());
            assertEquals(expected.get(i).getNumberOfOrders(), shop.returnAllCartItemOfCustomer("00904").get(i).getNumberOfOrders());
        }
    }

    //test case 17
    @Test
    @Order(18)
    void returnStrOrderAllCartItemOfCustomerTwoProduct() throws IOException {
        ECommerceSystem shop = new ECommerceSystem();

        //test case 17.1
        assertThrows(UnknownUserException.class, () -> shop.returnStrOrderAllCartItemOfCustomer("99999"));

        //test case 17.2
        shop.addToCart("00717", "00904", 1);
        shop.addToCart("00717", "00900", 1);
        shop.returnStrOrderAllCartItemOfCustomer("00900");
        assertThrows(ProductOutOfStockException.class, () -> shop.returnStrOrderAllCartItemOfCustomer("00904"));

        //test case 17.3
        shop.addToCart("00700", "00900", 1);
        assertThrows(UserDoesNotHaveEnoughMoneyException.class, () -> shop.returnStrOrderAllCartItemOfCustomer("00900"));

        //test case 17.4
        shop.addToCart("00704", "00904", 2);
        assertEquals("00502", shop.returnStrOrderAllCartItemOfCustomer("00904").get(0).getOrderNumber());

        //test case 17.5
        assertThrows(UserHasNoItemsInCartException.class, () -> shop.returnStrOrderAllCartItemOfCustomer("00904"));
    }

    //test case 18
    @Test
    @Order(19)
    void rateProductOneProduct() throws IOException{
        ECommerceSystem shop = new ECommerceSystem();

        //test case 18.1
        assertThrows(UserCantRateProductTheyHaveNotGotException.class, () -> shop.rateProduct("00900", "00704", "5"));

        //test case 18.2
        assertThrows(UnknownUserException.class, () -> shop.rateProduct("99999", "00704", "5"));

        //test case 18.3
        assertThrows(UnknownProductException.class, () -> shop.rateProduct("00904", "77777", "5"));

        //test case 18.4
        assertThrows(InvalidRatingException.class, () -> shop.rateProduct("00904", "00704", "-1"));

        //test case 18.5
        assertDoesNotThrow(() -> shop.rateProduct("00904", "00704", "5"));
    }

    //test case 19
    @Test
    @Order(20)
    void updateProductOneProductOneProduct() {
        ECommerceSystem shop = new ECommerceSystem();

        //test case 19.1
        assertThrows(UnknownProductException.class, () -> shop.updateProduct("77777", "00970"));

        //test case 19.2
        assertThrows(UnknownUserException.class, () -> shop.updateProduct("00720", "99999"));

        //test case 19.3
        assertThrows(UserNotSellerException.class, () -> shop.updateProduct("00720", "00904"));

        //test case 19.4
        assertThrows(SellerDoesNotSellThisProuct.class, () -> shop.updateProduct("00720", "00906"));

        //test case 19.5
        assertDoesNotThrow(() -> shop.updateProduct("00720", "00970"));


        Product product = new Product("Polo High Socks", "00970", "00704", 5.0, 199, Product.Category.CLOTHING, "4 2 5", 91);
        Product book = new Book("Ahm Gonna Make You Learn", "00970", "00702", 45.0, 41, "", 4, "A. Einstein", "2021");
        Product movie = new Movie("Shrek", "00970", "00719", 30.0, 51, "5 5 5 5 5", 12, "Comedy", "01hours :30minutes");
        Product videoGame = new VideoGame("Halo 3", "00970", "00718", 59.99, 4, "2 5 5", 7, "M", "FPS");

        //test case 19.6
        assertThrows(InvalidProductNameException.class, () -> shop.updateProduct(product, "", 1.0, 1));

        //test case 19.7
        assertThrows(InvalidPriceException.class, () -> shop.updateProduct(product, "better socks", -1, 1));

        //test case 19.8
        assertThrows(InvalidStockException.class, () -> shop.updateProduct(product, "better socks", 1.0, -1));

        //test case 19.9
        assertDoesNotThrow(() -> shop.updateProduct(product, "better socks", 1.0, 1));

        //test case 19.10
        assertThrows(InvalidAuthorNameException.class, () -> shop.updateProduct(book, "better book", 1.0, 1, "", "2020"));

        //test case 19.11
        assertThrows(InvalidBookYearException.class, () -> shop.updateProduct(book, "better book", 1.0, 1, "guy", ""));

        //test case 19.12
        assertDoesNotThrow(() -> shop.updateProduct(book, "better book", 1.0, 1, "guy", "2020"));

        //test case 19.13
        assertThrows(InvalidGenreException.class, () -> shop.updateProduct(movie, "better Shrek", 1.0, 23, "", "02hours :30minutes"));

        //test case 19.14
        assertThrows(InvalidMovieLengthException.class, () -> shop.updateProduct(movie, "better Shrek", 1.0, 23, "Comedy", "2h:30min"));

        //test case 19.15
        assertDoesNotThrow(() -> shop.updateProduct(movie, "better Shrek", 1.0, 23, "Comedy", "02hours :30minutes"));

        //test case 19.16
        assertThrows(InvalidGenreException.class, () -> shop.updateProduct(videoGame, "halo 4", 1.0, 2, "", "PG-13"));

        //test case 19.17
        assertThrows(InvalidESRBRatingException.class, () -> shop.updateProduct(videoGame, "halo 4", 1.0, 2, "RPG", ""));

        //test case 19.18
        assertDoesNotThrow(() -> shop.updateProduct(videoGame, "halo 4", 1.0, 2, "RPG", "E"));
    }

    //test case 20
    @Test
    @Order(21)
    void removeProductOneProduct() {
        ECommerceSystem shop = new ECommerceSystem();

        //test case 20.1
        assertThrows(UnknownProductException.class, () -> shop.removeProduct("77777", "00970"));

        //test case 20.2
        assertThrows(UnknownUserException.class, () -> shop.removeProduct("00720", "99999"));

        //test case 20.3
        assertThrows(UserNotSellerException.class, () -> shop.removeProduct("00720", "00904"));

        //test case 20.4
        assertThrows(SellerDoesNotSellThisProuct.class, () -> shop.removeProduct("00720", "00906"));

        //test case 20.5
        assertDoesNotThrow(() -> shop.removeProduct("00720", "00970"));
    }

    //test case 21
    @Test
    @Order(22)
    void addProductOneProduct(){
        ECommerceSystem shop = new ECommerceSystem();

        //test case 21.1
        assertThrows(UnknownUserException.class, () -> shop.addProduct("99999"));

        //test case 21.2
        assertThrows(UserNotSellerException.class, () -> shop.addProduct("00904"));

        //test case 21.3
        assertDoesNotThrow(() -> shop.addProduct("00970"));


        //test case 21.4
        assertThrows(InvalidProductNameException.class, () -> shop.addProduct(Product.Category.CLOTHING, "00970", "",  1.0, 1));

        //test case 21.5
        assertThrows(InvalidPriceException.class, () ->  shop.addProduct(Product.Category.CLOTHING, "00970", "new socks",  -1.0, 1));

        //test case 21.6
        assertThrows(InvalidStockException.class, () ->  shop.addProduct(Product.Category.CLOTHING, "00970", "new socks",  1.0, -1));

        //test case 21.7
        assertDoesNotThrow(() -> shop.addProduct(Product.Category.CLOTHING, "00970", "better socks", 1.0, 1));


        //test case 21.8
        assertThrows(InvalidAuthorNameException.class, () -> shop.addProduct(Product.Category.BOOK, "00970", "better book", 1.0, 1, "", "2020"));

        //test case 21.9
        assertThrows(InvalidBookYearException.class, () -> shop.addProduct(Product.Category.BOOK, "00970", "better book", 1.0, 1, "guy", ""));

        //test case 21.10
        assertDoesNotThrow(() -> shop.addProduct(Product.Category.BOOK, "00970", "better book", 1.0, 1, "guy", "2020"));


        //test case 21.11
        assertThrows(InvalidGenreException.class, () -> shop.addProduct(Product.Category.MOVIE, "00970","better Shrek", 1.0, 23, "", "02hours :30minutes"));

        //test case 21.12
        assertThrows(InvalidMovieLengthException.class, () -> shop.addProduct(Product.Category.MOVIE,  "00970","better Shrek", 1.0, 23, "Comedy", "2h:30min"));

        //test case 21.13
        assertDoesNotThrow(() -> shop.addProduct(Product.Category.MOVIE, "00970", "better Shrek", 1.0, 23, "Comedy", "02hours :30minutes"));


        //test case 21.14
        assertThrows(InvalidGenreException.class, () -> shop.addProduct(Product.Category.VIDEOGAME, "00970", "halo 4", 1.0, 2, "", "PG-13"));

        //test case 21.15
        assertThrows(InvalidESRBRatingException.class, () -> shop.addProduct(Product.Category.VIDEOGAME, "00970", "halo 4", 1.0, 2, "RPG", ""));

        //test case 21.16
        assertDoesNotThrow(() -> shop.addProduct(Product.Category.VIDEOGAME, "00970", "halo 4", 1.0, 2, "RPG", "E"));
    }

    //test case 22
    @Test
    @Order(23)
    void userLoginOneTime(){
        ECommerceSystem shop = new ECommerceSystem();

        //test case 22.1
        assertNull(shop.userLogin("", ""));

        //test case 22.2
        assertNull(shop.userLogin("v22", ""));

        //test case 22.3
        assertNull(shop.userLogin("", "qwe123"));

        //test case 22.4
        assertDoesNotThrow(() -> shop.userLogin("v22", "qwe123"));
    }

    //test case 23
    @Test
    @Order(24)
    void updateAccountInfo() {
        ECommerceSystem shop = new ECommerceSystem();
        // current user will always be logged in, thus there is no need to check if the user Id exists.

        //test case 23.1
        assertThrows(InvalidUsernameException.class, () -> shop.updateAccountUsername("00904", ""));

        //test case 23.2
        assertDoesNotThrow(() -> shop.updateAccountUsername("00904", "v2"));


        //test case 23.3
        assertThrows(InvalidUserPasswordException.class, () -> shop.updateAccountPassword("00904", ""));

        //test case 23.4
        assertDoesNotThrow(() -> shop.updateAccountPassword("00904", "qwe"));


        //test case 23.5
        assertThrows(InvalidNameException.class, () -> shop.updateAccountName("00904", ""));

        //test case 23.6
        assertDoesNotThrow(() -> shop.updateAccountName("00904", "Bob"));


        //test case 23.7
        assertThrows(NegativeUserBalanceException.class, () -> shop.updateAccountBalance("00904", -0.01));

        //test case 23.8
        assertDoesNotThrow(() -> shop.updateAccountBalance("00904", 1.0));


        //test case 23.9
        assertThrows(InvalidUserAddressException.class, () -> shop.updateAccountShippingAddress("00904", ""));

        //test case 23.10
        assertDoesNotThrow(() -> shop.updateAccountShippingAddress("00904", "guy street"));
    }

    //used for resetting the files before testing.
    @Test
    @Order(1)
    void getOldData() throws IOException {
        String oldProductForTesting = """
                COMPUTER
                00970
                00700
                Acer Laptop
                989.0
                99
                5
                4
                FURNITURE
                00970
                00701
                Apex Desk
                1378.0
                12
                4
                1
                BOOK
                00970
                00702
                Ahm Gonna Make You Learn
                45.0
                41

                4
                A. Einstein
                2021
                CLOTHING
                00970
                00703
                DadBod Jeans
                24.0
                50
                5 2
                37
                CLOTHING
                00970
                00704
                Polo High Socks
                5.0
                199
                4 2 5
                91
                CLOTHING
                00970
                00705
                Tightie Whities
                15.0
                99
                4 2
                5
                BOOK
                00970
                00706
                How to Fool Your Prof
                35.0
                11
                1 5 4
                11
                D. Umbast
                1997
                BOOK
                00970
                00707
                How to Escape from Prison
                45.0
                24
                4
                5
                A. Fugitive
                1963
                BOOK
                00970
                00708
                How to Teach Programming
                45.0
                12
                4 5 5 5
                32
                A. Einstein
                2001
                GENERAL
                00970
                00709
                Rock Hammer
                10.0
                22

                0
                BOOK
                00970
                00710
                Ahm Gonna Make You Learn More
                45.0
                322
                4 2 3 4 1
                52
                A. Einstein
                2022
                BOOK
                00970
                00711
                Sir Gawain & The Green Knight
                15.0
                16
                4 2 1
                7
                J.R.R. Tolkien
                1925
                BOOK
                00970
                00712
                The Book of Lost Tales, Part I
                22.99
                12

                0
                J.R.R. Tolkien
                1983
                BOOK
                00970
                00713
                Smith of Wootton Major
                14.84
                19

                0
                J.R.R. Tolkien
                1990
                BOOK
                00970
                00714
                The Hobbit
                14.5
                17

                0
                J.R.R. Tolkien
                1966
                BOOK
                00970
                00715
                The Fellowship of the Ring
                10.99
                32
                4
                13
                J.R.R. Tolkien
                1997
                GENERAL
                00970
                00716
                A pack of pens
                12.0
                5

                0
                CLOTHING
                00970
                00717
                An ugly sweater
                35.0
                1

                0
                VIDEOGAME
                00970
                00718
                Halo 3
                59.99
                4
                2 5 5
                7
                M
                FPS
                MOVIE
                00970
                00719
                Shrek
                30.0
                51
                5 5 5 5 5
                12
                Comedy
                01hours :30minutes
                BOOK
                00970
                00720
                this book
                43.23
                0

                0
                this Author
                1999
                """;
        String oldOrders = "";
        String oldUsers = """
                CUSTOMER
                00900
                Inigo Montoya
                swordGuy1
                ilikeswords123
                123.56
                1 SwordMaker Lane, Florin
                CUSTOMER
                00901
                Prince Humperdinck
                prince2
                royal123
                1999.01
                The Castle, Florin
                CUSTOMER
                00902
                Andy Dufresne
                4andy2
                password123
                50
                Shawshank Prison, Maine
                CUSTOMER
                00903
                Ferris Bueller
                Bull
                Fer
                101.12
                4160 Country Club Drive, Long Beach
                CUSTOMER
                00904
                Viet Hoang
                v22
                qwe123
                19806.99
                myHouse St.
                SELLER
                00970
                Death Merchant
                gun1
                bullet
                190220.23
                classified
                CUSTOMER
                00905
                amcus
                cus
                cus123
                999.0
                cusSt
                SELLER
                00906
                seller
                sel
                se123
                1234.0
                sellerSt
                """;
        String oldShippedOrders = "";

        Files.write(Paths.get("products.txt"), oldProductForTesting.getBytes());
        Files.write(Paths.get("orders.txt"), oldOrders.getBytes());
        Files.write(Paths.get("users.txt"), oldUsers.getBytes());
        Files.write(Paths.get("shippedOrders.txt"), oldShippedOrders.getBytes());
        assertTrue(true);
    }

    //used for resetting the files after testing.
    @Test
    @Order(25)
    void getOldDataAgain() throws IOException {
        String oldProductForTesting = """
                COMPUTER
                00970
                00700
                Acer Laptop
                989.0
                99
                5
                4
                FURNITURE
                00970
                00701
                Apex Desk
                1378.0
                12
                4
                1
                BOOK
                00970
                00702
                Ahm Gonna Make You Learn
                45.0
                41

                4
                A. Einstein
                2021
                CLOTHING
                00970
                00703
                DadBod Jeans
                24.0
                50
                5 2
                37
                CLOTHING
                00970
                00704
                Polo High Socks
                5.0
                199
                4 2 5
                91
                CLOTHING
                00970
                00705
                Tightie Whities
                15.0
                99
                4 2
                5
                BOOK
                00970
                00706
                How to Fool Your Prof
                35.0
                11
                1 5 4
                11
                D. Umbast
                1997
                BOOK
                00970
                00707
                How to Escape from Prison
                45.0
                24
                4
                5
                A. Fugitive
                1963
                BOOK
                00970
                00708
                How to Teach Programming
                45.0
                12
                4 5 5 5
                32
                A. Einstein
                2001
                GENERAL
                00970
                00709
                Rock Hammer
                10.0
                22

                0
                BOOK
                00970
                00710
                Ahm Gonna Make You Learn More
                45.0
                322
                4 2 3 4 1
                52
                A. Einstein
                2022
                BOOK
                00970
                00711
                Sir Gawain & The Green Knight
                15.0
                16
                4 2 1
                7
                J.R.R. Tolkien
                1925
                BOOK
                00970
                00712
                The Book of Lost Tales, Part I
                22.99
                12

                0
                J.R.R. Tolkien
                1983
                BOOK
                00970
                00713
                Smith of Wootton Major
                14.84
                19

                0
                J.R.R. Tolkien
                1990
                BOOK
                00970
                00714
                The Hobbit
                14.5
                17

                0
                J.R.R. Tolkien
                1966
                BOOK
                00970
                00715
                The Fellowship of the Ring
                10.99
                32
                4
                13
                J.R.R. Tolkien
                1997
                GENERAL
                00970
                00716
                A pack of pens
                12.0
                5

                0
                CLOTHING
                00970
                00717
                An ugly sweater
                35.0
                1

                0
                VIDEOGAME
                00970
                00718
                Halo 3
                59.99
                4
                2 5 5
                7
                M
                FPS
                MOVIE
                00970
                00719
                Shrek
                30.0
                51
                5 5 5 5 5
                12
                Comedy
                01hours :30minutes
                BOOK
                00970
                00720
                this book
                43.23
                0

                0
                this Author
                1999
                """;
        String oldOrders = "";
        String oldUsers = """
                CUSTOMER
                00900
                Inigo Montoya
                swordGuy1
                ilikeswords123
                123.56
                1 SwordMaker Lane, Florin
                CUSTOMER
                00901
                Prince Humperdinck
                prince2
                royal123
                1999.01
                The Castle, Florin
                CUSTOMER
                00902
                Andy Dufresne
                4andy2
                password123
                50
                Shawshank Prison, Maine
                CUSTOMER
                00903
                Ferris Bueller
                Bull
                Fer
                101.12
                4160 Country Club Drive, Long Beach
                CUSTOMER
                00904
                Viet Hoang
                v22
                qwe123
                19806.99
                myHouse St.
                SELLER
                00970
                Death Merchant
                gun1
                bullet
                190220.23
                classified
                CUSTOMER
                00905
                amcus
                cus
                cus123
                999.0
                cusSt
                SELLER
                00906
                seller
                sel
                se123
                1234.0
                sellerSt
                """;
        String oldShippedOrders = "";
        Files.write(Paths.get("products.txt"), oldProductForTesting.getBytes());
        Files.write(Paths.get("orders.txt"), oldOrders.getBytes());
        Files.write(Paths.get("users.txt"), oldUsers.getBytes());
        Files.write(Paths.get("shippedOrders.txt"), oldShippedOrders.getBytes());
        assertTrue(true);
    }
}