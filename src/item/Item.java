package item;

import entity.Character;
import java.awt.geom.Rectangle2D;

public interface Item {
    void use(Character character);
    Rectangle2D.Float getHitBox();
    boolean isPickedUp();
    void setPickedUp(boolean pickedUp);
    int getSpriteIndex();
}
