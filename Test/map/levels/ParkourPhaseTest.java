package map.levels;


import map.tiles.Tile;
import mock.MockPlayer;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkourPhaseTest {
    private ParkourPhase phase;
    private MockPlayer player;
    private Tile[] tiles;

    @BeforeEach
    void setUp() {
        tiles = new Tile[256];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
            tiles[i].setCollision(i < 50);
        }
        phase = new ParkourPhase("world/parkour_1.txt", tiles);
        player = new MockPlayer(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
    }

    @Test
    void startPointTest() {
        phase.setStartPoint(48, 48);
        phase.start(player);
        assertEquals(48, player.getHitBox().x);
    }

    @Test
    void getXPForCompletingLvLTest() {
        phase.setPlayer(player);
        phase.complete();
        assertTrue(phase.isCompleted());
        assertEquals(100, player.getXp());
    }

    @Test
    void respawnOnCheckpointTest() {
        phase.setCheckpoint(2, 3);
        phase.setPlayer(player);
        player.setDead(true);
        phase.respawnPlayer();
        assertEquals(96, player.getHitBox().x);
        assertFalse(player.isDead());
    }
}