package gui;

import entity.Player;
import util.LoadData;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static core.GameModel.SCALE;
import static util.Constants.DIRECTION.*;
import static util.Constants.PlayerConstants.*;

import util.Generated;
/**
 * This class is responsible for rendering and animating the player.
 * It manages animations, sprites, and the player's graphical state based on their actions and movement directions.
 */
@Generated
public class PlayerView {
    private BufferedImage[][] rightAnim, leftAnim;
    private int aniTick=0, aniSpeed=10, aniIndex=0, action;
    private Player player;
    private final int xHitBoxOffset = (int)(9*SCALE*2);
    private final int yHitBoxOffset = (int)(4*SCALE*3);

    /**
     * Constructs a new PlayerView object associated with a specific Player instance.
     *
     * @param player the Player instance.
     */
    public PlayerView(Player player) {
        this.player = player;
        loadAnimation();
    }

    private void loadAnimation() {
        BufferedImage imgLeft = LoadData.getImage(LoadData.PLAYER_LEFT);
        BufferedImage imgRight = LoadData.getImage(LoadData.PLAYER_RIGHT);
        rightAnim = new BufferedImage[9][8];
        leftAnim = new BufferedImage[9][8];
        for (int i = 0; i < rightAnim.length; i++) {
            for (int j = 0; j < rightAnim[i].length; j++) {
                rightAnim[i][j] = imgRight.getSubimage(j * 32, i * 32, 32, 32);
                leftAnim[i][j] = imgLeft.getSubimage((7-j) * 32, i * 32, 32, 32);
            }
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetAniAmount(action)) {
                aniIndex = 0;
            }
        }
    }

    private void setAnimation() {
        int start = action;

        if (!player.isOnGround()) {
            action = JUMPING;
        }
        else if (player.isLeft() || player.isRight()) {
            action = RUNNING;
        }
        else {
            action = IDLE;
        }

        if (start != action) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    /**
     * Renders the player sprite on the screen at the adjusted position considering the level offset.
     * Draws the appropriate animation frame, determined by the player's current state and direction.
     *
     * @param g the {@code Graphics} object used for rendering the player
     * @param lvlOffset the horizontal offset of the level, used to adjust the player's rendered position
     */
    public void render(Graphics g, int lvlOffset) {
        updateAnimationTick();
        setAnimation();
        BufferedImage sprite;
        if (player.getDirection() == LEFT) {sprite =leftAnim[action][aniIndex];}
        else {sprite = rightAnim[action][aniIndex];}


        g.drawImage(sprite,
                (int)(player.getHitBox().x - xHitBoxOffset) - lvlOffset,
                (int)(player.getHitBox().y - yHitBoxOffset),
                (int)(player.getWidth()*2),
                (int)(player.getHeight()*2),
                null);

        //FOR DEBUG
//        drawHitBox(g, lvlOffset);
//        drawAttackHitBox(g, lvlOffset);
    }

    private void drawHitBox(Graphics g, int lvlOffset) {
        Rectangle2D.Float hb = player.getHitBox();
        g.setColor(Color.RED);
        g.drawRect((int)hb.x - lvlOffset, (int)hb.y, (int)hb.width, (int)hb.height);
    }

    private void drawAttackHitBox(Graphics g, int lvlOffset) {
        Rectangle2D.Float hb = player.getAttackHitBox();
        g.setColor(Color.YELLOW);
        g.drawRect((int)hb.x-lvlOffset,(int)hb.y,(int)hb.width,(int)hb.height);
    }
}
