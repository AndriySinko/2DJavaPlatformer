package entity;

import item.Item;
import item.ItemHandler;
import item.armor.Armor;
import item.inventory.Inventory;
import item.weapon.Weapon;
import map.levels.Level;

import java.awt.geom.Rectangle2D;
import static core.GameModel.SCALE;

/**
 * Represents a Player character in the game. The Player has various attributes and abilities
 * such as health points, experience points, inventory management, attack capabilities, and
 * movement actions. This class extends {@code Character} and implements additional features.
 */
public class Player extends Character{
    private int xp=0;
    private boolean dead = false;
    private int invulnerabilityFrames = 0;
    private static final int INVULNERABILITY_DURATION = 60;
    private final Inventory inventory = new Inventory();

    /**
     * Constructs a new {@code Player} instance with the specified position, size, and default attributes.
     *
     * @param x      the x-coordinate of the player's initial position
     * @param y      the y-coordinate of the player's initial position
     * @param width  the width of the player
     * @param height the height of the player
     */
    public Player(float x, float y, float width, float height) {
        super(x,y,width,height);
        super.speed = 5;
        super.jumpSpeed = -11;
        super.velocityY = 0;
        super.onGround = false;
        initHitBox(x,y,(int)(14*SCALE*1.5),(int)(28*SCALE*1.5));
        initAttackHitBox(x,y,(int)(10*SCALE*1.5),(int)(30*SCALE*1.5));
        super.MAX_HP = 70;
        super.hp = MAX_HP;
        super.damage = 10;
    }

    /**
     * Executes an attack action against a specified target character.
     * The method checks if the target is within the attack hitbox.
     * If a weapon is equipped, it uses the weapon's attack functionality;
     * otherwise, it directly applies damage to the target's health.
     * Finally, resets the attacking state.
     *
     * @param target the character being targeted by the player's attack.
     *               Must be a valid instance of a {@link java.lang.Character}.
     */
    @Override
    public void attack(Character target) {
        if (attackHitBox.intersects(target.getHitBox())) {
            if (currentWeapon != null ) {
                currentWeapon.performAttack(this,target);
            }else {
                target.takeDamage(damage);
            }
        }
        attacking = false;
    }

    /**
     * Handles the damage taken by the player. Reduces damage based on equipped armor protection and applies any
     * remaining damage to the player's health. If no health remains, calls death logic. Also sets
     * invulnerability frames to prevent immediate subsequent damage.
     *
     * @param damage the amount of damage inflicted on the player. Must be a positive integer.
     */
    @Override
    public void takeDamage(int damage) {
        int remainingDamage = damage;
        for (int slot = 2; slot <= 6; slot++) {
            Item item =  inventory.getItemInSlot(slot);
            if (item instanceof Armor armor) {
                remainingDamage -= armor.protect(this);
                if (remainingDamage <= 0) return;
            }
        }

        hp -= Math.max(0, remainingDamage);
        if (hp <= 0) {
            getKilled();
        }

        invulnerabilityFrames = INVULNERABILITY_DURATION;
    }
    /**
     * Increases the player's health points by amount, up to the maximum allowed value.
     *
     * @param amount the amount of health points to be restored. Must be a valid positive integer.
     */
    public void heal(int amount) {
        hp = Math.min(MAX_HP,hp+amount);
    }

    /**
     * Handles the player's death logic by setting their health points to zero, marking their state
     * as dead, and reducing their experience points by 10.
     *
     * Overrides the {@link Killable#getKilled()} method.
     */
    @Override
    public void getKilled() {
        this.hp = 0;
        this.dead = true;
        gainXP(-10);
    }
    /**
     * Updates the player's state during each game tick. This includes updating cooldowns,
     * the player's position within the level, and the position of the attack hitbox.
     *
     * @param level the current level context used for updating the player's position and interactions
     *              with the environment.
     */
    public void update(Level level) {
        updateCooldowns();
        updatePosition(level);
        updateAttackHitBox(-(int) (2*SCALE*1.5));
    }

    /**
     * Updates the player's cooldown states, specifically related to their invulnerability frames.
     */
    protected void updateCooldowns() {
        if(invulnerabilityFrames > 0 ) {
            invulnerabilityFrames--;
        }
    }


    private void updatePosition(Level level) {
        float dx = 0;

        if (left && !right) dx -= speed;
        else if (right && !left) dx += speed;
        applyGravity(level);
        move(dx,level);
    }

    /**
     * Handles the jump action for the player. Updates the player's vertical velocity
     * and on-ground state based on whether the jump button is pressed.
     *
     * @param jumpPressed a boolean indicating whether the jump button is pressed.
     *                    If true and the player is on the ground, the player will jump.
     */
    public void jump(boolean jumpPressed) {
        this.jumping = jumpPressed;
        if (jumpPressed && onGround) {
            velocityY = jumpSpeed;
            onGround = false;
        }
    }

    /**
     * Sets the position of the player to the specified x and y coordinates within the level.
     * Updates the player's hitbox if it exists and resets vertical velocity and ground state.
     *
     * @param x the new x-coordinate for the player.
     * @param y the new y-coordinate for the player.
     * @param level the level context in which the player's position is being set.
     */
    public void setPosition(float x, float y, Level level) {
        this.x = x;
        this.y = y;
        this.velocityY = 0;
        this.onGround = false;
        if (hitBox != null) {
            hitBox.x = x;
            hitBox.y = y;
        }
    }

    /**
     * Drops the currently selected item from the player's inventory and places it
     * at the player's current position in the game world. If the dropped item is a weapon,
     * the player's currently equipped weapon is set to null. The item is then added to the game
     * map through the provided ItemHandler.
     *
     * @param itemHandler the ItemHandler instance responsible for managing and adding items to the game map.
     */
    public void dropSelectedItem(ItemHandler itemHandler) {
        Item item = inventory.removeSelectedItem();
        if (item != null) {
            item.setPickedUp(false);
            Rectangle2D.Float hitBox = item.getHitBox();
            hitBox.x = this.x;
            hitBox.y = this.y;

            if (item instanceof Weapon) setCurrentWeapon(null);

            itemHandler.addItemToMap(item);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isDead() {
        return dead;
    }

    /**
     * Adds experience points (XP) to the player. The XP is accumulated up to a maximum cap of 400.
     * The method ensures that the player's XP remains within the range of 0 to 400.
     * If the XP reaches exactly 400, the player upgrades.
     *
     * @param xp the amount of experience points to be added to the player. Must be a valid integer.
     *           Negative values will not decrease the player's XP below 0.
     */
    public void gainXP(int xp) {
            this.xp = Math.max(0, Math.min(this.xp + xp, 400));
            if (xp==400) upgrade();
    }

    private void upgrade() {
        for (int i = 0; i < 6; i++) {
            Item item = inventory.getItemInSlot(i);
            if(item instanceof Weapon weapon) {
                weapon.upgrade();
            }else if(item instanceof Armor armor) {
                armor.upgrade();
            }
        }
    }

    public int getXp() {
        return xp;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Determines if the player is ready to take damage.
     * This is based on whether the player has remaining invulnerability frames.
     *
     * @return true if the player has no invulnerability frames left (and is ready to take damage),
     *         false otherwise.
     */
    public boolean readyToTakeDamage() {
        if (invulnerabilityFrames > 0) return false;
        return true;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getInvulnerabilityFrames() {
        return invulnerabilityFrames;
    }

    public void setInvulnerabilityFrames(int invulnerabilityFrames) {
        this.invulnerabilityFrames = invulnerabilityFrames;
    }
}
