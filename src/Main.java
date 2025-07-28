import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart();
        Payment payment = new Payment(0.0);
        displayMenus menus = new displayMenus();
        int discountPercent = 0; // Track discount separately

        // Predefined menu items
        List<MenuItem> menuItems = Arrays.asList(
                new Drink("McWater", "Small", 15.00, false),
                new Drink("Coca Cola", "Small", 15.00, true),
                new Drink("Juice", "Small", 15.00, true),
                new Fries("Jolly Fries", "Small", 20.00),
                new Fries("Twister Fries", "Small", 25.00),
                new Fries("Cheese Fries", "Small", 40.00),
                new Burger("YumBurger",  50.00, false, false),
                new Burger("Cheese Bacon Burger",  60.00, true, false),
                new Burger("Crispy Cheesy Bacon Burger",  75.00, false, true)
        );

        menuRepository menuRepo = new menuRepository(menuItems);

        while (true) {
            cart.clearCart(); // Clear cart at the start of every new order

            System.out.println("\nWelcome to the Self-Ordering Kiosk Menu System!");
            System.out.print("1. Takeout\n2. Dine In\nChoose (1 or 2): ");
            int dineType = scanner.nextInt();
            scanner.nextLine();
            System.out.println("\nYou selected " + menus.DineTakeout(dineType) + ".\n");

            boolean ordering = true;
            while (ordering) {
                System.out.println("\n=== Order Menu ===\n1. Drinks\n2. Sides\n3. Burgers\n4. View Cart\n5. Apply Discount\n6. Checkout");
                System.out.print("Choose (1-6): ");
                int choice = scanner.nextInt();

                int validated = menus.orderMenu(choice);
                if (validated == -1) {
                    System.out.println("Invalid menu choice. Try again.");
                    continue;
                }

                switch (choice) {
                    case 1 -> displaySubmenu(scanner, cart, menuRepo, "Drinks", 0, 2, menus);
                    case 2 -> displaySubmenu(scanner, cart, menuRepo, "Sides", 3, 5, menus);
                    case 3 -> displaySubmenu(scanner, cart, menuRepo, "Burgers", 6, 8, menus);
                    case 4 -> cart.manageCart(scanner);
                    case 5 -> {
                        scanner.nextLine(); // consume newline
                        System.out.println("Valid Discount Codes: student10, pwd, senior");
                        System.out.print("Enter discount code: ");
                        String code = scanner.nextLine();

                        if (code.equalsIgnoreCase("student10")) {
                            discountPercent = 10;
                            System.out.println("10% student discount applied!\n");
                        } else if (code.equalsIgnoreCase("pwd") || code.equalsIgnoreCase("senior")) {
                            discountPercent = 20;
                            System.out.println("20% discount applied for " + code + "!\n");
                        } else {
                            System.out.println("Invalid discount code. No discount applied.\n");
                        }
                    }
                    case 6 -> {
                        if (cart.getItemsInCart().isEmpty()) {
                            System.out.println("\nYour cart is empty. Please add items before checking out.");
                        } else {
                            checkout(scanner, cart, payment, dineType, discountPercent);
                            ordering = false;
                        }
                    }
                }
            }

            System.out.print("Place another order? (y/n): ");
            if (!scanner.next().equalsIgnoreCase("y")) {
                System.out.println("Thank you for visiting!");
                break;
            }
        }
        scanner.close();
    }

    private static void displaySubmenu(Scanner scanner, Cart cart, menuRepository repo, String title, int start, int end, displayMenus menus) {
        while (true) {
            System.out.println("\n=== " + title + " ===");
            List<MenuItem> items = repo.getMenuItems();
            for (int i = start; i <= end; i++) {
                MenuItem item = items.get(i);
                System.out.println((i - start + 1) + ". " + item.getName() + " - ₱" + item.getPrice());
            }

            System.out.print("Choose item (0 to go back): ");
            int choice = scanner.nextInt();
            if (choice == 0) break;
            if (choice >= 1 && choice <= (end - start + 1)) {
                MenuItem baseItem = items.get(start + choice - 1);
                System.out.print("Quantity: ");
                int qty = scanner.nextInt();

                int validated = menus.orderSubmenu(choice, qty);
                if (validated == -1) {
                    System.out.println("Invalid selection. Try again.");
                    continue;
                }

                if (baseItem instanceof Drink) {
                    System.out.println("Available sizes and prices: Small - ₱15.00, Medium - ₱20.00, Large - ₱25.00");
                    System.out.print("Select size (Small/Medium/Large): ");
                    scanner.nextLine();
                    String size = scanner.nextLine();
                    double price = switch (size.toLowerCase()) {
                        case "small" -> 15.00;
                        case "medium" -> 20.00;
                        case "large" -> 25.00;
                        default -> ((Drink) baseItem).getPrice();
                    };
                    System.out.print("Is it cold? (yes/no): ");
                    String coldInput = scanner.nextLine();
                    boolean isCold = coldInput.equalsIgnoreCase("yes");
                    Drink drink = new Drink(baseItem.getName(), size, price, isCold);
                    cart.addItem(drink, qty);
                } else if (baseItem instanceof Fries) {
                    System.out.println("Available sizes and prices: Small - ₱25.00, Medium - ₱40.00, Large - ₱45.00");
                    System.out.print("Select size (Small/Medium/Large): ");
                    scanner.nextLine();
                    String size = scanner.nextLine();
                    double price = switch (size.toLowerCase()) {
                        case "small" -> 25.00;
                        case "medium" -> 40.00;
                        case "large" -> 45.00;
                        default -> ((Fries) baseItem).getPrice();
                    };
                    Fries fries = new Fries(baseItem.getName(), size, price);
                    cart.addItem(fries, qty);
                } else if (baseItem instanceof Burger) {
                    scanner.nextLine();
                    double price = ((Burger) baseItem).getPrice();
                    System.out.print("Add cheese? (yes/no): ");
                    String cheeseInput = scanner.nextLine();
                    boolean hasCheese = cheeseInput.equalsIgnoreCase("yes");
                    System.out.print("Add bacon? (yes/no): ");
                    String baconInput = scanner.nextLine();
                    boolean hasBacon = baconInput.equalsIgnoreCase("yes");
                    Burger burger = new Burger(baseItem.getName(), price, hasCheese, hasBacon);
                    cart.addItem(burger, qty);
                } else {
                    cart.addItem(baseItem, qty);
                }

                System.out.println(qty + " " + baseItem.getName() + "(s) added.\n");
            }
        }
    }

    private static void checkout(Scanner scanner, Cart cart, Payment payment, int dineType, int discountPercent) {
        System.out.println("\n=== Checkout ===");
        cart.displayCart();

        double subtotal = cart.getTotalPrice();
        double total = subtotal * (1 - (discountPercent / 100.0));
        double discount = subtotal - total;

        System.out.println("Subtotal: ₱" + subtotal);
        if (discount > 0) {
            System.out.println("Discount: -₱" + discount);
        }
        System.out.println("Total: ₱" + total);

        boolean paid = false;
        int method;
        do {
            System.out.print("Payment method (1. Cash, 2. GCash): ");
            method = scanner.nextInt();
            scanner.nextLine();

            if (method == 1) {
                paid = payment.counterPayment();
            } else if (method == 2) {
                System.out.print("Please enter your phone number: ");
                String phoneNumber = scanner.nextLine();

                while (!paid) {
                    System.out.print("Please enter your 6-digit MPIN: ");
                    String mpin = scanner.nextLine();
                    if (mpin.matches("\\d{6}")) {
                        System.out.println("\nGCash payment successful for " + phoneNumber + ".");
                        paid = true;
                    } else {
                        System.out.println("Invalid MPIN. Try again.");
                    }
                }
            } else {
                System.out.println("Invalid payment method.");
            }
        } while (!paid && (method != 1 && method != 2));

        if (paid) {
            System.out.println("\n=== Receipt ===");
            System.out.println("Items:");
            cart.displayCart();
            System.out.println("Total Paid: ₱" + total);
            System.out.println("Payment Method: " + (method == 1 ? "Cash" : "GCash"));
            System.out.println("Order Type: " + (new displayMenus()).DineTakeout(dineType));
            System.out.println("Thank you for your order!\n");
            cart.clearCart();
        }
    }
}