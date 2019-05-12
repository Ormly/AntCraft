package interfaces;

import gameobjects.AGameObject;

public interface APhysicsSystem
{
    public AGameObjectList getCollisions(AGameObject object);
}
