package utilities;

import gameobjects.GameObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utilities.logging.Logger;
import utilities.logging.Logging;
import java.util.ArrayList;

public abstract class TimelineEvent
{
    private static final Logger logger = Logging.getLogger(TimelineEvent.class.getName());
    protected boolean isGameOverEvent;
    protected double scheduledTimeSec;
    protected ArrayList<GameObject> objects;

    public TimelineEvent(double scheduledTimeSec)
    {
        this.scheduledTimeSec = scheduledTimeSec;
    }

    public double getScheduledTimeSec()
    {
        return this.scheduledTimeSec;
    }

    public void setScheduledTimeSec(double scheduledTimeSec)
    {
        this.scheduledTimeSec = scheduledTimeSec;
    }

    public ArrayList<GameObject> getObjects()
    {
        return this.objects;
    }

    public boolean isGameOverEvent()
    {
        return this.isGameOverEvent;
    }

    public String toString()
    {
        if(this.isGameOverEvent)
            return "GameOver even at: " + this.scheduledTimeSec;
        return "Spawning event: " + objects.size() + " objects at: " + this.scheduledTimeSec + " seconds.";
    }

    public JSONObject getASJSONObject()
    {
        JSONObject obj = new JSONObject();
        obj.put("type",this.getClass().getName());
        if(this instanceof SpawnEvent)
        {
            JSONArray objs = new JSONArray();

            for(GameObject go:this.objects)
            {
                objs.add(go.getASJSONObject());
            }
            obj.put("objects",objs);
        }

        obj.put("schedTimeSec",this.scheduledTimeSec);

        return obj;
    }

    public static TimelineEvent parseTimeLineEventFromJSON(JSONObject obj)
    {
        TimelineEvent tle = null;
        double schedTimeSec = (double) obj.get("schedTimeSec");

        String type = (String) obj.get("type");

        if(type.compareTo("utilities.SpawnEvent") == 0)
        {
            JSONArray array = (JSONArray) obj.get("objects");

            ArrayList<GameObject> objs = new ArrayList<>();

            for(Object o : array)
            {
                GameObject go = GameObject.parseFromJSON((JSONObject) o);
                objs.add(go);
            }

            tle = new SpawnEvent(objs, schedTimeSec);
        }
        else if(type.compareTo("utilities.GameOverEvent") == 0)
        {
            tle = new GameOverEvent(schedTimeSec);
        }

        return tle;
    }
}
