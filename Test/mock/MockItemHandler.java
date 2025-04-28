package mock;

import item.Item;
import item.ItemHandler;
import map.levels.Level;
import entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MockItemHandler extends ItemHandler {
    private List<Item> items;

    public MockItemHandler() {
        this.items = new ArrayList<>();
    }

    @Override
    public void addItemToMap(Item item) {
        items.add(item);
    }

    @Override
    public List<Item> getItems() {
        return items;
    }

    @Override
    public void update(Player player, Level currentPhase) {
        for (Item item : items) {
            if (!item.isPickedUp() && player.getHitBox().intersects(item.getHitBox())) {
                player.getInventory().addItem(item);
                item.setPickedUp(true);
            }
        }
    }

    @Override
    public void setPickUp(boolean pickUp) {
    }
}