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
        this.xPos = Constants.SCREEN_WIDTH - 120;
        this.yPos = 15;
        this.width = 100;
        this.height = 69;

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
