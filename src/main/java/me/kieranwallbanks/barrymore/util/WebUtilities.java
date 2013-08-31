package me.kieranwallbanks.barrymore.util;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * Class containing web based utilities
 */
public class WebUtilities {

    /**
     * Reads a {@link URL} to a String.
     * If any exceptions are thrown, a blank String will be returned.
     *
     * @param url the url
     *
     * @return the contents of the webpage as a String
     */
    public static String readURLToString(URL url) {
        Scanner scanner;
        try {
            scanner = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A");
        } catch (IOException e) {
            return "";
        }
        String out = scanner.next();
        scanner.close();
        return out;
    }

}
