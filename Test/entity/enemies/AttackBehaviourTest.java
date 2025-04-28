package entity.enemies;

import entity.Player;
import mock.MockEnemy;
import mock.MockLevel;
import mock.MockPlayer;
import mock.MockSword;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackBehaviourTest {

    private MockEnemy enemy;
    private MockLevel level;
    private MockPlayer player;
    private AttackBehaviour attackBehaviour;

    @BeforeEach
    void setUp() {
        enemy = new MockEnemy(96,96,96,96);
        level = new MockLevel();
        player = new MockPlayer(96,96,96,96);
        attackBehaviour = new AttackBehaviour();
    }

    @Test
    void testUpdate_TransitionToFreezeBehaviourAfterAttack() {
        enemy.setCanAttackPlayer(true);
        player.setReadyToTakeDamage(true);

        attackBehaviour.update(enemy, level, player);

        assertTrue(enemy.getBehaviour() instanceof FrezeBehaviour);
        assertFalse(enemy.isAttacking());
    }

    @Test
    void testUpdate_ToChaseBehaviourIfEnemyCannotAttackPlayer() {
        enemy.setCanAttackPlayer(false);
        player.setReadyToTakeDamage(false);

        attackBehaviour.update(enemy, level, player);

        assertTrue(enemy.getBehaviour() instanceof ChaseBehaviour);
    }

    @Test
    void testUpdate_PerformWeaponAttackIfAvailable() {
        MockSword sword = new MockSword(0,0);
        enemy.setCurrentWeapon(sword);
        enemy.setCanAttackPlayer(true);
        player.setReadyToTakeDamage(true);

        attackBehaviour.update(enemy, level, player);

        assertTrue(sword.isAttackPerformed());
    }

    @Test
    void testUpdate_DirectDamageIfNoWeapon() {
        enemy.setCurrentWeapon(null);
        enemy.setDamage(25);
        enemy.setCanAttackPlayer(true);
        player.setReadyToTakeDamage(true);

        attackBehaviour.update(enemy, level, player);

        assertEquals(45, player.getHp());
    }
}