package map.tiles;

import map.levels.Level;

/**
 * Handles the interaction logic for tiles within a game level.
 */
public class TileActionHandler {
    /**
     * Handles interaction logic for a tile at a specific position within a game level.
     *
     * @param level the game level where the interaction occurs.
     * @param x the x-coordinate of the tile within the level
     * @param y the y-coordinate of the tile within the level
     */
    public static void handleTileInteraction(Level level, int x, int y) {
        if (level.getPlayer() == null) return;
        int tileIndex = level.getSpriteIndex(x, y);
        Tile tile = level.getTile(tileIndex);
        if (tile != null) {
            tile.executeAction(level,x,y);
        }
    }
}
