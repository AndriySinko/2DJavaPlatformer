package mock;

import entity.Player;
import map.levels.Level;

public class MockPlayer extends Player {
    private int hp = 70;
    private boolean readyToTakeDamage;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isReadyToTakeDamage() {
        return readyToTakeDamage;
    }

    public void setReadyToTakeDamage(boolean readyToTakeDamage) {
        this.readyToTakeDamage = readyToTakeDamage;
    }

    public void takeDamage(int damage) {
        if (readyToTakeDamage) {
            hp = Math.max(0, hp - damage);
        }
    }

    public MockPlayer(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void update(Level level) {
        updateCooldowns();
    }
}
