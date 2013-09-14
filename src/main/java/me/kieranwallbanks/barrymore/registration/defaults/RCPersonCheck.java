package me.kieranwallbanks.barrymore.registration.defaults;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.command.InstantCommandListener;
import me.kieranwallbanks.barrymore.registration.RegistrationChecker;
import me.kieranwallbanks.barrymore.util.LangUtilities;
import org.pircbotx.User;

import java.util.HashSet;
import java.util.Set;

public class RCPersonCheck implements RegistrationChecker {
    private final User checkWith;
    private final Barrymore barrymore;
    private final Set<String> allowed = new HashSet<String>();

    public RCPersonCheck(Barrymore instance, User checker) {
        checkWith = checker;
        barrymore = instance;
    }

    @Override
    public boolean canRegister(final User user) {
        if(allowed.contains(user.getNick())) {
            allowed.remove(user.getNick());
            return true;
        }

        checkWith.sendMessage(user.getNick() + " is trying to register. Is this allowed?");
        barrymore.getCommandListener().registerInstantCommandListener(checkWith, new InstantCommandListener() {

            @Override
            public void onPrivateMessage(String message) {
                if(LangUtilities.isAffirmative(message)) {
                    allowed.add(user.getNick());
                }
            }

        });

        user.sendMessage("One is just awaiting confirmation that you can register. Please try again later.");
        return false;
    }

}
