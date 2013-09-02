package me.kieranwallbanks.barrymore.command.defaults;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.command.BaseCommand;
import me.kieranwallbanks.barrymore.command.CommandCaller;
import me.kieranwallbanks.barrymore.theme.Theme;
import org.pircbotx.User;

public class ShutdownCommand extends BaseCommand {
    private Barrymore barrymore;

    public ShutdownCommand(Barrymore instance) {
        super(instance);

        barrymore = instance;

        setName("shutdown");
        setDescription("causes me to shutdown");
        setConfusion("shut me down");
        setCaller(new CommandCaller().contains("shutdown"));
    }

    @Override
    public void onCommand(User sender) {
        Theme theme = barrymore.getThemeManager().getUsersTheme(sender);

        if(barrymore.getConfiguration().USERS_Admins.contains(sender.getNick())) {
            sender.sendMessage(theme.EXIT());
            System.exit(0);
        } else {
            sender.sendMessage(theme.NO_PERMISSION_MESSAGE());
        }
    }

}
