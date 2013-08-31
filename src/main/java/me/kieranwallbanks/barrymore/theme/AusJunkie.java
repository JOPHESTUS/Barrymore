package me.kieranwallbanks.barrymore.theme;

import me.kieranwallbanks.barrymore.Theme;

public class AusJunkie implements Theme {

    @Override
    public String getName() {
        return "aussie junkie";
    }

    @Override
    public String COMMAND_NOT_FOUND() {
        return "Hold the fuck up! I can't find that command, I gotta find me bong first";
    }

    @Override
    public String NO_PERMISSION_MESSAGE() {
        return "You can't fucking do that.";
    }

    @Override
    public String EXIT() {
        return "Cya, I gotta go collect me pension from centrelink to buy some grog";
    }

}
