package ui;

import gameobjects.*;
import core.ResourceManager;
import gameobjects.GameObject;
import interfaces.IGraphicsSystem;
import utilities.logging.Logger;
import utilities.logging.Logging;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GraphicsSystem extends JPanel implements IGraphicsSystem
{
    private Logger logger = Logging.getLogger(this.getClass().getName());
    private static final long serialVersionUID = 1L;
    private GraphicsConfiguration graphicsConf = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();

    private BufferedImage imageBuffer;
    private BufferedImage backgroundImage;

    private Graphics graphics;
    private InputSystem inputSystem = new InputSystem();

    public GraphicsSystem(int width, int height)
    {
        this.setSize(width, height);

        this.imageBuffer = graphicsConf.createCompatibleImage(width, height);
        this.backgroundImage = ResourceManager.getInstance().getImage("background");
        this.graphics = this.imageBuffer.getGraphics();

        this.addMouseListener(inputSystem);
        this.addMouseMotionListener(inputSystem);
        this.addKeyListener(inputSystem);
    }

    public void draw(GameObject gameObject)
    {
        int xPos = (int) gameObject.getXPos() - gameObject.getRadius();
        int yPos = (int) gameObject.getYPos() - gameObject.getRadius();
        int radius = gameObject.getRadius() * 2;

        graphics.setColor(gameObject.getColor());
        graphics.fillOval(xPos, yPos, radius, radius);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(xPos, yPos, radius, radius);

        BufferedImage icon = gameObject.getIcon();
        if(icon != null)
        {
            AffineTransform at = new AffineTransform();
            // image at bug
            at.setToTranslation(xPos, yPos);


            at.translate(-8, -8);
            at.scale(0.5,0.5);
            at.rotate(gameObject.getAngle()+Math.PI/2.0,icon.getWidth()/2,icon.getHeight()/2);

            ((Graphics2D)graphics).drawImage(icon,at,null);
        }

        graphics.setColor(Color.GREEN);

        double health = (gameObject.getHealthStatus() / gameObject.getMaxHealth()) * 100.0;
        double barLength = 100.0;

        yPos = (int) gameObject.getYPos() - gameObject.getRadius() - 15;
        xPos = (int) (gameObject.getXPos() - (barLength/2.0));

        graphics.drawRect(xPos,yPos,(int)barLength,10);
        graphics.fillRect(xPos,yPos,(int)health,10);
    }

    public void clear()
    {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.drawImage(this.backgroundImage,0,0,null);
    }

    public void draw(HUDObject hudObject)
    {
        int xPos = hudObject.getxPos();
        int yPos = hudObject.getyPos();
        int width = hudObject.getWidth();
        int height = hudObject.getHeight();

        graphics.setColor(Color.MAGENTA);
        graphics.drawRect(xPos,yPos,width,height);
    }

    public void draw(ArrayList<GameObject> gameObjectsSelected)
    {
        int xPos;
        int yPos;
        int ringRadius;
        int objectRadius;
        int widthAndHeight;
        int ringThickness = 7;
        graphics.setColor(Color.MAGENTA);

        for(GameObject gameObject : gameObjectsSelected)
        {
            if(!(gameObject instanceof Bug))
            {
                //TODO selection rings right now is just some rings stacked, not sure if solid ring is possible, maybe figure that out later
                for(int i = 2; i < ringThickness; ++i)
                {
                    objectRadius = gameObject.getRadius();
                    ringRadius = objectRadius + i;
                    xPos = (int) gameObject.getXPos() - ringRadius;
                    yPos = (int) gameObject.getYPos() - ringRadius;
                    widthAndHeight = objectRadius * 2;
                    graphics.drawOval(xPos, yPos, widthAndHeight + i * 2, widthAndHeight + i * 2);
                }
            }
        }
    }

    public void swapBuffers() { this.getGraphics().drawImage(this.imageBuffer, 0, 0, this); }
    public InputSystem getInputSystem() { return this.inputSystem; }
}
