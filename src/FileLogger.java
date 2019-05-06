import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger implements ILogger
{
    //private static final String filePath = Constants.LOG_FILE_PATH;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    private static final File file = new File(Constants.LOG_FILE_PATH);
    private String owner;
    private PrintWriter writer = null;

    public FileLogger(String owner)
    {
        this.owner = owner;
        if(!file.exists() && !file.isDirectory())
        {
            try
            {
                file.createNewFile();
            }
            catch(IOException e){
                System.exit(1);
            }
        }

        if(writer == null){
            try
            {
                writer = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsolutePath(),true)),true);
            }
            catch(IOException e){
                System.exit(1);
            }
        }
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
        writer.println(dateFormat.format(new Date()) + "::" + owner + "::" + msg);
    }
}
