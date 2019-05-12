package interfaces;

import gameobjects.GameObject;

public interface IGraphicsSystem
{
    public void clear();

    public void draw(GameObject go); // create one for each kind of gameobject. until a better solution is available

    public void swapBuffers();
}
