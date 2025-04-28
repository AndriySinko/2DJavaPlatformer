package map.tiles;

import map.levels.Level;

public interface TileAction {
    void execute(Level level, int tileX, int tileY);
}
