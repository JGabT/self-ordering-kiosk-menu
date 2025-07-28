public class Burger implements MenuItem {
    private String name;
    private double price;
    private boolean hasCheese;
    private boolean hasBacon;

    public Burger(String name, double price, boolean hasCheese, boolean hasBacon) {
        this.name = name;
        this.price = (price >= 0) ? price : 0.0;
        this.hasCheese = hasCheese;
        this.hasBacon = hasBacon;
    }

    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }

    public boolean hasCheese() {
        return hasCheese;
    }

    public boolean hasBacon() {
        return hasBacon;
    }
}