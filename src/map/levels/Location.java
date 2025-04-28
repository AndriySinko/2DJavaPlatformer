package map.levels;

import core.GameController;
import entity.Player;
import map.tiles.CheckpointAction;
import map.tiles.ObstacleAction;
import map.tiles.LevelCompleteAction;
import map.tiles.Tile;
import util.LoadData;
import java.awt.image.BufferedImage;

/**
 * Represents a game location that manages various levels, tiles, and phases.
 * A location can include parkour and battle phases that the player interacts with.
 */
public class Location {
    private LocationConfig config;
    private Level currentPhase;
    private Tile[] tiles;
    private PhaseFactory parkourFactory;
    private PhaseFactory battleFactory;
    private int currLvlIndex;
    private boolean entered;

    /**
     * Constructs a new Location instance using the specified configuration.
     *
     * @param locationConfig the configuration object that contains details necessary
     *                       for initializing the location, such as tile setup and level data paths
     */
    public Location(LocationConfig locationConfig) {
        this.config = locationConfig;
        this.parkourFactory = new ParkourPhaseFactory();
        this.battleFactory = new BattlePhaseFactory();
        this.tiles = loadLocationTiles();
    }


    /**
     * Allows the player to enter this location, initializing the first parkour phase.
     *
     * @param player the player attempting to enter the location
     */
    void enter(Player player) {
            this.currLvlIndex = 0;
            loadParkourPhase(player);
            entered = true;
    };
    void complete(GameController gameController) {
    }

    /**
     * Updates the current phase of the location for the specified player.
     * This method manages transitions between phases (ParkourPhase and BattlePhase)
     * If the game has reached the final level, it resets and restarts from the initial level.
     *
     * @param player the player instance for whom the location state and phases are being updated
     */
    public void update(Player player) {
        if(!entered){
            enter(player);
        }

        if (currentPhase.isCompleted()){
            if (currentPhase instanceof ParkourPhase){
                loadBattlePhase(player);
            }else if (currentPhase instanceof BattlePhase){
                if (currLvlIndex + 1 < config.getTotalLevels()) {
                    currLvlIndex++;
                    loadParkourPhase(player);
                }else{
                    enter(player); //start game again
                }
            }
        }

        currentPhase.update();
    }
    private Tile[] loadLocationTiles() {
        BufferedImage img = LoadData.getImage(config.getSpritePath());
        tiles = new Tile[config.getNumberOfTiles()];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int index = i*16+j;

                tiles[index]=new Tile();
                tiles[index].setImage(img.getSubimage(j*16, i*16, 16, 16));
                tiles[index].setCollision(false);

                if (index == 58){
                    tiles[index].setAction(new LevelCompleteAction());
                } else if (index == 56 || index ==72) {
                    tiles[index].setAction(new CheckpointAction());
                }else if (index == 148 || index==150 || index == 180 || index == 212 ) {
                    tiles[index].setAction(new ObstacleAction());
                }
            }
        }
        // Setting solid tiles
        for (int i : config.getSolidTiles()) {
            tiles[i].setCollision(true);
        }


        return tiles;
    }

    private void loadParkourPhase(Player player) {
        String levelPath = config.getParkourLvLDataPath(currLvlIndex);
        currentPhase = parkourFactory.createPhase(levelPath, null, tiles);
        currentPhase.start(player);
    }

    private void loadBattlePhase(Player player) {
        String levelPath = config.getBattleLvLDataPath(currLvlIndex);
        String enemiesPath = config.getBattleEnemyDataPath(currLvlIndex);
        currentPhase = battleFactory.createPhase(levelPath, enemiesPath, tiles);
        currentPhase.start(player);
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public Tile getTile(int index) {
        if (index >= 0 && index < tiles.length) return tiles[index];
        return null;
    }

    public int getxLvlOffset() {
        return currentPhase.getXLvlOffset();
    }

    public Level getCurrentPhase() {
        return currentPhase;
    }

    public boolean isEntered() {
        return entered;
    }
}
