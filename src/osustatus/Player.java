package osustatus;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.json.*;

public final class Player
{
    private static Mode mode;
    private static BigDecimal pp;
    private static BigDecimal acc;
    private static recentPlay lastplay;
    private static String lastplayid;
    private static JSONObject newuser;
    
    public static void setMode(Mode m)
    {
        mode = m;
        
        JSONArray recentdata = API.getUserRecentArr(Settings.getKey(), Settings.getUser(), mode);
        lastplay = new recentPlay(recentdata);
        
        JSONObject user = API.getUserJSON(Settings.getKey(), Settings.getUser(), mode);
        pp = fetchPP(user);
        acc = fetchAcc(user);
    }
    
    private static BigDecimal fetchPP (JSONObject u)
    {
        return new BigDecimal(u.get("pp_raw").toString());
    }
    
    private static BigDecimal fetchAcc (JSONObject u)
    {
        return new BigDecimal(u.get("accuracy").toString());
    }
    
    public static BigDecimal getPP ()
    {
        return pp;
    }
    
    public static BigDecimal getAcc ()
    {
        return acc.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    public static String getRecentID ()
    {
        return lastplay.getID();
    }
    
    public static boolean gamePlayed ()
    {
        JSONArray recentdata = API.getUserRecentArr(Settings.getKey(), Settings.getUser(), mode);
        recentPlay newplay = new recentPlay(recentdata);
        
        boolean change = !newplay.dateEquals(lastplay);
        lastplay = newplay;
        lastplayid = lastplay.getID();
        
        return change;
    }
    
    public static void update ()
    {
        newuser = API.getUserJSON(Settings.getKey(), Settings.getUser(), mode);
    }
    
    public static BigDecimal ppChange ()
    {
        BigDecimal newpp = fetchPP(newuser);
        if (pp.equals(BigDecimal.ZERO))
            return BigDecimal.ZERO;
        BigDecimal difference = newpp.subtract(pp);
        pp = newpp;
        
        return difference;
    }
    
    public static BigDecimal accChange ()
    {
        BigDecimal newacc = fetchAcc(newuser);
        if (acc.equals(BigDecimal.ZERO))
            return BigDecimal.ZERO;
        BigDecimal difference = newacc.subtract(acc);
        acc = newacc;
        
        return difference.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    public static String getRecentMapString ()
    {
        JSONObject map = API.getBeatmap(Settings.getKey(), lastplayid);
        StringBuilder sb = new StringBuilder();
        
        String diff = map.get("difficultyrating").toString();
        Double stars = Double.valueOf(diff);
        DecimalFormat df = new DecimalFormat("#.00");

        sb.append("[");
        sb.append(lastplay.getRank());
        sb.append(" - ");
        sb.append(lastplay.getAcc(mode));
        sb.append("%] ");
        sb.append(map.get("artist").toString());
        sb.append(" - ");
        sb.append(map.get("title").toString());
        sb.append(" [");
        sb.append(map.get("version").toString());
        sb.append("] ");
        sb.append(lastplay.getMods());
        sb.append("(");
        sb.append(df.format(stars));
        sb.append(" stars)");
        
        return sb.toString();
    }
    
    public static double userBeatmapPP ()
    {
        JSONArray data = API.getUserTop100(Settings.getKey(), Settings.getUser(), mode);
        double pp = 0;
        
        for (int i = 0; i < 100; i++)
        {
            JSONObject score = data.getJSONObject(i);
            String mapid = score.get("beatmap_id").toString();
            
            if (mapid.equals(lastplayid))
            {
                pp = Double.valueOf(score.get("pp").toString());
                i = 100;
            }
        }
        
        return pp;
    }

}
