package gameobjects;

public abstract class HUDObject
{
    protected int xPos;
    protected int yPos;

    protected int width;
    protected int height;

    protected boolean isVisible;

    public boolean isVisible()
    {
        return this.isVisible;
    }
    public void setIsVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    public int getxPos()
    {
        return xPos;
    }
    public int getyPos()
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
}
