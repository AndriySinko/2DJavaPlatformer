package input;

import core.GameModel;
import util.Generated;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles mouse input for the game by implementing the MouseListener interface.
 * Processes mouse click events to interact with the game's model.
 */
@Generated
public class MouseHandler implements MouseListener {

    private GameModel model;

    public MouseHandler(GameModel model) {
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            model.getPlayer().setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
