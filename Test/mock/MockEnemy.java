package mock;

import entity.Player;
import entity.enemies.Enemy;
import entity.enemies.EnemyBehaviour;
import map.levels.Level;

public class MockEnemy extends Enemy {
    private boolean canAttackPlayer = true;
    private boolean attacking;
    private int damage;
    private MockSword currentWeapon;
    private EnemyBehaviour behaviour;

    public MockEnemy(float x, float y, float width, float height) {
        super(x, y, width, height);
        initHitBox(x, y, width, height);
    }


    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) setActive(false);
    }

    @Override
    protected boolean canSeePlayer(Level level, Player player) {
        return true;
    }

    @Override
    protected boolean canAttackPlayer(Player player) {
        return canAttackPlayer;
    }
    public void setCanAttackPlayer(boolean canAttackPlayer) {
        this.canAttackPlayer = canAttackPlayer;
    }
    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }


    @Override
    public int getXPReward() {
        return 10;
    }

    @Override
    public void getKilled() {

    }
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public MockSword getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(MockSword currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public EnemyBehaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(EnemyBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public void performAttack(MockPlayer player) {
        if (currentWeapon != null) {
            currentWeapon.performAttack(this,player);
        } else {
            player.takeDamage(damage);
        }
    }

}
