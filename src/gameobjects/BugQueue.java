package gameobjects;

import main.GameWorld;
import utilities.Constants;

import java.util.ArrayList;
import java.util.Arrays;

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

    public ArrayList<String> waveContains()
    {
        boolean ladyFlag = false;
        boolean spiderFlag = false;
        boolean dongFlag = false;

        String str = "";

        for(GameObject gameObject : upcomingWave)
        {
            if(gameObject instanceof LadyBug)
                ladyFlag = true;
            if(gameObject instanceof SpiderBug)
                spiderFlag = true;
            if(gameObject instanceof DongBug)
                dongFlag = true;
        }

        if(ladyFlag)
            str += "ladybug,";
        if(spiderFlag)
            str += "spider,";
        if(dongFlag)
            str += "kingdong,";

        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(str.length()-1);

        ArrayList<String> result = new ArrayList<>(Arrays.asList(sb.toString().split(",")));
        return result;
    }
}