package entity;

import item.weapon.Sword;
import mock.*;
import core.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Constants;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {
    private Player player;
    private MockLevel level;
    private MockEnemy enemy;
    private MockItemHandler itemHandler;

    @BeforeEach
    void setUp() {
        player = new Player(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        level = new MockLevel();
        enemy = new MockEnemy(96, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        itemHandler = new MockItemHandler();
    }

    @Test
    void jumpAndVelocityTest() {
        player.setOnGround(true);
        player.jump(true);
        assertEquals(-11, player.getVelocityY());
        assertFalse(player.isOnGround());
    }

    @Test
    void enemyTakeDamageTest() {
        player.setAttacking(true);
        player.setDirection(Constants.DIRECTION.RIGHT);
        player.attack(enemy);
        assertTrue(enemy.getHp() < enemy.getMAX_HP());
    }

    @Test
    void playerTakeDamageTest() {
        player.takeDamage(20);
        assertEquals(50, player.getHp());
    }

    @Test
    void armorProtectionTest() {
        player.getInventory().addItem(new MockBoots(0, 0));
        player.takeDamage(20);
        assertEquals(51, player.getHp());
    }

    @Test
    void testHeal() {
        player.setHp(50);
        player.heal(10);
        assertEquals(60, player.getHp());
    }

    @Test
    void playerGetKilledTest() {
        player.takeDamage(100);
        assertTrue(player.isDead());
        assertEquals(0, player.getXp());
    }

    @Test
    void setPositionTest() {
        player.setPosition(100, 200, level);
        assertEquals(100, player.getHitBox().x);
        assertEquals(200, player.getHitBox().y);
    }

    @Test
    void dropSelectedItemTest() {
        player.getInventory().addItem(new MockSword(0, 0));
        player.dropSelectedItem(itemHandler);
        assertTrue(player.getInventory().isSlotEmpty(1));
        assertEquals(1, itemHandler.getItems().size());
    }

    @Test
    void gainXPTest() {
        player.gainXP(50);
        assertEquals(50, player.getXp());
    }

    @Test
    void XPUpgradeTest() {
        player.getInventory().addItem(new MockSword(0, 0));
        player.gainXP(400);
        assertEquals(400, player.getXp());
        assertEquals(10, ((Sword) player.getInventory().getItemInSlot(1)).getDamage());
    }

    @Test
    void playerReadyToTakeDamageTest() {
        player.setInvulnerabilityFrames(10);
        assertFalse(player.readyToTakeDamage());
        for (int i = 0; i < 10; i++) player.update(level);
        assertTrue(player.readyToTakeDamage());
    }
}