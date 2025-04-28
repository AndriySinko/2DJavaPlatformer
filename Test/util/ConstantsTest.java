package util;

import entity.enemies.DudeMonster;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ConstantsTest {
    @Test
    void getPlayerAnimationFrameTest() {
        assertEquals(8, Constants.PlayerConstants.GetAniAmount(Constants.PlayerConstants.JUMPING));
    }

    @Test
    void getEnemyAnimationFrameTest() {
        DudeMonster monster = new DudeMonster(48, 48);
        assertEquals(4, Constants.EnemyConstants.GetAniAmount(monster, Constants.EnemyConstants.ATTACK));
    }
}