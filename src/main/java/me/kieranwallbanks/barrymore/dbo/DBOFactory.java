package me.kieranwallbanks.barrymore.dbo;

import me.kieranwallbanks.barrymore.util.LangUtilities;
import me.kieranwallbanks.barrymore.util.WebUtilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Collection of awesome DBO stuff!
 */
public class DBOFactory {

    /**
    * Gets someone's Bukkit Dev username from their api key
    *
    * @param api the api key
    *
    * @return the username, or an empty string if not found
    */
    public String getUsernameFromAPIKey(String api) {
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

    public int getUnreadMessages(String api) {
        String webpage = "";
        try {
            webpage = WebUtilities.readURLToString(new URL("http://dev.bukkit.org/home/private-messages/?api-key=" + api));
        } catch (MalformedURLException e) {/* Still won't happen */}

        int messages = 0;
        Matcher m = Pattern.compile("New Messages").matcher(webpage);

        while(m.find()) {
            messages++;
        }

        return messages / 2;
    }

}
