package entity;

import mock.MockCharacter;
import mock.MockLevel;
import core.GameModel;
import item.weapon.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.DIRECTION.*;

class CharacterTest {
    private MockCharacter character;
    private MockLevel level;

    @BeforeEach
    void setUp() {
        character = new MockCharacter(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        level = new MockLevel();
    }

    @Test
    void testMoveNoCollision() {
        character.move(2.0f, level);
        assertEquals(50.0f, character.getX());
        assertEquals(50.0f, character.getHitBox().x);
    }

    @Test
    void testMoveWithCollision() {
        level.getLvlData()[1][2] = 0;
        character.move(20.0f, level);
        assertEquals(48.0f, character.getX());
    }

    @Test
    void testApplyGravityNoCollision() {
        character.setVelocityY(0);
        character.applyGravity(level);
        assertEquals(0.5f, character.getVelocityY());
        assertEquals(48.5f, character.getY());
        assertFalse(character.isOnGround());
    }

    @Test
    void testApplyGravityWithCollision() {
        level.getLvlData()[2][1] = 0;
        character.setVelocityY(20.0f);
        character.applyGravity(level);
        assertEquals(0, character.getVelocityY());
        assertTrue(character.isOnGround());
        assertTrue(character.getY() <= 49.0f);
    }

    @Test
    void testApplyGravityMaxFallSpeed() {
        character.setVelocityY(10.0f);
        character.applyGravity(level);
        assertEquals(10.0f, character.getVelocityY());
    }

    @Test
    void testApplyKnockback() {
        character.applyKnockback(5.0f, -3.0f);
        assertEquals(53.0f, character.getX());
        assertEquals(-3.0f, character.getVelocityY());
    }

    @Test
    void testUpdateAttackHitBoxRight() {
        character.setDirection(RIGHT);
        character.setAttackHitBox(new Rectangle2D.Float(0, 0, 16, 16));
        assertEquals(80.0f, character.getAttackHitBox().x);
        assertEquals(48.0f, character.getAttackHitBox().y);
    }

    @Test
    void testUpdateAttackHitBoxLeft() {
        character.setDirection(LEFT);
        character.setAttackHitBox(new Rectangle2D.Float(0, 0, 16, 16));
        assertEquals(32.0f, character.getAttackHitBox().x);
        assertEquals(48.0f, character.getAttackHitBox().y);
    }

    @Test
    void testSettersAndGetters() {
        character.setX(100);
        character.setY(200);
        character.setWidth(64);
        character.setHeight(64);
        character.setSpeed(3.0f);
        character.setJumpSpeed(-12.0f);
        character.setDamage(15);
        character.setMAX_HP(100);
        character.setHp(80);
        character.setDirection(LEFT);
        character.setLeft(true);
        character.setRight(false);
        character.setJumping(true);
        character.setAttacking(true);
        character.setOnGround(false);
        character.setCurrentWeapon(new Sword(0, 0,1));

        assertEquals(100, character.getX());
        assertEquals(200, character.getY());
        assertEquals(64, character.getWidth());
        assertEquals(64, character.getHeight());
        assertEquals(3.0f, character.getSpeed());
        assertEquals(-12.0f, character.getJumpSpeed());
        assertEquals(15, character.getDamage());
        assertEquals(100, character.getMAX_HP());
        assertEquals(80, character.getHp());
        assertEquals(LEFT, character.getDirection());
        assertTrue(character.isLeft());
        assertFalse(character.isRight());
        assertTrue(character.isJumping());
        assertTrue(character.isAttacking());
        assertFalse(character.isOnGround());
        assertNotNull(character.getCurrentWeapon());
    }

    @Test
    void testTakeDamage() {
        character.takeDamage(20);
        assertEquals(30, character.getHp());
    }
}