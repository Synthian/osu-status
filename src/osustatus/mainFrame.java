package osustatus;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.*;

public class mainFrame extends JFrame
{
    private final JPanel buttonholder;
    private final JButton standardBtn, taikoBtn, ctbBtn, maniaBtn, settingsBtn;
    private final JScrollPane scrollpane;
    private final JTextArea display;
    private final Timer timer;
    
    private Mode m;

    public mainFrame() {
        super("osu!Status");
        setSize(800,600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.darkGray);
        
        ImageIcon img = new ImageIcon(getClass().getResource("/resources/icon.png"));
        setIconImage(img.getImage());
        
        buttonholder = new JPanel();
        buttonholder.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonholder.setOpaque(false);
        add(buttonholder, BorderLayout.NORTH);
        
            standardBtn = new JButton();
            standardBtn.setText("osu!Standard");
            standardBtn.setFocusPainted(false);
            standardBtn.setBackground(Color.BLACK);
            standardBtn.setForeground(Color.WHITE);
            buttonholder.add(standardBtn);

            taikoBtn = new JButton();
            taikoBtn.setText("Taiko");
            taikoBtn.setFocusPainted(false);
            taikoBtn.setBackground(Color.BLACK);
            taikoBtn.setForeground(Color.WHITE);
            buttonholder.add(taikoBtn);
            
            ctbBtn = new JButton();
            ctbBtn.setText("Catch the Beat");
            ctbBtn.setFocusPainted(false);
            ctbBtn.setBackground(Color.BLACK);
            ctbBtn.setForeground(Color.WHITE);
            buttonholder.add(ctbBtn);
            
            maniaBtn = new JButton();
            maniaBtn.setText("Mania");
            maniaBtn.setFocusPainted(false);
            maniaBtn.setBackground(Color.BLACK);
            maniaBtn.setForeground(Color.WHITE);
            buttonholder.add(maniaBtn);
            
            settingsBtn = new JButton();
            settingsBtn.setText("Settings");
            settingsBtn.setFocusPainted(false);
            settingsBtn.setBackground(Color.BLACK);
            settingsBtn.setForeground(Color.WHITE);
            buttonholder.add(settingsBtn);
            
        display = new JTextArea();
        display.setEditable(false);
        display.setText("Choose a game mode to begin tracking your gameplay!");
        Font font = new Font("SansSerif", Font.BOLD, 12);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setFont(font);
        display.setBackground(Color.darkGray);
        display.setForeground(Color.WHITE);
        scrollpane = new JScrollPane(display);
        add(scrollpane, BorderLayout.CENTER);
        
        //Listeners
        
        EventHandler eventhandler = new EventHandler();
        standardBtn.addActionListener(eventhandler);
        taikoBtn.addActionListener(eventhandler);
        ctbBtn.addActionListener(eventhandler);
        maniaBtn.addActionListener(eventhandler);
        settingsBtn.addActionListener(eventhandler);
        
        //Timer
        
        timer = new Timer(5000, eventhandler);
    }
    
