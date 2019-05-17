package gameobjects;

import utilities.logging.AbstractLogger;
import utilities.logging.Logging;

import java.awt.*;

public abstract class GameObject
{
    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());

    private double xPos;
    private double yPos;
    private int radius;
    private double angle;
    private double speed;

    private Color color;

    private double destinationXPos;
    private double destinationYPos;

    private double previousXPos;
    private double previousYPos;

    private boolean isMoving;
    private boolean isVulnerable;
    private boolean hasDestination;

    public GameObject(double xPos, double yPos, double angle, double speed, int radius, Color color)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
        this.speed = speed;
        this.radius = radius;
        this.color = color;

        isMoving = false;
        hasDestination = false;
        isVulnerable = false;
    }

    public void setDestination(double destinationXPos, double destinationYPos)
    {
        isMoving = true;
        hasDestination = true;

        this.destinationXPos = destinationXPos;
        this.destinationYPos = destinationYPos;

        angle = Math.atan2(this.destinationYPos - yPos, this.destinationXPos - xPos);
    }

    public void move(double lastFrameDuration)
    {
        //TODO handle collisions

        if(!isMoving)
            return;

        if(hasDestination)
        {
            double differenceX = Math.abs(xPos - destinationXPos);
            double differenceY = Math.abs(yPos - yPos);

            if(differenceX < 3 && differenceY < 3)
            {
                isMoving = false;
                return;
            }
        }

        previousXPos = xPos;
        previousYPos = yPos;

        double updatedX = xPos + Math.cos(angle) * speed * lastFrameDuration;
        double updatedY = yPos + Math.sin(angle) * speed * lastFrameDuration;

        xPos = updatedX;
        yPos = updatedY;

        //this.logger.debug("oldX/newX: "+this.getPreviousXPos()+"/"+updatedX+" oldY/newY: "+this.getPreviousYPos()+"/"+updatedY);
    }

    public void update(double lastFrameDuration) {}

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

    public boolean hasDestination() { return this.hasDestination; }
    //public void setHasDestination(boolean hasDestination) { this.hasDestination = hasDestination; }
}
