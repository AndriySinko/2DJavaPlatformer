package item.armor;


/**
 * Represents concrete armor component
 */
public class Shield extends Armor {
    public Shield(float x, float y, int spriteIndex) {
        super(x, y, spriteIndex);
        super.maxDurability = 7;
        super.durability = maxDurability;
        super.protection=3;
    }
}
