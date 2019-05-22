package gameobjects;

import main.GameWorld;
import utilities.Timer;
import utilities.logging.AbstractLogger;
import utilities.logging.Logging;

import java.awt.*;

public abstract class GameObject
{
    protected enum State
    {
        FIGHTING,
        CHILLING,
        HUNTING
    }

    public static GameWorld world;

    protected AbstractLogger logger = Logging.getLogger(this.getClass().getName());

    protected double xPos;
    protected double yPos;
    protected int radius;
    protected double angle;
    protected double speed;

    protected double healthStatus;
    protected double damageFactor;
    protected Timer attackTimer;
    protected State state;
    protected GameObject opponent = null;


    protected Color color;

    protected double destinationXPos;
    protected double destinationYPos;

    //think about whether we need this
    protected double previousXPos;
    protected double previousYPos;

    protected boolean isMoving;
    protected boolean isVulnerable;
    protected boolean isDead = false;

    public GameObject(double xPos, double yPos, double angle, double speed, int radius, Color color)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
        this.speed = speed;
        this.radius = radius;
        this.color = color;

        isMoving = false;
        isVulnerable = false;
    }

    public void setDestination(double destinationXPos, double destinationYPos)
    {
        logger.debug("destination is set: " + destinationXPos + " - " + destinationYPos);

        this.destinationXPos = destinationXPos;
        this.destinationYPos = destinationYPos;

        angle = Math.atan2(this.destinationYPos - yPos, this.destinationXPos - xPos);

        this.state = State.HUNTING;
    }

    public void update(double lastFrameDuration)
    {
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
                        this.state = State.HUNTING;
                    }
                    else
                        this.opponent.damage(this.damageFactor);
                }
            }
        }
        else if(this.state == State.HUNTING)
        {
            double differenceX = Math.abs(xPos - destinationXPos);
            double differenceY = Math.abs(yPos - destinationYPos);

            if(differenceX < 3 && differenceY < 3)
            {
                isMoving = false;
                return;
            }
            previousXPos = xPos;
            previousYPos = yPos;
//            this.logger.debug("elapsed: "+ lastFrameDuration);
            double updatedX = xPos + Math.cos(angle) * speed * lastFrameDuration;
            double updatedY = yPos + Math.sin(angle) * speed * lastFrameDuration;

            xPos = updatedX;
            yPos = updatedY;
        }
    }

    public void damage(double damageFactor)
    {
        this.healthStatus -= damageFactor;
        logger.debug("Bug health status: " + this.healthStatus);
        if(this.healthStatus <= 0)
        {
            logger.debug("Bug is dead!");
            this.isDead = true;
        }
    }

    public boolean isDead()
    {
        return isDead;
    }

    public double getXPos() { return this.xPos; }
    //public void setXPos(double xPos) { this.xPos = xPos;}

    public double getYPos() { return this.yPos; }
    //public void setYPos(double yPos) { this.yPos = yPos; }

    //public double getDestinationXPos() { return this.destinationXPos; }
    //public double getDestinationYPos() { return this.destinationYPos; }

    //public double getPreviousXPos() { return this.previousXPos; }
    //public void setPreviousXPos(double previousXPos) { this.previousXPos = previousXPos; }

    //public double getPreviousYPos() { return this.previousYPos; }
    //public void setPreviousYPos(double previousYPos) { this.previousYPos = previousYPos; }

    public int getRadius() { return this.radius; }
    //public void setRadius(int radius) { this.radius = radius; }

    public double getAngle() { return this.angle; }
    //public void setAngle(double angle) { this.angle = angle; }

    public double getSpeed() { return this.speed; }
    //public void setSpeed(double speed) { this.speed = speed; }

    public Color getColor() { return this.color; }
    public void setColor(Color color) { this.color = color; }

    public boolean isMoving() { return this.isMoving; }
    //public void setIsMoving(boolean isMoving) { this.isMoving = isMoving; }

    public boolean isVulnerable() { return this.isVulnerable; }
    //public void setIsVulnerable(boolean isVulnerable) { this.isVulnerable = isVulnerable; }

    //public boolean hasDestination() { return this.hasDestination; }
    //public void setHasDestination(boolean hasDestination) { this.hasDestination = hasDestination; }
    public static void setGameWorld(GameWorld world){ GameObject.world = world; }
}
