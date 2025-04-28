package item.inventory;

import item.Item;
import item.armor.*;
import item.weapon.Weapon;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an inventory system
 *
 * The inventory includes predefined slots for specific item categories:
 * Slot 1: Weapon
 * Slot 2: Helmet
 * Slot 3: Chestplate
 * Slot 4: Leggings
 * Slot 5: Boots
 * Slot 6: Shield
 *
 */
public class Inventory {
    private final Map<Integer, InventorySlot<? extends Item>> slots = new HashMap<>();
    private int selectedSlot = 1;

    /**
     * Constructs an instance of the Inventory class.
     * Initializes predefined slots in the inventory, each associated with a specific item type.
     */
    public Inventory() {
        slots.put(1, new SingleItemSlot<>(Weapon.class));
        slots.put(2, new SingleItemSlot<>(Helmet.class));
        slots.put(3, new SingleItemSlot<>(Chestplate.class));
        slots.put(4, new SingleItemSlot<>(Leggings.class));
        slots.put(5, new SingleItemSlot<>(Boots.class));
        slots.put(6, new SingleItemSlot<>(Shield.class));
    }

    /**
     * Attempts to add an item to the first inventory slot that can accept the item, by iterating through all available slots.
     *
     * @param item the item to be added to the inventory
     * @return true if the item is successfully added to a compatible slot, false otherwise
     */
    public boolean addItem(Item item) {
        for (InventorySlot<?> slot : slots.values()) {
            if (slot.canAccept(item)) {
                ((InventorySlot<Item>) slot).setItem(item);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the specified item from the inventory if it exists.
     * Method checks for item existence by iterating through all slots.
     *
     * @param itemToRemove the item to be removed from the inventory; if null, the method does nothing
     */
    public void removeItem(Item itemToRemove) {
        if (itemToRemove == null) return;

        for (InventorySlot<?> slot : slots.values()) {
            if (itemToRemove.equals(slot.getItem())) {
                slot.removeItem();
                return;
            }
        }
    }

    /**
     * Selects the specified inventory slot.
     *
     * @param slotNumber the number of the slot to be selected; must exist within the inventory slots
     */
    public void selectSlot(int slotNumber) {
        if (slots.containsKey(slotNumber)) {
            selectedSlot = slotNumber;
        }
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    /**
     * Removes the item from the currently selected slot
     *
     * @return the removed item from the currently selected slot, or null if the
     *         slot is either not selected or empty
     */
    public Item removeSelectedItem() {
        InventorySlot<?> slot = slots.get(selectedSlot);
        return slot != null ? slot.removeItem() : null;
    }

    /**
     * Retrieves item from currently selected slot.
     *
     * @return the item in the currently selected inventory slot, or null if the slot is empty or invalid
     */
    public Item getSelectedItem() {
        InventorySlot<?> slot = slots.get(selectedSlot);
        return slot != null ? slot.getItem() : null;
    }

    /**
     * Retrieves the item stored in the specified inventory slot.
     *
     * @param slotNumber the number of the slot to query; must correspond to an existing slot
     * @return the item in the specified slot, or null if the slot is empty or does not exist
     */
    public Item getItemInSlot(int slotNumber) {
        InventorySlot<?> slot = slots.get(slotNumber);
        return slot != null ? slot.getItem() : null;
    }

    /**
     * Checks whether the specified inventory slot is empty.
     *
     * @param slotNumber the index of the inventory slot to check; must correspond to an existing slot in the inventory
     * @return true if the slot is either null or empty, false otherwise
     */
    public boolean isSlotEmpty(int slotNumber) {
        InventorySlot<?> slot = slots.get(slotNumber);
        return slot == null || slot.isEmpty();
    }

    public Map<Integer, InventorySlot<? extends Item>> getSlots() {
        return slots;
    }
}