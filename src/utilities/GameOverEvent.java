package utilities;

public class GameOverEvent extends TimelineEvent
{
    public GameOverEvent(double scheduledTimeSec){
        super(scheduledTimeSec);
        this.isGameOverEvent = true;
    }
}
