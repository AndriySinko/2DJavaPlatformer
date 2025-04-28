package gui;

import entity.enemies.Enemy;
import map.levels.BattlePhase;
import map.levels.Level;
import util.LoadData;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static core.GameModel.SCALE;
import static util.Constants.DIRECTION.LEFT;
import util.Generated;
/**
 * The EnemyView class is responsible for rendering enemy characters during the battle phase.
 *
 */
@Generated
public class EnemyView {
    private BufferedImage[][] dudeLeftAnim, dudeRightAnim;
    private Map<Enemy, EnemyAnimationState> animationStates = new HashMap<>();
    /**
     * Constructs an instance of the EnemyView class and loads images for animation.
     */
    public EnemyView() {
        loadEnemyImg();
    }

    private void loadEnemyImg() {
        BufferedImage imgLeft = LoadData.getImage(LoadData.DUDE_LEFT);
        BufferedImage imgRight = LoadData.getImage(LoadData.DUDE_RIGHT);
        dudeRightAnim = new BufferedImage[6][8];
        dudeLeftAnim = new BufferedImage[6][8];
        for (int i = 0; i < dudeRightAnim.length; i++) {
            for (int j = 0; j < dudeRightAnim[i].length; j++) {
                dudeRightAnim[i][j] = imgRight.getSubimage(j * 32, i * 32, 32, 32);
                dudeLeftAnim[i][j] = imgLeft.getSubimage((7 - j) * 32, i * 32, 32, 32);
            }
        }
    }


    /**
     * Renders the current state of the enemie.
     * This method is specifically designed to handle levels of type {@code BattlePhase}.
     * Active enemies will have their animation state updated and will be rendered.
     * Inactive enemies are removed from the animation states tracking.
     *
     * @param g the {@code Graphics} object used for rendering the enemies
     * @param level the {@code Level} where the enemies are located; must be an instance of {@code BattlePhase}
     */
    public void render(Graphics g, Level level) {
        if (level instanceof BattlePhase) {
            BattlePhase battlePhase = (BattlePhase) level;
            for (Enemy enemy : battlePhase.getEnemies()) {
                if (enemy.isActive()) {

                    EnemyAnimationState state = animationStates.computeIfAbsent(enemy,
                                            k -> new EnemyAnimationState(enemy));

                    state.updateAnimationTick(enemy);
                    state.setAnimation(enemy);
                    renderEnemy(g,enemy,state,battlePhase);
                }
            }
            animationStates.keySet().removeIf(enemy -> !enemy.isActive());
        }
    }

    private void renderEnemy(Graphics g,Enemy enemy, EnemyAnimationState state, BattlePhase battlePhase) {
        BufferedImage sprite;
        if (enemy.getDirection() == LEFT) {
            sprite = dudeLeftAnim[state.getAction()][state.getAniIndex()];
        } else {
            sprite = dudeRightAnim[state.getAction()][state.getAniIndex()];
        }

        g.drawImage(sprite,
                (int) (enemy.getHitBox().x - enemy.getxHitBoxOffset()) - battlePhase.getXLvlOffset(),
                (int) (enemy.getHitBox().y - enemy.getyHitBoxOffset()),
                (int) (enemy.getWidth() * SCALE),
                (int) (enemy.getHeight() * SCALE),
                null);

        //DEBUG
        //drawHitBox(g,enemy,battlePhase.getXLvlOffset());
        //drawAttackHitBox(g,enemy,battlePhase.getXLvlOffset());
    }
    private void drawHitBox(Graphics g, Enemy enemy, int xLvlOffset) {
        Rectangle2D.Float hb = enemy.getHitBox();
        g.setColor(Color.RED);
        g.drawRect((int)hb.x - xLvlOffset, (int)hb.y, (int)hb.width, (int)hb.height);
    }

    private void drawAttackHitBox(Graphics g, Enemy enemy, int lvlOffset) {
        Rectangle2D.Float hb = enemy.getAttackHitBox();
        g.setColor(Color.YELLOW);
        g.drawRect((int)hb.x-lvlOffset,(int)hb.y,(int)hb.width,(int)hb.height);
    }
}