    private class EventHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource() == settingsBtn)
            {
                if (timer.isRunning())
                {
                    timer.stop();
                }
                
                standardBtn.setEnabled(true);
                taikoBtn.setEnabled(true);
                ctbBtn.setEnabled(true);
                maniaBtn.setEnabled(true);
                
                Settings.setSettings(mainFrame.this);
            }
            
            else if (e.getSource() == standardBtn)
            {
                if (timer.isRunning())
                {
                    timer.stop();
                }
                
                Player.setMode(Mode.STANDARD);
                display.setText("");
                display.append("--- osu!Standard ---\n");
                display.append(Player.getPP().toString());
                display.append(" pp\t");
                display.append(Player.getAcc().toString());
                display.append("% Accuracy");
                
                if (Player.getPP().equals(new BigDecimal("0")))
                {
                    display.append("\nI see you're new to this game mode, or you haven't played in a long time");
                    display.append("\nI won't show you pp changes until you get ranked, sorry!");
                }
                
                standardBtn.setEnabled(false);
                taikoBtn.setEnabled(true);
                ctbBtn.setEnabled(true);
                maniaBtn.setEnabled(true);
                
                timer.start();
                display.setCaretPosition(display.getDocument().getLength());
            }
            
            else if (e.getSource() == taikoBtn)
            {
                if (timer.isRunning())
                {
                    timer.stop();
                }
                
                Player.setMode(Mode.TAIKO);
                display.setText("");
                display.append("--- Taiko ---\n");
                display.append(Player.getPP().toString());
                display.append(" pp\t");
                display.append(Player.getAcc().toString());
                display.append("% Accuracy");
                
                if (Player.getPP().equals(new BigDecimal("0")))
                {
                    display.append("\nI see you're new to this game mode, or you haven't played in a long time");
                    display.append("\nI won't show you pp changes until you get ranked, sorry!");
                }
                
                standardBtn.setEnabled(true);
                taikoBtn.setEnabled(false);
                ctbBtn.setEnabled(true);
                maniaBtn.setEnabled(true);
                
                timer.start();
                display.setCaretPosition(display.getDocument().getLength());
            }
            
            else if (e.getSource() == ctbBtn)
            {
                if (timer.isRunning())
                {
                    timer.stop();
                }
                
                Player.setMode(Mode.CTB);
                display.setText("");
                display.append("--- Catch the Beat ---\n");
                display.append(Player.getPP().toString());
                display.append(" pp\t");
                display.append(Player.getAcc().toString());
                display.append("% Accuracy");
                
                if (Player.getPP().equals(new BigDecimal("0")))
                {
                    display.append("\nI see you're new to this game mode, or you haven't played in a long time");
                    display.append("\nI won't show you pp changes until you get ranked, sorry!");
                }
                
                standardBtn.setEnabled(true);
                taikoBtn.setEnabled(true);
                ctbBtn.setEnabled(false);
                maniaBtn.setEnabled(true);
                
                timer.start();
                display.setCaretPosition(display.getDocument().getLength());
            }
            
            else if (e.getSource() == maniaBtn)
            {
                if (timer.isRunning())
                {
                    timer.stop();
                }
                
                Player.setMode(Mode.MANIA);
                display.setText("");
                display.append("--- osu!mania ---\n");
                display.append(Player.getPP().toString());
                display.append(" pp\t");
                display.append(Player.getAcc().toString());
                display.append("% Accuracy");
                
                if (Player.getPP().equals(new BigDecimal("0")))
                {
                    display.append("\nI see you're new to this game mode, or you haven't played in a long time");
                    display.append("\nI won't show you pp changes until you are ranked, sorry!\n");
                }
                
                standardBtn.setEnabled(true);
                taikoBtn.setEnabled(true);
                ctbBtn.setEnabled(true);
                maniaBtn.setEnabled(false);
                
                timer.start();
                display.setCaretPosition(display.getDocument().getLength());
            }
            
            else if (e.getSource() == timer)
            {
                if (Player.gamePlayed())
                {
                    Player.update();
                    display.append("\n" + Player.getRecentMapString());
                    BigDecimal deltapp = Player.ppChange();
                    BigDecimal deltaacc = Player.accChange();
                    
                    //pp
                    int comparepp = deltapp.compareTo(BigDecimal.ZERO);
                    if (comparepp == 1)
                    {
                        double toppp = Player.userBeatmapPP();
                        if (toppp == 0)
                        {
                            display.append("\n---- +" + deltapp + " pp from new map bonus");
                            display.append("\n---- Total pp: " + Player.getPP());
                        }
                        else
                        {
                            display.append("\n---- Song value: " + toppp + " pp");
                            display.append("\n---- +" + deltapp + " total pp from song");
                            display.append("\n---- Total pp: " + Player.getPP());
                        }
                    }
                    else if (comparepp == 0)
                        display.append("\n---- No pp change");
                    else
                    {
                        display.append("\n---- " + deltapp + " pp lost :(");
                        display.append("\n---- Total pp: " + Player.getPP());
                    }
                    
                    //Accuracy
                    int compareacc = deltaacc.compareTo(BigDecimal.ZERO);
                    if (compareacc == 1)
                    {
                        display.append("\n---- Overall accuracy increased by " + deltaacc + "%");
                        display.append("\n---- Overall accuracy: " + Player.getAcc() + "%");
                    }
                    else if (compareacc == 0)
                        display.append("\n---- No acc change");
                    else
                    {
                        display.append("\n---- Overall accuracy down! " + deltaacc + "% change!");
                        display.append("\n---- Overall accuracy: " + Player.getAcc() + "%");
                    }
                    
                    display.append("\n");
                }
                display.setCaretPosition(display.getDocument().getLength());
            }
        }
    }
}