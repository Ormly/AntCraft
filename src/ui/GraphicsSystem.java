package ui;

import gameobjects.*;
import core.ResourceManager;
import gameobjects.GameObject;
import interfaces.IGraphicsSystem;
import utilities.Constants;
import utilities.logging.Logger;
import utilities.logging.Logging;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
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

    private Font font = new Font("Arial",Font.BOLD,35);

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

        Icon icon = gameObject.getIcon();
        if(icon != null)
        {
            icon.update(gameObject.getXPos(),gameObject.getYPos(),gameObject.getAngle());
            ((Graphics2D)graphics).drawImage(icon.getImage(),icon.getTransform(),null);
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

    public void draw(AttackIndicator attackIndicator)
    {
        //logger.debug("Entered AttackIndicator draw");
        double xPos = attackIndicator.getXPos();
        double yPos = attackIndicator.getYPos();
        double angle = attackIndicator.getAngle();

        Icon icon = new Icon(ResourceManager.getInstance().getImage("indicator"),-20,0.4);
        icon.update(xPos,yPos,angle);
        ((Graphics2D)graphics).drawImage(icon.getImage(),icon.getTransform(),null);
    }

    public void draw(MouseAreaSelection mouseAreaSelection)
    {
        double xPos = mouseAreaSelection.getXPos();
        double yPos = mouseAreaSelection.getYPos();
        int width = mouseAreaSelection.getWidth();
        int height = mouseAreaSelection.getHeight();

        graphics.setColor(Color.MAGENTA);
        graphics.drawRect((int)xPos,(int)yPos,width,height);
    }

    public void draw(AntStockIndicator antStockIndicator)
    {
        double xPos = antStockIndicator.getXPos();
        double yPos = antStockIndicator.getYPos();
        int width = antStockIndicator.getWidth();
        int height = antStockIndicator.getHeight();

        Icon icon;
        icon = new Icon(ResourceManager.getInstance().getImage("squareFrameColor"),0.0,1.0);
        icon.update(Constants.ANTSTOCKINDICATOR_X_POS,Constants.ANTSTOCKINDICATOR_Y_POS,(3 * Math.PI) / 2);
        ((Graphics2D) graphics).drawImage(icon.getImage(), icon.getTransform(), null);

        icon = new Icon(ResourceManager.getInstance().getImage("ant"),0,0.53);
        icon.update(xPos,yPos,(3*Math.PI)/2);
        ((Graphics2D)graphics).drawImage(icon.getImage(),icon.getTransform(),null);

        graphics.setColor(Color.BLACK);
        //graphics.drawRect((int)xPos,(int)yPos,width,height);
        //graphics.drawRect((int)xPos-1,(int)yPos-1,width+2,height+2);

        graphics.setFont((font));
        graphics.drawString(Integer.toString(antStockIndicator.getNumOfAnts()),(int)xPos+53,(int)yPos+50);
    }

    public void draw(BugQueue bugQueue)
    {
        if(bugQueue.getUpcomingWave() == null)
            return;

        graphics.setFont((font));

        double xPos = bugQueue.getXPos();
        double yPos = bugQueue.getYPos();
        int width = bugQueue.getWidth();
        int height = bugQueue.getHeight();
        int size = bugQueue.getUpcomingWave().size();

        int xOffset = 53;
        int cumulativeXOffset = 0;

        Icon icon;
        icon = new Icon(ResourceManager.getInstance().getImage("bugQueueFrame"),0.0,1.0);
        icon.update(Constants.BUGQUEUE_X_POS-10,Constants.BUGQUEUE_Y_POS,(3 * Math.PI) / 2);
        ((Graphics2D) graphics).drawImage(icon.getImage(), icon.getTransform(), null);

        for(String str : bugQueue.waveContains())
        {
            if(str.equals("ladybug"))
                icon = new Icon(ResourceManager.getInstance().getImage(str), 8, 0.37);
            else if(str.equals("spider"))
                icon = new Icon(ResourceManager.getInstance().getImage(str), 8, 0.3);
            else
                icon = new Icon(ResourceManager.getInstance().getImage(str), 5, 0.42);

            icon.update(xPos+cumulativeXOffset+(str.equals("spider") ? +5 : +0),
                        yPos+(str.equals("spider") ? -5 : +0),
                        (3 * Math.PI) / 2);

            cumulativeXOffset += xOffset;

            ((Graphics2D) graphics).drawImage(icon.getImage(), icon.getTransform(), null);
            graphics.drawString("  x " + Integer.toString(size), (int) icon.getTransform().getTranslateX() + 40
                                                                      +(str.equals("ladybug") ? -5 : +0)
                                                                      +(str.equals("spider") ? -15 : +0)
                                                                      +(str.equals("kingdong") ? -10 : +0),
                                                                   (int) icon.getTransform().getTranslateY() + 45
                                                                      +(str.equals("ladybug") ? -5 : +0)
                                                                      +(str.equals("kingdong") ? -2 : +0));
            cumulativeXOffset += 50;
        }

        graphics.setColor(Color.BLACK);
        //graphics.drawRect((int)xPos,(int)yPos,width,height);
        //graphics.drawRect((int)xPos-1,(int)yPos-1,width+2,height+2);
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

    public void draw(GUIMenu menu)
    {
        menu.draw((Graphics2D) this.graphics);
    }

    public void draw(Impact impact)
    {
        graphics.setColor(Color.yellow);
        graphics.drawOval((int)(impact.getXPos()-impact.getRadius()/2.0),
                          (int)(impact.getYPos()-impact.getRadius()/2.0),
                          impact.getRadius(),
                          impact.getRadius());
    }

    public void swapBuffers() { this.getGraphics().drawImage(this.imageBuffer, 0, 0, this); }
    public InputSystem getInputSystem() { return this.inputSystem; }
}
