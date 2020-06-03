package it.polimi.project14;

import java.util.Set;
import java.util.logging.Logger;

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
    }
}
