package ui;

import main.GameWorld;
import java.awt.*;
import java.util.ArrayList;

public abstract class GUIMenu
{
    protected ArrayList<MenuButton> buttons;
    protected Rectangle mainBox;
    protected int PADDING = 10;
    protected static InputSystem inputSystem;
    protected static GameWorld game;

    public void draw(Graphics2D g)
    {
        g.setColor(Color.LIGHT_GRAY);
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

    public static void setGameWorld(GameWorld gw)
    {
        game = gw;
    }

    public static void setInputSystem(InputSystem is)
    {
        inputSystem = is;
    }
}
