package gui;

import entity.Player;
import item.Item;
import item.inventory.Inventory;
import util.LoadData;

import java.awt.*;
import java.awt.image.BufferedImage;
import static core.GameModel.*;
import util.Generated;
/**
 * The UI class is responsible for rendering HUD for the game
 */
@Generated
public class UI {
    private BufferedImage[][] damageAni;
    private BufferedImage[] xpSprites;
    private int aniSpeed = 5, aniTick = 0;
    private static final int BAR_WIDTH = 64;
    private static final int BAR_HEIGHT = 16;
    private static final int SCALE = 4;
    private int previousHealth = 70;
    private boolean isAnimatingDamage = false;
    private int damageAnimationFrame = 0;

    private BufferedImage[] slotImages;
    private BufferedImage[] itemImages;
    private static final int SLOT_SIZE = 64;
    private static final int SLOT_SPACING = 5;
    private static final int SLOTS_START_X = GAME_WIDTH - (6 * SLOT_SIZE + 5 * SLOT_SPACING) - 20;
    private static final int SLOTS_START_Y = 20;
    private static final int ITEM_SIZE = 48;

    /**
     * Constructs an instance of the UI class and initializes its graphical components.
     */
    public UI() {
        loadBars();
    }

    private void loadBars() {
        BufferedImage img = LoadData.getImage(LoadData.HEALTH_BAR_DAMAGE);
        damageAni = new BufferedImage[8][6];

        for (int i = 0; i < damageAni.length; i++) {
            for (int j = 0; j < damageAni[i].length; j++) {
                int y = (i * 6 + j) * BAR_HEIGHT;
                damageAni[i][j] = img.getSubimage(0, y, BAR_WIDTH, BAR_HEIGHT);
            }
        }

        img = LoadData.getImage(LoadData.XP_BAR);
        xpSprites = new BufferedImage[41];

        for (int i = 0; i < xpSprites.length; i++) {
            xpSprites[i] = img.getSubimage(0, i * BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
        }

        img = LoadData.getImage(LoadData.SLOTS);
        slotImages = new BufferedImage[2];
        slotImages[0] = img.getSubimage(64, 64, 64, 64);
        slotImages[1] = img.getSubimage(0, 64, 64, 64);
    }

    /**
     * Renders the user interface elements, including the player's health bar,
     * experience bar, and inventory slots.
     *
     * @param g       The Graphics object used for rendering on the screen.
     * @param player  The Player object containing the data to be rendered,
     *                such as health points, experience points, and inventory.
     */
    public void render(Graphics g, Player player) {
        renderHP(g,player.getHp());
        renderXP(g,player.getXp());
        renderInventorySlots(g, player.getInventory());
    }

    private void renderInventorySlots(Graphics g, Inventory inventory) {
        for (int slotNum = 1; slotNum <= 6; slotNum++) {
            int xPos = SLOTS_START_X + (slotNum-1) * (SLOT_SIZE + SLOT_SPACING);

            boolean isSelected = slotNum == inventory.getSelectedSlot();
            BufferedImage slotImg = isSelected ? slotImages[1] : slotImages[0];

            g.drawImage(slotImg,
                    xPos,
                    SLOTS_START_Y,
                    SLOT_SIZE,
                    SLOT_SIZE,
                    null);

            Item item = inventory.getItemInSlot(slotNum);
            if (item != null && itemImages != null && item.getSpriteIndex() < itemImages.length) {
                BufferedImage itemImg = itemImages[item.getSpriteIndex()];

                int itemX = xPos + (SLOT_SIZE - ITEM_SIZE) / 2;
                int itemY = SLOTS_START_Y + (SLOT_SIZE - ITEM_SIZE) / 2;

                g.drawImage(itemImg,
                        itemX,
                        itemY,
                        ITEM_SIZE,
                        ITEM_SIZE,
                        null);
            }
        }
    }

    private void renderHP(Graphics g, int playerHealth) {
        updateHealthTick(playerHealth);

        int currentStage = 7 - (playerHealth / 10);
        currentStage = Math.max(0, Math.min(7, currentStage));

        int frameToShow = isAnimatingDamage ? damageAnimationFrame : 0;
        g.drawImage(damageAni[currentStage][frameToShow],
                TILES_DEFAULT_SIZE,
                TILES_DEFAULT_SIZE,
                BAR_WIDTH * SCALE,
                BAR_HEIGHT * SCALE,
                null);

        drawHPText(g, playerHealth);
    }

    private void renderXP(Graphics g, int playerXP) {
        int currentStage = playerXP / 10;

        g.drawImage(xpSprites[currentStage],
                TILES_DEFAULT_SIZE,
                TILES_DEFAULT_SIZE+BAR_HEIGHT*SCALE+10,
                BAR_WIDTH * SCALE,
                BAR_HEIGHT * SCALE,
                null);

        drawXPText(g, playerXP);
    }

    private void drawHPText(Graphics g, int currentHP) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String text = currentHP + "/70";
        g.drawString(text,
                TILES_DEFAULT_SIZE + BAR_WIDTH * SCALE + 10,
                TILES_DEFAULT_SIZE + BAR_HEIGHT * 2);
    }

    private void drawXPText(Graphics g, int currentXP) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String text = currentXP + "/400";
        g.drawString(text,
                TILES_DEFAULT_SIZE + BAR_WIDTH * SCALE + 10,
                TILES_DEFAULT_SIZE + BAR_HEIGHT * SCALE+ 10 + (BAR_HEIGHT * SCALE)/2 + 8);
    }

    private void updateHealthTick(int currentHealth) {
        if (currentHealth < previousHealth) {
            isAnimatingDamage = true;
            damageAnimationFrame = 1;
            aniTick = 0;
        }
        previousHealth = currentHealth;


        if (isAnimatingDamage) {
            aniTick++;
            if (aniTick >= aniSpeed) {
                aniTick = 0;
                damageAnimationFrame++;

                if (damageAnimationFrame >= 5) {
                    isAnimatingDamage = false;
                    damageAnimationFrame = 0;
                }
            }
        }
    }

    public void setItemSprites(BufferedImage[] itemSprites) {
        this.itemImages = itemSprites;
    }
}