package ui;

import core.ResourceManager;
import utilities.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverMenu extends GUIMenu
{
    private final int WIDTH = 500;
    private final int HEIGHT = 200;

    private BufferedImage image;

    public GameOverMenu(boolean win)
    {
        this.setPosition(Constants.SCREEN_WIDTH / 2 - WIDTH / 2, 100);
        this.setSize(WIDTH,HEIGHT);
        if(win)
            this.image = ResourceManager.getInstance().getImage("win");
        else
            this.image = ResourceManager.getInstance().getImage("lose");
    }

    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        g.drawImage(this.image,(int)this.getX(),(int)this.getY(),null);
    }
}
