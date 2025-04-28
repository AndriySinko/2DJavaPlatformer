package entity.enemies;

import entity.Player;
import core.CollisionChecker;
import item.weapon.Sword;
import map.levels.Level;

import static core.GameModel.SCALE;
import static core.GameModel.TILE_SIZE;
import static util.Constants.DIRECTION.*;

/**
 * Represents a concrete type of enemy in the game.
 *
 */
public class DudeMonster extends Enemy{
    /**
     * Constructs a DudeMonster instance at the specified coordinates and predefined attributes.
     *
     * @param x the x-coordinate where the DudeMonster spawns
     * @param y the y-coordinate where the DudeMonster spawns
     */
    public DudeMonster(float x, float y) {
        super(x, y, TILE_SIZE, TILE_SIZE);
        initHitBox(x,y,(int)(16*SCALE*1.5),(int)(26*SCALE*1.5));
        initAttackHitBox(x,y,(int)(10*SCALE),(int)(8*SCALE*1.5));
        super.xHitBoxOffset = (int)(8*SCALE*1.5);
        super.yHitBoxOffset = (int)(6*SCALE*1.5);
        super.attackHitBoxOffset =(int) (12*SCALE*1.5);
        super.behaviour = new PatrolBehaviour();
        super.direction = RIGHT;
        super.right = true;
        super.speed = 3;
        super.currentWeapon = new Sword(x,y,0);

        super.attackDistance=TILE_SIZE;
        super.visionDistance=TILE_SIZE*5;

        this.MAX_HP = 50;
        this.hp = MAX_HP;
        this.damage = 5;
    }

    /**
     * Determines if the DudeMonster has a clear line of sight to the player within the level
     * on the same vertical tile and with no obstacles.
     *
     * @param level the current game level, used to determine environmental and positional context
     * @param player the player instance whose visibility to the DudeMonster is being evaluated
     * @return true if the player is in the same vertical tile as the DudeMonster, within the vision range,
     *         and no environmental obstructions block the sightline; false otherwise
     */
    @Override
    public boolean canSeePlayer(Level level, Player player) {
        int playerTileY = (int)(player.getY() / TILE_SIZE);
        if (playerTileY == (int)(y/TILE_SIZE)) {
            if(isPlayerInRange(player)){
                return CollisionChecker.isSightClear(level, hitBox, player.getHitBox(), (int)(y/TILE_SIZE));
            }
        }
        return false;
    }

    /**
     * Determines if the DudeMonster can attack the player based on their
     * vertical alignment and horizontal proximity.
     *
     * @param player the player to analyze attack conditions against
     * @return true if the player is on the same vertical tile and within the attack distance; false otherwise
     */
    @Override
    public boolean canAttackPlayer(Player player) {
        int playerTileY = (int)(player.getY() / TILE_SIZE);
        return playerTileY == (int)(y/TILE_SIZE) &&
                Math.abs(player.getHitBox().x - hitBox.x) <= attackDistance;
    }

    /**
     * Determines if the player is within the DudeMonster's vision range,
     * based on the horizontal distance between their hitboxes.
     *
     * @param player the player whose position is being evaluated
     * @return true if the player is within the DudeMonster's vision range; false otherwise
     */
    protected boolean isPlayerInRange(Player player) {
        int distance = (int)Math.abs(player.getHitBox().x - hitBox.x);
        return distance<=visionDistance;
    }

    /**
     * Reduces the health points of the DudeMonster by damage value.
     * If the DudeMonster's invulnerability time is active, no damage is taken.
     * When health points reach zero, DudeMonster is killed,
     * After taking damage DudeMoster responds by entering chasing state.
     *
     * @param damage the amount of damage to inflict on the DudeMonster
     */
    @Override
    public void takeDamage(int damage) {
        if (invulnerabilityFrames > 0) return;

        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            getKilled();
            return;
        }

        invulnerabilityFrames = INVULNERABILITY_DURATION;
        setBehaviour(new ChaseBehaviour());
    }

    @Override
    public void getKilled() {
        setActive(false);
    }

    public int getXPReward() {
        return 30;
    }
}
