public class Payment {
    private double originalAmount;
    private double discountedAmount;

    public Payment(double amount) {
        this.originalAmount = amount;
        this.discountedAmount = amount;
    }

    public void discount(double code) {
        if (code > 0 && code <= 100) {
            this.discountedAmount = originalAmount * (1 - (code / 100.0));
        }
    }

    public double calculatePrice() {
        return discountedAmount;
    }

    public boolean counterPayment() {
        System.out.println("Counter Payment");
        return true;
    }

    public boolean gcashPayment() {
        System.out.println("GCash Payment");
        return false;
    }
}
