package mock;

import entity.Character;
import entity.Killable;

public class MockCharacter extends Character implements Killable {
    private boolean pushed;
    private boolean stopped;

    public MockCharacter(float x, float y, float width, float height) {
        super(x, y, width, height);
        initHitBox(x, y, width, height);
        initAttackHitBox(x, y, width, height);
        speed = 2.0f;
        jumpSpeed = -11.0f;
        hp = 50;
        MAX_HP = 50;
        damage = 10;
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) hp = 0;
    }

    @Override
    public void getKilled() {

    }

    public boolean wasPushed() {
        return pushed;
    }

    public boolean wasStopped() {
        return stopped;
    }

}