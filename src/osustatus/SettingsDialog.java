package osustatus;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.*;

public class SettingsDialog extends JDialog implements ActionListener
{
    private final JPanel container;
    private final JTextField nameField, keyField;
    private final JButton submit;
    private final JLabel n, k;
    
    public SettingsDialog(JFrame frame) 
    {
        super(frame, "Settings");
        setModal(true);
        setResizable(false);
        setPreferredSize(new Dimension(400,120));
        
        container = new JPanel();
        setLayout(new BorderLayout());
        getContentPane().add(container);
        
        n = new JLabel();
        n.setText("Username: ");
        container.add(n, BorderLayout.NORTH);
        
        nameField = new JTextField();
        nameField.setColumns(27);
        nameField.setText(Settings.getUser());
        container.add(nameField, BorderLayout.NORTH);
        
        k = new JLabel();
        k.setText("API Key: ");
        container.add(k, BorderLayout.CENTER);
        
        keyField = new JTextField();
        keyField.setColumns(27);
        keyField.setText(Settings.getKey());
        container.add(keyField, BorderLayout.CENTER);
        
        submit = new JButton();
        submit.setText("OK");
        container.add(submit, BorderLayout.SOUTH);
        
        submit.addActionListener(this);
        
        pack();
        setLocationRelativeTo(frame);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Settings.setKey(keyField.getText());
        Settings.setUser(nameField.getText());
        
        Properties prop = new Properties();
        OutputStream output = null;
        try 
        {
            output = new FileOutputStream("config.properties");
            prop.setProperty("key", Settings.getKey());
            prop.setProperty("user", Settings.getUser());
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
                } 
                catch (IOException ex) 
                {
                    ex.printStackTrace();
                }
            }
        }
        
        dispose();
    }
}
