package map.levels;

import entity.Player;
import map.tiles.Tile;

public interface Level {
    void start(Player player);
    void complete();
    void update();
    boolean isCompleted();
    int[][] getLvlData();
    int getSpriteIndex(int x, int y);
    int getXLvlOffset();
    Tile getTile(int tileIndex);
    void setStartPoint(float x, float y);
    void setCheckpoint(float x, float y);
    void setPlayer(Player player);
    Player getPlayer();
    void respawnPlayer();
}