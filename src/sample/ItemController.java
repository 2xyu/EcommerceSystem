package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sample.ShoppingSystem.Product;
import sample.ShoppingSystem.ProductOrdered;

import java.io.IOException;

public class ItemController {
    @FXML private Button itemDisplay,itemRemove,productSelect;
    @FXML private Label itemLabel,categoryLabel,priceLabel;
    @FXML private HBox itemC;
    @FXML private AnchorPane itemPage,itemAnchPane;
    private Product product,productRem,productSel;
    private ProductOrdered productShip;
    public void initialize() {
        product = ProductTransfer.productList;
        productRem = ProductTransfer.productRemove;
        productShip = ProductTransfer.productShip;
        productSel = ProductTransfer.productItem;
    }
    public void itemBtnHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == itemDisplay) {
            System.out.println("Item View BTN Pressed");
            System.out.println(itemLabel.getText());
            ProductTransfer.productView = product;


        }
        if (actionEvent.getSource() == itemRemove) {
            System.out.println("Remove Cart Item BTN Pressed");
            System.out.println(productRem.returnStrProductPrint());
            ProductTransfer.productRemove = productRem;
        }
        if (actionEvent.getSource() == productSelect) {
            System.out.println("Select Product BTN Pressed");
            System.out.println(productSel.returnStrProductPrint());
            ProductTransfer.productItem = productSel;
        }
    }
}
