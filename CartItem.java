//Viet Hoang
//501108602

public class CartItem {

    private Product product;
    private String productOptions;
    private String typeBookOrTypeShoe;

    public CartItem(Product product, String productOptions, String typeBookOrTypeShoe) {

        this.product = product;
        this.productOptions = productOptions;
        this.typeBookOrTypeShoe = typeBookOrTypeShoe;
    }
    public Product getProduct(){

        return product;
    }

    public String getProductOptions() {

        return productOptions;
    }

    public String getTypeBookOrTypeShoe() {

        return typeBookOrTypeShoe;
    }
}
