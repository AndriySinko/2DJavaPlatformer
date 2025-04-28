package item.armor;
/**
 * Represents concrete armor component
 */
public class Helmet extends Armor {

    public Helmet(float x, float y,int spriteIndex) {
        super(x, y,spriteIndex);
        super.maxDurability = 6;
        super.durability = maxDurability;
        super.protection=2;
    }
}
