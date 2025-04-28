package map.levels;

import entity.Player;
import core.GameModel;
import map.tiles.Tile;
import util.LoadData;

/**
 * Represents a ParkourPhase level in the game.
 */
public class ParkourPhase implements Level {
    private float startX,startY;
    private float checkpointX, checkpointY;
    private boolean hasCheckpoint = false;
    private Tile[] tiles;
    private String lvlDataPath;
    private int[][] lvlData;
    private boolean completed;
    private int xLvlOffset;
    private int maxLvlOffsetX;
    private int leftBorder;
    private int rightBorder;
    private Player player;

    /**
     * Constructs a new ParkourPhase instance with the specified level data and tiles.
     *
     * @param lvlData the file path or source containing the level data to be loaded.
     * @param tiles an array of Tile objects that represent the tiles in the level.
     */
    public ParkourPhase(String lvlData, Tile[] tiles) {
        this.lvlDataPath = lvlData;
        this.leftBorder = (int)(0.4 * GameModel.GAME_WIDTH);
        this.rightBorder = (int)(0.6 * GameModel.GAME_WIDTH);
        this.tiles = tiles;
    }

    /**
     * Initiates the ParkourPhase by loading necessary data, setting player position.
     *
     * @param player the player instance which will participate in this phase
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

    /**
     * Updates the current state of the ParkourPhase.
     * This method focuses on handling camera adjustments,
     * and checking whether player is alive to determine whether respawn is needed.
     */
    @Override
    public void update() {
        checkAlive();
        updateCamera();
    }

    private void checkAlive() {
        if(player.isDead()) respawnPlayer();
    }

    /**
     * Marks the parkour phase as complete and rewards the player with experience points.
     *
     */
    @Override
    public void complete() {
        this.completed = true;
        player.gainXP(100);
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

    private void loadData() {
        this.lvlData = LoadData.getLevelData(lvlDataPath);
    }

    @Override
    public int[][] getLvlData() {
        return lvlData;
    }

    @Override
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

    @Override
    public void setStartPoint(float x, float y) {
        this.startX = x;
        this.startY = y;
    }

    /**
     * Sets a checkpoint in the current level at the specified coordinates.
     *
     * @param x the x-coordinate of the checkpoint in tile grid units
     * @param y the y-coordinate of the checkpoint in tile grid units
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
     *
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
}
