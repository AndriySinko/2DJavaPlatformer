package entity.enemies;


import core.CollisionChecker;
import map.levels.Level;

import java.awt.geom.Rectangle2D;

import static util.Constants.DIRECTION.LEFT;
import static util.Constants.DIRECTION.RIGHT;

/**
 * Represents the movement behavior logic for an enemy in the game.
 */
public abstract class MovingBehaviour {
    /**
     * Determines whether the enemy's movement path is blocked by either a wall
     * or the absence of a floor in the level.
     *
     * @param enemy     the enemy whose path is being checked
     * @param level     the current level the enemy is part of, used to check collisions with tiles
     * @param moveSpeed the speed at which the enemy is moving, used to calculate the future hitbox
     * @return true if the path is blocked by a wall or if there is no floor ahead, false otherwise
     */
    protected boolean isPathBlocked(Enemy enemy, Level level, float moveSpeed) {
        Rectangle2D.Float hb = enemy.getHitBox();
        float footX,footY;
        if (enemy.isLeft()) footX = hb.x -1;
        else footX = hb.x + hb.width;
        footY = hb.y + hb.height + 1;

        Rectangle2D.Float futureXHitbox = new Rectangle2D.Float(hb.x + moveSpeed, hb.y, hb.width, hb.height);
        Rectangle2D.Float futureFoot = new Rectangle2D.Float(footX, footY, 1, 1);

        boolean wallAhead = CollisionChecker.isCollidingWithTiles(futureXHitbox, level);
        boolean floorAhead = CollisionChecker.isCollidingWithTiles(futureFoot, level);

        return wallAhead || !floorAhead;
    }

     /**
      * Sets the enemy's movement direction to the left.
      *
      * @param enemy the enemy whose movement direction is being set
      */
     protected void setLeftMoveDirection(Enemy enemy) {
         enemy.setDirection(LEFT);
         enemy.setRight(false);
         enemy.setLeft(true);
     }

    /**
     * Sets the enemy's movement direction to the right.
     *
     * @param enemy the enemy whose movement direction is being set
     */
    protected void setRightMoveDirection(Enemy enemy) {
        enemy.setDirection(RIGHT);
        enemy.setRight(true);
        enemy.setLeft(false);
    }
}
