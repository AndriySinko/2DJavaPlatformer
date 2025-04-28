package map.tiles;

import map.levels.Level;

import java.awt.image.BufferedImage;

/**
 * Represents a tile in a game map, providing collision detection, image representation,
 * and an optional action that can be executed upon tile interaction.
 */
public class Tile {
    private boolean collision;
    private BufferedImage image;
    private TileAction action;

    /**
     * Executes the action associated with the tile at the specified position in the given level.
     * If no action is assigned to the tile, this method does nothing.
     *
     * @param level the current level in which the action is executed
     * @param tileX the x-coordinate of the tile where the action is executed
     * @param tileY the y-coordinate of the tile where the action is executed
     */
    public void executeAction(Level level, int tileX, int tileY) {
        if(action != null) {
            action.execute(level,tileX,tileY);
        }
    }

    public void setAction(TileAction action) {
        this.action = action;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean isCollision() {
        return collision;
    }
    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public TileAction getAction() {
        return action;
    }
}

