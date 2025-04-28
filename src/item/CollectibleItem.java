package item;

import java.awt.geom.Rectangle2D;

import static core.GameModel.TILE_SIZE;

/**
 * Represents an abstract base class for all collectible items within the game.
 * Collectible items are entities that can be picked up by characters.
 */
public abstract class CollectibleItem implements Item {
    protected float x, y;
    protected Rectangle2D.Float hitBox;
    protected boolean pickedUp = false;
    protected final int spriteIndex;

    /**
     * Constructs a new CollectibleItem at location with the given sprite index.
     * Initializes its position, sprite index, and hitbox for collision detection.
     *
     * @param x the x-coordinate of the item in the game world
     * @param y the y-coordinate of the item in the game world
     * @param spriteIndex the index of the sprite to be used for rendering this item
     */
    public CollectibleItem(float x, float y, int spriteIndex) {
        this.spriteIndex = spriteIndex;
        this.x = x;
        this.y = y;
        this.hitBox = new Rectangle2D.Float(x, y, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    @Override
    public boolean isPickedUp() {
        return pickedUp;
    }

    @Override
    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }
    @Override
    public int getSpriteIndex(){
        return spriteIndex;
    }
}