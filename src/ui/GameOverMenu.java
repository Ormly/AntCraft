package ui;

import utilities.Constants;
import utilities.DrawUtils;

import java.awt.*;

public class GameOverMenu extends GUIMenu
{
    private final int WIDTH = 500;
    private final int HEIGHT = 200;

    private String winorlose;

    public GameOverMenu(boolean win)
    {
        this.setPosition(Constants.SCREEN_WIDTH / 2 - WIDTH / 2, 100);
        this.setSize(WIDTH,HEIGHT);
        if(win)
            this.winorlose = "Congrats! you survived!";
        else
            this.winorlose = "Bummer! the nest was destroyed!";
    }

    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);

        String gameover = "GAME OVER!";
        g.setColor(Color.ORANGE);
        g.drawString(gameover, DrawUtils.getTextCenterX(gameover,g.getFont(),this.mainBox),(int)(this.mainBox.getY()+this.mainBox.getHeight()/2));
        g.drawString(this.winorlose, DrawUtils.getTextCenterX(this.winorlose,g.getFont(),this.mainBox),(int)(this.mainBox.getY()+this.mainBox.getHeight()/2+ 30));


    }
}
