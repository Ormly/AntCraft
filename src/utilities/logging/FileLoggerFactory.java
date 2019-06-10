package utilities.logging;

import java.io.File;
import java.io.IOException;

public class FileLoggerFactory extends LoggerFactory
{
    private File file;
    public FileLoggerFactory(String logFilePath){
        file = new File(logFilePath);
        if(!file.exists() && !file.isDirectory()){
            try
            {
                file.createNewFile();
            }
            catch(IOException e)
            {
                System.exit(1);
            }
        }
    }

    @Override
    public Logger getLogger(String owner)
    {
        if(!this.loggers.containsKey(owner))
        {
            Logger logger = new FileLogger(owner, file.getAbsolutePath());
            this.loggers.put(owner,logger);
        }

        return this.loggers.get(owner);
    }
}
