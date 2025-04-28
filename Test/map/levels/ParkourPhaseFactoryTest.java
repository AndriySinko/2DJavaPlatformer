package map.levels;

import map.tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ParkourPhaseFactoryTest {
    private ParkourPhaseFactory factory;
    private Tile[] tiles;

    @BeforeEach
    void setUp() {
        factory = new ParkourPhaseFactory();
        tiles = new Tile[256];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
        }
    }

    @Test
    void createsParkourPhaseTest() {
        Level phase = factory.createPhase("world/parkour_1.txt", null, tiles);
        assertTrue(phase instanceof ParkourPhase);
    }
}