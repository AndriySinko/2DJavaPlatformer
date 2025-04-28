package item.armor;

import entity.Character;
import entity.Player;
import item.CollectibleItem;
import item.Upgradable;


/**
 * Represents an abstract class for armor items in the game.
 * Armor items provide protection to the player, can be collected, upgraded, and fixed.
 */
public abstract class Armor extends CollectibleItem implements Upgradable {
    protected int protection;
    protected int durability;
    protected int maxDurability;

    public Armor(float x, float y, int spriteIndex) {
        super(x, y, spriteIndex);
    }


    /**
     * Allows the armor item to be used by the specified character.
     * If the character is a player, this method attempts to equip the armor in the appropriate inventory slot.
     * The item is only equipped if the target slot is valid and currently empty.
     *
     * @param character the character using the armor; must be an instance of Player to be equipped
     */
    @Override
    public void use(Character character) {
        if (character instanceof Player player) {
            int targetSlot;
            switch (this) {
                case Helmet helmet -> targetSlot = 2;
                case Chestplate chestplate -> targetSlot = 3;
                case Leggings leggings -> targetSlot = 4;
                case Boots boots -> targetSlot = 5;
                case Shield shield -> targetSlot = 6;
                default -> targetSlot = -1;
            }

            // Only pick up if we found a valid slot AND it's empty
            if (targetSlot != -1 && player.getInventory().isSlotEmpty(targetSlot)) {
                if (player.getInventory().addItem(this)) {
                    pickedUp = true;
                }
            }
        }
    }

    /**
     * This method upgrades current armor, by incrementing durability and protection
     */
    @Override
    public void upgrade() {
        this.maxDurability++;
        this.protection++;
    }

    /**
     * Restores the durability of the armor to its maximum durability level.
     */
    @Override
    public void fix() {
        this.durability = maxDurability;
    }

    /**
     * Reduces the armor's durability and provides protection to the player.
     * If the armor is broken after reducing durability, it is removed from the player's inventory.
     *
     * @param player the Player object that is being protected by the armor
     * @return the protection value provided by the armor, or 0 if the armor is broken
     */
    public int protect(Player player) {
        if (durability > 0) {
            durability--;
            if (isBroken()) {
                player.getInventory().removeItem(this);
            }
            return protection;
        }
        return 0;
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    public int getProtection() {
        return protection;
    }

    public int getDurability() {
        return durability;
    }

    public int getMaxDurability() {
        return maxDurability;
    }
}