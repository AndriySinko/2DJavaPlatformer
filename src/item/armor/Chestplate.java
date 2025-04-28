package item.armor;


/**
 * Represents concrete armor component
 */
public class Chestplate extends Armor {
    public Chestplate(float x, float y, int spriteIndex) {
        super(x, y, spriteIndex);
        super.maxDurability = 9;
        super.durability = maxDurability;
        super.protection=5;
    }
}
