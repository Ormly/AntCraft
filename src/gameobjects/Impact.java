package gameobjects;

import utilities.DrawUtils;

import java.util.Random;

public class Impact extends GameObject
{
    private static final double GROWTH_RATE = 20;
    private static final double FINAL_SIZE = 10;
    private double finalSize;
    boolean isVisible;
    private double doubleRad = 1.0;

    public Impact(double x, double y)
    {
        super(x,y,0,1);
        this.isVisible = true;
        this.finalSize = (int)(FINAL_SIZE + DrawUtils.getRandomDouble()*20);
    }

    @Override
    public void update(double lastFrameDuration)
    {
        this.doubleRad+=GROWTH_RATE*lastFrameDuration;
        this.radius = (int)doubleRad;
//        logger.debug("impact " + this.getRadius());
    }

    @Override
    public boolean isVisible()
    {
        return true;
    }

    @Override
    public boolean isDead()
    {
        return this.radius >= finalSize;
    }
}
