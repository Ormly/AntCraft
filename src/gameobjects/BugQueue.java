package gameobjects;

import main.GameWorld;
import java.util.ArrayList;

public class BugQueue extends HUDObject
{
    protected ArrayList<GameObject> upcomingWave;

    public BugQueue(GameWorld gameWorld)
    {
        this.setGameWorld(gameWorld);
        upcomingWave = new ArrayList<>();

        this.isVisible = true;
        //TODO define in Constants
        this.xPos = 20;
        this.yPos = 15;
        this.width = 320;
        this.height = 60;
    }

    public void update(ArrayList<GameObject> wave)
    {
        this.upcomingWave.clear();
        this.upcomingWave.addAll((ArrayList<GameObject>)wave.clone());
    }

    public ArrayList<GameObject> getUpcomingWave()
    {
        return this.upcomingWave;
    }
}