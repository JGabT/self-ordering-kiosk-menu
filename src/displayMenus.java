import java.util.HashMap;
import java.util.Map;

public class displayMenus {
    private Map<Integer, String> menuOptions;

    public displayMenus() {
        menuOptions = new HashMap<>();
        menuOptions.put(1, "Dine In");
        menuOptions.put(2, "Takeout");
    }

    public String DineTakeout(int option) {
        if (menuOptions.containsKey(option)) {
            return menuOptions.get(option);
        }
        return "Invalid option";
    }

    public int orderMenu(int option) {
        return (option >= 1 && option <= 5) ? option : -1;
    }

    public int orderSubmenu(int option, int quantity) {
        return (option >= 1 && option <= 5 && quantity > 0) ? option + quantity : -1;
    }

    public int cartMenu(int option, int quantity) {
        return (option >= 1 && option <= 5 && quantity > 0) ? (option + 10 * quantity) : -1;
    }

    public int checkoutMenu(int option) {
        return (option == 1) ? 4 : -1;
    }

    public int discountCode(int option) {
        return (option == 1) ? 5 : -1;
    }
}