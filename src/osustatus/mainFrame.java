package osustatus;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import javax.swing.*;

public class mainFrame extends JFrame
{
    private final JPanel buttonholder, userdata, userstat, display, blank;
    private final JButton standardBtn, taikoBtn, ctbBtn, maniaBtn, settingsBtn;
    private final JScrollPane scrollpane;
    private final JLabel username, userpp, useracc;
    private final Timer timer;
    private final Font ubufont;
    private int cards;

    public mainFrame() {
        //Frame setup
        super("osu!Status");
        setSize(800,800);
        setMinimumSize(new Dimension(700,400));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.darkGray);
        
        //Load font
        ubufont = fontFromFile("/resources/ubuntu.ttf");
        
        //Window icon
        ImageIcon img = new ImageIcon(getClass().getResource("/resources/icon.png"));
        setIconImage(img.getImage());
        
        //Menu bar
        //<editor-fold>
        buttonholder = new JPanel();
        buttonholder.setPreferredSize(new Dimension(784,70));
        buttonholder.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonholder.setOpaque(false);
        
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        add(buttonholder, c);
        
            standardBtn = new JButton();
            standardBtn.setIcon(new ImageIcon(getClass().getResource("/resources/std.png")));
            standardBtn.setDisabledIcon(new ImageIcon(getClass().getResource("/resources/stddbl.png")));
            standardBtn.setFocusPainted(false);
            standardBtn.setBackground(Color.BLACK);
            standardBtn.setForeground(Color.WHITE);
            buttonholder.add(standardBtn);

            taikoBtn = new JButton();
            taikoBtn.setIcon(new ImageIcon(getClass().getResource("/resources/taiko.png")));
            taikoBtn.setDisabledIcon(new ImageIcon(getClass().getResource("/resources/taikodbl.png")));
            taikoBtn.setFocusPainted(false);
            taikoBtn.setBackground(Color.BLACK);
            taikoBtn.setForeground(Color.WHITE);
            buttonholder.add(taikoBtn);
            
            ctbBtn = new JButton();
            ctbBtn.setIcon(new ImageIcon(getClass().getResource("/resources/ctb.png")));
            ctbBtn.setDisabledIcon(new ImageIcon(getClass().getResource("/resources/ctbdbl.png")));
            ctbBtn.setFocusPainted(false);
            ctbBtn.setBackground(Color.BLACK);
            ctbBtn.setForeground(Color.WHITE);
            buttonholder.add(ctbBtn);
            
            maniaBtn = new JButton();
            maniaBtn.setIcon(new ImageIcon(getClass().getResource("/resources/mania.png")));
            maniaBtn.setDisabledIcon(new ImageIcon(getClass().getResource("/resources/maniadbl.png")));
            maniaBtn.setFocusPainted(false);
            maniaBtn.setBackground(Color.BLACK);
            maniaBtn.setForeground(Color.WHITE);
            buttonholder.add(maniaBtn);
            
            settingsBtn = new JButton();
            settingsBtn.setIcon(new ImageIcon(getClass().getResource("/resources/settings.png")));
            settingsBtn.setFocusPainted(false);
            settingsBtn.setBackground(Color.BLACK);
            settingsBtn.setForeground(Color.WHITE);
            buttonholder.add(settingsBtn);
        //</editor-fold>
            
        //Player data display   
        //<editor-fold>
        userdata = new JPanel();
        userdata.setBorder(BorderFactory.createEtchedBorder(0));
        userdata.setLayout(new BorderLayout());
        userdata.setBackground(new Color(190,190,220));
        userdata.setPreferredSize(new Dimension(2000,50));
        userdata.setMinimumSize(new Dimension(500,50));
        userdata.setMaximumSize(new Dimension(2000,50));
        
            username = new JLabel(Settings.getUser());
            username.setFont(ubufont.deriveFont(30.0f));
            username.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
            username.setForeground(new Color(0,0,180));
            userdata.add(username, BorderLayout.WEST);
            
            userstat = new JPanel();
            userstat.setLayout(new GridLayout(2,2));
            userdata.add(userstat, BorderLayout.EAST);
            
