package item.weapon;

import entity.Character;
import entity.Player;

import static core.GameModel.TILE_SIZE;
import static util.Constants.DIRECTION.LEFT;

/**
 * Represents a Sword, a concrete type of Weapon that can be used in combat.
 */
public class Sword extends Weapon {
    private final float KNOCKBACK_FORCE = TILE_SIZE;

    public Sword(float x, float y, int spriteIndex) {
        super(x, y, spriteIndex);
        super.damage = 5;
        super.maxDurability = 15;
        super.durability = maxDurability;
    }


    /**
     * Assigns the sword as the current weapon for the character if none is currently equipped.
     * If the character is a player, the sword is also added to the player's inventory.
     *
     * @param character the character attempting to use or pick up the sword. If the character already has a weapon equipped,
     *                  this method will not assign the sword.
     */
    @Override
    public void use(Character character) {
        if (character.getCurrentWeapon() == null) {
            character.setCurrentWeapon(this);
            if (character instanceof Player player) player.getInventory().addItem(this);
            pickedUp = true;
        }
    }

    /**
     * Performs an attack on a target character by the attacker using the weapon.
     * Applies damage to the target based on the weapon's damage and the attacker's additional damage,
     * and applies a knockback effect to the target determined by the attacker's direction.
     *
     * Reduces the durability of the weapon and, if the weapon breaks, removes it from the attacker's inventory
     * or unequips it as the current weapon.
     *
     * @param attacker the character performing the attack. Determines the direction of the attack and additional damage applied.
     * @param target the character being attacked. Receives damage and is affected by the knockback effect.
     */
    @Override
    public void performAttack(Character attacker, Character target) {
        target.takeDamage(damage + attacker.getDamage());

        float knockbackDir;
        if (attacker.getDirection() == LEFT) knockbackDir = -KNOCKBACK_FORCE;
        else knockbackDir = KNOCKBACK_FORCE;

        target.applyKnockback(knockbackDir, -KNOCKBACK_FORCE / 8);

        durability--;

        if (isBroken()) {
            if (attacker instanceof Player player)
                player.getInventory().removeItem(this);
            attacker.setCurrentWeapon(null);
        }
    }

}

