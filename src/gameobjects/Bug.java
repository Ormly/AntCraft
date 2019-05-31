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
        ATTACKING,
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

        this.opponent = this.world.getNest();   // a bug always has an opponent

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
        switch(this.state)
        {
            case HUNTING:

                if(this.handleCollisionWithAntOrNest())
                    this.setState(State.ATTACKING);
                else
                {
                    this.moveToDestination(elapsed);
                    setDestination(this.opponent.getXPos(),this.opponent.getYPos());
                }

                break;

            case ATTACKING:

                if(this.handleCollisionWithAntOrNest())
                {
                    if(this.attackOpponent())
                    {
                        setState(State.HUNTING);    // opponent is dead -> go for nest
                        this.opponent = this.world.getNest();
                    }
                }
                else
                    this.setState(State.HUNTING); // opponent moved away -> chase it

                break;
        }

        if(this.healthStatus <= 0)
            setState(State.DEAD);
    }

    @Override
    public void setDestination(double destinationXPos, double destinationYPos)
    {
        this.setState(State.HUNTING);
        super.setDestination(destinationXPos, destinationYPos);
    }

    private void setState(State newState)
    {
        logger.debug("Transition to " + newState);
        this.state = newState;
    }


    private boolean handleCollisionWithAntOrNest()
    {
        ArrayList<GameObject> collisions = this.getCollisions();
        if(!collisions.isEmpty() && (collisions.get(0) instanceof Ant || collisions.get(0) instanceof Nest))
        {
            this.opponent = collisions.get(0);
            this.setDestination(this.opponent.getXPos(),this.opponent.getYPos());
            return true;
        }

        return false;
    }
}
