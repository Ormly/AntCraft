package ui;

import core.ResourceManager;
import utilities.Constants;
import utilities.logging.Logging;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu extends GUIMenu
{
    private final int PADDING = 10;
    private BufferedImage logo;

    public Menu()
    {
        this.mainBox = new Rectangle(Constants.MENU_X_POS,Constants.MENU_Y_POS,Constants.MENU_WIDTH,Constants.MENU_HEIGHT);
        this.logo = ResourceManager.getInstance().getImage("menulogo");
        this.buttons = new ArrayList<>();
        MenuButton play = new MenuButton((int)this.mainBox.getX()+15,(int)(this.mainBox.getY()+this.mainBox.getHeight()/2.5-PADDING), (int)(this.mainBox.width*0.9),70);
        MenuButton levels = new MenuButton((int)play.getX(), (int)play.getY()+play.getHeight()+PADDING,(int)(this.mainBox.width*0.9),70);
        MenuButton exit = new MenuButton((int)levels.getX(), (int)levels.getY()+levels.getHeight()+PADDING,(int)(this.mainBox.width*0.9),70);
        play.setText("Play");
        levels.setText("Levels");
        exit.setText("Exit");

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.unPauseGame();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Logging.getLogger(getClass().getName()).debug("Exit notified!");
                System.exit(0);
            }
        });

        this.buttons.add(play);
        this.buttons.add(levels);
        this.buttons.add(exit);

    }

    public void draw(Graphics2D g)
    {
        super.draw(g);
        g.drawImage(this.logo,Constants.MENU_X_POS,Constants.MENU_Y_POS, null);
    }
}
