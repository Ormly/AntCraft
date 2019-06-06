package gameobjects;

import core.ResourceManager;
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
        RETURNING
    }

    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());
    private State state;

    public Ant()
    {
        super(Constants.NEST_X_POS, Constants.NEST_Y_POS, 0, 100.0, 15, new Color(128, 0, 0));

        this.healthStatus = Constants.MAX_ANT_HEALTH;
        this.maxHealth = Constants.MAX_ANT_HEALTH;
        this.damageFactor = Constants.ANT_DAMAGE_FACTOR;
        this.attackTimer = new Timer(Constants.ANT_ATTACK_FREQ);
        this.healingTimer = new Timer(Constants.ANT_HEALING_FREQ);
        this.state = State.IN_NEST;
    }

    public void update(double lastFrameDuration)
    {
        switch(this.state)
        {
            case MOVING:

                if(this.moveToDestination(lastFrameDuration))
                    setState(State.WAITING);
                if(this.handleCollisionWithBug())
                    setState(State.ATTACKING);
                break;

            case HUNTING:

                if(this.handleCollisionWithBug())
                    setState(State.ATTACKING);
                else
                    this.moveToDestination(lastFrameDuration);
                break;

            case WAITING:

                if(this.handleCollisionWithBug())
                    setState(State.ATTACKING);
                break;

            case ATTACKING:

                if(this.handleCollisionWithBug())
                    if(this.attackOpponent())
                        setState(State.WAITING);    // opponent is dead
                else    // moved away from opponent
                {
                    setState(State.WAITING);
                    this.opponent = null;
                }
                break;

            case RETURNING:

                if(arrivedBackToNest())
                {
                    setState(State.IN_NEST);
                    this.xPos = Constants.NEST_X_POS; // place ant in middle of nest
                    this.yPos = Constants.NEST_Y_POS;
                }
                else
                    this.moveToDestination(lastFrameDuration);
                break;

            case IN_NEST:
                this.heal();
                break;
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
//        logger.debug(this.state + " -> " + newState);
        this.state = newState;
    }

    private boolean handleCollisionWithBug()
    {
        for(GameObject obj:this.getCollisions()){
            if(obj instanceof Bug){
                this.opponent = obj;
                return true;
            }
        }

        return false;
    }

    private boolean arrivedBackToNest()
    {
        return this.world.getGameObjectAtCoordinate((int)this.xPos,(int)this.yPos) instanceof Nest;
    }
}
