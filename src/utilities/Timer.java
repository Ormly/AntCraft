package utilities;

public class Timer
{
    private double secondsToExpire;
    private double startTime = 0.0;
    private boolean hasStarted;

    public Timer(double secondsToExpire)
    {
        this.secondsToExpire = secondsToExpire;
        hasStarted = false;
    }

    public void start()
    {
        this.startTime = System.currentTimeMillis();
        hasStarted = true;
    }

    public boolean hasExpired()
    {
        return (System.currentTimeMillis() - this.startTime) >= secondsToExpire*1000.0;
    }

    public boolean hasStarted()
    {
        return hasStarted;
    }

}
