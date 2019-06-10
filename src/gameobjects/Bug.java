package gameobjects;

import core.ResourceManager;
import ui.Icon;
import utilities.Constants;
import utilities.Timer;

import java.awt.*;
import java.util.ArrayList;

public class Bug extends GameObject
{
    private enum State
    {
        HUNTING,
        ATTACKING
    }

    private State state;

    public Bug(double xPos, double yPos, double angle, double speed)
    {
        super(xPos,yPos,angle,speed,22,new Color(220, 20, 60));

        this.healthStatus = Constants.MAX_BUG_HEALTH;
        this.maxHealth = Constants.MAX_BUG_HEALTH;
        this.damageFactor = 20;
        this.attackTimer = new Timer(2);
        this.icon = new Icon(ResourceManager.getInstance().getImage("ladybug"), -30, 0.50);

        this.opponent = this.world.getNest();   // a bug always has an opponent

        this.state = State.HUNTING;
    }

    @Override
    public boolean isVisible()
    {
        // a bug is visible as long as it's alive
        return !this.isDead();
    }

    public void update(double elapsed)
    {
        switch(this.state)
        {
            case HUNTING:

                if(this.handleCollisionWithAntOrNest())
                {
                    this.setState(State.ATTACKING);
                }
                else
                {
                    this.followOpponent();
                    this.moveToDestination(elapsed);
                }
                break;

            case ATTACKING:
                if(this.handleCollisionWithAntOrNest())
                {
                    if(this.attackOpponent())   // ant is dead
                    {
                        this.opponent = this.world.getNest();
                        this.setState(State.HUNTING);
                    }
                }
                else
                {
                    this.setState(State.HUNTING);
                }
                break;
        }
    }

    public void followOpponent()
    {
        this.setDestination(this.opponent.getXPos(),this.opponent.getYPos());
    }

    private void setState(State newState)
    {
        logger.debug(this.state + " -> " + newState);
        this.state = newState;
    }

    // if collided with ant or nest, set it as opponent and return true, otherwise set nest as opponent, and return false
    private boolean handleCollisionWithAntOrNest()
    {
        ArrayList<GameObject> collisions = this.getCollisions();
        if(!collisions.isEmpty() && (collisions.get(0) instanceof Ant || collisions.get(0) instanceof Nest))
        {
            this.opponent = collisions.get(0);
            return true;
        }

        return false;
    }
}
