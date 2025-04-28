package gui;

import javax.swing.*;
import util.Generated;
/**
 * Represents the main window for game application.
 * This class is responsible for creating and displaying the game window,
 * ensuring it integrates with the game view and other components effectively.
 */
@Generated
public class GameWindow extends JFrame {

    public GameWindow(GameView view) {
        this.setTitle("GhostRunner2D");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.add(view);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);}
}
