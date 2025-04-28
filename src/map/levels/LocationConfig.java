package map.levels;

import java.util.List;

/**
 * Represents the configuration for a game location.
 * This class holds all necessary data required to configure and initialize levels and tiles in a location.
 */
public class LocationConfig {
    //Tiles
    private String spritePath;
    private int numberOfTiles;
    private int[] solidTiles;

    //Levels
    private List<String> parkourLvLDataPath;
    private List<String> battleLvLDataPath;
    private List<String> battleEnemyDataPath;

    /**
     * Constructs a LocationConfig object with these configurations:
     *
     * @param spritePath the file path to the sprite sheet used for tiles in this location
     * @param numberOfTiles the total number of tiles in this location
     * @param solidTiles an array of tile indices that are marked as solid, which cannot be passed through
     * @param parkourLvLDataPath a list of file paths containing data for parkour levels
     * @param battleLvLDataPath a list of file paths containing data for battle levels
     * @param battleEnemyDataPath a list of file paths containing data for enemies in each battle level
     */
    public LocationConfig(String spritePath, int numberOfTiles, int[] solidTiles, List<String> parkourLvLDataPath, List<String> battleLvLDataPath, List<String> battleEnemyDataPath) {
        this.spritePath = spritePath;
        this.numberOfTiles = numberOfTiles;
        this.solidTiles = solidTiles;
        this.parkourLvLDataPath = parkourLvLDataPath;
        this.battleLvLDataPath = battleLvLDataPath;
        this.battleEnemyDataPath = battleEnemyDataPath;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public int getNumberOfTiles() {
        return numberOfTiles;
    }

    public int[] getSolidTiles() {
        return solidTiles;
    }

    public String getParkourLvLDataPath(int index) {
        return parkourLvLDataPath.get(index);
    }

    public String getBattleLvLDataPath(int index) {
        return battleLvLDataPath.get(index);
    }

    public String getBattleEnemyDataPath(int index) {
        return battleEnemyDataPath.get(index);
    }

    public int getTotalLevels() {
        return parkourLvLDataPath.size();
    }
}
