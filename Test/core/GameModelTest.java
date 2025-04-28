package core;

import core.GameModel;
import mock.MockPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    private GameModel model;

    @BeforeEach
    void setUp() {
        model = new GameModel();
    }

    @Test
    void updateTest() {
        MockPlayer player = new MockPlayer(48, 48, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
        model.getPlayer().setInvulnerabilityFrames(10);
        model.update();
        assertEquals(9, model.getPlayer().getInvulnerabilityFrames());
    }

    @Test
    void PlayerInitPositionTest() {
        assertEquals(96, model.getPlayer().getHitBox().x);
    }

}