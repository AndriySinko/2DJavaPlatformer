package item;

import map.levels.ParkourPhase;
import map.tiles.Tile;
import mock.MockItem;
import mock.MockLevel;
import mock.MockPlayer;
import mock.MockSword;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemHandlerTest {
    private ItemHandler handler;
    private MockPlayer player;
    private MockLevel level;

    @BeforeEach
    void setUp() {
        handler = new ItemHandler();
        player = new MockPlayer(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        level = new MockLevel();
    }

    @Test
    void pickUpItemTest() {
        handler.getItems().add(new MockSword(48, 48));
        handler.setPickUp(true);
        handler.update(player, new ParkourPhase("world/parkour_1.txt", new Tile[256]));
        assertTrue(handler.getItems().isEmpty());
        assertNotNull(player.getInventory().getItemInSlot(1));
    }

    @Test
    void dropItemOnMapTest() {
        MockItem item = new MockItem(48, 48);
        handler.addItemToMap(item);
        assertEquals(1, handler.getItems().size());
        assertFalse(item.isPickedUp());
    }
}