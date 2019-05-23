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
                result.add(currentObj);
                //Logging.getLogger(this.getClass().getName()).debug("collision: " + object.getClass().getName() + " with " + currentObj.getClass().getName());
            }
        }
        return result;
    }
}
