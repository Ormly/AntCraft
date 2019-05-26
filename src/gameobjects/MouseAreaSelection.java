package gameobjects;

public class MouseAreaSelection extends HUDObject
{
    public MouseAreaSelection()
    {
        this.isVisible = false;
    }

    public MouseAreaSelection(int xPos, int yPos, int width, int height)
    {
        this.update(xPos,yPos,width,height);
    }

    public void update(int xPos, int yPos, int width, int height)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.isVisible = true;
    }
}
