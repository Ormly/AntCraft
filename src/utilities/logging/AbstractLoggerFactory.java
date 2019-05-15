package utilities.logging;

import java.util.HashMap;

public abstract class AbstractLoggerFactory
{
    protected HashMap<String, AbstractLogger> loggers = new HashMap<>();


    public abstract AbstractLogger getLogger(String owner);
}
