package me.kieranwallbanks.barrymore.util;

/**
 * General String based utility class
 */
public class LangUtilities {

    /**
     * Strips all whitespace from a String
     *
     * @param string the string
     *
     * @return a whitespace-less version of the string
     */
    public static String stripWhitespace(String string) {
        return string.replaceAll("\\s","");
    }

    /**
     * Checks if a string is affirmative
     *
     * @param string the string
     *
     * @return {@code true} if the string is affirmative
     */
    public static boolean isAffirmative(String string) {
        string = string.toLowerCase().trim().replaceAll("\\W", "");
        return string.startsWith("affirmative") || string.startsWith("amen") ||
               string.startsWith("fine") || string.startsWith("good") ||
               string.startsWith("ok") || string.startsWith("true") ||
               string.startsWith("yea") || string.startsWith("all right") ||
               string.startsWith("aye") || string.startsWith("beyond a doubt") ||
               string.startsWith("by all means") || string.startsWith("certainly") ||
               string.startsWith("definitely") || string.startsWith("even so") ||
               string.startsWith("exactly") || string.startsWith("gladly") ||
               string.startsWith("granted") || string.startsWith("indubitably") ||
               string.startsWith("just so") || string.startsWith("most assuredly") ||
               string.startsWith("naturally") || string.startsWith("of course") ||
               string.startsWith("positively") || string.startsWith("precisely") ||
               string.startsWith("sure") || string.startsWith("undoubtedly") ||
               string.startsWith("unquestionably") || string.startsWith("very well") ||
               string.startsWith("willingly") || string.startsWith("without fail") ||
               string.startsWith("yup") || string.startsWith("yep") ||
               string.startsWith("yes");
    }

    /**
     * Checks if a string is dissenting, or negative
     *
     * @param string the string
     *
     * @return {@code true} if the string is dissenting
     */
    public static boolean isDissenting(String string) { // This one is easier ;)
        string = string.toLowerCase().trim().replaceAll("\\W", "");
        return string.startsWith("no") || string.startsWith("nix") ||
               string.startsWith("negative") || string.startsWith("never") ||
               string.startsWith("not");
    }

}
