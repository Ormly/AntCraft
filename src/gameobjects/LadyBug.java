package gameobjects;

import core.ResourceManager;
import ui.Icon;
import utilities.Constants;
import utilities.Timer;

public class LadyBug extends Bug
{
    public LadyBug(double xPos, double yPos)
    {
        super(xPos,yPos,Constants.LADYBUG_RADIUS);

        this.speed = Constants.LADYBUG_SPEED;
        this.healthStatus = Constants.MAX_LADYBUG_HEALTH;
        this.maxHealth = Constants.MAX_LADYBUG_HEALTH;
        this.damageFactor = Constants.LADYBUG_DAMAGE_FACTOR;
        this.attackTimer = new Timer(Constants.LADYBUG_ATTACK_FREQ);
        this.icon = new Icon(ResourceManager.getInstance().getImage("ladybug"), -30, 0.3);
    }
}
