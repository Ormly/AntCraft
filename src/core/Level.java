package core;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utilities.Constants;
import utilities.Timeline;
import utilities.logging.Logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Level
{
    public int getNumOfAnts()
    {
        return numOfAnts;
    }

    public void setNumOfAnts(int numOfAnts)
    {
        this.numOfAnts = numOfAnts;
    }

    public Timeline getTimeline()
    {
        return timeline;
    }

    public void setTimeline(Timeline timeline)
    {
        this.timeline = timeline;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private String name;
    private int numOfAnts;
    private Timeline timeline;

    public static void writeToFile(ArrayList<Level> levels)
    {
        JSONArray lvs = new JSONArray();
        File file = new File(Constants.LEVEL_FILE_PATH);
        FileWriter writer = null;

        for(Level l:levels)
        {
            JSONObject level = new JSONObject();
            level.put("name",l.getName());
            level.put("numOfAnts",l.getNumOfAnts());
            level.put("events",l.getTimeline().getASJSONArray());
            lvs.add(level);
        }

        try
        {
            if(!file.exists() && !file.isDirectory())
                file.createNewFile();

            writer = new FileWriter(file, false);
            writer.write(lvs.toJSONString());
        }
        catch(IOException e)
        {
            Logging.getLogger(Level.class.getName()).error("Error writing level to file\n"+e.getMessage());
        }
        finally
        {
            if(writer != null)
            {
                try
                {
                    writer.close();
                }
                catch(IOException e){}
            }
        }
    }

//    public static ArrayList<Level> readFromFile(String fileName)
//    {
//
//    }

}
