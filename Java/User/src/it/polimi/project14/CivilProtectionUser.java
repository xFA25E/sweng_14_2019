package it.polimi.project14;

import java.util.Set;

import it.polimi.project14.user.Caps;
import it.polimi.project14.user.User;
import it.polimi.project14.user.gui.FrameCivilProtectionUser;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class CivilProtectionUser {
    public static void main(String[] args) {
        Set<String> comoCaps = Caps.filter("Como", null);
        User user = new User(comoCaps);
        FrameCivilProtectionUser userGui = new FrameCivilProtectionUser(user);
        userGui.setSize(600, 700);

        SystemTray tray  = SystemTray.getSystemTray();
        Image civilProtectionIcon = Toolkit.getDefaultToolkit().createImage("protezione-civile-icona.png");
        
        TrayIcon trayIcon = new TrayIcon(civilProtectionIcon, "Protezione Civile");
  
        trayIcon.setImageAutoSize(true);
        try {
           tray.add(trayIcon);
        } catch (Exception e) {
        }
  
        trayIcon.displayMessage("Nuova previsione fra i tuoi caps", "Terremoto 20:00", MessageType.WARNING);
    }
}
