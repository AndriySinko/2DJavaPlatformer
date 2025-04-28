package entity.enemies;

import entity.Player;
import map.levels.Level;
import mock.MockEnemy;
import mock.MockLevel;
import mock.MockPlayer;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.DIRECTION.*;

class PatrolBehaviourTest {
    private PatrolBehaviour behaviour;
    private MockEnemy enemy;
    private MockLevel level;
    private MockPlayer player;

    @BeforeEach
    void setUp() {
        behaviour = new PatrolBehaviour();
        enemy = new MockEnemy(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        level = new MockLevel();
        player = new MockPlayer(96, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        enemy.setBehaviour(behaviour);
        enemy.setSpeed(2.0f);
        enemy.setOnGround(true);
        enemy.setLeft(true);
        enemy.setDirection(LEFT);
        for (int y = 0; y < level.getLvlData().length; y++) {
            for (int x = 0; x < level.getLvlData()[0].length; x++) {
                level.getLvlData()[y][x] = 50;
            }
        }
    }

    @Test
    void testMoveLeftNoCollision() {
        behaviour.update(enemy, level, player);
        assertEquals(46.6f, enemy.getX(), 0.1);
        assertEquals(LEFT, enemy.getDirection());
    }

    @Test
    void testMoveRightNoCollision() {
        enemy.setLeft(false);
        enemy.setRight(true);
        enemy.setDirection(RIGHT);
        behaviour.update(enemy, level, player);
        assertEquals(49.4f, enemy.getX(), 0.1);
        assertEquals(RIGHT, enemy.getDirection());
    }

    @Test
    void testChangeDirectionWallBlocked() {
        level.getLvlData()[1][1] = 0;
        behaviour.update(enemy, level, player);
        assertEquals(RIGHT, enemy.getDirection());
        assertTrue(enemy.isRight());
        assertFalse(enemy.isLeft());
        assertEquals(48.0f, enemy.getX());
    }

    @Test
    void testChangeDirectionNoFloor() {
        level.getLvlData()[2][1] = 50;
        behaviour.update(enemy, level, player);
        assertEquals(RIGHT, enemy.getDirection());
        assertTrue(enemy.isRight());
        assertFalse(enemy.isLeft());
        assertEquals(48.0f, enemy.getX());
    }

    @Test
    void testApplyGravityNotOnGround() {
        enemy.setOnGround(false);
        enemy.setVelocityY(0.0f);
        behaviour.update(enemy, level, player);
        assertEquals(0.5f, enemy.getVelocityY(), 0.001);
        assertFalse(enemy.isOnGround());
    }

    @Test
    void testTransitionToChaseBehaviour() {
        MockEnemy chaseEnemy = new MockEnemy(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE) {
            @Override
            protected boolean canSeePlayer(Level level, Player player) {
                return true;
            }
            @Override
            protected boolean canAttackPlayer(Player player) {
                return false;
            }
        };
        chaseEnemy.setBehaviour(behaviour);
        chaseEnemy.setSpeed(2.0f);
        chaseEnemy.setOnGround(true);
        chaseEnemy.setLeft(true);
        chaseEnemy.setDirection(LEFT);
        behaviour.update(chaseEnemy, level, player);
        assertTrue(chaseEnemy.getBehaviour() instanceof ChaseBehaviour);
    }
}