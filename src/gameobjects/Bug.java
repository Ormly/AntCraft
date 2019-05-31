package gameobjects;

import utilities.Constants;
import utilities.Timer;

import java.awt.*;
import java.util.ArrayList;

public class Bug extends GameObject
{
    private enum State
    {
        HUNTING,
        FIGHTING,
        DEAD
    }

    private State state;

    public Bug(double xPos, double yPos, double angle, double speed)
    {
        super(xPos,yPos,angle,speed,22,new Color(220, 20, 60));

        this.healthStatus = Constants.MAX_BUG_HEALTH;
        this.maxHealth = Constants.MAX_BUG_HEALTH;
        this.damageFactor = 20;
        this.attackTimer = new Timer(2);


        this.setDestination(Constants.NEST_X_POS, Constants.NEST_Y_POS);
        this.state = State.HUNTING;
    }

    @Override
    public boolean isVisible()
    {
        // a bug is visible as long as it's alive
        return this.state != State.DEAD;
    }

    public void update(double elapsed)
    {

    }

    private boolean hasCollidedWithAnt()
    {
        ArrayList<GameObject> collisions = this.getCollisions();
        return (!collisions.isEmpty() && collisions.get(0) instanceof Ant);
    }

    private boolean hasCollidedWithNest()
    {
        ArrayList<GameObject> collisions = this.getCollisions();
        return (!collisions.isEmpty() && collisions.get(0) instanceof Nest);
    }

}
