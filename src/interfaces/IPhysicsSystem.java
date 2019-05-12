package interfaces;

import gameobjects.GameObject;

public interface IPhysicsSystem
{
    public IGameObjectList getCollisions(GameObject object);
}
