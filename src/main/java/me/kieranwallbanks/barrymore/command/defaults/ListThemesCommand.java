package me.kieranwallbanks.barrymore.command.defaults;

import java.util.Collection;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.command.BaseCommand;
import me.kieranwallbanks.barrymore.command.CommandCaller;
import me.kieranwallbanks.barrymore.util.LangUtilities;
import org.pircbotx.User;

public class ListThemesCommand extends BaseCommand {
    private Barrymore barrymore;

    public ListThemesCommand(Barrymore instance) {
        super(instance);

        barrymore = instance;

        setName("list themes");
        setDescription("lists available themes");
        setConfusion("list available themes");
        setCaller(new CommandCaller().contains("list themes").contains("show themes").contains("list all themes").contains("show all themes"));
    }

    @Override
    public void onCommand(User sender) {
        sender.sendMessage(String.format(barrymore.getThemeManager().getUsersTheme(sender).THEME_LIST(), LangUtilities.joinCollection(barrymore.getThemeManager().getThemes(), ", ", " and ")));
    }

}
