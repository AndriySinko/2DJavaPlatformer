package entity.enemies;


import entity.Character;
import entity.Player;
import map.levels.Level;


import static util.Constants.DIRECTION.*;


/**
 * Represents a general enemy character in the game. This abstract class
 * extends the Character class and defines common properties and behaviors
 * that all enemy types share.
 */
public abstract class Enemy extends Character {
    protected int attackDistance;
    protected int visionDistance;
    protected boolean active;
    protected EnemyBehaviour behaviour;
    protected int xHitBoxOffset;
    protected int yHitBoxOffset;
    protected int attackHitBoxOffset;
    protected int invulnerabilityFrames = 0;
    protected static final int INVULNERABILITY_DURATION = 30; // 0.5 second at 60 FPS
    /**
     * Constructs an Enemy instance with the specified position and size.
     * Initializes the enemy's direction, state.
     *
     * @param x      the x-coordinate of the enemy
     * @param y      the y-coordinate of the enemy
     * @param width  the width of the enemy
     * @param height the height of the enemy
     */
    public Enemy(float x, float y, float width, float height) {
        super(x, y, width, height);
        super.direction = RIGHT;
        super.right = true;
        this.active = true;
    }

    /**
     * Updates the state of the enemy based on its behaviour, the level, and the player.
     *
     * @param level  the current level the enemy is part of, providing access to level-specific data and mechanics
     * @param player the player character interacting with the enemy, used for detecting and responding to player actions
     */
    public void update(Level level, Player player) {
        if (invulnerabilityFrames > 0) {
            invulnerabilityFrames--;
        }

        if (active) {
            if (behaviour != null) {
                behaviour.update(this, level, player);
                updateAttackHitBox(attackHitBoxOffset);
            }
        }
    }

    @Override
    public abstract void takeDamage(int damage);
    protected abstract boolean canSeePlayer(Level level, Player player);
    protected abstract boolean canAttackPlayer(Player player);
    public void setBehaviour(EnemyBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean isActive() {
        return active;
    }

    public int getxHitBoxOffset() {
        return xHitBoxOffset;
    }

    public int getyHitBoxOffset() {
        return yHitBoxOffset;
    }

    public EnemyBehaviour getBehaviour() {
        return behaviour;
    }
    public abstract int getXPReward();
    /**
     * Determines whether the enemy is ready to take damage based on its invulnerability frames status.
     *
     * @return true if the enemy is ready to take damage, false if invulnerability frames are active.
     */
    public boolean readyToTakeDamage() {
        if (invulnerabilityFrames > 0) return false;
        return true;
    }

    public int getInvulnerabilityFrames() {
        return invulnerabilityFrames;
    }

    public void setInvulnerabilityFrames(int i) {
        this.invulnerabilityFrames = i;
    }
}
