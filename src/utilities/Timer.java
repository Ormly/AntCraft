package utilities;

public class Timer
{
    private double secondsToExpire;
    private double startTime;
    public Timer(double secondsToExpire){
        this.secondsToExpire = secondsToExpire;
    }

    public void start(){
        this.startTime = System.currentTimeMillis();
    }

    public boolean hasExpired(){
        return (System.currentTimeMillis() - this.startTime) >= secondsToExpire;
    }
}
