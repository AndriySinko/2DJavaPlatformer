package mock;

import entity.Character;
import entity.Player;
import map.levels.Level;
import java.awt.geom.Rectangle2D;

public class MockCollisionChecker {
    private boolean shouldCollideWithTiles = false;
    private boolean shouldBlockSight = false;
    private boolean shouldPushCharacter = true;

    public void setShouldCollideWithTiles(boolean value) {
        this.shouldCollideWithTiles = value;
    }

    public void setShouldBlockSight(boolean value) {
        this.shouldBlockSight = value;
    }

    public void setShouldPushCharacter(boolean value) {
        this.shouldPushCharacter = value;
    }

    public boolean isCollidingWithTiles(Rectangle2D.Float hitBox, Level level) {
        return shouldCollideWithTiles;
    }

    public boolean isSightClear(Level level, Rectangle2D.Float fromHitBox,
                                Rectangle2D.Float toHitBox, int yTile) {
        return !shouldBlockSight;
    }

    public void handlePlayerCharacterCollision(Player player, Character character) {
        if (shouldPushCharacter) {
            if (player.isRight()) {
                character.getHitBox().x += 2;
            } else if (player.isLeft()) {
                character.getHitBox().x -= 2;
            }
        }
    }
}