package mock;

import entity.Character;
import item.Item;
import core.GameModel;

import java.awt.geom.Rectangle2D;

public class MockItem implements Item {
    private Rectangle2D.Float hitBox;
    private boolean pickedUp;

    public MockItem(float x, float y) {
        hitBox = new Rectangle2D.Float(x, y, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
    }

    @Override
    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    @Override
    public boolean isPickedUp() {
        return pickedUp;
    }

    @Override
    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    @Override
    public void use(Character character) {
        pickedUp = true;
    }

    @Override
    public int getSpriteIndex() {
        return 0;
    }
}
