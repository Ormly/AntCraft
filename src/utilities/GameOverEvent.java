package utilities;

public class GameOverEvent extends TimelineEvent
{
    public GameOverEvent(double scheduledTimeMs){
        super(scheduledTimeMs);
        this.isGameOverEvent = true;
    }
}
