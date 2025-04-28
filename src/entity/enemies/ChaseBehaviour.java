package entity.enemies;

import entity.Player;
import map.levels.Level;

import static util.Constants.DIRECTION.*;

/**
 * Defines the chasing behavior for an enemy in the game.
 */
public class ChaseBehaviour extends MovingBehaviour implements EnemyBehaviour {
    /**
     * Detecting whether the player is to the left or right of the enemy and
     * setting the movement direction accordingly.
     *
     * Checking for obstacles or the absence of the floor ahead to avoid invalid
     * movements.
     *
     * @param enemy the enemy being updated; its position, state, and behavior
     *              are modified by this method.
     * @param level the current game level; provides environmental context and
     *              collision data for enemy movement.
     * @param player the player character; used to determine the enemy's
     *               direction, attack range, and visibility.
     */
    @Override
    public void update(Enemy enemy, Level level, Player player) {
        float moveSpeed;

        if (!enemy.isOnGround()) {
            enemy.applyGravity(level);
            return;
        }

        if (player.getHitBox().x>enemy.getHitBox().x){
            setRightMoveDirection(enemy);
        }else{
            setLeftMoveDirection(enemy);
        }

        if (enemy.getDirection()==RIGHT) moveSpeed = enemy.getSpeed();
        else moveSpeed = -enemy.getSpeed();

        if (isPathBlocked(enemy, level, moveSpeed)) {
            enemy.setBehaviour(new PatrolBehaviour());
            return;
        }

        enemy.move(moveSpeed, level);

        if (enemy.canAttackPlayer(player)){
            enemy.setBehaviour(new AttackBehaviour());
        }else if(!enemy.canSeePlayer(level, player)){
            enemy.setBehaviour(new PatrolBehaviour());
        }
    }
}
