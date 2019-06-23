package core;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utilities.Constants;
import utilities.Timeline;
import utilities.logging.Logger;
import utilities.logging.Logging;

import java.io.*;
import java.util.ArrayList;

public class Level
{
    private static final File file = new File(Constants.LEVEL_FILE_PATH);
    private static final Logger logger = Logging.getLogger(Level.class.getName());

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

    public static ArrayList<Level> readFromFile()
    {
        ArrayList<Level> levels = new ArrayList<>();
        FileReader reader = null;
        try
        {
            reader = new FileReader(file);
            JSONParser parser = new JSONParser();

            JSONArray jlevels = (JSONArray) parser.parse(reader);

            jlevels.forEach(lvl -> parseLevelObject((JSONObject) lvl, levels));
        }
        catch(FileNotFoundException e)
        {
            logger.info("No level file found.");
        }
        catch(ParseException | IOException e)
        {
            logger.error("Error parsing levels file!");
        }

        return levels;
    }

    private static void parseLevelObject(JSONObject level, ArrayList<Level> levels)
    {
        Level l = new Level();
        l.setName((String) level.get("name"));
        l.setNumOfAnts(((Long)level.get("numOfAnts")).intValue());
        l.setTimeline(Timeline.parseTimeLineFromJSON((JSONArray)level.get("events")));
        levels.add(l);
    }

}
