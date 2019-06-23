package gameobjects;

import main.GameWorld;

public abstract class HUDObject
{
    protected double xPos;
    protected double yPos;

    protected int width;
    protected int height;

    protected boolean isVisible;

    protected static GameWorld gameWorld;

    public HUDObject()
    {
        
    }

    public HUDObject(GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;
    }

    public boolean isVisible()
    {
        return this.isVisible;
    }
    public void setIsVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    public double getXPos()
    {
        return xPos;
    }
    public double getYPos()
    {
        return yPos;
    }

    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }

    public void setGameWorld(GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;
    }
}
