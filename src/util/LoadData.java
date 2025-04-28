package util;

import entity.enemies.DudeMonster;
import entity.enemies.Enemy;
import core.GameModel;
import item.Item;
import item.armor.*;
import item.weapon.Sword;

import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.function.Function;

import static core.GameModel.TILE_SIZE;

/**
 * The LoadData class provides utility methods and static configurations for loading game resources
 */
public class LoadData {
    public static final String PLAYER_LEFT = "entity/Character/AnimationSheet_Character_Left.png";
    public static final String PLAYER_RIGHT = "entity/Character/AnimationSheet_Character_Right.png";
    public static final String DUDE_LEFT = "entity/Dude_Monster/DudeLeft.png";
    public static final String DUDE_RIGHT = "entity/Dude_Monster/DudeRight.png";
    public static final String LEVEL_1_DESIGN = "tiles/world_tileset_v2.png";
    public static final String LEVEL_1_PARKOUR = "world/parkour_1.txt";
    public static final String LEVEL_1_BATTLE = "world/battle_1.txt";
    public static final String LEVEL_1_ENEMY = "world/enemy_1.txt";
    public static final String LEVEL_1_ITEM = "world/items_1.txt";
    public static final String HEALTH_BAR_DAMAGE = "ui/MinimumDamage-Sheet.png";
    public static final String XP_BAR = "ui/ManaRegeneration-Sheet.png";
    public static final String ITEMS = "item/Items.png";
    public static final String SLOTS = "item/06 Border 03.png";

    /**
     * Loads an image from a specified file path within the application's resource directory.
     *
     * @param path the relative path of the image file to be loaded, starting from the root of the resource directory
     * @return the loaded {@link BufferedImage} if the file is successfully read; otherwise, returns null
     */
    public static BufferedImage getImage(String path) {
        BufferedImage img = null;

        InputStream is = LoadData.class.getResourceAsStream("/" + path);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    /**
     * Reads level data from a specified file and returns a 2D array representing the map tiles.
     * The method parses the file line by line, splitting values into integers
     * and assigning them to the corresponding positions in the 2D array.
     *
     * @param fileName the name of the file containing the level data to be loaded
     * @return a 2D integer array representing the level's tile map where each element corresponds to a tile
     */
    public static int[][] getLevelData(String fileName) {
        int[][] mapTileNum = new int[GameModel.TILES_IN_HEIGHT][100];

        try {
            InputStream input = LoadData.class.getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            int row = 0;
            while (row < GameModel.TILES_IN_HEIGHT) {
                String line = reader.readLine();
                if (line == null) break;

                String[] numbers = line.split(" ");
                for (int col = 0; col < Math.min(numbers.length, mapTileNum[0].length); col++) {
                    mapTileNum[row][col] = Integer.parseInt(numbers[col]);
                }
                row++;
            }

            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return mapTileNum;
    }

    /**
     * Loads enemy instances based on data from a specified file. The file is
     * processed to generate a 2D array of integers representing the level design,
     * where specific integer value corresponds to enemies. Each enemy is
     * created and positioned based on this data.
     *
     * @param fileName the name of the file containing the level data used to populate enemies
     * @return an ArrayList of Enemy instances created based on the level data
     */
    public static ArrayList<Enemy> loadEnemies(String fileName) {
        ArrayList<Enemy> monsters = new ArrayList<>();
        int[][] enemyData = getLevelData(fileName);

        for (int row = 0; row < enemyData.length; row++) {
            for (int col = 0; col < enemyData[0].length; col++) {
                if (enemyData[row][col] == 1) {
                    monsters.add(new DudeMonster(col * TILE_SIZE, row * TILE_SIZE));
                }
            }
        }

        return monsters;
    }

    /**
     * Loads item instances based on data from a specified file. The file is
     * processed to generate a 2D array of integers representing the level design,
     * where specific integer value corresponds items. Each item
     * is created and positioned based on this data.
     *
     * @param fileName the name of the file containing the level data used to populate items
     * @return an ArrayList of Item instances created based on the level data
     */
    public static ArrayList<Item> loadItems(String fileName) {
        ArrayList<Item> items = new ArrayList<>();
        int[][] itemData = getLevelData(fileName);

        for (int row = 0; row < itemData.length; row++) {
            for (int col = 0; col < itemData[0].length; col++) {
                switch (itemData[row][col]) {
                    case 1 -> items.add(new Sword(col * TILE_SIZE, row * TILE_SIZE,itemData[row][col]-1));
                    case 2 -> items.add(new Helmet(col * TILE_SIZE, row * TILE_SIZE,itemData[row][col]-1));
                    case 3 -> items.add(new Chestplate(col * TILE_SIZE, row * TILE_SIZE,itemData[row][col]-1));
                    case 4 -> items.add(new Leggings(col * TILE_SIZE, row * TILE_SIZE,itemData[row][col]-1));
                    case 5 -> items.add(new Boots(col * TILE_SIZE, row * TILE_SIZE,itemData[row][col]-1));
                    case 6 -> items.add(new Shield(col * TILE_SIZE, row * TILE_SIZE,itemData[row][col]-1));
                }
            }
        }
        return items;
    }
}
