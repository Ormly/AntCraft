package gameobjects;

import core.ResourceManager;
import ui.Icon;
import utilities.Constants;
import utilities.Timer;

public class SpiderBug extends Bug
{
    public SpiderBug(double xPos, double yPos)
    {
        super(xPos,yPos,Constants.SPIDER_RADIUS);

        this.speed = Constants.SPIDER_SPEED;
        this.healthStatus = Constants.MAX_SPIDER_HEALTH;
        this.maxHealth = Constants.MAX_SPIDER_HEALTH;
        this.damageFactor = Constants.SPIDER_DAMAGE_FACTOR;
        this.attackTimer = new Timer(Constants.SPIDER_ATTACK_FREQ);
        this.icon = new Icon(ResourceManager.getInstance().getImage("spider"), -30, 0.5);
    }
}