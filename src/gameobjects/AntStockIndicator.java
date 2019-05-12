package gameobjects;

public class AntStockIndicator extends HUDObject
{
    private int numOfAnts;

    public AntStockIndicator(int amount)
    {
        this.numOfAnts = amount;
    }

    public int getNumOfAnts()
    {
        return this.numOfAnts;
    }
}
