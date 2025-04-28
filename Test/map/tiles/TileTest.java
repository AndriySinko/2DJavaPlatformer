package map.tiles;

import static org.junit.jupiter.api.Assertions.*;


import mock.MockLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TileTest {
    private Tile tile;

    @BeforeEach
    void setUp() {
        tile = new Tile();
    }

    @Test
    void setCollisionTest() {
        tile.setCollision(true);
        assertTrue(tile.isCollision());
    }

    @Test
    void executeActionTest() {
        MockLevel level = new MockLevel();
        tile.setAction(new ObstacleAction());
        tile.executeAction(level, 0, 0);
        assertTrue(level.getPlayer().isDead());
    }
}