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
        INNEST,
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

        this.state = State.INNEST;
    }

    public void update(double lastFrameDuration)
    {
        switch(this.state)
        {
            case MOVING:
                // move, check if arrived, if yes changeg to waiting
                if(this.moveToDestination(lastFrameDuration))
                    this.state = State.WAITING;
                // if collided with bug attack
                if(this.hasCollidedWithBug())
                    this.state = State.ATTACKING;
                // if collided with powerup, pickup
                break;
            case HUNTING:
                // move
                if(this.hasCollidedWithBug())
                    this.state = State.ATTACKING;
                else
                    this.moveToDestination(lastFrameDuration);
                // if collided with bug state->attack
                break;
            case DEAD:
                break;
            case INNEST:
                break;
            case WAITING:
                // if collided with bug state->attack
                if(this.hasCollidedWithBug())
                    this.state = State.ATTACKING;
                break;
            case ATTACKING:
                // attack, if opponent dead, is waiting
                break;
            case RETURNING:
                // move, if arrived state->innest
                if(arrivedBackToNest())
                    this.state = State.INNEST;
                else
                    this.moveToDestination(lastFrameDuration);
                break;
        }

        if(this.healthStatus <= 0)
        {
            this.state = State.DEAD;
        }

    }

    private boolean hasCollidedWithBug()
    {
        ArrayList<GameObject> collisions = this.getCollisions();
        return (!collisions.isEmpty() && collisions.get(0) instanceof Bug);
    }

    private boolean arrivedBackToNest()
    {
        return this.world.getGameObjectAtCoordinate((int)this.xPos,(int)this.yPos) instanceof Nest;
    }
}
