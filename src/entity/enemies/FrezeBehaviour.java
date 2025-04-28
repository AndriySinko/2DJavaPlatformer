package entity.enemies;

import entity.Player;
import map.levels.Level;

/**
 * The FrezeBehaviour represents a behavior where an enemy is frozen for a specified duration.
 *
 */
public class FrezeBehaviour implements EnemyBehaviour {
    private int freezeDuration ;
    private int currentFreezeTime = 0;

    public FrezeBehaviour(int freezeDuration) {
        this.freezeDuration = freezeDuration;
    }

    /**
     *  Once the freeze duration is complete, the enemy transitions to a new behavior
     *  * based on its ability to attack the player, see the player, or patrol the area.
     *
     * @param enemy  the enemy to be updated, whose behavior and state will be modified
     * @param level  the current level, providing context about the environment in which the enemy resides
     * @param player the player character, whose position and actions influence enemy behavior
     */
    @Override
    public void update(Enemy enemy, Level level, Player player) {
        currentFreezeTime++;

        if (currentFreezeTime >= freezeDuration) {
            if (enemy.canAttackPlayer(player)){
                enemy.setBehaviour(new AttackBehaviour());
            }else if(enemy.canSeePlayer(level, player)){
                enemy.setBehaviour(new ChaseBehaviour());
            }else {
                enemy.setBehaviour(new PatrolBehaviour());
            }
        }
    }
}
