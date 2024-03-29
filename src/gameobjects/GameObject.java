package gameobjects;

import main.GameWorld;
import org.json.simple.JSONObject;
import utilities.Constants;
import utilities.Timer;
import utilities.logging.Logger;
import utilities.logging.Logging;
import ui.Icon;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
    public static GameWorld world;

    protected static Logger logger = Logging.getLogger(GameObject.class.getName());

    protected double xPos;
    protected double yPos;
    protected double angle;
    protected double speed;
    protected int radius;

    protected double maxHealth;
    protected double healthStatus;
    protected double damageFactor;
    protected double healingFactor;
    protected Timer attackTimer;
    protected Timer healingTimer;
    protected GameObject opponent = null;

    protected Icon icon;

    protected double destinationXPos;
    protected double destinationYPos;

    //think about whether we need this
    protected double previousXPos;
    protected double previousYPos;

    public GameObject(double xPos, double yPos, double angle, int radius)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
        this.radius = radius;
    }

    public void setDestination(double destinationXPos, double destinationYPos)
    {
        this.destinationXPos = destinationXPos;
        this.destinationYPos = destinationYPos;

        angle = Math.atan2(this.destinationYPos - yPos, this.destinationXPos - xPos);
    }

    public abstract void update(double lastFrameDuration);

    protected ArrayList<GameObject> getCollisions()
    {
        return this.world.getCollisions(this);
    }

    // returns true if reached destination, false otherwise
    protected boolean moveToDestination(double lastFrameDuration)
    {
        double differenceX = Math.abs(xPos - destinationXPos);
        double differenceY = Math.abs(yPos - destinationYPos);

        if(differenceX < 3 && differenceY < 3)
            return true;

        previousXPos = xPos;
        previousYPos = yPos;
//            this.logger.debug("elapsed: "+ lastFrameDuration);
        double updatedX = xPos + Math.cos(angle) * speed * lastFrameDuration;
        double updatedY = yPos + Math.sin(angle) * speed * lastFrameDuration;

        xPos = updatedX;
        yPos = updatedY;

        return false;
    }

    protected void heal()
    {
        if(this.healingTimer.hasExpired()) // attack in fixed intervals
        {
            this.healingTimer.start();
            if(this.healthStatus < this.maxHealth)
                this.healthStatus+=this.healingFactor;

            if(this.healthStatus > this.maxHealth) // just in case we overshoot
                this.healthStatus = this.maxHealth;
        }
    }

    // returns true if opponent is dead, false otherwise
    protected boolean attackOpponent()
    {
        if(this.attackTimer.hasExpired()) // attack in fixed intervals
        {
            this.attackTimer.start();

            if(this.opponent != null)
            {
                this.opponent.damage(this.damageFactor);

                world.spawnImpactEffect(this.getXPos()+this.getRadius()*Math.cos(this.getAngle()),
                                        this.getYPos()+this.getRadius()*Math.sin(this.getAngle()));

                if(this.opponent.isDead())
                {
                    this.logger.debug("opponent is dead!");
                    return true;
                }
            }
        }
        return false;
    }

    public void damage(double damageFactor)
    {
        this.healthStatus -= damageFactor;
//        logger.debug("Bug health status: " + this.healthStatus);
        if(this.healthStatus <= 0)
        {
            logger.debug("I'm dead!");
        }
    }

    public void powerUpHeal()
    {
        if(this.healthStatus < this.maxHealth)
            this.healthStatus+= Constants.POWERUP_HEALING_FACTOR;

        if(this.healthStatus > this.maxHealth) // just in case we overshoot
            this.healthStatus = this.maxHealth;
    }

    public void powerUpDamage(double factor)
    {
        this.damageFactor = this.damageFactor + factor;
    }

    public abstract boolean isVisible();

    public boolean isDead()
    {
        return this.healthStatus <= 0;
    }

    public double getXPos() { return this.xPos; }

    public double getYPos() { return this.yPos; }

    public int getRadius() { return this.radius; }

    public double getAngle() { return this.angle; }

    public double getSpeed() { return this.speed; }


    public double getMaxHealth()
    {
        return this.maxHealth;
    }

    public double getHealthStatus()
    {
        return this.healthStatus;
    }

    public static void setGameWorld(GameWorld world){ GameObject.world = world; }

    public Icon getIcon()
    {
        return this.icon;
    }

    public JSONObject getASJSONObject()
    {
            JSONObject object = new JSONObject();
            object.put("type", this.getClass().getName());
            object.put("xPos", this.xPos);
            object.put("yPos", this.yPos);

            return object;
    }

    public static GameObject parseFromJSON(JSONObject obj)
    {
        GameObject b = null;
        try
        {
            Class cls = Class.forName((String) obj.get("type"));
            double xPos = (double) obj.get("xPos");
            double yPos = (double) obj.get("yPos");

            b = (GameObject) cls.getConstructor(double.class, double.class).newInstance(xPos,yPos);

        }
        catch(ClassNotFoundException e)
        {
            logger.error("class "+ (String) obj.get("type") + " not found");
        }
        catch(IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e)
        {
            logger.error(e.getMessage());
        }

        return b;
    }
}
