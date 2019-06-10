package utilities.logging;

public class ConsoleLoggerFactory extends LoggerFactory
{
    @Override
    public Logger getLogger(String owner)
    {
        if(!this.loggers.containsKey(owner))
        {
            Logger logger = new ConsoleLogger(owner);
            this.loggers.put(owner,logger);
        }

        return this.loggers.get(owner);
    }
}
