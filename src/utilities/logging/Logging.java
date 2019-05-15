/*
This static class serves as a logging manager, that returns a valid logger anywhere in the application.
 */
package utilities.logging;

public class Logging
{
    private static AbstractLoggerFactory logFactory=null;
    public static void setLoggerFactory(AbstractLoggerFactory factory)
    {
        logFactory = factory;
        factory.getLogger(Logging.class.getName()).info("Started logging..");
    }

    public static AbstractLogger getLogger(String owner)
    {
        if(logFactory == null)
            return null;

        return logFactory.getLogger(owner);
    }
}
