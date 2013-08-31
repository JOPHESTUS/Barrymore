package me.kieranwallbanks.barrymore.command;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.BaseCommand;
import org.pircbotx.User;

public class ShutdownCommand extends BaseCommand {
    private Barrymore barrymore;

    public ShutdownCommand(Barrymore instance) {
        super(instance);

        barrymore = instance;

        setName("shutdown");
        setDescription("Causes Barrymore to shutdown");
    }

    @Override
    public void onCommand(User sender, String[] args) {
        if(barrymore.getConfiguration().USERS_Admins.contains(sender.getNick())) {
            System.exit(0);
        } else {

        }
    }

}
