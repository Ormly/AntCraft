package gameobjects;

import main.GameWorld;
import utilities.Constants;

public class AntStockIndicator extends HUDObject
{
    protected int currentNumOfAnts;
    protected int maxNumOfAnts;

    public AntStockIndicator(GameWorld gameWorld)
    {
        super(gameWorld);

        this.isVisible = true;
        this.xPos = Constants.ANTSTOCKINDICATOR_X_POS;
        this.yPos = Constants.ANTSTOCKINDICATOR_Y_POS;
        this.width = Constants.ANTSTOCKINDICATOR_WIDTH;
        this.height = Constants.ANTSTOCKINDICATOR_HEIGHT;

        this.maxNumOfAnts = this.gameWorld.getNumOfAnts();
        this.currentNumOfAnts = this.maxNumOfAnts;
    }

    public void decrement()
    {
        if(currentNumOfAnts > 0)
            currentNumOfAnts--;
    }

    public void increment()
    {
        if(currentNumOfAnts < maxNumOfAnts)
            currentNumOfAnts++;
    }

    public int getNumOfAnts()
    {
        return this.currentNumOfAnts;
    }


}
