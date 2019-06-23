package ui;

import sun.rmi.runtime.Log;
import utilities.DrawUtils;
import utilities.logging.Logger;
import utilities.logging.Logging;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MenuButton
{
    private Logger logger = Logging.getLogger(this.getClass().getName());
    private Rectangle box;
    private ArrayList<ActionListener> listeners;
    private String text;

    public MenuButton(int x, int y, int width, int height)
    {
        this.listeners = new ArrayList<>();
        this.box = new Rectangle(x,y,width,height);
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void update()
    {

    }

    public void draw(Graphics2D g)
    {
        g.setColor(Color.BLUE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        g.fill(this.box);
        g.setColor(Color.ORANGE);
        g.drawString(this.text, DrawUtils.getTextCenterX(this.text,g.getFont(),this.box),(int)(this.box.getY()+this.box.getHeight()/2 + 5));
    }

    public int getX()
    {
        return (int)this.box.getX();
    }

    public int getY()
    {
        return (int)this.box.getY();
    }

    public int getWidth()
    {
        return (int)this.box.getWidth();
    }

    public int getHeight()
    {
        return (int)this.box.getHeight();
    }

    public void processInput(UserInput input)
    {
        if(input.isMousePressed() && this.box.contains(input.getMousePressedX(),input.getMousePressedY()))
            this.mousePressed();
    }

    public void addActionListener(ActionListener action)
    {
        this.listeners.add(action);
    }

    public void mouseClicked(MouseEvent e)
    {

    }

    public void mousePressed()
    {
        Logging.getLogger(getClass().getName()).debug(this.text + " pressed!");
        for(ActionListener al:this.listeners)
            al.actionPerformed(null);
    }

    public void mouseReleased(MouseEvent e)
    {

    }

    public void mouseEntered()
    {
        Logging.getLogger(this.getClass().getName()).debug(this.text);
    }

    public void mouseExited(MouseEvent e)
    {

    }
}
