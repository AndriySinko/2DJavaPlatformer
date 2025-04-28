package gui;
import map.levels.Location;
import core.GameModel;

import java.awt.*;
import util.Generated;
/**
 * The LocationView class is responsible for rendering current game location on the screen.
 */
@Generated
public class LocationView {
    private Location location;

    /**
     * Constructs a LocationView instance.
     *
     * @param location the Location object providing data for rendering current location
     */
    public LocationView(Location location) {
        this.location = location;
    }

    /**
     * Renders the current game location on the screen.
     * Iterates through the level data and draws tiles based on their sprites and the current level offset.
     *
     * @param g the Graphics object used for rendering the tiles to the screen
     */
    public void render(Graphics g) {
        for (int row = 0; row < GameModel.TILES_IN_HEIGHT; row++) {
            for (int col = 0; col < location.getCurrentPhase().getLvlData()[0].length; col++) {
                int index = location.getCurrentPhase().getSpriteIndex(col, row);
                g.drawImage(location.getTiles()[index].getImage(),
                        (col * GameModel.TILE_SIZE) - location.getxLvlOffset(),
                        row * GameModel.TILE_SIZE,
                        GameModel.TILE_SIZE,
                        GameModel.TILE_SIZE,
                        null);


                //FOR COLLISION DEBUG
//                renderHitbox(g,index,row,col);
            }
        }
    }

    private void renderHitbox(Graphics g, int index, int row, int col) {
        g.setColor(Color.CYAN);
        if (location.getTiles()[index].isCollision()) {
            g.drawRect(col * GameModel.TILE_SIZE - location.getxLvlOffset(),
                    row * GameModel.TILE_SIZE,
                    GameModel.TILE_SIZE,
                    GameModel.TILE_SIZE);
        }
    }
}
