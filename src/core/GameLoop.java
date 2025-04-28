package core;

import gui.GameView;

/**
 * The {@code GameLoop} class implements the core game loop functionality. It continuously updates
 * the game logic and refreshes the view at fixed intervals to ensure smooth gameplay.
 * The class runs in a separate thread and orchestrates updates according to its configured
 * frames-per-second (FPS) and updates-per-second (UPS).
 */
public class GameLoop implements Runnable {

    private final int FPS = 60; //frames per second (what we see on the screen)
    private final int UPS = 60; // updates per second (how often game states of objects are updated)
    private boolean running = true;

    private GameModel model;
    private GameView view;

    /**
     * Constructs a {@code GameLoop} instance, initializing the model and view components of the game.
     *
     * @param model the {@code GameModel} instance representing the game state and logic
     * @param view  the {@code GameView} instance responsible for rendering the game visuals
     */
    public GameLoop(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Executes the game loop. This method updates the game state and renders the visuals in
     * synchrony, ensuring a smooth and consistent gameplay experience. It is controlled by the
     * {@code running} flag, which stops the loop when set to false.
     */
    @Override
    public void run() {

        double timePerFrame = 1000000000 / FPS; // 8,3ms per frame
        double timePerUpdate = 1000000000 / UPS; // 8,3ms per update

        long lastTime = System.nanoTime();
        double deltaU = 0;
        double deltaF = 0;

        while (running) {
            long now = System.nanoTime(); // check current time
            deltaU += (now - lastTime) / timePerUpdate;  // calculates how many units (updates since last loop) have passed
            deltaF += (now - lastTime) / timePerFrame;
            lastTime = now;

            // If enough time have passed: update and repaint

            if (deltaU >= 1) {
                model.update();
                deltaU--;
            }

            if (deltaF >= 1) {
                view.repaint();
                deltaF--;
            }
        }
    }

    /**
     * Stops the game loop. This method sets the {@code running} flag to false, which
     * causes the loop to exit gracefully.
     */
    public void stop() {
        running = false;
    }

    /**
     * Executes a single update cycle for the game logic. This method is useful for
     * testing or performing isolated updates outside the main loop.
     */
    public void runSingleUpdate() {
        model.update();
    }

    /**
     * Triggers a single frame repaint. This method is useful for manually refreshing the
     * visual state of the game outside the main loop.
     */
    public void runSingleFrame() {
        view.repaint();
    }
}
