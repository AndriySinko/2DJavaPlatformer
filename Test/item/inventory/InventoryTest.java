package item.inventory;

import item.Item;
import mock.MockBoots;
import mock.MockSword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void addWeaponToSlotTest() {
        MockSword sword = new MockSword(0, 0);
        assertTrue(inventory.addItem(sword));
        assertEquals(sword, inventory.getItemInSlot(1));
    }

    @Test
    void addArmorToSlot() {
        MockBoots boots = new MockBoots(0, 0);
        assertTrue(inventory.addItem(boots));
        assertEquals(boots, inventory.getItemInSlot(5));
    }

    @Test
    void removeItemFromSlotTest() {
        MockSword sword = new MockSword(0, 0);
        inventory.addItem(sword);
        inventory.removeItem(sword);
        assertTrue(inventory.isSlotEmpty(1));
    }

    @Test
    void selectSlotTest() {
        inventory.selectSlot(3);
        assertEquals(3, inventory.getSelectedSlot());
    }

    @Test
    void removeSelectedItemTest() {
        MockSword sword = new MockSword(0, 0);
        inventory.addItem(sword);
        Item removed = inventory.removeSelectedItem();
        assertEquals(sword, removed);
        assertTrue(inventory.isSlotEmpty(1));
    }
}