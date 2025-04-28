package item;

import entity.Player;
import map.levels.BattlePhase;
import map.levels.Level;
import map.levels.ParkourPhase;
import util.LoadData;


import java.util.List;

import static util.LoadData.loadItems;

/**
 * Handles the management and interactions of items on specific level.
 */
public class ItemHandler {
    private List<Item> items;
    private boolean pickUp;

    /**
     * Constructs an instance of ItemHandler and initializes the item list for the game.
     */
    public ItemHandler() {
        this.items = loadItems(LoadData.LEVEL_1_ITEM);
    }

    /**
     * Updates the state of items on current level or phase of the game.
     * This method checks if the player can interact with items during the specified phase,
     * handles item pickups, and removes items that have been collected.
     *
     * @param player the player object interacting with the items.
     *               Used to determine if an item can be picked up and applied to the player.
     * @param currentPhase the current level or phase of the game.
     *                     Items are updated only if the current phase is an instance of ParkourPhase.
     */
    public void update(Player player, Level currentPhase) {
        if (currentPhase instanceof ParkourPhase) {
            if (pickUp) {
                for (Item item : items) {
                    if (!item.isPickedUp() && player.getHitBox().intersects(item.getHitBox())) {
                        item.use(player);
                    }
                }
            }
            items.removeIf(Item::isPickedUp);
            pickUp = false;
        }
    }

    /**
     * Adds an item to the game map and sets its state as not picked up.
     *
     * @param item the item to be added to the game map. Its state is updated
     *             to indicate that it has not been picked up and is ready for interaction.
     */
    public void addItemToMap(Item item) {
        item.setPickedUp(false);
        items.add(item);
    }

    public void setPickUp(boolean pickUp) {
        this.pickUp = pickUp;
    }

    public List<Item> getItems() {
        return items;
    }
}
