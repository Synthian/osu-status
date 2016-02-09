package osustatus;

import javax.swing.JFrame;

public final class Settings
{
    private static String key = "";
    private static String user = "";
    
    public static void setKey(String k)
    {
        key = k;
    }
    
    public static void setUser(String u)
    {
        user = u;
    }
    
    public static String getKey()
    {
        return key;
    }
    
    public static String getUser()
    {
        return user;
    }
    
    public static void setSettings(JFrame host)
    {
        SettingsDialog sd = new SettingsDialog(host);
        sd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        sd.setVisible(true);
    }
}