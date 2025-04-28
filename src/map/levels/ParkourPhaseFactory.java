package map.levels;

import map.tiles.Tile;

/**
 * A factory class for creating instances of the ParkourPhase.
 */
public class ParkourPhaseFactory implements PhaseFactory {

    @Override
    public Level createPhase(String lvlData, String enemies, Tile[] tiles) {
        float startX = 96;
        float startY = 96;

        ParkourPhase phase = new ParkourPhase(lvlData, tiles);
        phase.setStartPoint(startX, startY);
        return phase;
    }
}
