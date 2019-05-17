package utilities;

import gameobjects.GameObject;

import java.util.ArrayList;

public class SpawnEvent extends TimelineEvent
{
    public SpawnEvent(ArrayList<GameObject> objects, double scheduledTimeMs){
        super(scheduledTimeMs);
        this.isGameOverEvent = false;
        this.objects = objects;
    }
}
