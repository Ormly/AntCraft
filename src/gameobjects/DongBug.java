package gameobjects;

import core.ResourceManager;
import ui.Icon;
import utilities.Constants;
import utilities.Timer;

public class DongBug extends Bug
{
    public DongBug(double xPos, double yPos)
    {
        super(xPos,yPos);

        this.speed = Constants.DONG_SPEED;
        this.healthStatus = Constants.MAX_DONG_HEALTH;
        this.maxHealth = Constants.MAX_DONG_HEALTH;
        this.damageFactor = Constants.DONG_DAMAGE_FACTOR;
        this.attackTimer = new Timer(Constants.DONG_ATTACK_FREQ);
        this.icon = new Icon(ResourceManager.getInstance().getImage("kingdong"), -30, 0.8);
    }
}
