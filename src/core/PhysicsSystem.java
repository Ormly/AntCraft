package core;

import gameobjects.GameObject;
import gameobjects.Nest;
import interfaces.IPhysicsSystem;
import main.GameWorld;
import utilities.Constants;
import utilities.logging.Logging;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PhysicsSystem implements IPhysicsSystem
{
    private GameWorld world;

    public PhysicsSystem(GameWorld world)
    {
        this.world = world;
    }

    @Override
    public ArrayList<GameObject> getCollisions(GameObject object)
    {
        ArrayList<GameObject> result = new ArrayList<>();
        ArrayList<GameObject> objects = world.getGameObjects();
        for(int i = 0; i < objects.size(); i++)
        {
            GameObject currentObj = objects.get(i);

            // an object doesn't collide with itself
            if(currentObj == object) continue;

            // check if they touch each other
            double distance = object.getRadius()+currentObj.getRadius();
            double distancex   = object.getXPos()-currentObj.getXPos();
            double distancey   = object.getYPos()-currentObj.getYPos();

            if(distancex*distancex+distancey*distancey < distance*distance)
            {
                if(currentObj.isVisible()) // only allow visible objects to collide
                    result.add(currentObj);
            }
        }
        return result;
    }

    public GameObject getGameObjectAtCoordinate(int xPos,int yPos)
    {
        double distanceX;
        double distanceY;
        double radius;

        for(GameObject gameObject : this.world.getGameObjects())
        {
            distanceX = Math.abs(xPos - gameObject.getXPos());
            distanceY = Math.abs(yPos - gameObject.getYPos());
            radius = gameObject.getRadius();

            if((distanceX + distanceY <= radius) || (Math.pow(distanceX,2) + Math.pow(distanceY,2)) <= Math.pow(radius,2))
            {
                if(gameObject.isVisible()) // only select objects that are visible
                    return gameObject;
            }
        }
        return null;

    }

    public ArrayList<GameObject> getAreaSelectedObjects(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
    {
        double objectX;
        double objectY;
        ArrayList<GameObject> areaSelectedObjects = new ArrayList<>();

        for(GameObject gameObject : this.world.getGameObjects())
        {
            if(!gameObject.isVisible()) // only select objects that are visible
                continue;

            objectX = gameObject.getXPos();
            objectY = gameObject.getYPos();

            if(objectX >= topLeftX && objectX <= bottomRightX && objectY >= topLeftY && objectY <= bottomRightY)
                areaSelectedObjects.add(gameObject);
        }
        return areaSelectedObjects;
    }

    public Point2D getLineEllipseIntersection(Point2D linePoint, Point2D ellipseCenterPoint, float ellipseHalfWidth, float ellipseHalfHeight)
    {
        double ellipseX = ellipseCenterPoint.getX();
        double ellipseY = ellipseCenterPoint.getY();
        double lineX = linePoint.getX();
        double lineY = linePoint.getY();


        double distanceX = ellipseX - lineX;
        double distanceY = ellipseY - lineY;

        double theta = Math.atan2(distanceY, distanceX);

        distanceX = Math.abs(distanceX);
        distanceY = Math.abs((distanceY));

        double r = Math.hypot(distanceY,distanceX) - ((ellipseHalfWidth * ellipseHalfHeight) / Math.sqrt(Math.pow(ellipseHalfHeight * Math.cos(theta), 2) + Math.pow(ellipseHalfWidth * Math.sin(theta), 2)));

        return new Point2D.Double((lineX + r * Math.cos(theta)), (lineY + r * Math.sin(theta)));
    }

    public boolean isObjectOnScreen(GameObject gameObject)
    {
        double objectX = gameObject.getXPos();
        double objectY = gameObject.getYPos();

        double topLeftX = 0;
        double topLeftY = 0;
        double bottomRightX = Constants.SCREEN_WIDTH;
        double bottomRightY = Constants.SCREEN_HEIGHT;

        if(objectX >= topLeftX && objectX <= bottomRightX && objectY >= topLeftY && objectY <= bottomRightY)
            return true;
        else
            return false;
    }

}
