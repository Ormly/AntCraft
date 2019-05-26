package gameobjects;

import utilities.Constants;
import utilities.Timer;
import utilities.logging.AbstractLogger;
import utilities.logging.Logging;

import java.awt.*;
import java.util.ArrayList;

public class Ant extends GameObject
{
    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());
    public Ant()
    {
        super(Constants.NEST_X_POS, Constants.NEST_Y_POS, 0, 100.0, 15, new Color(128, 0, 0));

        this.healthStatus = Constants.MAX_ANT_HEALTH;
        this.maxHealth = Constants.MAX_ANT_HEALTH;
        this.damageFactor = 50.0;
        this.attackTimer = new Timer(1);

        this.state = State.CHILLING;
    }

    public void update(double lastFrameDuration)
    {
        super.update(lastFrameDuration);

        if(this.state == State.FIGHTING)
            return;

        ArrayList<GameObject> collisions = world.getCollisions(this);
        for(GameObject object: collisions)
        {
            if(object instanceof Bug)
            {
                logger.debug("Ant collided with a bug!");

                this.isMoving = false;

                this.opponent = (Bug)object;
                this.state = State.FIGHTING;
            }
        }
    }
}
