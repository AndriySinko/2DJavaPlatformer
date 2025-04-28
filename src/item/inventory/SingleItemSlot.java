package item.inventory;

import item.Item;

/**
 * Represents a single-slot container for items of a specific type.
 *
 * @param <T> The type of item this slot accepts, which must implement {@code Item} interface.
 */
public class SingleItemSlot<T extends Item> implements InventorySlot<T> {
    private T item;
    private final Class<T> acceptedType;

    public SingleItemSlot(Class<T> acceptedType) {
        this.acceptedType = acceptedType;
    }

    /**
     * Determines whether the specified item can be accepted by this slot.
     *
     * @param item the item to be checked for compatibility with the slot
     * @return true if the item is of the accepted type and the slot is empty, false otherwise
     */
    @Override
    public boolean canAccept(Item item) {
        return acceptedType.isInstance(item) && this.item == null;
    }

    @Override
    public boolean isEmpty() {
        return item == null;
    }

    @Override
    public T getItem() {
        return item;
    }

    @Override
    public void setItem(T item) {
        this.item = item;
    }

    @Override
    public Item removeItem() {
        Item removed = item;
        item = null;
        return removed;
    }
}