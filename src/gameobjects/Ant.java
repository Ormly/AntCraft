package gameobjects;

import interfaces.IMoveable;

public class Ant extends GameObject implements IMoveable
{
    public Ant(double xPos, double yPos, int radius)
    {
        super(xPos, yPos, radius);
    }

    public void move(double msElapsed)
    {

    }
}
