package entity.enemies;

import mock.MockEnemy;
import mock.MockLevel;
import mock.MockPlayer;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EnemyTest {
    private MockEnemy enemy;
    private MockLevel level;
    private MockPlayer player;

    @BeforeEach
    void setUp() {
        enemy = new MockEnemy(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        level = new MockLevel();
        player = new MockPlayer(96, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
    }

    @Test
    void invulnerabilityTest() {
        enemy.setInvulnerabilityFrames(10);
        enemy.update(level, player);
        assertEquals(9, enemy.getInvulnerabilityFrames());
    }

    @Test
    void setActiveTest() {
        enemy.setActive(false);
        assertFalse(enemy.isActive());
    }
}