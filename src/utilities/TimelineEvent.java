package utilities;

import gameobjects.GameObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public abstract class TimelineEvent
{
    protected boolean isGameOverEvent;
    protected double scheduledTimeMs;
    protected ArrayList<GameObject> objects;

    public TimelineEvent(double scheduledTimeMs)
    {
        this.scheduledTimeMs = scheduledTimeMs;
    }

    public double getScheduledTimeMs()
    {
        return this.scheduledTimeMs;
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
            return "GameOver even at: " + this.scheduledTimeMs/1000;
        return "Spawning event: " + objects.size() + " objects at: " + this.scheduledTimeMs/1000.0 + " seconds.";
    }

}
