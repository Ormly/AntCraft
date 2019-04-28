import javax.swing.*;

public interface IGraphicsManager
{
    public void clear();

    public void draw(AGameObject go); // create one for each kind of gameobject. until a better solution is available

    public void swapBuffers();
}
