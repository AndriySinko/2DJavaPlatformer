package map.tiles;

import map.levels.Level;

/**
 * Represents an action that occurs when a player interacts with an obstacle tile within the game level.
 * When executed, this action results in the player's death.
 *
 */
public class ObstacleAction implements TileAction {

    @Override
    public void execute(Level level, int tileX, int tileY) {
        level.getPlayer().getKilled();
    }
}
