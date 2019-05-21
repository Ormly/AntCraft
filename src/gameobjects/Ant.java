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
        FIGHTING,
        CHILLING
    }

    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());
    private State state;
    private Bug opponent = null;

    public Ant(double xPos, double yPos)
    {
        super(Constants.NEST_X_POS, Constants.NEST_Y_POS, 0, 40, 15, new Color(128, 0, 0));
        this.state = State.CHILLING;
        this.damageFactor = 50.0;
        this.healthStatus = 100.0;
        this.attackTimer = new Timer(1);
    }

    public void update(double lastFrameDuration)
    {
        super.update(lastFrameDuration);

        if(this.state == State.FIGHTING)
        {
            if(this.attackTimer.hasExpired()) // attack in fixed intervals
            {
                this.attackTimer.start();
                logger.debug("Ant is fighting bug!");

                if(this.opponent != null)
                {
                    if(this.opponent.isDead())
                    {
                        this.logger.debug("opponent is dead!");
                        this.opponent = null;
                        this.state = State.CHILLING;
                    }
                    else
                        this.opponent.damage(this.damageFactor);
                }
            }
        }
        ArrayList<GameObject> collisions = world.getCollisions(this);
        for(GameObject object: collisions)
        {
            if(object instanceof Bug)
            {
                logger.debug("Collided with a bug!");

                this.isMoving = false;
                this.xPos = this.previousXPos;
                this.yPos = this.previousYPos;

                this.opponent = (Bug)object;
                this.state = State.FIGHTING;
            }
        }
    }
}
