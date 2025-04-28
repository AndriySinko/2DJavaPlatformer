package mock;

import entity.Character;
import item.weapon.Sword;
import item.weapon.Weapon;

public class MockSword extends Sword {
    private boolean attackPerformed = false;
    private boolean isPickedUp;

    public MockSword(float x, float y) {
        super(x, y, 0);
        this.durability = 10;
        this.damage = 5;

    }
    @Override
    public void use(Character character) {
        this.isPickedUp = true;
    }

    public boolean isAttackPerformed() {
        return attackPerformed;
    }

    @Override
    public void performAttack(entity.Character attacker, Character target) {
        target.takeDamage(getDamage() + attacker.getDamage());
        attackPerformed = true;
    }


}
