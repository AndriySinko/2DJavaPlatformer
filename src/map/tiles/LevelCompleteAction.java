package map.tiles;
import map.levels.Level;

/**
 * Represents an action that is executed when a level completion tile is interacted with.
 * Upon execution, the action marks the level as complete by invoking the {@link Level#complete()} method.
 */
public class LevelCompleteAction implements TileAction {
    @Override
    public void execute(Level level, int tileX, int tileY) {
        level.complete();
    }
}
