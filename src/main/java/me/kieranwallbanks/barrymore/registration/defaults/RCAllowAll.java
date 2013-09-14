package me.kieranwallbanks.barrymore.registration.defaults;

import me.kieranwallbanks.barrymore.registration.RegistrationChecker;
import org.pircbotx.User;

public class RCAllowAll implements RegistrationChecker {

    @Override
    public boolean canRegister(User user) {
        return true;
    }

}
