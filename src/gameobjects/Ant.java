package gameobjects;

import utilities.Constants;
import utilities.logging.AbstractLogger;
import utilities.logging.Logging;

import java.awt.*;

public class Ant extends GameObject
{
    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());

    public Ant(double xPos, double yPos, int radius)
    {
        super(Constants.NEST_X_POS, Constants.NEST_Y_POS, 0, 30, radius, new Color(128, 0, 0));
        //this.setIsVulnerable(true);
    }

    public void move(double lastFrameDuration) { super.move(lastFrameDuration); }

    public void update(double lastFrameDuration) { this.move(lastFrameDuration); }
}
