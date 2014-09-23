/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prodigy4440;

import com.prodigy4440.view.MainJFrame;
import com.prodigy4440.view.SerialKeyJDialog;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author prodigy4440
 */
public class YorTextEditor {

    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setVisible(true);
        try {
            Thread.sleep(400);
        } catch (InterruptedException ex) {
            Logger.getLogger(YorTextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

        splashScreen.updateStatusText("Checking activation status");

        SValidator sv = new SValidator();

        if (sv.validate()) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException ex) {
                Logger.getLogger(YorTextEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            SerialKeyJDialog serialKeyJDialog = new SerialKeyJDialog(null, true);
            serialKeyJDialog.setLocationRelativeTo(splashScreen);
            serialKeyJDialog.setVisible(true);
        }

        splashScreen.updateStatusText("Loading look and feel");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(YorTextEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(YorTextEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(YorTextEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(YorTextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Thread.sleep(800);
        } catch (InterruptedException ex) {
            Logger.getLogger(YorTextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        splashScreen.setAlwaysOnTop(false);
        splashScreen.setVisible(false);
        splashScreen.dispose();
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });

    }

    public static class SValidator {

        Preferences preferences;

        public SValidator() {
            preferences = Preferences.userRoot().node("com/prodigy4440/Yted");
        }

        public Preferences getPreferences() {
            return preferences;
        }

        public boolean validate() {
            String key = preferences.get("key", "Empty");
            String genSerialNumber = this.genSerialNumber();
            return genSerialNumber.equalsIgnoreCase(key);
        }

        public String genSerialNumber() {
            String serial = null;
            Sigar sigar = new Sigar();
            try {
                String hwaddr = sigar.getNetInterfaceConfig().getHwaddr();
                String[] split = hwaddr.split(":");
                String s = "";
                for (String string : split) {
                    s += string;
                }
                String sub1 = s.substring(0, 4);
                String sub2 = s.substring(4, 8);
                String sub3 = s.substring(8, s.length());
                OperatingSystem operatingSystem = OperatingSystem.getInstance();
                String user = sigar.getWhoList()[0].getUser();
                String uS = user.charAt(0) + "" + user.charAt(user.length() - 1);
                String userPart = uS.toUpperCase() + operatingSystem.getDataModel();
                serial = "YTED-" + sub3 + "-" + userPart + "-" + sub1 + "-" + sub2;
            } catch (SigarException ex) {
                System.out.println(ex.getMessage());
            }
            return serial;
        }
    }

}
