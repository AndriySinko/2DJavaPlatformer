package entity.enemies;

import entity.Player;
import map.levels.Level;

public interface EnemyBehaviour {
    void update(Enemy enemy, Level level, Player player);
}
