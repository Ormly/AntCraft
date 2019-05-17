package gameobjects;

import java.awt.*;

public class Bug extends GameObject
{
    public Bug(double xPos, double yPos, int radius)
    {
        super(xPos,yPos,0,15,radius,new Color(96, 96, 255));
    }
}
