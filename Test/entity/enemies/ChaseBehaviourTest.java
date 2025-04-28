package entity.enemies;

import core.GameModel;
import entity.Player;
import map.levels.Level;
import map.tiles.Tile;
import mock.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static util.Constants.DIRECTION.*;

public class ChaseBehaviourTest {
    private ChaseBehaviour chaseBehaviour;
    private MockEnemy enemy;
    private MockPlayer player;
    private MockLevel level;

    @Before
    public void setUp() {
        chaseBehaviour = new ChaseBehaviour();
        enemy = new MockEnemy(100, 100, 32, 32);
        player = new MockPlayer(200, 100, 32, 32);
        level = new MockLevel() {
            @Override
            public Tile getTile(int tileIndex) {
                // Ensure no collision for movement tests
                return new MockTile(false);
            }
        };
        level.setPlayer(player);
        enemy.setOnGround(true);
    }

    @Test
    public void testChaseRightWhenPlayerIsToRight() {
        player.getHitBox().x = 200;
        enemy.getHitBox().x = 100;
        float initialX = enemy.getHitBox().x;

        chaseBehaviour.update(enemy, level, player);

        assertEquals(RIGHT, enemy.getDirection());
        assertTrue(enemy.getHitBox().x > initialX);
    }

    @Test
    public void testChaseLeftWhenPlayerIsToLeft() {
        player.getHitBox().x = 50;
        enemy.getHitBox().x = 100;
        float initialX = enemy.getHitBox().x;

        chaseBehaviour.update(enemy, level, player);

        assertEquals(LEFT, enemy.getDirection());
        assertTrue(enemy.getHitBox().x < initialX);
    }

    @Test
    public void testSwitchToAttackWhenCanAttackPlayer() {
        player.getHitBox().x = 110; // Close enough to attack
        enemy.getHitBox().x = 100;

        chaseBehaviour.update(enemy, level, player);

        assertTrue(enemy.getBehaviour() instanceof AttackBehaviour);
    }

    @Test
    public void testSwitchToPatrolWhenCannotSeePlayer() {
        // Use a modified enemy that can't see the player
        MockEnemy modifiedEnemy = new MockEnemy(100, 100, 32, 32) {
            @Override
            protected boolean canSeePlayer(Level level, Player player) {
                return false;
            }
        };
        modifiedEnemy.setOnGround(true);

        chaseBehaviour.update(modifiedEnemy, level, player);

        assertTrue(modifiedEnemy.getBehaviour() instanceof PatrolBehaviour);
    }

    @Test
    public void testApplyGravityWhenNotOnGround() {
        enemy.setOnGround(false);
        float initialY = enemy.getHitBox().y;

        chaseBehaviour.update(enemy, level, player);

        assertTrue(enemy.getHitBox().y > initialY);
    }

    @Test
    public void testSwitchToPatrolWhenPathBlocked() {
        MockLevel blockedLevel = new MockLevel() {
            @Override
            public Tile getTile(int tileIndex) {
                return new MockTile(true);
            }
        };
        blockedLevel.setPlayer(player);

        chaseBehaviour.update(enemy, blockedLevel, player);

        assertTrue(enemy.getBehaviour() instanceof PatrolBehaviour);
    }
}