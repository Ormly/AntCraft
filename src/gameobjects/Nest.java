package gameobjects;

import utilities.Constants;

import java.awt.*;

public class Nest extends GameObject
{
    public Nest(double xPos, double yPos, int radius)
    {
        super(Constants.NEST_X_POS, Constants.NEST_Y_POS, 0, 0,radius, new Color(0, 100, 0));
    }

    @Override
    public void update(double lastFrameDuration)
    {
        // Does nothing
    }
}
