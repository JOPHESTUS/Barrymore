package me.kieranwallbanks.barrymore.command.defaults;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.command.BaseCommand;
import me.kieranwallbanks.barrymore.command.CommandCaller;
import org.pircbotx.User;

public class HelpCommand extends BaseCommand {
    private final Barrymore barrymore;

    public HelpCommand(Barrymore instance) {
        super(instance);

        barrymore = instance;

        setName("help");
        setDescription("displays help");
        setConfusion("obtain help");
        setCaller(new CommandCaller().contains("help me"));
    }

    @Override
    public void onCommand(User sender) {
        for(BaseCommand command : barrymore.getCommandListener().getCommands()) {
            sender.sendMessage(command.getName() + " - " + command.getDescription());
        }
    }

}
