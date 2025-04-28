package mock;

import core.GameModel;
import entity.Player;
import item.ItemHandler;

public class MockGameModel extends GameModel {
    public boolean pickUpCalled = false;
    public boolean dropItemCalled = false;
    public int selectedSlot = 0;
    public boolean attackTriggered = false;

    private MockPlayer player = new MockPlayer(0, 0, 32, 32);
    private MockItemHandler itemHandler = new MockItemHandler();

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public ItemHandler getItemHandler() {
        return itemHandler;
    }
}