package osustatus;

import java.io.*;
import java.util.Properties;
import javax.swing.JFrame;

public class OsuStatus 
{
    public static void main(String[] args) 
    {
        //Affirm the the config file exists
        File file = new File("config.properties");
        if(file.exists())
        {
            Properties prop = new Properties();
            InputStream input = null;
            try 
            {
                input = new FileInputStream(file);
                prop.load(input);
                Settings.setKey(prop.getProperty("key"));
                Settings.setUser(prop.getProperty("user"));
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            } 
            finally 
            {
                if (input != null) 
                {
                    try 
                    {
                        input.close();
                    } 
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            Properties prop = new Properties();
            OutputStream output = null;
            try 
            {
		output = new FileOutputStream("config.properties");
		prop.setProperty("key", "");
		prop.setProperty("user", "");
		prop.store(output, null);
            } 
            catch (IOException io) 
            {
		io.printStackTrace();
            } 
            finally 
            {
		if (output != null) 
                {
                    try 
                    {
                        output.close();
                    } catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
		}
            }   
        }
        
        mainFrame mainframe = new mainFrame();
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setVisible(true);
        
        //Pop open the Settings if they are not set
        
        if (Settings.getKey().equals("") || Settings.getUser().equals(""))
        {
            Settings.setSettings(mainframe);
        }
        
    }
}