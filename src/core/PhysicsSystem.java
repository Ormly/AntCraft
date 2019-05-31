package core;

import gameobjects.GameObject;
import gameobjects.Nest;
import interfaces.IPhysicsSystem;
import main.GameWorld;
import utilities.logging.Logging;

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

}
