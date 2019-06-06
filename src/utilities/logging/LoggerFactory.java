package utilities.logging;

import java.util.HashMap;

public abstract class LoggerFactory
{
    protected HashMap<String, Logger> loggers = new HashMap<>();


    public abstract Logger getLogger(String owner);
}
