package ui;

import main.GameWorld;
import utilities.Constants;

import java.awt.*;
import java.util.ArrayList;

public abstract class GUIMenu
{
    protected ArrayList<MenuButton> buttons;
    protected Rectangle mainBox;
    protected int PADDING = 10;
    protected static InputSystem inputSystem;
    protected static GameWorld game;

    public GUIMenu()
    {
        this.mainBox = new Rectangle();
        this.buttons = new ArrayList<>();

    }

    public void draw(Graphics2D g)
    {
        g.setColor(Constants.MENU_COLOR);
        g.fill(this.mainBox);

        for(MenuButton b: this.buttons)
        {
            b.draw(g);
        }
    }

    public void processInput(UserInput input)
    {
        for(MenuButton b:this.buttons)
        {
            b.processInput(input);
        }
    }

    public void setPosition(int x, int y)
    {
        this.mainBox.setLocation(x,y);
    }

    public void setSize(int w, int h)
    {
        this.mainBox.setSize(w,h);
    }

    public double getX()
    {
        return this.mainBox.getX();
    }

    public double getY()
    {
        return this.mainBox.getY();
    }

    public double getWidth()
    {
        return this.mainBox.getWidth();
    }

    public double getHeight()
    {
        return this.mainBox.getHeight();
    }

    public static void setGameWorld(GameWorld gw)
    {
        game = gw;
    }

    public static void setInputSystem(InputSystem is)
    {
        inputSystem = is;
    }
}
