package utilities.logging;

import java.text.SimpleDateFormat;
import java.util.Observable;

public abstract class Logger extends Observable
{
    protected final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    protected String owner;

    public void info(String msg) {
        log("INFO::"+msg);
    }

    public void error(String msg) {
        log("ERROR::"+msg);
    }

    public void debug(String msg) {
        log("DEBUG::"+msg);
    }

    protected abstract void log(String msg);
}
