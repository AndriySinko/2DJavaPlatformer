package gui;

import entity.enemies.ChaseBehaviour;
import entity.enemies.Enemy;
import util.Constants;

import static util.Constants.EnemyConstants.GetAniAmount;

import util.Generated;
/**
 * Represents the animation state for an enemy in the game.
 */
@Generated


public class EnemyAnimationState {
    private int aniTick = 0;
    private int aniIndex = 0;
    private int action;
    private int aniSpeed;
    public EnemyAnimationState(Enemy enemy) {
        updateSpeedBasedOnBehavior(enemy);
    }

    private void updateSpeedBasedOnBehavior(Enemy enemy) {
        if (enemy.getBehaviour() instanceof ChaseBehaviour) {
            aniSpeed = 4;
        } else {
            aniSpeed = 12;
        }
    }

    /**
     * Updates the animation tick and index for the specified enemy.
     * The animation speed is dynamically updated based on the enemy's behavior.
     *
     * @param enemy the enemy object whose animation tick and index are updated. The method
     *              also uses the enemy's behavior and state to determine animation speed
     *              and frame transitions.
     */
    public void updateAnimationTick(Enemy enemy) {
        updateSpeedBasedOnBehavior(enemy);

        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetAniAmount(enemy,action)){
                aniIndex = 0;
            }
        }
    }

    /**
     * Sets the animation state for the specified enemy based on its current state
     * and behavior.
     *
     * @param enemy the enemy object whose animation state is being set. The method
     *              checks the enemy's movement and grounded status to determine the
     *              appropriate animation action.
     */
    public void setAnimation(Enemy enemy) {
        int start = action;

        if (!enemy.isOnGround()) {
            action = Constants.EnemyConstants.JUMP;
        }
        else if (enemy.isLeft() || enemy.isRight()) {
            action  = Constants.EnemyConstants.WALK;
        }
        else {
            action = Constants.EnemyConstants.IDLE;
        }

        if (start!=action){
            aniTick =0;
            aniIndex=0;
        }
    }

    public int getAniTick() {
        return aniTick;
    }

    public void setAniTick(int aniTick) {
        this.aniTick = aniTick;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getAniSpeed() {
        return aniSpeed;
    }

    public void setAniSpeed(int aniSpeed) {
        this.aniSpeed = aniSpeed;
    }
}
