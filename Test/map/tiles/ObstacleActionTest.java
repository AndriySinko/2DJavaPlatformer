package map.tiles;

import mock.MockLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ObstacleActionTest {
    private ObstacleAction action;
    private MockLevel level;

    @BeforeEach
    void setUp() {
        action = new ObstacleAction();
        level = new MockLevel();
    }

    @Test
    void killsPlayerTest() {
        action.execute(level, 0, 0);
        assertTrue(level.getPlayer().isDead());
    }
}