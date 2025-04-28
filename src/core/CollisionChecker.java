package core;

import entity.Character;
import entity.Player;
import map.levels.Level;
import map.tiles.Tile;
import map.tiles.TileActionHandler;

import java.awt.geom.Rectangle2D;

import static core.GameModel.TILE_SIZE;
import static util.Constants.DIRECTION.LEFT;
/**
 * Handles collisions between entities, tiles.
 */
public class CollisionChecker {
    /**
     * Checks if a hitbox collides with any solid tiles in the given level.
     *
     * @param hitBox the hitbox of the game object to check for collisions
     * @param level the level data to perform collision checks against
     * @return {@code true} if the hitbox collides with a solid tile; {@code false} otherwise
     */

    public static boolean isCollidingWithTiles(Rectangle2D.Float hitBox, Level level) {
        //TOP LEFT CORNER
        int startX = (int) (hitBox.x / GameModel.TILE_SIZE); // left side
        int startY = (int) (hitBox.y / GameModel.TILE_SIZE); // top side
        //RIGHT BOTTOM CORNER
        int endX = (int)(hitBox.x + hitBox.width-1) / GameModel.TILE_SIZE; // right side
        int endY = (int)(hitBox.y + hitBox.height-1) / GameModel.TILE_SIZE; // bottom side

        int maxWidth = level.getLvlData()[0].length; // length of the level
        //check whether hit box intersects with solid tile by checking position of hit box and tile
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                if (x < 0 || y < 0 || x >= maxWidth || y >= GameModel.TILES_IN_HEIGHT) //bound of map
                    return true;
                TileActionHandler.handleTileInteraction(level,x,y);

                if (isTileSolid(x,y,level)){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isTileSolid(int x, int y, Level level) {
        int tileIndex = level.getSpriteIndex(x, y);
        Tile tile = level.getTile(tileIndex);
        return tile != null && tile.isCollision();
    }
    /**
     * Checks for obstacles between two points on same Y level.
     *
     * @param level the level data used to check for obstacles
     * @param fromHitBox the hitbox representing the starting point
     * @param toHitBox the hitbox representing the ending point
     * @param yTile the y-coordinate (in tiles) to check for obstruction
     * @return {@code true} if there are no solid tiles; {@code false} if a blockage occurs
     */

    public static boolean isSightClear(Level level, Rectangle2D.Float fromHitBox, Rectangle2D.Float toHitBox, int yTile) {
        int fromTileX = (int) (fromHitBox.x / TILE_SIZE);
        int toTileX = (int) (toHitBox.x / TILE_SIZE);

        int xStart = Math.min(fromTileX, toTileX);
        int xEnd = Math.max(fromTileX, toTileX);

        return noSolidTilesBetween(xStart, xEnd, yTile, level);
    }

    private static boolean noSolidTilesBetween(int xStart, int xEnd, int yTile, Level level) {
        for (int x = xStart; x <= xEnd; x++) {
            if (isTileSolid(x, yTile, level) || isTileSolid(x, yTile+1, level)) { //check if there is any solid tiles between
                return false;
            }
        }
        return true;
    }
    /**
     * Handles collision logic between a player and a character.
     * This includes pushing and stopping character while colliding with player.
     *
     * @param player the {@code Player} entity involved in the collision
     * @param character the {@code Character} entity being collided with
     */

    public static void handlePlayerCharacterCollision(Player player, Character character) {
        Rectangle2D.Float playerBox = player.getHitBox();
        Rectangle2D.Float characterBox = character.getHitBox();

        if (!playerBox.intersects(characterBox)) {
            return;
        }

        if (isMovingToward(player,characterBox)) {
            pushCharacter(character, player.getDirection(), 2f);
        } else {
            stopCharacterAtPlayer(character, playerBox);
        }
    }

    private static boolean isMovingToward(Player player, Rectangle2D.Float otherBox) {
        Rectangle2D.Float playerBox = player.getHitBox();
        if (player.isLeft() && playerBox.x > otherBox.x) return true;
        if (player.isRight() && playerBox.x < otherBox.x) return true;
        return false;
    }

    private static void pushCharacter(Character character, int pushDirection, float distance) {
        Rectangle2D.Float characterBox = character.getHitBox();

        if (pushDirection == LEFT) {
            characterBox.x -= distance;
        } else {
            characterBox.x += distance;
        }

        character.setHitBox(characterBox);
        character.setX(characterBox.x);
    }

    private static void stopCharacterAtPlayer(Character character, Rectangle2D.Float playerBox) {
        Rectangle2D.Float characterBox = character.getHitBox();

        if (characterBox.x < playerBox.x) {
            characterBox.x = playerBox.x - characterBox.width;
        } else {
            characterBox.x = playerBox.x + playerBox.width;
        }

        character.setHitBox(characterBox);
        character.setX(characterBox.x);
    }
}
