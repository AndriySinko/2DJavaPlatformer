package item.armor;

import mock.MockBoots;
import mock.MockPlayer;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BootsTest {
    private MockBoots boots;
    private MockPlayer player;

    @BeforeEach
    void setUp() {
        boots = new MockBoots(48, 48);
        player = new MockPlayer(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
    }

    @Test
    void armorAddsToInventoryTest() {
        boots.use(player);
        assertEquals(boots, player.getInventory().getItemInSlot(5));
        assertTrue(boots.isPickedUp());
    }

    @Test
    void protectionTest() {
        player.getInventory().addItem(boots);
        int protection = boots.protect(player);
        assertEquals(1, protection);
        assertEquals(4, boots.getDurability());
    }
}