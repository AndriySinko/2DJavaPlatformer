package entity.enemies;

import mock.MockLevel;
import mock.MockPlayer;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DudeMonsterTest {
    private DudeMonster monster;
    private MockLevel level;
    private MockPlayer player;

    @BeforeEach
    void setUp() {
        monster = new DudeMonster(48, 48);
        level = new MockLevel();
        player = new MockPlayer(96, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
    }

    @Test
    void cnSeePlayerTest() {
        assertTrue(monster.canSeePlayer(level, player));
    }

    @Test
    void canAttackPlayerTest() {
        player.setPosition(60, 48, level);
        assertTrue(monster.canAttackPlayer(player));
    }

    @Test
    void testTakeDamageReducesHp() {
        monster.takeDamage(20);
        assertEquals(30, monster.getHp());
    }

    @Test
    void switchToChaseBehaviourTest() {
        monster.takeDamage(10);
        assertTrue(monster.getBehaviour() instanceof ChaseBehaviour);
    }

    @Test
    void enemyGetKilledTest() {
        monster.takeDamage(50);
        assertFalse(monster.isActive());
    }

    @Test
    void enemyXPReturnValueTest() {
        assertEquals(30, monster.getXPReward());
    }
}