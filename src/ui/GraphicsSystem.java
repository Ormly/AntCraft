package ui;

import gameobjects.GameObject;
import interfaces.IGraphicsSystem;
import utilities.logging.Logging;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicsSystem extends JPanel implements IGraphicsSystem
{
    private static final long serialVersionUID = 1L;
    private GraphicsConfiguration graphicsConf = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();

    private BufferedImage imageBuffer;
    private Graphics graphics;
    private InputSystem inputSystem = new InputSystem();

    public GraphicsSystem(int width, int height)
    {
        this.setSize(width, height);

        this.imageBuffer = graphicsConf.createCompatibleImage(width, height);
        this.graphics = this.imageBuffer.getGraphics();

        this.addMouseListener(inputSystem);
        this.addMouseMotionListener(inputSystem);
        this.addKeyListener(inputSystem);
    }

    public void draw(GameObject object)
    {
        int x = (int) object.getXPos() - object.getRadius();
        int y = (int) object.getYPos() - object.getRadius();
        int r = object.getRadius() * 2;

        graphics.setColor(object.getColor());
        graphics.fillOval(x, y, r, r);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(x, y, r, r);

        //healthbar
        graphics.setColor(Color.GREEN);

        double health = (object.getHealthStatus() / object.getMaxHealth()) * 100.0;
        double barLength = 100.0;

        y = (int) object.getYPos() - object.getRadius() - 15;
        x = (int) (object.getXPos() - (barLength/2.0));

        graphics.drawRect(x,y,(int)barLength,10);
        graphics.fillRect(x,y,(int)health,10);
    }

    public void clear()
    {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, 1000, 600);
    }

    public void swapBuffers() { this.getGraphics().drawImage(this.imageBuffer, 0, 0, this); }
    public InputSystem getInputSystem() { return this.inputSystem; }
}
