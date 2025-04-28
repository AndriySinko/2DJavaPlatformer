package map.levels;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LocationConfigTest {
    private LocationConfig config;

    @BeforeEach
    void setUp() {
        config = new LocationConfig("sprite.png", 256, new int[]{0, 1}, Arrays.asList("parkour.txt"), Arrays.asList("battle.txt"), Arrays.asList("enemies.txt"));
    }

    @Test
    void getSpritePathTest() {
        assertEquals("sprite.png", config.getSpritePath());
    }

    @Test
    void getTotalLevelsTest() {
        assertEquals(1, config.getTotalLevels());
    }
}