package entity.enemies;

import entity.Player;
import map.levels.Level;

/**
 * Represents the attack behavior of an enemy in the game.
 */
public class AttackBehaviour implements EnemyBehaviour{
    private static final int FREZE_DURATION = 30;
    /**

     * Determines whether the enemy attacks the player or switches to a different behavior.
     *
     * @param enemy  the enemy that is being updated, whose state and behavior are dynamically modified
     * @param level  the current level the enemy exists in, providing necessary context for state changes
     * @param player the player interacting with the enemy, whose actions affect the enemy's decisions
     */
    @Override
    public void update(Enemy enemy, Level level, Player player) {

       if (enemy.canAttackPlayer(player) && player.readyToTakeDamage()){
           enemy.setAttacking(true);

           if (enemy.getCurrentWeapon()!=null){
               enemy.getCurrentWeapon().performAttack(enemy,player);
           }else {
               player.takeDamage(enemy.getDamage());
           }

           enemy.setAttacking(false);
           enemy.setBehaviour(new FrezeBehaviour(FREZE_DURATION));
       }else {
           enemy.setBehaviour(new ChaseBehaviour());
       }
    }
}
