import java.util.List;
import java.util.ArrayList;
public class menuRepository {
    private List<MenuItem> allMenuItems;

    public menuRepository(List<MenuItem> allMenuItems) {
        this.allMenuItems = allMenuItems;
    }

    public List<MenuItem> getMenuItems() {
        return new ArrayList<>(allMenuItems);
    }

    public MenuItem getItem(String name) {
        for (MenuItem item : allMenuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}