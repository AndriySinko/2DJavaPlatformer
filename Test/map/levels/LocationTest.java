package map.levels;


import map.tiles.Tile;
import mock.MockPlayer;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    private Location location;
    private MockPlayer player;
    private LocationConfig config;

    @BeforeEach
    void setUp() {
        config = new LocationConfig("tiles/world_tileset_v2.png", 256, new int[]{0, 1}, Arrays.asList("world/parkour_1.txt"), Arrays.asList("world/battle_1.txt"), Arrays.asList("world/enemy_1.txt"));
        location = new Location(config);
        player = new MockPlayer(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
    }

    @Test
    void enterParkourPhaseTest() {
        location.enter(player);
        assertTrue(location.getCurrentPhase() instanceof ParkourPhase);
        assertTrue(location.isEntered());
    }

    @Test
    void switchToBattlePhaseTest() {
        location.enter(player);
        location.getCurrentPhase().complete();
        location.update(player);
        assertTrue(location.getCurrentPhase() instanceof BattlePhase);
    }

    @Test
    void getTileFromLocationTest() {
        location.enter(player);
        Tile tile = location.getTile(0);
        assertNotNull(tile);
        assertTrue(tile.isCollision());
    }
}