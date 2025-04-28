package map.levels;

import entity.Player;
import entity.enemies.Enemy;
import core.CollisionChecker;
import core.GameModel;
import map.tiles.Tile;
import util.LoadData;

import java.util.List;

/**
 * The BattlePhase class represents a specific level phase in the game where the player
 * engages in combat with enemies.
 */
public class BattlePhase implements Level {
    private float startX,startY;
    private float checkpointX,checkpointY;
    private boolean hasCheckpoint = false;
    private Tile[] tiles;
    private String lvlDataPath;
    private String enemiesPath;
    private int[][] lvlData;
    private List<Enemy> enemies;
    private boolean completed;
    private int xLvlOffset;
    private int maxLvlOffsetX;
    private int leftBorder;
    private int rightBorder;
    private Player player;

    /**
     * Constructs a new BattlePhase instance with the specified level data, enemy data, and tiles.
     *
     * @param lvlData The file path to the level data used in this battle phase.
     * @param enemies The file path to the enemy data for this battle phase.
     * @param tiles An array of Tile objects representing the tiles in the game's environment.
     */
    public BattlePhase(String lvlData, String enemies, Tile[] tiles) {
        this.lvlDataPath = lvlData;
        this.enemiesPath = enemies;
        this.leftBorder = (int)(0.5 * GameModel.GAME_WIDTH);
        this.rightBorder = (int)(0.5 * GameModel.GAME_WIDTH);
        this.tiles = tiles;
    }


    /**
     * Initializes the battle phase by loading level data, setting the player's start position,
     * and preparing the level for interaction.
     *
     * @param player The player object to be positioned and associated with this battle phase.
     */
    @Override
    public void start(Player player) {
        loadData();
        this.xLvlOffset = 0;
        this.maxLvlOffsetX = (lvlData[0].length - GameModel.TILES_IN_WIDTH) * GameModel.TILE_SIZE;
        this.player = player;
        player.setPosition(startX, startY,this);
        this.completed = false;
    }

    private void updatePlayerAttack(){
        if(player.isDead()){
                respawnPlayer();
                return;
        }
        if (player.isAttacking()){
            for (Enemy enemy: enemies){
                if (enemy.isActive() && enemy.readyToTakeDamage()){
                    player.attack(enemy);
                    if (!enemy.isActive()) player.gainXP(enemy.getXPReward());
                }
            }
        }
    }

    /**
     * Updates the current state of the battle phase.
     * This method focuses on handling camera adjustments, enemy actions, and player attack dynamics.
     */
    @Override
    public void update() {
        updateCamera();
        updateEnemies();
        updatePlayerAttack();
    }

    private void updateEnemies(){
        for (Enemy enemy : enemies){
            if (enemy.isActive()){
                enemy.update(this,player);
                CollisionChecker.handlePlayerCharacterCollision(player, enemy);
            }
        }
    }

    /**
     * Marks the current battle phase as complete if all enemies have been defeated.
     * Player is rewarded with 100 experience points (XP) for successfully completing the phase.
     */
    @Override
    public void complete() {
        if(enemies.stream().allMatch(enemy -> !enemy.isActive())) {
            this.completed = true;
            player.gainXP(100);
        }

    }

    private void loadData(){
        this.lvlData = LoadData.getLevelData(lvlDataPath);
        this.enemies = LoadData.loadEnemies(enemiesPath);
    }

    private void updateCamera() {
        int diff = (int)(player.getHitBox().x - xLvlOffset);

        if(diff > rightBorder){
            xLvlOffset += diff - rightBorder;
        }else if(diff < leftBorder){
            xLvlOffset += diff - leftBorder;
        }

        //checking borders
        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        }else if(xLvlOffset < 0){
            xLvlOffset = 0;
        }
    }

    @Override
    public int[][] getLvlData() {
        return lvlData;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public int getXLvlOffset() {
        return xLvlOffset;
    }

    @Override
    public Tile getTile(int tileIndex) {
        return tiles[tileIndex];
    }

    /**
     * Sets the starting point of the player or entity in the game environment.
     *
     * @param x The x-coordinate of the starting point.
     * @param y The y-coordinate of the starting point.
     */
    @Override
    public void setStartPoint(float x, float y) {
        this.startX = x;
        this.startY = y;
    }

    /**
     * Sets a checkpoint location in the game environment based on the given coordinates.
     *
     * @param x The x-coordinate of the checkpoint in tile units.
     * @param y The y-coordinate of the checkpoint in tile units.
     */
    @Override
    public void setCheckpoint(float x, float y) {
        this.checkpointX = x*GameModel.TILE_SIZE;
        this.checkpointY = (y-1)*GameModel.TILE_SIZE;
        this.hasCheckpoint = true;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
    /**
     * Respawns the player by restoring his health and repositioning him within the level.
     *
     */
    @Override
    public void respawnPlayer() {
        player.setHp(player.getMAX_HP());
        player.setDead(false);
        if (hasCheckpoint) {
            player.setPosition(checkpointX,checkpointY,this);
        }else {
            player.setPosition(startX, startY, this);
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
