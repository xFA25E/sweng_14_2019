package it.polimi.project14;

import java.util.Set;

import it.polimi.project14.user.Caps;
import it.polimi.project14.user.User;
import it.polimi.project14.user.gui.FrameCivilProtectionUser;

public class CivilProtectionUser {
    public static void main(String[] args) {
        Set<String> comoCaps = Caps.filter("Como", null);
        User user = new User(comoCaps);
        FrameCivilProtectionUser userGui = new FrameCivilProtectionUser(user);
    }
}
