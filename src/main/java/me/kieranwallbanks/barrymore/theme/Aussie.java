package me.kieranwallbanks.barrymore.theme;

import me.kieranwallbanks.barrymore.Theme;

public class BritishButler implements Theme {

    @Override
    public String getName() {
        return "Aussie";
    }

    @Override
    public String COMMAND_NOT_FOUND() {
        return "Sorry mate, I couldn't find that command.";
    }

    @Override
    public String NO_PERMISSION_MESSAGE() {
        return "Shit, you don't have permission to use that.";
    }

    @Override
    public String EXIT() {
        return "Cya, I'm gonna go throw another snag on the barbie.";
    }

}
