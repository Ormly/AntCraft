package utilities;

import gameobjects.Bug;
import gameobjects.GameObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utilities.logging.Logger;
import utilities.logging.Logging;

import java.util.*;

public class Timeline extends Thread
{
    private Logger logger = Logging.getLogger(this.getClass().getName());
    private ArrayList<TimelineEvent> events;
    private Queue<TimelineEvent> occuredEvents; // a queue of events that have been triggered
    private double startTime;
    private boolean isFrozen;

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
                ArrayList<GameObject> temp = new ArrayList<>();
                if(event instanceof SpawnEvent)
                {
                    for(GameObject gameObject : event.getObjects())
                        if(gameObject instanceof Bug)
                            temp.add(gameObject);
                    return temp;
                }
            }
        }

        return null;
    }

    public void addEvent(TimelineEvent event)
    {
        this.events.add(event);
        logger.debug("event added "+ event.toString());
    }

    @Override
    public synchronized void start()
    {
        logger.debug("Timeline started");
        this.unFreeze();
        super.start();
    }

    public void freeze()
    {
        logger.debug("timeline paused");
        double now = System.currentTimeMillis();

        // deduct the already passed time from all events schedule
        for(TimelineEvent event:this.events)
            event.setScheduledTimeSec(event.getScheduledTimeSec()-((now-this.startTime)/1000.0));

        this.isFrozen = true;
    }


    public void unFreeze()
    {
        isFrozen = false;
        this.startTime = System.currentTimeMillis();
    }

    public void run(){
        //sort by scheduled time INCR
        this.events.sort(Comparator.comparing(TimelineEvent::getScheduledTimeSec));
        
	while(true)
        {
            // thread never runs if there's nothing here, for some reason..
            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException e) {}

            // check if thread is frozen
            while(!this.isFrozen)
            {
                double currTime = System.currentTimeMillis();

                LinkedList<TimelineEvent> toRemove = new LinkedList<>();

                for(Iterator<TimelineEvent> iterator = this.events.iterator(); iterator.hasNext(); )
                {

                    TimelineEvent event = iterator.next();
                    // has event occurred
                    if(currTime - startTime >= event.getScheduledTimeSec() * 1000)
                    {
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

    public JSONArray getASJSONArray()
    {
        JSONArray events = new JSONArray();
        for(TimelineEvent e:this.events)
        {
            events.add(e.getASJSONObject());
        }

        return events;
    }

    public static Timeline parseTimeLineFromJSON(JSONArray eventsArr)
    {
        Timeline tl = new Timeline();
        for(Object o: eventsArr)
        {
            tl.addEvent(TimelineEvent.parseTimeLineEventFromJSON((JSONObject) o));
        }

        return tl;
    }

}
