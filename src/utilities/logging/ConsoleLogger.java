package utilities.logging;

import java.util.Date;

public class ConsoleLogger extends AbstractLogger
{
    public ConsoleLogger(String owner){
        this.owner = owner;
    }

    protected void log(String msg){
        System.out.println(dateFormat.format(new Date()) + "::" + owner + "::" + msg);
    }
}
