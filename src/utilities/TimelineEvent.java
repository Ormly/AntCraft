package utilities;

import gameobjects.GameObject;

import java.util.ArrayList;

public abstract class TimelineEvent
{
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

}
