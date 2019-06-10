package utilities.logging;

import java.io.*;
import java.util.Date;

public class FileLogger extends Logger
{

    private PrintWriter writer = null;

    public FileLogger(String owner, String logFilePath)
    {
        this.owner = owner;
        if(writer == null){
            try
            {
                writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)),true);
            }
            catch(IOException e){
                System.exit(1);
            }
        }
    }

    protected void log(String msg){
        writer.println(dateFormat.format(new Date()) + "::" + owner + "::" + msg);
    }
}
