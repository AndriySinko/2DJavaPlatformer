package item.armor;


/**
 * Represents concrete armor component
 */
public class Leggings extends Armor {
    public Leggings(float x, float y,int spriteIndex) {
        super(x, y,spriteIndex);
        super.maxDurability = 8;
        super.durability = maxDurability;
        super.protection=4;
    }
}
