package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController();
    }

    @Test
    void controllerInitializesModelViewAndWindow() {
        assertNotNull(gameController.getModel());
        assertNotNull(gameController.getView());
        assertNotNull(gameController.getWindow());
    }

    @Test
    void controllerStartsGameLoop() {
        assertNotNull(gameController.getGameThread());
        assertTrue(gameController.getGameThread().isAlive());
    }
}