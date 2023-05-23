package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import sample.ShoppingSystem.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Controller {

    //Any GUI Element that exists on the main page
    @FXML private AnchorPane itemShow,loginAnch,accountInfo,cartMenu,productManagerPane,updateAccountPane,shipOrdersPane;
    @FXML private VBox pnlItems;
    @FXML private Button homeBtn,accountBtn,displayCloseBtn,shipOrdersBtn,signLogBtn,cartBtn,itemDisplayBtn,productsManagerBtn;
    @FXML private ComboBox<String> userCategory = new ComboBox<>();
    @FXML private TextField search;
    @FXML private Pane greyAccountPane;

    //Sign up & Login Related Elements
    @FXML private Button signUpBtn,loginBtn,closeBtn;
    @FXML private Label errorLogin, errorSignUp, errorSignUp1, errorOrderMsg;
    @FXML private TextField nameValue, userValue, passwordValue, addressValue, balanceValue, logUser, logPass, orderAmount;

    //Account Display Related Elements
    @FXML private Label acntName,acntAddress,acntBalance,acntUser,acntErrorMsg,acntErrorMsg2;
    @FXML private Label errorBalanceMsg2,errorUserUpdateMsg;
    @FXML private Button updateAccountBtn, updateAccountInfo, closeUpdateAccountBtn, viewOrderHistory, closeOrderHistoryBtn;
    @FXML private TextField newName, newAddress, newBalance, newUsername, newPassword;
    @FXML private GridPane orderHistoryBox;

    //Order Related Elements
    @FXML private VBox orderedItems,shippedItems;

    //Product Display Related Elements
    @FXML private GridPane itemGridBox;
    @FXML private Button addRateBtn;
    @FXML private TextField ratingTextBox;
    @FXML private Label amountLabel, displayMiscLabel1, displayMiscLabel2, errorRatingMsg, errorRatingValMsg, rateLabel;

    //Cart Display Related Elements
    @FXML private Label cartErrorMsg,totalPurchasePrice, errorBalanceMsg;
    @FXML private ScrollPane scrollCart;
    @FXML private VBox cartItems;

    @FXML private Button addToCartBtn, purchaseAllBtn, removeCartBtn;

    //Home Sort Buttons
    @FXML private Button nameSortBtn,categorySortBtn,priceSortBtn,searchBarBtn;

    //Seller Ship Orders
    @FXML private Button shipBtn;
    @FXML private TextField shipAmount,shipID;
    @FXML private VBox shipItemBox;
    @FXML private Label errorShipMsg;
    //Seller Product Manager
    @FXML private VBox productsList;
    @FXML private Button addProductBtn,removeProductBtn,updateProductBtn;

    //Add new Product Elements
    @FXML private Button addNewProductBtn, closeAddProductBtn;
    @FXML private Label errorAddProdMsg;
    @FXML private TextField newProdName, newProdPrice, newProdStock, miscInfo1, miscInfo2;
    @FXML private ComboBox<String> categoryBox = new ComboBox<>();
    @FXML private GridPane addNewProductBox;

    //Update Product Elements
    @FXML private Button updateProductBtn2,closeUpdateProductBtn;
    @FXML private TextField updateProdName, updateProdPrice, updateProdStock, updateMiscInfo1, updateMiscInfo2;
    @FXML private GridPane updateProductBox;
    @FXML private Label miscLabel1,miscLabel2,miscLabel3,miscLabel4,miscLabel5,errorUpdateProdMsg,originalNameLabel,originalPriceLabel,originalStockLabel,originalMiscLabel1,originalMiscLabel2;

    private ECommerceSystem eCommerceSystem;
    private User user = null;
    private Product prodDisplay;
    private Product prodRemove;

    // This method will automatically run when an instance of the controller is loaded.
    // In this case, the controller will only be loaded during the start up sequence of the program.
    public void initialize() throws IOException {
        userCategory.setItems(FXCollections.observableArrayList("Customer","Seller"));
        categoryBox.setItems(FXCollections.observableArrayList("Book","Clothing","Computer","Furniture","General","Movie","Video Game"));
        eCommerceSystem = new ECommerceSystem();
        shipOrdersBtn.setVisible(false);
        productsManagerBtn.setVisible(false);
        viewOrderHistory.setVisible(false);
        for (Product p: eCommerceSystem.returnProductByName()) {
            try {
                ProductTransfer.productList = p;
                AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("itemTest.fxml"));
                HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                Label tempLabel = (Label) tempItem.getChildren().get(0);
                tempLabel.setText(p.getName());
                tempLabel = (Label) tempItem.getChildren().get(1);
                tempLabel.setText(""+p.getCategory());
                tempLabel = (Label) tempItem.getChildren().get(2);
                tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                pnlItems.getChildren().add(tempItem);
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    // This method will handle any button that either have no relation to any group, or the side bar of the
    // main page.
    public void btnHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == homeBtn) {
            itemShow.toBack();
            accountInfo.toBack();
            cartMenu.toBack();
            productManagerPane.toBack();
            shipOrdersPane.toBack();
            pnlItems.getChildren().clear();
            for (Product p: eCommerceSystem.returnProductByName()) {
                try {
                    ProductTransfer.productList = p;
                    AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("itemTest.fxml"));
                    HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                    tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                    tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                    Label tempLabel = (Label) tempItem.getChildren().get(0);
                    tempLabel.setText(p.getName());
                    tempLabel = (Label) tempItem.getChildren().get(1);
                    tempLabel.setText(""+p.getCategory());
                    tempLabel = (Label) tempItem.getChildren().get(2);
                    tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                    pnlItems.getChildren().add(tempItem);
                } catch (IOException e) {e.printStackTrace();}
            }
        }
        if (actionEvent.getSource() == accountBtn) {
            if (user == null) {
                viewOrderHistory.setVisible(false);
                updateAccountBtn.setVisible(false);
                acntAddress.setVisible(false);
                acntUser.setVisible(false);
                acntName.setVisible(false);
                acntBalance.setVisible(false);
                acntErrorMsg.setVisible(true);
                acntErrorMsg2.setVisible(true);
            } else {
                eCommerceSystem.readAllFiles();
                updateAccountBtn.setVisible(true);
                acntAddress.setVisible(true);
                acntAddress.setText("Address: " + user.getShippingAddress());
                acntUser.setVisible(true);
                acntUser.setText("Username: " + user.getUserName());
                acntName.setVisible(true);
                acntName.setText("Name: " + user.getName());
                acntBalance.setVisible(true);
                acntBalance.setText("Balance: $" + String.format("%.2f",user.getBalance()));
                acntErrorMsg.setVisible(false);
                acntErrorMsg2.setVisible(false);
                viewOrderHistory.setVisible(true);
            }
            accountInfo.toFront();
        }
        //Display the Product stats, desc, etc
        if (actionEvent.getSource() == itemDisplayBtn) {
            prodDisplay = ProductTransfer.productView;
            System.out.println(prodDisplay.returnStrProductPrint());
            if (user == null) {
                addToCartBtn.setVisible(false);
                orderAmount.setVisible(false);
                amountLabel.setVisible(false);
                ratingTextBox.setVisible(false);
                rateLabel.setVisible(false);
                addRateBtn.setVisible(false);
            } else {
                addToCartBtn.setVisible(true);
                orderAmount.setVisible(true);
                amountLabel.setVisible(true);
                ratingTextBox.setVisible(true);
                rateLabel.setVisible(true);
                addRateBtn.setVisible(true);
            }
            if (prodDisplay != null) {
                //Product Name
                System.out.println(prodDisplay.getCategory());
                Label tempLabel = (Label) itemGridBox.getChildren().get(0);
                tempLabel.setText(prodDisplay.getName());
                //Product Ratings
                tempLabel = (Label) itemGridBox.getChildren().get(1);
                try {
                    tempLabel.setText("Ratings: " + String.format("%.1f", prodDisplay.getAverageRating()));
                } catch (NumberFormatException e) {tempLabel.setText("Ratings: Unrated");}
                //Product Stock
                tempLabel = (Label) itemGridBox.getChildren().get(2);
                tempLabel.setText("Stock: "+prodDisplay.getStockCount());
                //Product Category
                tempLabel = (Label) itemGridBox.getChildren().get(3);
                tempLabel.setText(""+prodDisplay.getCategory());
                //Product Price
                tempLabel = (Label) itemGridBox.getChildren().get(4);
                tempLabel.setText("$"+String.format("%.2f",prodDisplay.getPrice()));
                if (prodDisplay.getCategoryName().equals("BOOK") || prodDisplay.getCategoryName().equals("MOVIE") || prodDisplay.getCategoryName().equals("VIDEOGAME")) {
                    displayMiscLabel1.setVisible(true);
                    displayMiscLabel2.setVisible(true);
                    if (prodDisplay.getCategoryName().equals("BOOK")) {
                        displayMiscLabel1.setText("Author: " + ((Book)prodDisplay).getAuthor());
                        displayMiscLabel2.setText("Year: " + ((Book)prodDisplay).getYear());
                    }
                    if (prodDisplay.getCategoryName().equals("MOVIE")) {
                        displayMiscLabel1.setText("Genre: " + ((Movie)prodDisplay).getGenre());
                        displayMiscLabel2.setText("Length: " + ((Movie)prodDisplay).getLength());
                    }
                    if (prodDisplay.getCategoryName().equals("VIDEOGAME")) {
                        displayMiscLabel1.setText("Genre: " + ((VideoGame)prodDisplay).getGenre());
                        displayMiscLabel2.setText("ESRB Rating: " + ((VideoGame)prodDisplay).getESRBRating());
                    }
                } else {
                    displayMiscLabel1.setVisible(false);
                    displayMiscLabel2.setVisible(false);
                }
                itemShow.toFront();
            }
        }
        // Pressed Cart Button on Side Bar
        if (actionEvent.getSource() == cartBtn) {
            if (user == null) {
                scrollCart.setVisible(false);
                cartErrorMsg.setVisible(true);
                purchaseAllBtn.setVisible(false);
                removeCartBtn.setVisible(false);
                totalPurchasePrice.setVisible(false);
            } else {
                eCommerceSystem.readAllFiles(user.getId());
                user = eCommerceSystem.userLogin(user.getUserName(),user.getPassword());
                scrollCart.setVisible(true);
                cartErrorMsg.setVisible(false);
                purchaseAllBtn.setVisible(true);
                removeCartBtn.setVisible(true);
                totalPurchasePrice.setVisible(true);
                cartItems.getChildren().clear();
                if (!eCommerceSystem.returnAllCartItemOfCustomer(user.getId()).isEmpty()) {
                    for (Product p: eCommerceSystem.returnAllCartItemOfCustomer(user.getId())) {
                        try {
                            ProductTransfer.productRemove = p;
                            AnchorPane tempItemBox = FXMLLoader.load(getClass().getResource("cartItem.fxml"));
                            HBox tempItem = (HBox) tempItemBox.getChildren().get(0);
                            tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                            tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                            Label tempLabel = (Label) tempItem.getChildren().get(0);
                            tempLabel.setText(p.getName());
                            tempLabel = (Label) tempItem.getChildren().get(1);
                            tempLabel.setText(""+p.getCategory());
                            tempLabel = (Label) tempItem.getChildren().get(2);
                            tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                            cartItems.getChildren().add(tempItem);
                        } catch (IOException e) {e.printStackTrace();}
                    }
                }
                totalPurchasePrice.setText("Price Total: $" + String.format("%.2f",user.getCart().getTotal()));
            }
            cartMenu.toFront();
        }
        // Seller only Button that accesses shipping list
        if (actionEvent.getSource() == shipOrdersBtn) {
            try {
                ArrayList<ProductOrdered> productOrderedArrayList = eCommerceSystem.returnAllOrderRequestsForSeller(user.getId());
                shipItemBox.getChildren().clear();
                for (ProductOrdered p : productOrderedArrayList) {
                    try {
                        ProductTransfer.productShip = p;
                        AnchorPane tempItemBox = FXMLLoader.load(getClass().getResource("shipOrder.fxml"));
                        HBox tempItem = (HBox) tempItemBox.getChildren().get(0);
                        Label tempLabel = (Label) tempItem.getChildren().get(0);
                        tempLabel.setText("Order #"+p.getOrderNumber());
                        tempLabel = (Label) tempItem.getChildren().get(1);
                        tempLabel.setText("Customer ID: " + p.getUser().getId());
                        tempLabel = (Label) tempItem.getChildren().get(2);
                        tempLabel.setText("Product ID: " + p.getProduct().getId());
                        shipItemBox.getChildren().add(tempItem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (NumberFormatException e) {e.printStackTrace();}
            shipOrdersPane.toFront();
        }
        //Button on the top bar, is a multipurpose button used to both activate a signup/login sequence and log out as well.
        if (actionEvent.getSource() == signLogBtn) {
            // User is not logged in
            if (user == null) {
                loginAnch.toFront();
            } else {
                //User wishes to sign out
                if (user.isSeller()) {
                    shipOrdersBtn.setVisible(false);
                    productsManagerBtn.setVisible(false);
                }
                user = null;
                itemShow.toBack();
                accountInfo.toBack();
                cartMenu.toBack();
                productManagerPane.toBack();
                shipOrdersPane.toBack();
                signLogBtn.setText("Sign up/Login");
                signLogBtn.setStyle("-fx-background-color : skyblue");

            }
        }
        if (actionEvent.getSource() == closeBtn) {
            loginAnch.toBack();
        }
        if (actionEvent.getSource() == displayCloseBtn) {
            itemShow.toBack();
        }
        if (actionEvent.getSource() == productsManagerBtn) {
            try {
                productsList.getChildren().clear();
                for (Product p : eCommerceSystem.returnMyProducts(user.getId())) {
                    ProductTransfer.productItem = p;
                    AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("productItem.fxml"));
                    HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                    tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                    tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                    Label tempLabel = (Label) tempItem.getChildren().get(0);
                    tempLabel.setText(p.getName());
                    tempLabel = (Label) tempItem.getChildren().get(1);
                    tempLabel.setText(""+p.getCategory());
                    tempLabel = (Label) tempItem.getChildren().get(2);
                    tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                    productsList.getChildren().add(tempItem);
                }
            } catch (IOException e) {e.printStackTrace();}
            productManagerPane.toFront();
        }
        if (actionEvent.getSource() == addRateBtn) {
            try {
                Double test = Double.parseDouble(ratingTextBox.getText());
                if (test > 5 || test < 1) {
                    throw new NumberFormatException();
                }
                Double rate = eCommerceSystem.rateProduct(user.getId(), prodDisplay.getId(), ratingTextBox.getText());
                System.out.println(rate);
                if (rate == -1.0) {
                    throw new IOException("");
                } else {
                    errorRatingMsg.setVisible(false);
                    errorRatingValMsg.setVisible(false);
                    ratingTextBox.clear();
                }
            } catch (NumberFormatException e) {errorRatingValMsg.setVisible(true);}
            catch (IOException e) {errorRatingMsg.setVisible(true);}
        }
    }

    // This Button Handler is used to display the Account page which would display
    // account information.
    public void accountBtnHandler(ActionEvent actionEvent) throws IOException {
        // Sign up and create a new account
        if (actionEvent.getSource() == signUpBtn) {
            String name = nameValue.getText();
            String username = userValue.getText();
            String password = passwordValue.getText();
            String address = addressValue.getText();
            String category;
            String balance = balanceValue.getText();
            if (Objects.equals(userCategory.getValue(), "Customer")) {
                category = "1";
            } else {
                category = "2";
            }
            try {
                if (name == null | username == null | password == null | address == null | category == null | balance == null) {
                    throw new NumberFormatException("");
                } else if (Double.parseDouble(balance) < 0) {
                    throw new NumberFormatException("");
                } else {
                    errorSignUp1.setVisible(false);
                    user = eCommerceSystem.createUser(name, username, password, Double.parseDouble(balance), address, category);
                    System.out.println(user.returnStrUserPrint());
                    if (user == null) {throw new NullPointerException("");}
                    signLogBtn.setText("Sign out");
                    signLogBtn.setStyle("-fx-background-color : dodgerblue");
                    System.out.println("Sign up wOOO");
                    nameValue.clear();
                    userValue.clear();
                    passwordValue.clear();
                    addressValue.clear();
                    balanceValue.clear();
                    userCategory.getSelectionModel().clearSelection();
                    cartItems.getChildren().clear();
                    loginAnch.toBack();
                    // If logged in user is a seller (who have access to additional side menu commands)
                    if (user.isSeller()) {
                        shipOrdersBtn.setVisible(true);
                        productsManagerBtn.setVisible(true);
                    }
                    errorSignUp.setVisible(false);
                    errorSignUp1.setVisible(false);
                }
            } catch (NumberFormatException e) {
                errorSignUp1.setVisible(true);
            }
            catch (NullPointerException e){
                errorSignUp.setVisible(true);
            }
        }
        // Login to Account
        if (actionEvent.getSource() == loginBtn) {
            String username = logUser.getText();
            String password = logPass.getText();
            User loguser = eCommerceSystem.userLogin(username, password);

            if (loguser != null) {
                System.out.println(loguser.returnStrUserPrint());
                errorLogin.setVisible(false);
                user = loguser;
                signLogBtn.setText("Sign out");
                signLogBtn.setStyle("-fx-background-color : dodgerblue");
                System.out.println("Log in Yay");
                logUser.clear();
                logPass.clear();
                if (user.isSeller()) {
                    shipOrdersBtn.setVisible(true);
                    productsManagerBtn.setVisible(true);
                }
                loginAnch.toBack();
            } else {
                errorLogin.setVisible(true);
            }
        }
        if (actionEvent.getSource() == updateAccountBtn) {
            greyAccountPane.setVisible(true);
            updateAccountPane.setVisible(true);
        }
        if (actionEvent.getSource() == updateAccountInfo) {
            try {
                String name = newName.getText();
                String address = newAddress.getText();
                String username = newUsername.getText();
                String password = newPassword.getText();
                String balance = newBalance.getText();
                if (!username.isBlank()) {
                    eCommerceSystem.updateAccountUsername(user.getId(),username);
                } else {username = user.getUserName();}
                if (!balance.isBlank()) {
                    if (Double.parseDouble(balance) < 0){
                        throw new NumberFormatException();
                    }
                    eCommerceSystem.updateAccountBalance(user.getId(),Double.parseDouble(balance));
                }
                if (!name.isBlank()) {
                    eCommerceSystem.updateAccountName(user.getId(),name);
                }
                if (!address.isBlank()) {
                    eCommerceSystem.updateAccountShippingAddress(user.getId(),address);
                }
                if (!password.isBlank()) {
                    eCommerceSystem.updateAccountPassword(user.getId(),password);
                } else {
                    password = user.getPassword();
                }
                eCommerceSystem.readAllFiles(user.getId());
                user = eCommerceSystem.userLogin(username,password);
                System.out.println(user.returnStrUserPrint());
                newName.clear();
                newAddress.clear();
                newPassword.clear();
                newUsername.clear();
                newBalance.clear();
                acntAddress.setText("Address: " + user.getShippingAddress());
                acntUser.setText("Username: " + user.getUserName());
                acntName.setText("Name: " + user.getName());
                acntBalance.setText("Balance: $" + String.format("%.2f",user.getBalance()));
                errorBalanceMsg2.setVisible(false);
                errorUserUpdateMsg.setVisible(false);
                greyAccountPane.setVisible(false);
                updateAccountPane.setVisible(false);

            } catch (NumberFormatException e) {
                errorBalanceMsg2.setVisible(true);
            } catch (Exception e){
                // errorUsername.setVisible(true);
                errorUserUpdateMsg.setVisible(true);
            }
        }
        if (actionEvent.getSource() == closeUpdateAccountBtn) {
            greyAccountPane.setVisible(false);
            updateAccountPane.setVisible(false);
        }
        if (actionEvent.getSource() == viewOrderHistory) {
            orderedItems.getChildren().clear();
            shippedItems.getChildren().clear();
            for (ProductOrdered p: eCommerceSystem.returnOrderHistory(user.getId())) {
                try {
                    ProductTransfer.productOrder = p.getProduct();
                    AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("shipItem.fxml"));
                    HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                    tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                    tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                    Label tempLabel = (Label) tempItem.getChildren().get(0);
                    tempLabel.setText("Order #"+p.getOrderNumber());
                    tempLabel = (Label) tempItem.getChildren().get(1);
                    tempLabel.setText("Product ID: "+p.getProduct().getId());
                    tempLabel = (Label) tempItem.getChildren().get(2);
                    tempLabel.setText("$"+String.format("%.2f",p.getProduct().getPrice()));
                    tempLabel = (Label) tempItem.getChildren().get(3);
                    tempLabel.setText("Product Name: "+p.getProduct().getName());
                    orderedItems.getChildren().add(tempItem);
                } catch (IOException e) {e.printStackTrace();}
            }
            for (ProductOrdered p: eCommerceSystem.returnShipHistory(user.getId())) {
                try {
                    ProductTransfer.productOrder = p.getProduct();
                    AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("shipItem.fxml"));
                    HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                    tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                    tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                    Label tempLabel = (Label) tempItem.getChildren().get(0);
                    tempLabel.setText("Order #"+p.getOrderNumber());
                    tempLabel = (Label) tempItem.getChildren().get(1);
                    tempLabel.setText("Product ID: "+p.getProduct().getId());
                    tempLabel = (Label) tempItem.getChildren().get(2);
                    tempLabel.setText("$"+String.format("%.2f",p.getProduct().getPrice()));
                    tempLabel = (Label) tempItem.getChildren().get(3);
                    tempLabel.setText("Product Name: "+p.getProduct().getName());
                    shippedItems.getChildren().add(tempItem);
                } catch (IOException e) {e.printStackTrace();}
            }
            orderHistoryBox.setVisible(true);
        }
        if (actionEvent.getSource() == closeOrderHistoryBtn) {
            orderHistoryBox.setVisible(false);
        }
    }
    // This Cart Button Handler displays the Cart page on the site or handles cart related functions.
    public void cartBtnHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == addToCartBtn) {
            String orderAmnt = orderAmount.getText();
            try {
                if (orderAmnt.equals("0")) {throw new NumberFormatException("");}
                eCommerceSystem.addToCart(prodDisplay.getId(), user.getId(), Integer.parseInt(orderAmnt));
                System.out.println("User's Cart: ");
                for (Product p : eCommerceSystem.returnAllCartItemOfCustomer(user.getId())) {
                    System.out.println(p.returnStrProductPrint());
                }
                errorOrderMsg.setVisible(false);
                orderAmount.clear();
                itemShow.toBack();
            } catch (NumberFormatException e) {errorOrderMsg.setVisible(true);}
        }
        if (actionEvent.getSource() == purchaseAllBtn) {
            if (user.getCart().getTotal() > user.getBalance()) {
                errorBalanceMsg.setVisible(true);
            } else {
                errorBalanceMsg.setVisible(false);
                System.out.println("Purchasing All");
                eCommerceSystem.returnStrOrderAllCartItemOfCustomer(user.getId());
                cartItems.getChildren().clear();
                for (Product p: eCommerceSystem.returnAllCartItemOfCustomer(user.getId())) {
                    try {
                        ProductTransfer.productList = p;
                        AnchorPane tempItemBox = FXMLLoader.load(getClass().getResource("cartItem.fxml"));
                        HBox tempItem = (HBox) tempItemBox.getChildren().get(0);
                        tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                        tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                        Label tempLabel = (Label) tempItem.getChildren().get(0);
                        tempLabel.setText(p.getName());
                        tempLabel = (Label) tempItem.getChildren().get(1);
                        tempLabel.setText(""+p.getCategory());
                        tempLabel = (Label) tempItem.getChildren().get(2);
                        tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                        cartItems.getChildren().add(tempItem);
                    } catch (IOException e) {e.printStackTrace();}
                }
                totalPurchasePrice.setText("Price Total: $" + String.format("%.2f",user.getCart().getTotal()));
            }
        }
        if (actionEvent.getSource() == removeCartBtn) {
            prodRemove = ProductTransfer.productRemove;
            if (prodRemove != null) {
                if (eCommerceSystem.removeRemoveFromCart(prodRemove.getId(), user.getId())) {
                    System.out.println("Remove Successful");
                }
                cartItems.getChildren().clear();
                for (Product p: eCommerceSystem.returnAllCartItemOfCustomer(user.getId())) {
                    try {
                        ProductTransfer.productRemove = p;
                        AnchorPane tempItemBox = FXMLLoader.load(getClass().getResource("cartItem.fxml"));
                        HBox tempItem = (HBox) tempItemBox.getChildren().get(0);
                        tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                        tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                        Label tempLabel = (Label) tempItem.getChildren().get(0);
                        tempLabel.setText(p.getName());
                        tempLabel = (Label) tempItem.getChildren().get(1);
                        tempLabel.setText(""+p.getCategory());
                        tempLabel = (Label) tempItem.getChildren().get(2);
                        tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                        cartItems.getChildren().add(tempItem);
                    } catch (IOException e) {e.printStackTrace();}
                }
                totalPurchasePrice.setText("Price Total: $" + String.format("%.2f",user.getCart().getTotal()));
            }
        }
    }
    public void sortBtnHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == nameSortBtn) {
            pnlItems.getChildren().clear();
            for (Product p: eCommerceSystem.returnProductByName()) {
                try {
                    ProductTransfer.productList = p;
                    AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("itemTest.fxml"));
                    HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                    tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                    tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                    Label tempLabel = (Label) tempItem.getChildren().get(0);
                    tempLabel.setText(p.getName());
                    tempLabel = (Label) tempItem.getChildren().get(1);
                    tempLabel.setText(""+p.getCategory());
                    tempLabel = (Label) tempItem.getChildren().get(2);
                    tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                    pnlItems.getChildren().add(tempItem);
                } catch (IOException e) {e.printStackTrace();}
            }
        }
        if (actionEvent.getSource() == categorySortBtn) {
            pnlItems.getChildren().clear();
            for (Product p: eCommerceSystem.returnCategoryBaseOnMinimumRating("8","0")) {
                try {
                    ProductTransfer.productList = p;
                    AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("itemTest.fxml"));
                    HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                    tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                    tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                    Label tempLabel = (Label) tempItem.getChildren().get(0);
                    tempLabel.setText(p.getName());
                    tempLabel = (Label) tempItem.getChildren().get(1);
                    tempLabel.setText(""+p.getCategory());
                    tempLabel = (Label) tempItem.getChildren().get(2);
                    tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                    pnlItems.getChildren().add(tempItem);
                } catch (IOException e) {e.printStackTrace();}
            }
        }
        if (actionEvent.getSource() == priceSortBtn) {
            pnlItems.getChildren().clear();
            for (Product p: eCommerceSystem.returnProductByPrice()) {
                try {
                    ProductTransfer.productList = p;
                    AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("itemTest.fxml"));
                    HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                    tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                    tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                    Label tempLabel = (Label) tempItem.getChildren().get(0);
                    tempLabel.setText(p.getName());
                    tempLabel = (Label) tempItem.getChildren().get(1);
                    tempLabel.setText(""+p.getCategory());
                    tempLabel = (Label) tempItem.getChildren().get(2);
                    tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                    pnlItems.getChildren().add(tempItem);
                } catch (IOException e) {e.printStackTrace();}
            }
        }
        if (actionEvent.getSource() == searchBarBtn) {
            String strSearch = search.getText();
            pnlItems.getChildren().clear();
            for (Product p: eCommerceSystem.returnProductByName()) {
                try {
                    if (p.getName().toLowerCase(Locale.ROOT).contains(strSearch.toLowerCase(Locale.ROOT))) {
                        ProductTransfer.productList = p;
                        AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("itemTest.fxml"));
                        HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                        tempItem.setOnMouseEntered(event -> {
                            tempItem.setStyle("-fx-background-color : grey");
                        });
                        tempItem.setOnMouseExited(event -> {
                            tempItem.setStyle("-fx-background-color : ghostwhite");
                        });
                        Label tempLabel = (Label) tempItem.getChildren().get(0);
                        tempLabel.setText(p.getName());
                        tempLabel = (Label) tempItem.getChildren().get(1);
                        tempLabel.setText("" + p.getCategory());
                        tempLabel = (Label) tempItem.getChildren().get(2);
                        tempLabel.setText("$" + String.format("%.2f", p.getPrice()));
                        pnlItems.getChildren().add(tempItem);
                    }
                } catch (IOException e) {e.printStackTrace();}
            }
        }
    }
    public void sellerBtnHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == shipBtn) {
            errorShipMsg.setVisible(false);
            String productId = shipID.getText();
            String productAmount = shipAmount.getText();
            try {
                Integer prodAmt = Integer.parseInt(productAmount);
                ArrayList<ProductOrdered> productOrderedArrayList = eCommerceSystem.shipOrders(user.getId(),productId,prodAmt);
                if (productOrderedArrayList.isEmpty()) {
                    errorShipMsg.setVisible(true);
                } else {
                    ArrayList<ProductOrdered> newProductOrdersList = eCommerceSystem.returnAllOrderRequestsForSeller(user.getId());
                    shipItemBox.getChildren().clear();
                    for (ProductOrdered p : newProductOrdersList) {
                        try {

                            ProductTransfer.productShip = p;
                            AnchorPane tempItemBox = FXMLLoader.load(getClass().getResource("shipOrder.fxml"));
                            HBox tempItem = (HBox) tempItemBox.getChildren().get(0);
                            Label tempLabel = (Label) tempItem.getChildren().get(0);
                            tempLabel.setText("Order #"+p.getOrderNumber());
                            tempLabel = (Label) tempItem.getChildren().get(1);
                            tempLabel.setText("Customer ID: " + p.getUser().getId());
                            tempLabel = (Label) tempItem.getChildren().get(2);
                            tempLabel.setText("Product ID: " + p.getProduct().getId());
                            shipItemBox.getChildren().add(tempItem);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (NumberFormatException e) {errorShipMsg.setVisible(true);}
        }
        if (actionEvent.getSource() == addProductBtn) {
            addNewProductBox.setVisible(true);
        }
        if (actionEvent.getSource() == addNewProductBtn) {
            String prodName = newProdName.getText();
            String prodPrice = newProdPrice.getText();
            String prodCategory = categoryBox.getValue();
            String prodStock = newProdStock.getText();
            String miscData = miscInfo1.getText();
            String miscData2 = miscInfo2.getText();
            try {
                if (prodName.isBlank() || prodPrice.isBlank() || prodCategory.isBlank() || prodStock.isBlank()) {
                    throw new NumberFormatException("");
                } else {
                    if (prodCategory.equals("Book") || prodCategory.equals("Movie") || prodCategory.equals("Video Game")) {
                        if (miscData.isBlank() || miscData2.isBlank()) {
                            throw new NumberFormatException("");
                        } else {
                            eCommerceSystem.addProduct(new Product().strToCategory(prodCategory), user.getId(), prodName, Double.parseDouble(prodPrice), Integer.parseInt(prodStock),miscData,miscData2);
                        }
                    } else {
                        eCommerceSystem.addProduct(new Product().strToCategory(prodCategory), user.getId(), prodName, Double.parseDouble(prodPrice), Integer.parseInt(prodStock));
                    }
                }
                errorAddProdMsg.setVisible(false);
                //Re-displays Product List
                productsList.getChildren().clear();
                for (Product p : eCommerceSystem.returnMyProducts(user.getId())) {
                    ProductTransfer.productItem = p;
                    AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("productItem.fxml"));
                    HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                    tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                    tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                    Label tempLabel = (Label) tempItem.getChildren().get(0);
                    tempLabel.setText(p.getName());
                    tempLabel = (Label) tempItem.getChildren().get(1);
                    tempLabel.setText(""+p.getCategory());
                    tempLabel = (Label) tempItem.getChildren().get(2);
                    tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                    productsList.getChildren().add(tempItem);
                }
                addNewProductBox.setVisible(false);
            } catch (NumberFormatException e) {errorAddProdMsg.setVisible(true);}
        }
        if (actionEvent.getSource() == removeProductBtn) {
            Product tempRemove = ProductTransfer.productItem;
            eCommerceSystem.removeProduct(tempRemove.getId(), user.getId());
            productsList.getChildren().clear();
            for (Product p : eCommerceSystem.returnMyProducts(user.getId())) {
                ProductTransfer.productItem = p;
                AnchorPane tempItemPane = FXMLLoader.load(getClass().getResource("productItem.fxml"));
                HBox tempItem = (HBox) tempItemPane.getChildren().get(0);
                tempItem.setOnMouseEntered(event -> { tempItem.setStyle("-fx-background-color : grey"); });
                tempItem.setOnMouseExited(event -> { tempItem.setStyle("-fx-background-color : ghostwhite"); });
                Label tempLabel = (Label) tempItem.getChildren().get(0);
                tempLabel.setText(p.getName());
                tempLabel = (Label) tempItem.getChildren().get(1);
                tempLabel.setText(""+p.getCategory());
                tempLabel = (Label) tempItem.getChildren().get(2);
                tempLabel.setText("$"+ String.format("%.2f",p.getPrice()));
                productsList.getChildren().add(tempItem);
            }
        }
        //Press Update Button on Product Manager
        if (actionEvent.getSource() == updateProductBtn) {
            Product productSelect = ProductTransfer.productItem;
            if (productSelect.getCategoryName().equals("BOOK") || productSelect.getCategoryName().equals("MOVIE") || productSelect.getCategoryName().equals("VIDEOGAME")) {
                miscLabel1.setVisible(true);
                miscLabel2.setVisible(true);
                miscLabel3.setVisible(true);
                miscLabel4.setVisible(true);
                miscLabel5.setVisible(true);
                if (productSelect.getCategoryName().equals("BOOK")) {
                    originalMiscLabel1.setText("Original Author: " + ((Book) productSelect).getAuthor());
                    originalMiscLabel2.setText("Original Year: " + ((Book)productSelect).getYear());
                } else if (productSelect.getCategoryName().equals("MOVIE")) {
                    originalMiscLabel1.setText("Original Genre: " + ((Movie) productSelect).getGenre());
                    originalMiscLabel2.setText("Original Length: " + ((Movie)productSelect).getLength());
                } else if (productSelect.getCategoryName().equals("VIDEOGAME")) {
                    originalMiscLabel1.setText("Original Genre: " + ((VideoGame) productSelect).getGenre());
                    originalMiscLabel2.setText("Original ESRB Rating: " + ((VideoGame)productSelect).getESRBRating());
                }
                originalMiscLabel1.setVisible(true);
                originalMiscLabel2.setVisible(true);
                updateMiscInfo1.setVisible(true);
                updateMiscInfo2.setVisible(true);
            } else {
                miscLabel1.setVisible(false);
                miscLabel2.setVisible(false);
                miscLabel3.setVisible(false);
                miscLabel4.setVisible(false);
                miscLabel5.setVisible(false);
                originalMiscLabel1.setVisible(false);
                originalMiscLabel2.setVisible(false);
                updateMiscInfo1.setVisible(false);
                updateMiscInfo2.setVisible(false);
            }
            originalNameLabel.setText("Original Name: " + productSelect.getName());
            originalPriceLabel.setText("Original Price: $" + String.format("%.2f",productSelect.getPrice()));
            originalStockLabel.setText("Original Stock: " + productSelect.getStockCount());
            updateProductBox.setVisible(true);
        }
        //Press Update Button to update Product
        if (actionEvent.getSource() == updateProductBtn2) {
            Product toUpdateProd = ProductTransfer.productItem;
            String newProductName,newProductPrice,newProductStock,newProductMisc1 = null,newProductMisc2 = null;
            if (!updateProdName.getText().isBlank()) {
                newProductName = updateProdName.getText();
            } else {
                newProductName = toUpdateProd.getName();
            }
            if (!updateProdPrice.getText().isBlank()) {
                newProductPrice = updateProdPrice.getText();
            } else {
                newProductPrice = ""+toUpdateProd.getPrice();
            }
            if (!updateProdStock.getText().isBlank()) {
                newProductStock = updateProdStock.getText();
            } else {
                newProductStock = ""+toUpdateProd.getStockCount();
            }
            if (toUpdateProd.getCategoryName().equals("BOOK")) {
                if (!updateMiscInfo1.getText().isBlank()) {
                    newProductMisc1 = updateMiscInfo1.getText();
                } else {
                    newProductMisc1 = ((Book)toUpdateProd).getAuthor();
                }
                if (!updateMiscInfo2.getText().isBlank()) {
                    newProductMisc2 = updateMiscInfo2.getText();
                } else {
                    newProductMisc2 = ((Book)toUpdateProd).getYear();
                }
            }
            if (toUpdateProd.getCategoryName().equals("MOVIE")) {
                if (!updateMiscInfo1.getText().isBlank()) {
                    newProductMisc1 = updateMiscInfo1.getText();
                } else {
                    newProductMisc1 = ((Movie)toUpdateProd).getGenre();
                }
                if (!updateMiscInfo2.getText().isBlank()) {
                    newProductMisc2 = updateMiscInfo2.getText();
                } else {
                    newProductMisc2 = ((Movie)toUpdateProd).getLength();
                }
            }
            if (toUpdateProd.getCategoryName().equals("VIDEOGAME")) {
                if (!updateMiscInfo1.getText().isBlank()) {
                    newProductMisc1 = updateMiscInfo1.getText();
                } else {
                    newProductMisc1 = ((VideoGame)toUpdateProd).getGenre();
                }
                if (!updateMiscInfo2.getText().isBlank()) {
                    newProductMisc2 = updateMiscInfo2.getText();
                } else {
                    newProductMisc2 = ((VideoGame)toUpdateProd).getESRBRating();
                }
            }
            try {
                if (toUpdateProd.getCategoryName().equals("BOOK") || toUpdateProd.getCategoryName().equals("MOVIE") || toUpdateProd.getCategoryName().equals("VIDEOGAME")) {
                    eCommerceSystem.updateProduct(toUpdateProd, newProductName, Double.parseDouble(newProductPrice), Integer.parseInt(newProductStock),newProductMisc1,newProductMisc2);
                } else {
                    eCommerceSystem.updateProduct(toUpdateProd, newProductName, Double.parseDouble(newProductPrice), Integer.parseInt(newProductStock));
                }
                // Change Display

                updateProductBox.setVisible(false);
            } catch (NumberFormatException e) {errorUpdateProdMsg.setVisible(true);}
        }
        if (actionEvent.getSource() == closeAddProductBtn) {
            addNewProductBox.setVisible(false);
        }
        if (actionEvent.getSource() == closeUpdateProductBtn) {
            updateProductBox.setVisible(false);
        }
    }
}
