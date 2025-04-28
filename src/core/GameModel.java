package core;

import entity.Player;
import item.ItemHandler;
import map.levels.Location;
import map.levels.LocationConfig;
import util.LoadData;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the core game model containing all game state, configurations, and entities.
 * This class manages the player, location, items, and game world properties.
 * <p>
 * The class includes initialization of the player, location, and item handler. It also contains constants
 * used for game scaling, tiles, and dimensions. The model is the central part of the game's architecture
 * and serves as the primary data source for other game components.
 */
public class GameModel {


    //CONFIGURATION
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 30;
    public final static int TILES_IN_HEIGHT = 15;
    public final static int TILE_SIZE = (int)(TILES_DEFAULT_SIZE*SCALE);
    public final static int GAME_WIDTH = TILE_SIZE*TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILE_SIZE*TILES_IN_HEIGHT;

    private Player player;
    private Location location;
    private LocationConfig locationConfig;
    private ItemHandler itemHandler;

    /**
     * Constructor initializes core classes for the game, such as the player, location, and item handler.
     */
    public GameModel() {
        initLocation();
        initPlayer();
        this.itemHandler = new ItemHandler();
    }

    private void initPlayer(){
        player = new Player(96,96,TILE_SIZE,TILE_SIZE);
    }

    private void initLocation(){
        int[] solidTiles = {0,1,2,3,4,5,6,7,8,9,10,11,
                            16,17,18,19,20,21,22,23,24,25,26,27,
                            32,33,34,35,36,37,38,39,40,41,42,43,
                            50,55,71,99,115,131,
                            148,150,164,166,180,196,212,226};

        List<String> parkourLvlDataPath = new ArrayList<String>();
        List<String> battleLvLDataPath = new ArrayList<String>();
        List<String> battleEnemyDataPath = new ArrayList<String>();

        parkourLvlDataPath.add(LoadData.LEVEL_1_PARKOUR);
        battleLvLDataPath.add(LoadData.LEVEL_1_BATTLE);
        battleEnemyDataPath.add(LoadData.LEVEL_1_ENEMY);

        this.locationConfig = new LocationConfig(
                LoadData.LEVEL_1_DESIGN,256, solidTiles, parkourLvlDataPath, battleLvLDataPath, battleEnemyDataPath);
        location = new Location(locationConfig);
    }

    /**
     * Updates all game state including the location, player, and items.
     * This method is called once per game tick to ensure consistent updates
     * for all game components.
     */
    public void update() {
        location.update(player);
        player.update(location.getCurrentPhase());
        itemHandler.update(player, location.getCurrentPhase());
    }

    public Player getPlayer() {
        return player;
    }
    public Location getLocation() {
        return location;
    }
    public ItemHandler getItemHandler() {
        return itemHandler;
    }
}
