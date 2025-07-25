public class Burger implements MenuItem {
    private String name;
    private String size;
    private double price;
    private boolean hasCheese;
    private boolean hasBacon;

    public Burger(String name, String size, double price, boolean hasCheese, boolean hasBacon) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.hasCheese = hasCheese;
        this.hasBacon = hasBacon;
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

    @Override
    public String getDetails() {
        return "";
    }

    public boolean getCheese() {
        return hasCheese;
    }

    public boolean getBacon() {
        return hasBacon;
    }
}
