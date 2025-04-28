package input;

import entity.Player;
import core.GameModel;
import util.Generated;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static util.Constants.DIRECTION.LEFT;
import static util.Constants.DIRECTION.RIGHT;

/**
 * Handles keyboard input for the game by implementing the KeyListener interface.
 * Processes player movement, inventory management, item interactions.
 */
@Generated
public class KeyHandler implements KeyListener {
    private GameModel model;

    public KeyHandler(GameModel model) {
        this.model = model;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player player = model.getPlayer();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                player.setDirection(LEFT);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                player.setDirection(RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                player.jump(true);
                break;
            case KeyEvent.VK_E:
                model.getItemHandler().setPickUp(true);
                break;
            case KeyEvent.VK_1: player.getInventory().selectSlot(1); break;
            case KeyEvent.VK_2: player.getInventory().selectSlot(2); break;
            case KeyEvent.VK_3: player.getInventory().selectSlot(3); break;
            case KeyEvent.VK_4: player.getInventory().selectSlot(4); break;
            case KeyEvent.VK_5: player.getInventory().selectSlot(5); break;
            case KeyEvent.VK_6: player.getInventory().selectSlot(6); break;
            case KeyEvent.VK_Q: player.dropSelectedItem(model.getItemHandler()); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                model.getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_D:
                model.getPlayer().setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                model.getPlayer().jump(false);
                break;
            case KeyEvent.VK_E:
                model.getItemHandler().setPickUp(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
