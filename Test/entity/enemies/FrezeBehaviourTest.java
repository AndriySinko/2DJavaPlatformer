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

class FrezeBehaviourTest {
    private FrezeBehaviour behaviour;
    private MockEnemy enemy;
    private MockLevel level;
    private MockPlayer player;

    @BeforeEach
    void setUp() {
        behaviour = new FrezeBehaviour(30);
        enemy = new MockEnemy(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        level = new MockLevel();
        player = new MockPlayer(96, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        enemy.setBehaviour(behaviour);
    }

    @Test
    void testFreezeDurationNotElapsed() {
        for (int i = 0; i < 29; i++) {
            behaviour.update(enemy, level, player);
        }
        assertTrue(enemy.getBehaviour() instanceof FrezeBehaviour);
    }

    @Test
    void testTransitionToAttackBehaviour() {
        MockEnemy attackableEnemy = new MockEnemy(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE) {
            protected boolean canAttackPlayer(Player player) {
                return true;
            }
        };
        attackableEnemy.setBehaviour(behaviour);
        for (int i = 0; i < 30; i++) {
            behaviour.update(attackableEnemy, level, player);
        }
        assertTrue(attackableEnemy.getBehaviour() instanceof AttackBehaviour);
    }

    @Test
    void testTransitionToChaseBehaviour() {
        MockEnemy chaseableEnemy = new MockEnemy(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE) {

            protected boolean canAttackPlayer(Player player) {
                return false;
            }
            protected boolean canSeePlayer(Level level, Player player) {
                return true;
            }
        };
        chaseableEnemy.setBehaviour(behaviour);
        for (int i = 0; i < 30; i++) {
            behaviour.update(chaseableEnemy, level, player);
        }
        assertTrue(chaseableEnemy.getBehaviour() instanceof ChaseBehaviour);
    }

    @Test
    void testTransitionToPatrolBehaviour() {
        MockEnemy patrolEnemy = new MockEnemy(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE) {
            @Override
            protected boolean canAttackPlayer(Player player) {
                return false;
            }
            @Override
            protected boolean canSeePlayer(Level level, Player player) {
                return false;
            }
        };
        patrolEnemy.setBehaviour(behaviour);
        for (int i = 0; i < 30; i++) {
            behaviour.update(patrolEnemy, level, player);
        }
        assertTrue(patrolEnemy.getBehaviour() instanceof PatrolBehaviour);
    }
}