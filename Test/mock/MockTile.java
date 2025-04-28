package mock;

import map.tiles.Tile;

public class MockTile extends Tile {
    private boolean collision;

    public MockTile(boolean collision) {
        this.collision = collision;
    }

    @Override
    public boolean isCollision() {
        return collision;
    }
}
