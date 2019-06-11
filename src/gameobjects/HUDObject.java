package gameobjects;

import main.GameWorld;

public abstract class HUDObject
{
    protected int xPos;
    protected int yPos;

    protected int width;
    protected int height;

    protected boolean isVisible;

    protected static GameWorld gameWorld;

    public boolean isVisible()
    {
        return this.isVisible;
    }
    public void setIsVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    public int getXPos()
    {
        return xPos;
    }
    public int getYPos()
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
