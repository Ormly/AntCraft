package gameobjects;

import utilities.Constants;
import utilities.Timer;

import java.awt.*;
import java.util.ArrayList;

public class Bug extends GameObject
{
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

    public void update(double elapsed)
    {
        super.update(elapsed);

        if(this.state == State.FIGHTING)
            return;

        ArrayList<GameObject> collisions = world.getCollisions(this);
        for(GameObject object: collisions)
        {
            if(object instanceof Ant)
            {
                logger.debug("Bug collided with Ant!");

                this.isMoving = false;
//                this.xPos = this.previousXPos;
//                this.yPos = this.previousYPos;

                this.opponent = (Ant)object;
                this.state = State.FIGHTING;
            }
        }

    }
}
