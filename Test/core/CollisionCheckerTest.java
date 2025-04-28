package core;

import mock.MockCharacter;
import mock.MockPlayer;
import mock.MockLevel;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.*;

class CollisionCheckerTest {

    @Test
    void testIsCollidingWithTiles() {
        MockLevel level = new MockLevel();
        level.setSolidTile(5, 5);

        Rectangle2D.Float hitBox = new Rectangle2D.Float(5 * 32, 5 * 32, 32, 32);

        boolean isColliding = CollisionChecker.isCollidingWithTiles(hitBox, level);

        assertTrue(isColliding);
    }

    @Test
    void testIsSightClear_NoObstruction() {
        MockLevel level = new MockLevel();

        Rectangle2D.Float fromHitBox = new Rectangle2D.Float(0, 0, 32, 32);
        Rectangle2D.Float toHitBox = new Rectangle2D.Float(10 * 32, 0, 32, 32);

        int yTileRow = 0;

        boolean sightClear = CollisionChecker.isSightClear(level, fromHitBox, toHitBox, yTileRow);

        assertTrue(sightClear);
    }

    @Test
    void testIsSightClear_WithObstruction() {
        MockLevel level = new MockLevel();
        level.setSolidTile(6, 0);

        Rectangle2D.Float fromHitBox = new Rectangle2D.Float(0, 0, 32, 32);
        Rectangle2D.Float toHitBox = new Rectangle2D.Float(10 * 32, 0, 32, 32);

        int yTileRow = 0;

        boolean sightClear = CollisionChecker.isSightClear(level, fromHitBox, toHitBox, yTileRow);

        assertFalse(sightClear);
    }

    @Test
    void testHandlePlayerCharacterCollision_Push() {
        MockPlayer player = new MockPlayer(160, 160, 32, 32);
        MockCharacter character = new MockCharacter(160, 160, 32, 32);

        CollisionChecker.handlePlayerCharacterCollision(player, character);

        assertTrue(character.wasPushed() || character.wasStopped());
    }

    @Test
    void testHandlePlayerCharacterCollision_NoCollision() {
        MockPlayer player = new MockPlayer(160, 160, 32, 32);
        MockCharacter character = new MockCharacter(400, 400, 32, 32);

        CollisionChecker.handlePlayerCharacterCollision(player, character);

        assertFalse(character.wasPushed());
        assertFalse(character.wasStopped());
    }
}