                userpp = new JLabel("Choose a");
                userpp.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
                userpp.setFont(ubufont.deriveFont(20.0f));
                userstat.add(userpp);
            
                JLabel empty1 = new JLabel("");
                userstat.add(empty1);
                
                useracc = new JLabel("gamemode");
                useracc.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
                useracc.setFont(ubufont.deriveFont(20.0f));
                userstat.add(useracc);
                
                JLabel empty2 = new JLabel("");
                userstat.add(empty2);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        add(userdata, c);
        //</editor-fold>
            
        //Scores display container
        //<editor-fold>
        display = new JPanel();
        display.setLayout(new GridBagLayout());
        cards = 1;
        display.setBackground(new Color(20,45,60));
        scrollpane = new JScrollPane(display);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 1;
            c.weightx = 1;
            blank = new JPanel();
            blank.setBackground(Color.DARK_GRAY);
            display.add(blank, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(scrollpane, c);
        //</editor-fold>
        
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
                username.setText(Settings.getUser());
            }
            
            else if (e.getSource() == standardBtn)
            {
                if (timer.isRunning())
                {
                    timer.stop();
                }
                
                Player.setMode(Mode.STANDARD);
                updatePlayer();
                
                standardBtn.setEnabled(false);
                taikoBtn.setEnabled(true);
                ctbBtn.setEnabled(true);
                maniaBtn.setEnabled(true);
                
                timer.start();
            }
            
            else if (e.getSource() == taikoBtn)
            {
                if (timer.isRunning())
                {
                    timer.stop();
                }
                
                Player.setMode(Mode.TAIKO);
                updatePlayer();
                
                standardBtn.setEnabled(true);
                taikoBtn.setEnabled(false);
                ctbBtn.setEnabled(true);
                maniaBtn.setEnabled(true);
                
                timer.start();
            }
            
            else if (e.getSource() == ctbBtn)
            {
                if (timer.isRunning())
                {
                    timer.stop();
                }
                
                Player.setMode(Mode.CTB);
                updatePlayer();
                
                standardBtn.setEnabled(true);
                taikoBtn.setEnabled(true);
                ctbBtn.setEnabled(false);
                maniaBtn.setEnabled(true);
                
                timer.start();
            }
            
            else if (e.getSource() == maniaBtn)
            {
                if (timer.isRunning())
                {
                    timer.stop();
                }
                
                Player.setMode(Mode.MANIA);
                updatePlayer();
                
                standardBtn.setEnabled(true);
                taikoBtn.setEnabled(true);
                ctbBtn.setEnabled(true);
                maniaBtn.setEnabled(false);
                
                timer.start();
            }
            
