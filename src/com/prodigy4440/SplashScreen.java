/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prodigy4440;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author prodigy4440
 */
public class SplashScreen extends JWindow {
    
    private static final long serialVersionUID = 19463494634394376L;
    
    private JLabel mainContentJLabel;
    private JLabel statusJLabel;
    private final ImageIcon imageIcon;
    
    public SplashScreen() {
        this.imageIcon = new ImageIcon(getClass().getResource("/com/prodigy4440/Splash.png"));
        initComponents();
    }
    
    public final void initComponents() {
//        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - (getWidth() / 2), (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - (getHeight() / 2));
        this.setSize(320, 240);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.getContentPane().setBackground(Color.WHITE);
        mainContentJLabel = new JLabel(imageIcon);
        statusJLabel = new JLabel("Starting application . . .");
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new EmptyBorder(2, 2, 2, 2));
        panel.add(statusJLabel);
        panel.setOpaque(false);
        statusJLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        
        add(mainContentJLabel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }
    
    public void updateStatusText(String txt) {
        statusJLabel.setText(txt);
    }
}
