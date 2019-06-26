package gameobjects;

import core.ResourceManager;
import ui.Icon;

public class PowerupHeal extends Powerup
{
    public PowerupHeal(double xPos, double yPos)
    {
        super(xPos,yPos);
        this.icon = new Icon(ResourceManager.getInstance().getImage("health"), -30, 0.3);
    }

    @Override
    public void update(double lastFrameDuration)
    {
        if(this.handleCollisionWithAnt())
        {
            this.world.powerUpHeal();
            this.isDead = true;
        }
    }
}
