import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger implements ILogger
{
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    private String owner;

    public ConsoleLogger(String owner){
        this.owner = owner;
    }

    @Override
    public void info(String msg) {
        log("INFO::"+msg);
    }

    @Override
    public void error(String msg) {
        log("ERROR::"+msg);
    }

    @Override
    public void debug(String msg) {
        log("DEBUG::"+msg);
    }

    private void log(String msg){
        System.out.println(dateFormat.format(new Date()) + "::" + owner + "::" + msg);
    }
}
