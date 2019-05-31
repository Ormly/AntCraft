package gameobjects;

import utilities.Constants;
import utilities.Timer;
import utilities.logging.AbstractLogger;
import utilities.logging.Logging;

import java.awt.*;
import java.util.ArrayList;

public class Ant extends GameObject
{
    private enum State
    {
        MOVING,
        IN_NEST,
        WAITING,
        ATTACKING,
        HUNTING,
        DEAD,
        RETURNING
    }

    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());
    private State state;

    public Ant()
    {
        super(Constants.NEST_X_POS, Constants.NEST_Y_POS, 0, 100.0, 15, new Color(128, 0, 0));

        this.healthStatus = Constants.MAX_ANT_HEALTH;
        this.maxHealth = Constants.MAX_ANT_HEALTH;
        this.damageFactor = 50.0;
        this.attackTimer = new Timer(1);

        this.state = State.IN_NEST;
    }

    public void update(double lastFrameDuration)
    {
        switch(this.state)
        {
            case MOVING:
                if(this.moveToDestination(lastFrameDuration))
                    setState(State.WAITING);
                if(this.hasCollidedWithBug())
                    setState(State.ATTACKING);
                break;
            case HUNTING:
                if(this.hasCollidedWithBug())
                    setState(State.ATTACKING);
                else
                    this.moveToDestination(lastFrameDuration);
                break;
            case WAITING:
                if(this.hasCollidedWithBug())
                    setState(State.ATTACKING);
                break;
            case ATTACKING:
                if(this.attackOpponent())
                    setState(State.WAITING);
                break;
            case RETURNING:
                if(arrivedBackToNest())
                {
                    setState(State.IN_NEST);
                    this.xPos = Constants.NEST_X_POS;
                    this.yPos = Constants.NEST_Y_POS;
                }
                else
                    this.moveToDestination(lastFrameDuration);
                break;
        }

        if(this.healthStatus <= 0)
        {
            setState(State.DEAD);
        }

    }

    @Override
    public boolean isVisible()
    {
        // an Ant is visible as long as it's not in nest
        return !this.isInNest();
    }

    @Override
    public void setDestination(double destinationXPos, double destinationYPos)
    {
        double xDest = destinationXPos;
        double yDest = destinationYPos;

        GameObject destObj = this.world.getGameObjectAtCoordinate((int) destinationXPos, (int) destinationYPos);
        if(destObj == null)
            this.setState(State.MOVING);
        else if(destObj instanceof Bug)
            this.setState(State.HUNTING);
        else if(destObj instanceof Nest)
            this.setState(State.RETURNING);
        else if(destObj instanceof Ant)
        {
            xDest+=10;
            yDest+=10;
            this.setState(State.MOVING);
        }

        super.setDestination(xDest, yDest);
    }

    public boolean isInNest()
    {
        return this.state == State.IN_NEST;
    }

    private void setState(State newState)
    {
        logger.debug("Transition to " + newState);
        this.state = newState;
    }

    private boolean hasCollidedWithBug()
    {
        ArrayList<GameObject> collisions = this.getCollisions();
        return (!collisions.isEmpty() && collisions.get(0) instanceof Bug);
    }

    private boolean arrivedBackToNest()
    {
        logger.debug("checkifarrived");
        return this.world.getGameObjectAtCoordinate((int)this.xPos,(int)this.yPos) instanceof Nest;
    }

    public boolean isDead()
    {
        return this.state == State.DEAD;
    }
}
