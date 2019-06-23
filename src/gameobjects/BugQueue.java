package gameobjects;

import main.GameWorld;
import utilities.Constants;

import java.util.ArrayList;

public class BugQueue extends HUDObject
{
    protected ArrayList<GameObject> upcomingWave;

    public BugQueue(GameWorld gameWorld)
    {
        super(gameWorld);
        upcomingWave = new ArrayList<>();

        this.isVisible = true;
        this.xPos = Constants.BUGQUEUE_X_POS;
        this.yPos = Constants.BUGQUEUE_Y_POS;
        this.width = Constants.BUGQUEUE_WIDTH;
        this.height = Constants.BUGQUEUE_HEIGHT;
    }

    public void update()
    {
        ArrayList<GameObject> wave = this.gameWorld.getTimeline().getNextSpawn();
        if(wave != null)
        {
            this.upcomingWave.clear();
            this.upcomingWave.addAll((ArrayList<GameObject>) wave.clone());
        }
    }

    public ArrayList<GameObject> getUpcomingWave()
    {
        return this.upcomingWave;
    }
}