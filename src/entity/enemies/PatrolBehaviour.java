package entity.enemies;

import entity.Player;
import map.levels.Level;

import static util.Constants.DIRECTION.RIGHT;

/**
 * Represents a patrol behavior for an enemy. This behavior causes the enemy
 * to move back and forth in a predefined pattern, changing direction when
 * the path is blocked or the enemy cannot proceed further due to a lack of floor.
 * If the enemy detects the player within its vision, the behavior transitions to a chase behavior.
 *
 */
public class PatrolBehaviour extends MovingBehaviour implements EnemyBehaviour {

    /**
     * Updates the state and behavior of the enemy during the current game tick.
     *
     * @param enemy the enemy whose state is being updated
     * @param level the current level in which the enemy exists, providing access to level data
     * @param player the player character interacting with the enemy, used for determining visibility and transitions
     */
    @Override
    public void update(Enemy enemy, Level level, Player player) {
        float moveSpeed;

        if (!enemy.isOnGround()){
            enemy.applyGravity(level);
            return;
        }

        if (enemy.isLeft()) moveSpeed = -(enemy.getSpeed()*0.7f);
        else moveSpeed = (enemy.getSpeed()*0.7f);

        if (isPathBlocked(enemy,level, moveSpeed)){
            changeDirection(enemy);
            return;
        }

        enemy.move(moveSpeed, level);

        if (enemy.canSeePlayer(level, player)) enemy.setBehaviour(new ChaseBehaviour());

    }

    private void changeDirection(Enemy enemy) {
        if (enemy.getDirection() == RIGHT) {
            setLeftMoveDirection(enemy);
        }else{
            setRightMoveDirection(enemy);
        }
    }
}
