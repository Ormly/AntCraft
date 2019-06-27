package utilities.logging;

public class QuiteLoggerFactory extends LoggerFactory
{
    private static QuiteLogger logger = new QuiteLogger();

    @Override
    public Logger getLogger(String owner)
    {
        return logger;
    }
}
