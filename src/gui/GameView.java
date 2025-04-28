package gui;

import core.GameModel;
import input.KeyHandler;
import input.MouseHandler;

import javax.swing.*;
import java.awt.*;
import util.Generated;
/**
 * Represents the main game view rendering component of the application.
 * This class is responsible for drawing the game elements onto the screen, such as
 * the player, enemies, location, items, and user interface.
 */
@Generated
public class GameView extends JPanel {
    private GameModel model;
    private PlayerView playerView;
    private LocationView locationView;
    private EnemyView enemyView;
    private UI ui;
    private ItemView itemView;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    /**
     * Constructs the GameView and initializes its sub-components.
     *
     * @param model The GameModel instance representing the current state of the game,
     *              including the player, location, items, and other game configurations.
     */
    public GameView(GameModel model) {
        this.model = model;
        this.playerView = new PlayerView(model.getPlayer());
        this.locationView = new LocationView(model.getLocation());
        this.enemyView = new EnemyView();
        this.ui = new UI();
        this.itemView = new ItemView();
        ui.setItemSprites(itemView.getItemImages());
        keyHandler = new KeyHandler(model);
        mouseHandler = new MouseHandler(model);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(keyHandler);
        addMouseListener(mouseHandler);
        setPanelSize();
    }

    private void setPanelSize() {
        setPreferredSize(new Dimension(GameModel.GAME_WIDTH, GameModel.GAME_HEIGHT));
    }

    /**
     * Renders the entire game view by drawing the current game elements like location, items,
     * user interface, player, and enemies. Each element is rendered based on its respective state
     * and phase in the game model.
     *
     * @param g the {@code Graphics} object used to draw the game components
     */
    public void render(Graphics g) {
        if (model.getLocation().isEntered()) {
            locationView.render(g);
            itemView.render(g,model.getItemHandler(),model.getLocation().getCurrentPhase());
            ui.render(g, model.getPlayer());
            playerView.render(g, model.getLocation().getxLvlOffset());
            enemyView.render(g, model.getLocation().getCurrentPhase());
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }
}
