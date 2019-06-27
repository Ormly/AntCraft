package gameobjects;

import main.GameWorld;
import utilities.Constants;
import utilities.DrawUtils;
import java.util.ArrayList;
import java.util.HashMap;

public class BugQueue extends HUDObject
{
    private HashMap<String,Integer> upcomingWave;

    public BugQueue(GameWorld gameWorld)
    {
        super(gameWorld);
        upcomingWave = new HashMap<>();

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
            this.processWave(wave);
    }

    public HashMap<String, Integer> getUpcomingWave()
    {
        return this.upcomingWave;
    }

    private void clear()
    {
        this.upcomingWave.clear();
        this.upcomingWave.put("ladybug",0);
        this.upcomingWave.put("spider",0);
        this.upcomingWave.put("kingdong",0);
    }

    private void processWave(ArrayList<GameObject> objects)
    {
        this.clear();

        for(GameObject gameObject : objects)
        {
            if(gameObject instanceof LadyBug)
                this.upcomingWave.put("ladybug", ((int)this.upcomingWave.get("ladybug"))+1);
            if(gameObject instanceof SpiderBug)
                this.upcomingWave.put("spider", ((int)this.upcomingWave.get("spider"))+1);
            if(gameObject instanceof DongBug)
                this.upcomingWave.put("kingdong", ((int)this.upcomingWave.get("kingdong"))+1);
        }

        DrawUtils.sortByValue(this.upcomingWave);
    }
}