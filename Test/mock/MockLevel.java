package mock;

import entity.Player;
import map.levels.Level;
import map.tiles.Tile;
import core.GameModel;

public class MockLevel implements Level {
    private int[][] lvlData;
    private  boolean[][] solidTiles;
    private Tile[] tiles;
    private Player player;
    private boolean completed;

    public MockLevel() {
        lvlData = new int[GameModel.TILES_IN_HEIGHT][GameModel.TILES_IN_WIDTH];
        tiles = new Tile[256];
        this.solidTiles = new boolean[20][20];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
            tiles[i].setCollision(i < 50);
        }
    }


    public void setSolidTile(int x, int y) {
        if (x >= 0 && y >= 0 && x < solidTiles.length && y < solidTiles[0].length) {
            solidTiles[x][y] = true;
        }
    }



    public boolean isSolidTile(int x, int y) {
        if (x < 0 || y < 0 || x >= solidTiles.length || y >= solidTiles[0].length) {
            return false;
        }
        return solidTiles[x][y];
    }



    @Override
    public void start(Player player) {
        this.player = player;
        player.setPosition(48, 48, this);
    }

    @Override
    public void update() {}

    @Override
    public void complete() {
        completed = true;
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
        return 0;
    }

    @Override
    public Tile getTile(int tileIndex) {
        return tiles[tileIndex];
    }

    @Override
    public void setStartPoint(float x, float y) {}

    @Override
    public void setCheckpoint(float x, float y) {}

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void respawnPlayer() {
        player.setPosition(48, 48, this);
    }
}
