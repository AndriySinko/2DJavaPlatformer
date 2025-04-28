package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerConstantsTest {
    @Test
    void testGetAniAmount_Idle() {
        int playerAction = Constants.PlayerConstants.IDLE;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(2, result);
    }

    @Test
    void testGetAniAmount_IdleBlink() {
        int playerAction = Constants.PlayerConstants.IDLE_BLINK;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(2, result);
    }

    @Test
    void testGetAniAmount_Walk() {
        int playerAction = Constants.PlayerConstants.WALK;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(4, result);
    }

    @Test
    void testGetAniAmount_Running() {
        int playerAction = Constants.PlayerConstants.RUNNING;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(8, result);
    }

    @Test
    void testGetAniAmount_Duck() {
        int playerAction = Constants.PlayerConstants.DUCK;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(6, result);
    }

    @Test
    void testGetAniAmount_Jumping() {
        int playerAction = Constants.PlayerConstants.JUMPING;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(8, result);
    }

    @Test
    void testGetAniAmount_Hurt() {
        int playerAction = Constants.PlayerConstants.HURT;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(3, result);
    }

    @Test
    void testGetAniAmount_Die() {
        int playerAction = Constants.PlayerConstants.DIE;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(8, result);
    }

    @Test
    void testGetAniAmount_Attack() {
        int playerAction = Constants.PlayerConstants.ATTACK;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(8, result);
    }

    @Test
    void testGetAniAmount_DefaultCase() {
        int playerAction = 999;
        int result = Constants.PlayerConstants.GetAniAmount(playerAction);
        assertEquals(1, result);
    }
}