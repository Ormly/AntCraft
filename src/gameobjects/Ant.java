package gameobjects;

import utilities.Constants;
import utilities.logging.AbstractLogger;
import utilities.logging.Logging;

import java.awt.*;

public class Ant extends GameObject
{
    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());

    public Ant(double xPos, double yPos)
    {
        super(Constants.NEST_X_POS, Constants.NEST_Y_POS, 0, 20, 15, new Color(128, 0, 0));
    }

    public void update(double lastFrameDuration) { super.update(lastFrameDuration); }
}
