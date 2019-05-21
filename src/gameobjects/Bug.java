package gameobjects;

import java.awt.*;

public class Bug extends GameObject
{
    public Bug(double xPos, double yPos, double angle, double speed)
    {
        super(xPos,yPos,angle,speed,22,new Color(220, 20, 60));
        this.damageFactor = 0.02;
        this.healthStatus = 1000.0;
    }
}
