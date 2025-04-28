package item.inventory;

import mock.MockBoots;
import mock.MockSword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleItemSlotTest {
    private SingleItemSlot<MockSword> slot;

    @BeforeEach
    void setUp() {
        slot = new SingleItemSlot<>(MockSword.class);
    }

    @Test
    void unstuckableSlotTest() {
        assertTrue(slot.canAccept(new MockSword(0, 0)));
        assertFalse(slot.canAccept(new MockBoots(0, 0)));
    }

    @Test
    void setAndRemoveItemTest() {
        MockSword sword = new MockSword(0, 0);
        slot.setItem(sword);
        assertEquals(sword, slot.getItem());
        assertEquals(sword, slot.removeItem());
        assertTrue(slot.isEmpty());
    }
}