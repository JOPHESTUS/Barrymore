package me.kieranwallbanks.barrymore.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class containing Bukkit Dev related utilities
 */
public class BukkitDevUtilities {

    /**
     * Gets someone's Bukkit Dev username from their api key
     *
     * @param api the api key
     *
     * @return the username, or an empty string if not found
     */
    public static String getUsernameFromAPIKey(String api) {
        String webpage = "";
        try {
            webpage = LangUtilities.stripWhitespace(WebUtilities.readURLToString(new URL("http://dev.bukkit.org/home/?api-key=" + api)));
        } catch (MalformedURLException e) { /* It won't happen. If it does, WebUtilities will catch the error for us */ }

        try {
            return webpage.split(">")[5].split("-")[0];
        } catch (Exception e) {
            return ""; // If something bad happened :/
        }
    }

}
