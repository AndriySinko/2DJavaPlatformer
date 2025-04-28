package item.inventory;

import item.Item;

public interface InventorySlot <T extends Item>{
    boolean canAccept(Item item);
    boolean isEmpty();
    T getItem();
    void setItem(T item);
    Item removeItem();
}
