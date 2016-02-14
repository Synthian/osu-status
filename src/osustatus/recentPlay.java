package osustatus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.json.*;

public class recentPlay
{
    private String beatmap_id;
    private String date;
    private String rank;
    private String mods;
    private String beatmap_name;
    private BigDecimal stars;
    private BigDecimal cnt300, cnt100, cnt50, cnt0, cntkatu, cntgeki;
    
    public recentPlay (JSONArray data) {
        if(data.isNull(0))
        {
            date = "";
        }
        else
        {
            JSONObject obj = data.getJSONObject(0);
            beatmap_id = obj.get("beatmap_id").toString();
            date = obj.get("date").toString();
            rank = obj.get("rank").toString();
            
            BigInteger m = new BigInteger(obj.get("enabled_mods").toString());
            StringBuilder sb = new StringBuilder();
            
            if (!m.equals(new BigInteger("0")))
            {
                sb.append("+");   
                if (m.testBit(0))
                    sb.append("NF");
                if (m.testBit(1))
                    sb.append("EZ");
                if (m.testBit(3))
                    sb.append("HD");
                if (m.testBit(4))
                    sb.append("HR");
                if (m.testBit(5))
                    sb.append("SD");
                if (m.testBit(6))
                    if (m.testBit(9))
                        sb.append("NC");
                    else
                        sb.append("DT");
                if (m.testBit(7))
                    sb.append("RL");
                if (m.testBit(8))
                    sb.append("HT");
                if (m.testBit(10))
                    sb.append("FL");
                if (m.testBit(11))
                    sb.append("AT");
                if (m.testBit(12))
                    sb.append("SO");
                if (m.testBit(13))
                    sb.append("AP");
                if (m.testBit(14))
                    sb.append("PF");
                if (m.testBit(15))
                    sb.append("4K");
                if (m.testBit(16))
                    sb.append("5K");
                if (m.testBit(17))
                    sb.append("6K");
                if (m.testBit(18))
                    sb.append("7K");
                if (m.testBit(19))
                    sb.append("8K");
                if (m.testBit(20))
                    sb.append("FI");
                sb.append(" ");
            }
            mods = sb.toString();
            
            JSONObject map = API.getBeatmap(Settings.getKey(), beatmap_id);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(map.get("artist").toString());
            sb2.append(" - ");
            sb2.append(map.get("title").toString());
            sb2.append(" [");
            sb2.append(map.get("version").toString());
            sb2.append("] ");
            beatmap_name = sb2.toString();
            
            stars = new BigDecimal(map.get("difficultyrating").toString());
            
            cnt300 = new BigDecimal(obj.get("count300").toString());
            cnt100 = new BigDecimal(obj.get("count100").toString());
            cnt50 = new BigDecimal(obj.get("count50").toString());
            cnt0 = new BigDecimal(obj.get("countmiss").toString());
            cntkatu = new BigDecimal(obj.get("countkatu").toString());
            cntgeki = new BigDecimal(obj.get("countgeki").toString());
        }
    }
    
    public String getAcc(Mode m)
    {
        BigDecimal _300 = new BigDecimal("300");
        BigDecimal _200 = new BigDecimal("200");
        BigDecimal _100 = new BigDecimal("100");
        BigDecimal _50 = new BigDecimal("50");
        if (m == Mode.STANDARD)
        {
            BigDecimal points = cnt300.multiply(_300);
            points = points.add(cnt100.multiply(_100));
            points = points.add(cnt50.multiply(_50));
            points = points.multiply(_100);
            
            BigDecimal max = cnt300.add(cnt100.add(cnt50.add(cnt0)));
            max = max.multiply(_300);
            
            BigDecimal quotient = points.divide(max, 2, BigDecimal.ROUND_HALF_UP);
            return quotient.toString();
        }
        else if (m == Mode.TAIKO)
        {
            BigDecimal points = cnt300.multiply(_300);
            points = points.add(cnt100.multiply(new BigDecimal("150")));
            points = points.multiply(_100);
            
            BigDecimal max = cnt300.add(cnt100.add(cnt0));
            max = max.multiply(_300);
            
            BigDecimal quotient = points.divide(max, 2, BigDecimal.ROUND_HALF_UP);
            return quotient.toString();
        }
        else if (m == Mode.MANIA)
        {
            BigDecimal points = cnt300.multiply(_300);
            points = points.add(cntgeki.multiply(_300));
            points = points.add(cnt100.multiply(_100));
            points = points.add(cntkatu.multiply(_200));
            points = points.add(cnt50.multiply(_50));
            points = points.multiply(_100);
            
            BigDecimal max = cnt300.add(cntgeki.add(cnt100.add(cntkatu.add(cnt50.add(cnt0)))));
            max = max.multiply(_300);
            
            BigDecimal quotient = points.divide(max, 2, BigDecimal.ROUND_HALF_UP);
            return quotient.toString();
        }
        else
        {
            BigDecimal points = cnt300;
            points = points.add(cnt100);
            points = points.add(cnt50);
            
            BigDecimal max = points.add(cnt0.add(cntkatu));
            points = points.multiply(_100);
            
            BigDecimal quotient = points.divide(max, 2, BigDecimal.ROUND_HALF_UP);
            return quotient.toString();
        }
    }
    
    public String getMods()
    {
        return mods;
    }
    
    public String getRank()
    {
        return rank;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public String getID()
    {
        return beatmap_id;
    }
    
    public String getBeatmapName()
    {
        return beatmap_name;
    }
    
    public BigDecimal getStars()
    {
        return stars;
    }
    
    public boolean dateEquals(recentPlay p)
    {
        if (p.getDate().equals(date))
            return true;
        else
            return false;
    }
}
