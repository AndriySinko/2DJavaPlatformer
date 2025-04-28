package item.weapon;

import entity.Character;
import item.CollectibleItem;
import item.Upgradable;


/**
 * Represents a weapon that can be used by characters
 * This class is abstract and must be extended by specific weapon types.
 */
public abstract class Weapon extends CollectibleItem implements Upgradable {
    protected int damage;
    protected int durability;
    protected int maxDurability;

    public Weapon(float x, float y, int spriteIndex) {
        super(x, y, spriteIndex);
    }

    @Override
    public abstract void use(Character character);

    public abstract void performAttack(Character attacker, Character target);

    @Override
    public void fix() {
        this.durability = maxDurability;
    }

    @Override
    public void upgrade() {
        this.maxDurability+=2;
        this.damage +=5;
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }
}
