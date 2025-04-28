package item.weapon;

import item.Item;
import mock.*;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.DIRECTION.LEFT;

class SwordTest {
    private MockCollisionChecker collisionChecker;
    private MockLevel level;
    private MockPlayer player;
    private MockEnemy enemy;

    @BeforeEach
    void setUp() {
        collisionChecker = new MockCollisionChecker();
        level = new MockLevel();
        player = new MockPlayer(100, 100, 32, 32);
        enemy = new MockEnemy(150, 100, 32, 32);
    }

    @Test
    void testCollidingWithTilesWhenShouldCollide() {
        collisionChecker.setShouldCollideWithTiles(true);
        Rectangle2D.Float hitBox = new Rectangle2D.Float(100, 100, 32, 32);
        assertTrue(collisionChecker.isCollidingWithTiles(hitBox, level));
    }

    @Test
    void testNotCollidingWithTilesWhenShouldNotCollide() {
        collisionChecker.setShouldCollideWithTiles(false);
        Rectangle2D.Float hitBox = new Rectangle2D.Float(100, 100, 32, 32);
        assertFalse(collisionChecker.isCollidingWithTiles(hitBox, level));
    }

    @Test
    void testClearLineOfSightWhenNotBlocked() {
        collisionChecker.setShouldBlockSight(false);
        Rectangle2D.Float from = new Rectangle2D.Float(100, 100, 32, 32);
        Rectangle2D.Float to = new Rectangle2D.Float(150, 100, 32, 32);
        assertTrue(collisionChecker.isSightClear(level, from, to, 1));
    }

    @Test
    void testBlockedLineOfSightWhenShouldBlock() {
        collisionChecker.setShouldBlockSight(true);
        Rectangle2D.Float from = new Rectangle2D.Float(100, 100, 32, 32);
        Rectangle2D.Float to = new Rectangle2D.Float(150, 100, 32, 32);
        assertFalse(collisionChecker.isSightClear(level, from, to, 1));
    }

    @Test
    void testPlayerPushesEnemyRight() {
        player.setRight(true);
        float initialX = enemy.getX();
        collisionChecker.handlePlayerCharacterCollision(player, enemy);
        assertEquals(initialX + 2, enemy.getX());
    }

    @Test
    void testPlayerPushesEnemyLeft() {
        player.setLeft(true);
        float initialX = enemy.getX();
        collisionChecker.handlePlayerCharacterCollision(player, enemy);
        assertEquals(initialX - 2, enemy.getX());
    }

    @Test
    void testNoPushWhenShouldNotPush() {
        collisionChecker.setShouldPushCharacter(false);
        player.setRight(true);
        float initialX = enemy.getX();
        collisionChecker.handlePlayerCharacterCollision(player, enemy);
        assertEquals(initialX, enemy.getX());
    }

    @Test
    void testNoMovementWhenPlayerNotMoving() {
        player.setRight(false);
        player.setLeft(false);
        float initialX = enemy.getX();
        collisionChecker.handlePlayerCharacterCollision(player, enemy);
        assertEquals(initialX, enemy.getX());
    }
}