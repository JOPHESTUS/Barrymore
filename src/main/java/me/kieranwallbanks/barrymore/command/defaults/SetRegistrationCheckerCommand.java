package me.kieranwallbanks.barrymore.command.defaults;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.command.BaseCommand;
import me.kieranwallbanks.barrymore.command.CommandCaller;
import me.kieranwallbanks.barrymore.command.InstantCommandListener;
import me.kieranwallbanks.barrymore.registration.defaults.RCAllowAll;
import me.kieranwallbanks.barrymore.registration.defaults.RCDenyAll;
import me.kieranwallbanks.barrymore.registration.defaults.RCPersonCheck;
import me.kieranwallbanks.barrymore.theme.Theme;
import org.pircbotx.User;

public class SetRegistrationCheckerCommand extends BaseCommand {
    private final Barrymore barrymore;

    public SetRegistrationCheckerCommand(Barrymore instance) {
        super(instance);

        barrymore = instance;

        setName("set registration checker");
        setDescription("changes the registration checker");
        setConfusion("change the registration checker");
        setCaller(new CommandCaller().contains("change registration checker"));
    }

    @Override
    public void onCommand(final User sender) {
        final Theme theme = barrymore.getThemeManager().getUsersTheme(sender);

        if(barrymore.getConfiguration().USERS_Admins.contains(sender.getNick())) {
            sender.sendMessage(theme.RC_CHANGE_CHECK());
            barrymore.getCommandListener().registerInstantCommandListener(sender, new InstantCommandListener() {

                @Override
                public void onPrivateMessage(String message) {
                    if(message.equalsIgnoreCase("allow all")) {
                        barrymore.setRegistrationChecker(new RCAllowAll());
                    } else if(message.equalsIgnoreCase("deny all")) {
                        barrymore.setRegistrationChecker(new RCDenyAll());
                    } else if(message.equalsIgnoreCase("person check")) {
                        barrymore.setRegistrationChecker(new RCPersonCheck(barrymore, sender));
                    }
                    sender.sendMessage(theme.RC_CHANGED());
                }

            });
        } else {
            sender.sendMessage(theme.NO_PERMISSION_MESSAGE());
        }
    }

}
