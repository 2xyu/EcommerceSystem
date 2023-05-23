package sample.ShoppingSystem;

class UnknownUserException extends RuntimeException {

    public UnknownUserException(String userId) {

        super("User " + userId + " not found.");
    }
}

class UnknownProductException extends RuntimeException {

    public UnknownProductException(String productId) {

        super("Product " + productId + " not found.");
    }
}

class ProductOutOfStockException extends RuntimeException {

    public ProductOutOfStockException(String productId, String productName){
        super("Product Id: " + productId + " Product Name: " + productName  + " is out of stock.");
    }

}

class InvalidNameException extends RuntimeException {

    public InvalidNameException() {

        super("Invalid user, name.");
    }
}
class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException() {

        super("Invalid user, username.");
    }
}
class InvalidUserPasswordException extends RuntimeException {

    public InvalidUserPasswordException() {

        super("Invalid user password.");
    }
}

class InvalidUserAddressException extends RuntimeException {

    public InvalidUserAddressException() {

        super("Invalid user address.");
    }
}

class UnknownOrderNumberException extends RuntimeException {

    public UnknownOrderNumberException(String orderNumber) {

        super("Order " + orderNumber + " not found.");
    }
}

class ProductNotInCartException extends RuntimeException {

    public ProductNotInCartException(String productId, String userID) {

        super("Product " + productId + " is not in the cart of user " + userID + ".");
    }
}

class UserHasNoItemsInCartException extends RuntimeException {

    public UserHasNoItemsInCartException(String customerId) {
        super("User " + customerId + " has no items in their cart.");
    }
}

class InvalidRatingException extends RuntimeException {

    public InvalidRatingException(String rating) {

        super("Rating " + rating + " is invalid. Please only input a rating between 1 and 5");
    }
}

class InvalidCategoryException extends RuntimeException {

    public InvalidCategoryException(){

        super("Please only select Customer or Seller [1 or 2]: ");
    }

    public InvalidCategoryException(String category) {

        super("Category " + category + " is invalid. Please only select [1,2,3,4,5,6,7, or 8] instead");
    }
}

class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(int amount, String name, int productStock) {
        super("Invalid amount of " + amount + " given for " + name + ".\n" + name + " only has " + productStock + " in stock");
    }
    public InvalidAmountException(int amount, String name, int productStock, int curOrders) {
        super("Invalid amount of " + amount + " given for " + name + ".\nThere are " + curOrders + " orders requested for " + name);
    }
}

class NegativeUserBalanceException extends RuntimeException{
    public NegativeUserBalanceException(){

        super("Error, can't hava a negative user balance.");
    }
}

class UserNotSellerException extends RuntimeException{
    public UserNotSellerException(String userId, String action){
        super("User " + userId + " is not a Seller. Only sellers can " + action);
    }
}

class UserDoesNotHaveEnoughMoneyException extends RuntimeException {

    public UserDoesNotHaveEnoughMoneyException (String userId, double totalCost, double userBalance){
        super("User " + userId + " doesn't not have enough money.\nNeed: $" + totalCost + "\nHas: $" +userBalance);
    }
}

class OrderDoesNotBelongToUserException extends RuntimeException{

    public OrderDoesNotBelongToUserException(String orderNumber, String userId){
        super("Order " + orderNumber + " does not belong to you, user " + userId + ".");
    }
}

class UserCantRateProductTheyHaveNotGotException extends RuntimeException{

    public UserCantRateProductTheyHaveNotGotException(){

        super("You can't rate the product you don't have");
    }
}

class SellerDoesNotSellThisProuct extends RuntimeException{

    public SellerDoesNotSellThisProuct(){

        super("You can't remove a product you don't even sell");
    }
}

class InvalidProductNameException extends RuntimeException {
    public InvalidProductNameException(){

        super("Name can't be blank");
    }
}

class InvalidPriceException extends RuntimeException{
    public InvalidPriceException(){

        super("Price can't be negative");
    }
}

class InvalidStockException extends RuntimeException{

    public InvalidStockException(){

        super("Stock can't be negative");
    }
}

class InvalidAuthorNameException extends RuntimeException{

    public InvalidAuthorNameException(){

        super("Author name can't be blank");
    }
}

class InvalidBookYearException extends RuntimeException{

    public InvalidBookYearException(){

        super("Book year must be a 4 digit year");
    }
}

class InvalidGenreException extends RuntimeException{

    public InvalidGenreException(){

        super("Genre can't be blank");
    }
}

class InvalidMovieLengthException extends RuntimeException{

    public InvalidMovieLengthException(){

        super("Movie Length must be in the format of [0-9][0-9]hours :[0-9][0-9]minutes");
    }
}

class InvalidESRBRatingException extends RuntimeException{

    public InvalidESRBRatingException(){

        super("ESRBRating can't be blank");
    }
}

class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String username){

        super(username + " already exists. Please use a different username.");
    }
}