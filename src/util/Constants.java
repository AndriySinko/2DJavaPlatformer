package util;

import entity.enemies.DudeMonster;
import entity.enemies.Enemy;

public class Constants {
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int IDLE_BLINK = 1;
        public static final int WALK = 2;
        public static final int RUNNING = 3;
        public static final int DUCK = 4;
        public static final int JUMPING = 5;
        public static final int HURT = 6;
        public static final int DIE = 7;
        public static final int ATTACK = 8;

        public static int GetAniAmount(int playerAction) {
            return switch (playerAction) {
                case IDLE -> 2;
                case IDLE_BLINK -> 2;
                case WALK -> 4;
                case RUNNING -> 8;
                case DUCK -> 6;
                case JUMPING -> 8;
                case HURT -> 3;
                case DIE -> 8;
                case ATTACK -> 8;
                default -> 1;
            };
        }
    }
    public static class DIRECTION {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
    }

    public static class EnemyConstants {
        public static final int IDLE=0;
        public static final int WALK=1;
        public static final int ATTACK =2;
        public static final int HURT =3;
        public static final int DEATH =4;
        public static final int JUMP =5;

        public static int GetAniAmount(Enemy enemy, int action) {
            if (enemy instanceof DudeMonster) {
                return switch (action) {
                    case IDLE -> 4;
                    case WALK -> 6;
                    case ATTACK -> 4;
                    case HURT -> 4;
                    case DEATH -> 8;
                    case JUMP -> 8;
                    default -> 1;
                };
            }
            return 0;
        }
    }

}
