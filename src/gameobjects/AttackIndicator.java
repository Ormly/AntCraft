package gameobjects;

import main.GameWorld;
import utilities.Constants;
import utilities.logging.Logger;
import utilities.logging.Logging;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class AttackIndicator extends HUDObject
{
    private Logger logger = Logging.getLogger(this.getClass().getName());
    private Bug bug;

    public AttackIndicator(GameWorld gameWorld)
    {
        super(gameWorld);
        isVisible = true;
    }

    public void init(Bug bug)
    {
        this.bug = bug;

        Point2D bugPoint = new Point2D.Double(bug.getXPos(), bug.getYPos());
        Point2D centerPoint = new Point2D.Double(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2);

        Point2D pointOnEllipse = this.gameWorld.getPhysicsSystem().getLineEllipseIntersection(bugPoint,centerPoint,Constants.SCREEN_WIDTH / 2,Constants.SCREEN_HEIGHT / 2);

        this.xPos = pointOnEllipse.getX();
        this.yPos = pointOnEllipse.getY();
    }

    public void updateIsVisible()
    {
        double bugX = bug.getXPos();
        double bugY = bug.getYPos();

        double topLeftX;
        double topLeftY;
        double bottomRightX;
        double bottomRightY;
        int width = Constants.SCREEN_WIDTH;
        int height = Constants.SCREEN_HEIGHT;

        if(xPos <= width / 2)
        {
            if(yPos <= height / 2)
            {
                topLeftX = 0;
                topLeftY = 0;
                bottomRightX = xPos;
                bottomRightY = yPos;
            }
            else
            {
                topLeftX = 0;
                topLeftY = height - yPos;
                bottomRightX = xPos;
                bottomRightY = height;
            }
        }
        else
        {
            if(yPos <= height / 2)
            {
                topLeftX = width - xPos;
                topLeftY = 0;
                bottomRightX = width;
                bottomRightY = height - yPos;
            }
            else
            {
                topLeftX = xPos;
                topLeftY = yPos;
                bottomRightX = width;
                bottomRightY = height;
            }
        }

        if(bugX >= topLeftX && bugX <= bottomRightX && bugY >= topLeftY && bugY <= bottomRightY)
            this.isVisible = false;
    }

    public double getAngle()
    {
        return this.bug.getAngle();
    }
}
