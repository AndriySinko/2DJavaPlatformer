package map.levels;

import map.tiles.Tile;
import mock.MockPlayer;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattlePhaseTest {
    private BattlePhase phase;
    private MockPlayer player;
    private Tile[] tiles;

    @BeforeEach
    void setUp() {
        tiles = new Tile[256];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
            tiles[i].setCollision(i < 50);
        }
        phase = new BattlePhase("world/battle_1.txt", "world/enemy_1.txt", tiles);
        player = new MockPlayer(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
    }

    @Test
    void startPointTest() {
        phase.setStartPoint(48, 48);
        phase.start(player);
        assertEquals(48, player.getHitBox().x);
    }

    @Test
    void killAllEnemyAndCompleteLvlTest() {
        phase.start(player);
        player.setAttacking(true);
        phase.update();
        assertTrue(phase.getEnemies().stream().allMatch(enemy -> !enemy.isActive()));
        assertEquals(110, player.getXp());
    }

    @Test
    void respawnTest() {
        phase.setPlayer(player);
        player.setDead(true);
        phase.respawnPlayer();
        assertFalse(player.isDead());
        assertEquals(player.getMAX_HP(), player.getHp());
    }
}