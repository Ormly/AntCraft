package utilities;

import utilities.logging.Logger;
import utilities.logging.Logging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

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

    public void addEvent(TimelineEvent event)
    {
        this.events.add(event);
        logger.debug("event added "+ event.toString());
    }

    public void run(){
        logger.debug("Timeline started");
        double startTime = System.currentTimeMillis();

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
