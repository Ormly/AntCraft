package gameobjects;

import core.ResourceManager;
import ui.Icon;
import utilities.Constants;

import java.util.ArrayList;

public class Powerup extends GameObject
{

    private enum State
    {
        PICKEDUP,
        WAITING
    }

    PowerUpType type;
    State state;

    public Powerup(double xPos, double yPos, PowerUpType type)
    {
        super(xPos, yPos, 0, Constants.POWERUP_RADIUS);
        this.type = type;
        this.state = State.WAITING;
        this.icon = new Icon(ResourceManager.getInstance().getImage("health"), -30, 0.3);
    }

    @Override
    public void update(double lastFrameDuration)
    {
        switch(this.state)
        {
            case WAITING:
                if(this.handleCollisionWithAnt())
                    this.state = State.PICKEDUP;
        }
    }

    private boolean handleCollisionWithAnt()
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
        return (this.state == State.PICKEDUP ? false : true);
    }

    public PowerUpType getType()
    {
        return this.type;
    }

    @Override
    public boolean isDead()
    {
        return !isVisible();
    }
}

