package entity;


import core.CollisionChecker;
import item.weapon.Weapon;
import map.levels.Level;

import java.awt.geom.Rectangle2D;

import static util.Constants.DIRECTION.LEFT;

/**
 * The {@code Character} class represents a generic entity in the game that can move, attack, and
 * interact with its environment.
 */
public abstract class Character implements Killable {
    protected float x;
    protected float y;
    protected Rectangle2D.Float hitBox;
    protected Rectangle2D.Float attackHitBox;
    protected float width, height;
    protected int direction;

    protected float speed;
    protected float velocityY;
    protected final float GRAVITY = 0.5f;
    protected final float MAX_FALL_SPEED = 10f;
    protected float jumpSpeed;
    protected boolean right,left;
    protected boolean onGround,jumping;

    protected boolean attacking;
    protected int damage;
    protected int hp, MAX_HP;

    protected Weapon currentWeapon;

    /**
     * Constructs a new {@code Character} instance with the specified position and size.
     *
     * @param x      the x-coordinate of the character
     * @param y      the y-coordinate of the character
     * @param width  the width of the character
     * @param height the height of the character
     */
    public Character(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Initializes the character's hitbox.
     *
     * @param x      the x-coordinate of the hitbox
     * @param y      the y-coordinate of the hitbox
     * @param width  the width of the hitbox
     * @param height the height of the hitbox
     */
    protected void initHitBox(float x, float y, float width, float height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }

    /**
     * Sets up the hitbox for the character's attack.
     *
     * @param x      the x-coordinate of the attack hitbox
     * @param y      the y-coordinate of the attack hitbox
     * @param width  the width of the attack hitbox
     * @param height the height of the attack hitbox
     */
    protected void initAttackHitBox(float x, float y, float width, float height) {
        attackHitBox = new Rectangle2D.Float(x,y,width,height);
    }

    /**
     * Updates the position of the attack hitbox based on the direction and offset.
     *
     * @param offset the vertical offset for adjusting the y-coordinate of the attack hitbox
     */
    protected void updateAttackHitBox(int offset) {
        if(direction == LEFT){
            attackHitBox.x = hitBox.x - attackHitBox.width;
        }else {
            attackHitBox.x = hitBox.x + hitBox.width;
        }
        attackHitBox.y = hitBox.y +offset;
    }


    protected void attack(Character target) {
    }
    public abstract void takeDamage(int damage);

    /**
     * Moves the character horizontally within the level. The movement is only allowed
     * collision is not detected.
     *
     * @param dx    the horizontal movement distance
     * @param level the current level for collision detection
     */
    public void move(float dx, Level level) {
        if(dx!=0) {
            Rectangle2D.Float futureXHitbox = new Rectangle2D.Float(
                    hitBox.x + dx, hitBox.y, hitBox.width, hitBox.height);

            if (!CollisionChecker.isCollidingWithTiles(futureXHitbox, level)) {
                hitBox.x += dx;
                x += dx;
            }
        }
    }

    /**
     * Applies gravity to the character. If falling, the character's vertical position
     * is updated unless a collision occurs with the ground or other objects.
     *
     * @param level the current level for collision detection
     */
    public void applyGravity(Level level) {
        velocityY += GRAVITY;
        if (velocityY > MAX_FALL_SPEED) {
            velocityY = MAX_FALL_SPEED;
        }

        Rectangle2D.Float futureYHitbox = new Rectangle2D.Float(
                hitBox.x, hitBox.y + velocityY, hitBox.width, hitBox.height);

        if (!CollisionChecker.isCollidingWithTiles(futureYHitbox, level)) {
            hitBox.y += velocityY;
            y += velocityY;
            onGround = false;
        }else {
            handleVerticalCollision(level);
        }

    }

    private void handleVerticalCollision(Level level) {
        int direction = (int) Math.signum(velocityY);
        while (!CollisionChecker.isCollidingWithTiles(
                new Rectangle2D.Float(hitBox.x, hitBox.y + direction,
                        hitBox.width, hitBox.height), level)) {
            hitBox.y += direction;
            y += direction;
        }

        velocityY = 0;
        if (direction > 0) {
            onGround = true;
        }
    }

    /**
     * Applies a knockback effect to the character, moving its horizontally and vertically by displacing x and y respectively.
     *
     * @param xForce the force applied horizontally
     * @param yForce the force applied vertically
     */
    public void applyKnockback(float xForce, float yForce) {
        hitBox.x += xForce;
        x += xForce;
        velocityY = yForce;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public float getSpeed() {
        return speed;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Rectangle2D.Float getAttackHitBox() {
        return attackHitBox;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMAX_HP() {
        return MAX_HP;
    }

    public int getDamage() {
        return damage;
    }

    public void setHitBox(Rectangle2D.Float hitBox) {
        this.hitBox = hitBox;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void setAttackHitBox(Rectangle2D.Float attackHitBox) {
        this.attackHitBox = attackHitBox;
        updateAttackHitBox(0);
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGRAVITY() {
        return GRAVITY;
    }

    public float getMAX_FALL_SPEED() {
        return MAX_FALL_SPEED;
    }

    public float getJumpSpeed() {
        return jumpSpeed;
    }

    public void setJumpSpeed(float jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setMAX_HP(int MAX_HP) {
        this.MAX_HP = MAX_HP;
    }

}
