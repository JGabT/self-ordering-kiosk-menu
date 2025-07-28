import java.util.*;

public class Cart {
    private Map<MenuItem, Integer> itemsInCart;

    public Cart() {
        itemsInCart = new HashMap<>();
    }

    public int addItem(MenuItem item, int quantity) {
        if (item != null && quantity > 0) {
            itemsInCart.put(item, itemsInCart.getOrDefault(item, 0) + quantity);
        }
        return itemsInCart.getOrDefault(item, 0);
    }

    public void manageCart(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Your Cart ===");
            displayCart();

            if (itemsInCart.isEmpty()) return;

            List<MenuItem> itemList = new ArrayList<>(itemsInCart.keySet());
            for (int i = 0; i < itemList.size(); i++) {
                MenuItem item = itemList.get(i);
                System.out.println((i + 1) + ". " + item.getName() + " (Qty: " + itemsInCart.get(item) + ")");
            }
            System.out.println("0. Go Back");
            System.out.print("Select item to modify (0 to go back): ");
            int itemChoice = scanner.nextInt();

            if (itemChoice == 0) break;
            if (itemChoice < 1 || itemChoice > itemList.size()) {
                System.out.println("Invalid selection.");
                continue;
            }

            MenuItem selectedItem = itemList.get(itemChoice - 1);
            System.out.println("1. Update Quantity");
            System.out.println("2. Remove Item");
            System.out.print("Choose action: ");
            int action = scanner.nextInt();

            switch (action) {
                case 1 -> {
                    System.out.print("Enter new quantity (0 to remove): ");
                    int newQty = scanner.nextInt();
                    updateQuantity(selectedItem, newQty);
                    System.out.println("Quantity updated.");
                }
                case 2 -> {
                    removeItem(selectedItem, Integer.MAX_VALUE); // remove completely
                    System.out.println("Item removed.");
                }
                default -> System.out.println("Invalid action.");
            }
        }
    }

    public int removeItem(MenuItem item, int quantity) {
        if (item != null && itemsInCart.containsKey(item) && quantity > 0) {
            int currentQuantity = itemsInCart.get(item);
            if (currentQuantity <= quantity) {
                itemsInCart.remove(item);
                return 0;
            } else {
                itemsInCart.put(item, currentQuantity - quantity);
                return currentQuantity - quantity;
            }
        }
        return 0;
    }

    public int updateQuantity(MenuItem item, int quantity) {
        if (item != null && quantity >= 0) {
            if (quantity == 0) {
                itemsInCart.remove(item);
            } else {
                itemsInCart.put(item, quantity);
            }
        }
        return itemsInCart.getOrDefault(item, 0);
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Map.Entry<MenuItem, Integer> entry : itemsInCart.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void displayCart() {
        if (itemsInCart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        for (Map.Entry<MenuItem, Integer> entry : itemsInCart.entrySet()) {
            MenuItem item = entry.getKey();
            String details = "";
            if (item instanceof Drink) {
                Drink drink = (Drink) item;
                details = "Size: " + drink.getSize() + ", Cold: " + drink.isCold();
            } else if (item instanceof Fries) {
                Fries fries = (Fries) item;
                details = "Size: " + fries.getSize();
            } else if (item instanceof Burger) {
                Burger burger = (Burger) item;
                details =  "Cheese: " + burger.hasCheese() + ", Bacon: " + burger.hasBacon();
            }
            System.out.println(item.getName() + " - Quantity: " + entry.getValue() + ", " + details + ", Price: " + item.getPrice());
        }
        System.out.println("Total Price: " + getTotalPrice());
    }

    public void clearCart() {
        itemsInCart.clear();
    }

    public Map<MenuItem, Integer> getItemsInCart() {
        return new HashMap<>(itemsInCart); // Return a copy to prevent external modification
    }
}