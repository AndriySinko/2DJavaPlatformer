package core;

import gui.GameView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameLoopTest {
    private GameModel model;
    private GameView view;
    private GameLoop gameLoop;

    @BeforeEach
    void setUp() {
        model = new GameModel();
        view = new GameView(model);
        gameLoop = new GameLoop(model, view);
    }

    @Test
    void gameLoopStopsWhenRequested() {
        Thread thread = new Thread(gameLoop);
        thread.start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gameLoop.stop();

        try {
            thread.join(100);
        } catch (InterruptedException e) {
            fail("Thread didn't stop properly");
        }

        assertFalse(thread.isAlive());
    }

    @Test
    void gameLoopUpdatesModel() {
        float initialPlayerX = model.getPlayer().getX();

        gameLoop.runSingleUpdate();

        assertNotEquals(initialPlayerX, model.getPlayer().getX());
    }

    @Test
    void gameLoopRequestsViewRepaint() {
        TestGameView testView = new TestGameView(model);
        GameLoop testLoop = new GameLoop(model, testView);

        testLoop.runSingleFrame();

        assertTrue(testView.repaintCalled);
    }

    private static class TestGameView extends GameView {
        boolean repaintCalled = false;

        public TestGameView(GameModel model) {
            super(model);
        }

        @Override
        public void repaint() {
            repaintCalled = true;
            super.repaint();
        }
    }
}