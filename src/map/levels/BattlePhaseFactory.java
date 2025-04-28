package map.levels;

import map.tiles.Tile;

/**
 * The BattlePhaseFactory class is responsible for creating instances of the BattlePhase level.
 */
public class BattlePhaseFactory implements PhaseFactory {
    @Override
    public Level createPhase(String lvlData, String enemies, Tile[] tiles) {
        //default temporary start values
        float startX = 96;
        float startY = 96;

        BattlePhase phase = new BattlePhase(lvlData, enemies, tiles);
        phase.setStartPoint(startX, startY);
        return phase;
    }

}
