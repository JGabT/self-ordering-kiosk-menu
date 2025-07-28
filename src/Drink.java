public class Drink implements MenuItem {
    private String name;
    private String size;
    private double price;
    private boolean isCold;

    public Drink(String name, String size, double price, boolean isCold) {
        this.name = name;
        this.size = size;
        this.price = (price >= 0) ? price : 0.0;
        this.isCold = isCold;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public boolean isCold() {
        return isCold;
    }
}