package osustatus;

import java.io.InputStream;
import java.net.*;
import org.json.*;
import org.apache.commons.io.*;

public final class API
{   
    
    private static final String domain = "https://osu.ppy.sh/api/";
    private static final String beatmapPath = "get_beatmaps";
    private static final String userPath = "get_user";
    private static final String mapscoresPath = "get_scores";
    private static final String userbestPath = "get_user_best";
    private static final String userrecentPath = "get_user_recent";
    private static final String multimatchPath = "get_match";
    
    public static JSONObject getUserJSON(String key, String user, Mode mode) 
    {
        try
        {
            String link = domain + userPath + "?k=" + key + "&u=" + user + "&m=" + mode.getValue();
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            String body = IOUtils.toString(in, encoding);
            
            JSONArray jsarr = new JSONArray(body);
            JSONObject jsob = jsarr.getJSONObject(0);
            
            return jsob;
        }
        catch (Exception e)
        {
            System.out.println("error?");
            //Maybe do something here later
        }
            return new JSONObject();
    }
    
    public static JSONArray getUserRecentArr(String key, String user, Mode mode)
    {
        try
        {
            String link = domain + userrecentPath + "?k=" + key + "&u=" + user + "&m=" + mode.getValue();
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            String body = IOUtils.toString(in, encoding);
            
            JSONArray jsarr = new JSONArray(body);
            return jsarr;
        }
        catch (Exception e)
        {
            System.out.println("error?");
            //Maybe do something here later
        }
            return new JSONArray();
    }
    
    public static JSONObject getBeatmap (String key, String id)
    {
        try
        {
            String link = domain + beatmapPath + "?k=" + key + "&b=" + id;
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            String body = IOUtils.toString(in, encoding);
            
            JSONArray jsarr = new JSONArray(body);
            JSONObject jsob = jsarr.getJSONObject(0);
            return jsob;
        }
        catch (Exception e)
        {
            System.out.println("error?");
            //Maybe do something here later
        }
            return new JSONObject();
    }
    
    public static JSONArray getUserTop100 (String key, String user, Mode mode)
    {
        try
        {
            String link = domain + userbestPath + "?k=" + key + "&u=" + user + "&m=" + mode.getValue() + "&limit=100";
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            String body = IOUtils.toString(in, encoding);
            
            JSONArray jsarr = new JSONArray(body);
            return jsarr;
        }
        catch (Exception e)
        {
            System.out.println("error?");
            //Maybe do something here later
        }
            return new JSONArray();
    }
    
}