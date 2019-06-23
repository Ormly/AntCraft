package gameobjects;

import main.GameWorld;
import utilities.Constants;

public class AntStockIndicator extends HUDObject
{
    protected int currentNumOfAnts;

    public AntStockIndicator(GameWorld gameWorld)
    {
        super(gameWorld);

        this.isVisible = true;
        this.xPos = Constants.ANTSTOCKINDICATOR_X_POS;
        this.yPos = Constants.ANTSTOCKINDICATOR_Y_POS;
        this.width = Constants.ANTSTOCKINDICATOR_WIDTH;
        this.height = Constants.ANTSTOCKINDICATOR_HEIGHT;
    }

    public void update()
    {
        this.currentNumOfAnts = this.gameWorld.getNumOfAnts();
    }

    public int getNumOfAnts()
    {
        return this.currentNumOfAnts;
    }
}
