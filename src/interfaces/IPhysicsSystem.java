package interfaces;

import gameobjects.GameObject;

import java.util.ArrayList;

public interface IPhysicsSystem
{
    public ArrayList<GameObject> getCollisions(GameObject object);
    public GameObject getGameObjectAtCoordinate(int xPos,int yPos);
}
