package core;

import gameobjects.GameObject;
import interfaces.IPhysicsSystem;
import main.GameWorld;

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
        for(int i=0; i<objects.size(); i++)
        {
            GameObject currentObj = objects.get(i);

            // an object doesn't collide with itself
            if(currentObj==object) continue;

            // check if they touch each other
            double dist = object.getRadius()+currentObj.getRadius();
            double dx   = object.getXPos()-currentObj.getXPos();
            double dy   = object.getYPos()-currentObj.getYPos();

            if(dx*dx+dy*dy < dist*dist)
            { result.add(currentObj);
            }
        }
        return result;
    }
}
