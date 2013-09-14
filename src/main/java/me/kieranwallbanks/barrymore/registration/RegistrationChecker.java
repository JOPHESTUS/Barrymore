package me.kieranwallbanks.barrymore.registration;

import org.pircbotx.User;

public interface RegistrationChecker {

    /**
     * Checks if a user can register
     *
     * @param user the user
     *
     * @return {@code true} if they can
     */
    public boolean canRegister(User user);

}
