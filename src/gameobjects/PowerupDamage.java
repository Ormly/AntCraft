package gameobjects;

import core.ResourceManager;
import ui.Icon;
import utilities.Constants;
import utilities.Timer;

public class PowerupDamage extends Powerup
{
    private boolean isFrozen;

    public PowerupDamage(double xPos, double yPos)
    {
        super(xPos,yPos);
        this.icon = new Icon(ResourceManager.getInstance().getImage("power"), -30, 0.3);
        this.timer = new Timer(Constants.POWERUP_DAMAGE_DURATION);
        this.isFrozen = false;
    }

    @Override
    public void update(double lastFrameDuration)
    {
        if(this.timer.hasExpired() && this.timer.hasStarted())
        {
            this.isDead = true;
            this.world.powerUpDamage(-(Constants.POWERUP_DAMAGE_FACTOR));
        }

        if(isFrozen)
            return;

        if(this.handleCollisionWithAnt())
        {
            this.timer.start();
            this.world.powerUpDamage(Constants.POWERUP_DAMAGE_FACTOR);
            this.isFrozen = true;
        }
    }

    @Override
    public boolean isVisible()
    {
        return !this.isFrozen;
    }
}
