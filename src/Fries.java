public class Fries implements MenuItem {
    private String name;
    private String size;
    private double price;

    public Fries(String name, String size, double price) {
        this.name = name;
        this.size = size;
        this.price = price;
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
}
