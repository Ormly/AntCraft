import javax.swing.*;
import java.util.ArrayList;

public class Game
{
    private GraphicsManager graphicsManager;
    private long msSinceLastFrame;
    private double lastFrameDuration;
    private ArrayList<AGameObject> objects;
    private Nest nest;

    public Game()
    {
        this.objects = new ArrayList<>();
        this.lastFrameDuration = System.currentTimeMillis();
    }

    public void init()
    {
        this.nest = new Nest(400, 300, 50);
    }

    public void setGraphicsManager(GraphicsManager gm)
    {
        this.graphicsManager = gm;
    }

    public void run()
    {
        this.msSinceLastFrame = System.currentTimeMillis();

        while(true)
        {
            this.gameLoop();
        }
    }

    public void gameLoop()
    {
        calcFrameDuration();
        this.checkUserInput();
        // move stuff
        // collision detection
        this.redrawObjects();
    }

    private void calcFrameDuration()
    {
        long now = System.currentTimeMillis();
        this.lastFrameDuration = (now - this.msSinceLastFrame) / 1000.0;
        this.msSinceLastFrame = now;
    }

    private void updateObjects(long elapsed)
    {
        // traverse all game objects and update their position
        // should maybe happen in userInputCheck

    }

    private void redrawObjects()
    {
        // iterate over all objects and redraw them
//        this.logger.debug("Drawing element!");
        this.graphicsManager.clear();
//        this.graphicsManager.draw();
        this.graphicsManager.draw(this.nest);
        this.graphicsManager.swapBuffers();
    }

    private void checkUserInput()
    {

    }
}
