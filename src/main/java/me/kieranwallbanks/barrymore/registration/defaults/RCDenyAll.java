package me.kieranwallbanks.barrymore.registration.defaults;

import me.kieranwallbanks.barrymore.registration.RegistrationChecker;
import org.pircbotx.User;

public class RCDenyAll implements RegistrationChecker {

    @Override
    public boolean canRegister(User user) {
        user.sendMessage("Dreadfully sorry. User registration is disabled at the moment.");
        return false;
    }

}
