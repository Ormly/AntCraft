package gameobjects;

import utilities.Constants;
import utilities.Timer;

import java.util.ArrayList;

public abstract class Powerup extends GameObject
{

    protected boolean isDead;
    public Timer timer;

    //TODO add type to constructor, I don't know how that works with JSON
    public Powerup(double xPos, double yPos)
    {
        super(xPos, yPos, (3 * Math.PI) / 2, Constants.POWERUP_RADIUS);
        this.isDead = false;
    }

    @Override
    public void update(double lastFrameDuration)
    {
    }

    protected boolean handleCollisionWithAnt()
    {
        ArrayList<GameObject> collisions = this.getCollisions();
        if(!collisions.isEmpty() && collisions.get(0) instanceof Ant)
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean isVisible()
    {
        return !isDead;
    }

    @Override
    public boolean isDead()
    {
        return isDead;
    }
}

