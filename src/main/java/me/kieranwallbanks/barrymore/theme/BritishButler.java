package me.kieranwallbanks.barrymore.theme;

import me.kieranwallbanks.barrymore.Theme;

public class BritishButler implements Theme {

    @Override
    public String getName() {
        return "british butler";
    }

    @Override
    public String COMMAND_NOT_FOUND() {
        return "Dreadfully sorry, one can't seem to locate a command with that name.";
    }

    @Override
    public String NO_PERMISSION_MESSAGE() {
        return "Drats, you do not have permission to perform this command.";
    }

    @Override
    public String EXIT() {
        return "Jolly good; one is off now. Cheerio!";
    }

}
