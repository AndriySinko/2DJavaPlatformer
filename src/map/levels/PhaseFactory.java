package map.levels;

import map.tiles.Tile;

public interface PhaseFactory {
    Level createPhase(String lvlData, String enemies, Tile[] tile);
}
