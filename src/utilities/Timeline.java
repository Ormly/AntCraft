package utilities;

import gameobjects.GameObject;
import utilities.logging.Logger;
import utilities.logging.Logging;

import java.util.*;

public class Timeline extends Thread
{
    private Logger logger = Logging.getLogger(this.getClass().getName());
    private ArrayList<TimelineEvent> events;
    private Queue<TimelineEvent> occuredEvents; // a queue of events that have been triggered

    public Timeline()
    {
        this.events = new ArrayList<>();
        this.occuredEvents = new LinkedList<>();
    }

    public boolean hasEvents()
    {
        return occuredEvents.size() > 0;
    }

    public TimelineEvent getNextEvent()
    {
        return this.occuredEvents.poll();
    }

    public TimelineEvent peekNextEvent()
    {
        return this.events.get(0);
    }

    public ArrayList<GameObject> getNextSpawn()
    {
        if(events.size() > 0)
        {
            for(TimelineEvent event : events)
            {
                if(event instanceof SpawnEvent)
                    return event.getObjects();
            }
        }

        return null;
    }

    public void addEvent(TimelineEvent event)
    {
        this.events.add(event);
        logger.debug("event added "+ event.toString());
    }

    public void run(){
        logger.debug("Timeline started");
        double startTime = System.currentTimeMillis();


        //sort by scheduled time INCR
        this.events.sort(Comparator.comparing(TimelineEvent::getScheduledTimeSec));

        while(true){
            double currTime = System.currentTimeMillis();

            LinkedList<TimelineEvent> toRemove = new LinkedList<>();

            for(Iterator<TimelineEvent> iterator = this.events.iterator(); iterator.hasNext();){

                TimelineEvent event = iterator.next();
                // has event occurred
                if(currTime - startTime >= event.getScheduledTimeSec()*1000){
                    logger.debug("event occurred: " + event.toString());
                    this.occuredEvents.add(event);
                    toRemove.add(event);
                }
            }
            // the thread safe way
            this.events.removeAll(toRemove);
        }
    }
}
