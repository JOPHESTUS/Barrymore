package me.kieranwallbanks.barrymore.command.defaults;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.command.BaseCommand;
import me.kieranwallbanks.barrymore.command.CommandCaller;
import me.kieranwallbanks.barrymore.command.InstantCommandListener;
import me.kieranwallbanks.barrymore.theme.Theme;
import org.pircbotx.User;

import static me.kieranwallbanks.barrymore.mysql.Tables.*;

public class ThemeChangerCommand extends BaseCommand {
    private Barrymore barrymore;

    public ThemeChangerCommand(Barrymore instance) {
        super(instance);

        barrymore = instance;

        setName("theme changer");
        setDescription("lets me change your theme");
        setConfusion("change your theme");
        setCaller(new CommandCaller().contains("change theme").contains("theme change").contains("change my theme"));
    }

    @Override
    public void onCommand(final User sender) {
        final Theme theme = barrymore.getThemeManager().getUsersTheme(sender);

        sender.sendMessage(theme.THEME_CHANGER_CHECK());

        barrymore.getCommandListener().registerInstantCommandListener(sender, new InstantCommandListener() {

            @Override
            public void onPrivateMessage(String message) {
                Theme potentialTheme = barrymore.getThemeManager().getThemeByName(message.toLowerCase().trim());

                if(potentialTheme == null) {
                    sender.sendMessage(theme.THEME_DOESNT_EXIST());
                } else {
                    barrymore.getUsersRecord(sender).setTheme(potentialTheme.getName());                       // First update locally
                    sender.sendMessage(potentialTheme.THEME_CHANGED());                                        // Then send a message
                    barrymore.getMySQLContext().update(USERS).set(barrymore.getUsersRecord(sender)).execute(); // Then update the database
                }
            }

        });
    }

}
