package ui;

import gameobjects.AGameObject;
import interfaces.AGraphicsSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BGraphicsSystem extends JPanel implements AGraphicsSystem
{
    private static final long serialVersionUID = 1L;
    private GraphicsConfiguration graphicsConf = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();
    private BufferedImage imageBuffer;
    private Graphics graphics;

    public BGraphicsSystem(int width, int height)
    {
        this.setSize(width, height);

        this.imageBuffer = graphicsConf.createCompatibleImage(width, height);
        this.graphics = this.imageBuffer.getGraphics();
    }

    public void draw(AGameObject object)
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
