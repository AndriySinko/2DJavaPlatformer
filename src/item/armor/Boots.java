package item.armor;


/**
 * Represents concrete armor component
 */
public class Boots extends Armor {


    public Boots(float x, float y, int spriteIndex) {
        super(x, y, spriteIndex);
        super.maxDurability = 5;
        super.durability = maxDurability;
        super.protection=1;
    }
}
