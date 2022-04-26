//Viet Hoang
//501108602

public class Shoes extends Product {

    private int[][] shoes = new int[2][5];


    // constructor
    public Shoes(String name, String id, double price, int pairs6Black, int pairs7Black, int pairs8Black, int pairs9Black, int pairs10Black,
                                                       int pairs6Brown, int pairs7Brown, int pairs8Brown, int pairs9Brown, int pairs10Brown) {

        super(name, id, price, 0, Category.SHOES);

        shoes[0][0] = pairs6Black;
        shoes[0][1] = pairs7Black;
        shoes[0][2] = pairs8Black;
        shoes[0][3] = pairs9Black;
        shoes[0][4] = pairs10Black;

        shoes[1][0] = pairs6Brown;
        shoes[1][1] = pairs7Brown;
        shoes[1][2] = pairs8Brown;
        shoes[1][3] = pairs9Brown;
        shoes[1][4] = pairs10Brown;
    }


    // Checks if the format is correct
    public boolean validOptions(String productOptions) {

        productOptions = productOptions.replace(",", "");

        if (productOptions.split("\\s+").length != 4) {

            return false;
        }
        String[] sizes = {"6", "7", "8", "9", "10"};
        String[] colors = {"black", "brown"};

        for (String size : sizes) {

            for (String color : colors) {

                if (size.equalsIgnoreCase(productOptions.split(" ")[1])
                        && color.equalsIgnoreCase(productOptions.split(" ")[3])) {

                    return true;
                }
            }
        }
        return false;
    }

    // Override getStockCount() in super class.
    public int getStockCount(String productOptions) {

        if (productOptions.equalsIgnoreCase("size 6 in black")) {
            return shoes[0][0];
        } else if (productOptions.equalsIgnoreCase("size 7 in black")) {
            return shoes[0][1];
        } else if (productOptions.equalsIgnoreCase("size 8 in black")) {
            return shoes[0][2];
        } else if (productOptions.equalsIgnoreCase("size 9 in black")) {
            return shoes[0][3];
        } else if (productOptions.equalsIgnoreCase("size 10 in black")) {
            return shoes[0][4];

        } else if (productOptions.equalsIgnoreCase("size 6 in brown")) {
            return shoes[1][0];
        } else if (productOptions.equalsIgnoreCase("size 7 in brown")) {
            return shoes[1][1];
        } else if (productOptions.equalsIgnoreCase("size 8 in brown")) {
            return shoes[1][2];
        } else if (productOptions.equalsIgnoreCase("size 9 in brown")) {
            return shoes[1][3];
        } else if (productOptions.equalsIgnoreCase("size 10 in brown")) {
            return shoes[1][4];
        }

        // this will realistically never reach here since in ECommerceSystem.java, it will always check if the
        // productOptions is valid first.
        // if the productOptions isn't on here then just return 0.
        return 0;
    }

    // Setters for setting the pairs of shoes (override setStockCount() in super class)
    public void setStockCount(int stockCount, String productOptions) {

        if (productOptions.equalsIgnoreCase("size 6 in black")) {
            shoes[0][0] = stockCount;
        } else if (productOptions.equalsIgnoreCase("size 7 in black")) {
            shoes[0][1] = stockCount;
        } else if (productOptions.equalsIgnoreCase("size 8 in black")) {
            shoes[0][2] = stockCount;
        } else if (productOptions.equalsIgnoreCase("size 9 in black")) {
            shoes[0][3] = stockCount;
        } else if (productOptions.equalsIgnoreCase("size 10 in black")) {
            shoes[0][4] = stockCount;

        } else if (productOptions.equalsIgnoreCase("size 6 in brown")) {
            shoes[1][0] = stockCount;
        } else if (productOptions.equalsIgnoreCase("size 7 in brown")) {
            shoes[1][1] = stockCount;
        } else if (productOptions.equalsIgnoreCase("size 8 in brown")) {
            shoes[1][2] = stockCount;
        } else if (productOptions.equalsIgnoreCase("size 9 in brown")) {
            shoes[1][3] = stockCount;
        } else if (productOptions.equalsIgnoreCase("size 10 in brown")) {
            shoes[1][4] = stockCount;
        }
    }

    // Used to reduce the amount of pairs for a certain size and color by 1.
    public void reduceStockCount(String productOptions) {

        if (productOptions.equalsIgnoreCase("size 6 in black")) {
            shoes[0][0]--;
        } else if (productOptions.equalsIgnoreCase("size 7 in black")) {
            shoes[0][1]--;
        } else if (productOptions.equalsIgnoreCase("size 8 in black")) {
            shoes[0][2]--;
        } else if (productOptions.equalsIgnoreCase("size 9 in black")) {
            shoes[0][3]--;
        } else if (productOptions.equalsIgnoreCase("size 10 in black")) {
            shoes[0][4]--;

        } else if (productOptions.equalsIgnoreCase("size 6 in brown")) {
            shoes[1][0]--;
        } else if (productOptions.equalsIgnoreCase("size 7 in brown")) {
            shoes[1][1]--;
        } else if (productOptions.equalsIgnoreCase("size 8 in brown")) {
            shoes[1][2]--;
        } else if (productOptions.equalsIgnoreCase("size 9 in brown")) {
            shoes[1][3]--;
        } else if (productOptions.equalsIgnoreCase("size 10 in brown")) {
            shoes[1][4]--;
        }
    }

    //printer that uses the super print().
    public void print() {

        super.print();
    }

}
