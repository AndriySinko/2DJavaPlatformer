package gui;

import item.Item;
import item.ItemHandler;
import map.levels.Level;
import map.levels.ParkourPhase;
import util.LoadData;

import java.awt.*;
import java.awt.image.BufferedImage;

import static core.GameModel.SCALE;
import static core.GameModel.TILE_SIZE;
import util.Generated;
/**
 * The ItemView class is responsible for rendering items in the game.
 */
@Generated
public class ItemView {
    private BufferedImage[] itemImages;
    /**
     * Constructs an instance of the ItemView and loads item's images.
     */
    public ItemView() {
        loadItemImage();
    }

    private void loadItemImage() {
        BufferedImage spriteSheet = LoadData.getImage(LoadData.ITEMS);
        itemImages = new BufferedImage[6];

        for (int i = 0; i < itemImages.length; i++) {
            itemImages[i] = spriteSheet.getSubimage(i*512, 0, 512, 512);
        }
    }

    /**
     * Renders the items within the current phase.
     * Items are only rendered if they have not been picked up and the current phase
     * is an instance of ParkourPhase. Each item's image is drawn based on its position
     * and the phase's horizontal offset.
     *
     * @param g the graphics context used for rendering the items
     * @param itemHandler the handler managing the items to be rendered
     * @param currentPhase the current level or phase where items may exist
     */
    public void render(Graphics g, ItemHandler itemHandler, Level currentPhase) {
        if(currentPhase instanceof ParkourPhase) {
            for (Item item : itemHandler.getItems()) {
                if (!item.isPickedUp()) {
                    g.drawImage(itemImages[item.getSpriteIndex()],
                            (int) item.getHitBox().x - currentPhase.getXLvlOffset() - 12,
                            (int) item.getHitBox().y - 12,
                            (int) (TILE_SIZE * SCALE),
                            (int) (TILE_SIZE * SCALE),
                            null);
                }
            }
        }
    }

    public BufferedImage[] getItemImages() {
        return itemImages;
    }
}
