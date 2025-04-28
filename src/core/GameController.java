package core;

import gui.GameView;
import gui.GameWindow;
/**
 * The {@code GameController} class serves as the main controller in the game's MVC (Model-View-Controller) architecture.
 *
 * <p>This class is responsible for managing the main game components:</p>
 * <ul>
 *   <li>The game model, which holds the game's state and logic</li>
 *   <li>The game view, which provides graphical representation</li>
 *   <li>The game window, which serves as the graphical container</li>
 *   <li>The collision checker, which handles interactions between objects</li>
 *   <li>The main game loop, responsible for updating and rendering the game</li>
 * </ul>
 * <p>It also controls the thread responsible for the game's execution.</p>
 */

public class GameController{
    private GameModel model;
    private GameView view;
    private GameWindow window;
    private CollisionChecker collisionChecker;


    private GameLoop gameLoop;
    private Thread gameThread;

    /**
     * Default constructor for the {@code GameController}.
     *
     * <p>Initializes the game controller, setting up the model, view, window, collision checker,
     * and the game loop.</p>
     */

    public GameController() {
        model = new GameModel();
        view = new GameView(model);
        window = new GameWindow(view);
        startGameLoop();
    }

    private void startGameLoop() {
        gameLoop = new GameLoop(model, view);
        gameThread = new Thread(gameLoop);
        gameThread.start();
    }

    public Thread getGameThread() {
        return gameThread;
    }
    public GameModel getModel() {
        return model;
    }
    public GameView getView() {
        return view;
    }
    public GameWindow getWindow() {
        return window;
    }
    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }
    public GameLoop getGameLoop() {
        return gameLoop;
    }
}
