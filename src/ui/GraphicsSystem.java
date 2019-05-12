package ui;

import gameobjects.GameObject;
import interfaces.IGraphicsSystem;

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

    public GraphicsSystem(int width, int height)
    {
        this.setSize(width, height);

        this.imageBuffer = graphicsConf.createCompatibleImage(width, height);
        this.graphics = this.imageBuffer.getGraphics();
    }

    public void draw(GameObject object)
    {
        int x = (int) object.getX() - object.getRadius();
        int y = (int) object.getY() - object.getRadius();
        int r = object.getRadius() * 2;

        graphics.setColor(object.getColor());
        graphics.fillOval(x, y, r, r);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(x, y, r, r);
    }

    public void clear()
    {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, 1000, 600);
    }

    public void swapBuffers()
    {
        this.getGraphics().drawImage(this.imageBuffer, 0, 0, this);
    }
}