            else if (e.getSource() == timer)
            {
                if (Player.gamePlayed())
                {
                    Player.update();
                    BigDecimal deltapp = Player.ppChange();
                    BigDecimal deltaacc = Player.accChange();
                    pushCard(deltapp, deltaacc);
                    updatePlayer();
                }
            }
        }
    }
    
    private void updatePlayer()
    {
        userpp.setText(Player.getPP().toString() + " pp");
        useracc.setText(Player.getAcc().toString() + "%");
    }
    
    private Font fontFromFile(String location) 
    {
        try
        {
            URL url = getClass().getResource("/resources/ubuntu.ttf");
            Font fnt = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
            Font fnlfnt = fnt.deriveFont(Font.PLAIN, 12);
            return fnlfnt;
        }
        catch (FontFormatException | IOException ex)
        {
            System.out.println("help");
            return new Font("Helvetica", Font.PLAIN, 40);
        }
    }
    
    private void pushCard(BigDecimal deltapp, BigDecimal deltaacc)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel card = new JPanel();
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        card.setLayout(new GridBagLayout());
        card.setBackground(Color.BLACK);
        card.setPreferredSize(new Dimension(500,50));
        card.setMinimumSize(new Dimension(500,50));
        card.setMaximumSize(new Dimension(2000,50));
        
        //Rank JLabel
        //<editor-fold>
        JLabel rank = new JLabel();
        String rk = Player.getRank();
        switch(rk)
        {
            case "F":
                rank.setIcon(new ImageIcon(getClass().getResource("/resources/f.png")));
                break;
            case "D":
                rank.setIcon(new ImageIcon(getClass().getResource("/resources/d.png")));
                break;
            case "C":
                rank.setIcon(new ImageIcon(getClass().getResource("/resources/c.png")));
                break;
            case "B":
                rank.setIcon(new ImageIcon(getClass().getResource("/resources/b.png")));
                break;
            case "A":
                rank.setIcon(new ImageIcon(getClass().getResource("/resources/a.png")));
                break;
            case "S":
                rank.setIcon(new ImageIcon(getClass().getResource("/resources/s.png")));
                break;
            case "SH":
                rank.setIcon(new ImageIcon(getClass().getResource("/resources/sh.png")));
                break;
            case "X":
                rank.setIcon(new ImageIcon(getClass().getResource("/resources/x.png")));
                break;
            case "XH":
                rank.setIcon(new ImageIcon(getClass().getResource("/resources/xh.png")));
                break;    
        }
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        card.add(rank, gbc);
        //</editor-fold>
        
        //Map Text JPanel
        //<editor-fold>
        JPanel map = new JPanel();
        map.setPreferredSize(new Dimension(2000,2000));
        map.setBackground(Color.BLACK);
        map.setLayout(new GridLayout(2,1));
        
            JLabel mapname = new JLabel(Player.getBeatmapName());
            mapname.setHorizontalAlignment(JLabel.LEFT);
            mapname.setForeground(Color.WHITE);
            mapname.setFont(ubufont.deriveFont(15.0f));
            map.add(mapname);
            
            JLabel mapacc = new JLabel(Player.getAcc() + "%");
            mapacc.setForeground(Color.WHITE);
            mapacc.setFont(ubufont.deriveFont(Font.BOLD, 14.0f));
            map.add(mapacc);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        card.add(map, gbc);
        //</editor-fold>
          
        //RawPP JLabel
        //<editor-fold>
        JLabel rawpp = new JLabel();
        rawpp.setFont(ubufont.deriveFont(Font.BOLD, 32.0f));
        rawpp.setBorder(BorderFactory.createEmptyBorder(0,0,0,8));
        rawpp.setForeground(Color.WHITE);
        double toppp = Player.userBeatmapPP();
        if(toppp != 0)
            rawpp.setText(String.format("%.2f pp", toppp));
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 1;
        card.add(rawpp, gbc);
        //</editor-fold>
        
        //pp change data JPanel
        //<editor-fold>
        JPanel gains = new JPanel();
        gains.setBackground(Color.BLACK);
        gains.setLayout(new GridLayout(2,1));
        
            JLabel ppgain = new JLabel();
            ppgain.setFont(ubufont.deriveFont(Font.BOLD, 16.0f));
            int comparepp = deltapp.compareTo(BigDecimal.ZERO); 
            if (comparepp == 1)
            {
                ppgain.setForeground(Color.GREEN);
                ppgain.setText("(+ " + deltapp.toString() + " pp)");
            }
            else if (comparepp == 0)
            {
                ppgain.setForeground(Color.GRAY);
                ppgain.setText("(± 0 pp)");
            }
            else
            {
                ppgain.setForeground(Color.RED);
                ppgain.setText("(" + deltapp.toString() + " pp)");
            }
            gains.add(ppgain);
            
            JLabel accgain = new JLabel();
            accgain.setFont(ubufont.deriveFont(Font.BOLD, 12.0f));
            int compareacc = deltaacc.compareTo(BigDecimal.ZERO);
            if (compareacc == 1)
            {
                accgain.setForeground(Color.GREEN);
                accgain.setText("(+ " + deltaacc.toString() + "%)");
            }
            else if (compareacc == 0)
            {
                accgain.setForeground(Color.GRAY);
                accgain.setText("(± 0.00%)");
            }
            else
            {
                accgain.setForeground(Color.RED);
                accgain.setText("(" + deltaacc.toString() + "%)");
            }
            gains.add(accgain);
            
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        card.add(gains, gbc);
        //</editor-fold>
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = cards;
        display.add(card, gbc);
        cards++;
        
        scrollpane.validate();
        scrollpane.getVerticalScrollBar().setValue(scrollpane.getVerticalScrollBar().getMaximum());
        
        revalidate();
        repaint();        
    } 
}