package map.tiles;


import map.levels.Level;

/**
 * Represents an action that sets a checkpoint within a game level when a tile is interacted with.
 */
public class CheckpointAction implements TileAction {

    @Override
    public void execute(Level level, int tileX, int tileY) {
        level.setCheckpoint(tileX, tileY);
    }
}
