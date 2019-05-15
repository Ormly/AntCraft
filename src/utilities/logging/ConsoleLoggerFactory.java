package utilities.logging;

public class ConsoleLoggerFactory extends AbstractLoggerFactory
{
    @Override
    public AbstractLogger getLogger(String owner)
    {
        if(!this.loggers.containsKey(owner))
        {
            AbstractLogger logger = new ConsoleLogger(owner);
            this.loggers.put(owner,logger);
        }

        return this.loggers.get(owner);
    }
}
