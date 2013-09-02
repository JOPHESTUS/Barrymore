package me.kieranwallbanks.barrymore.util;

import java.util.Collection;

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

    /**
     * Joins an array with the specified join string
     *
     * @param array the array to join
     * @param joinString the string to join the array with
     *
     * @return a string containing the joined array
     */
    public static String joinArray(Object[] array, String joinString) {
        return joinArray(array, joinString, joinString);
    }

    /**
     * Joins an array with the specified join string
     *
     * @param array the array to join
     * @param joinString the string to join the array with
     * @param finalJoinString the string to join at the final join
     *
     * @return a string containing the joined array
     */
    public static String joinArray(Object[] array, String joinString, String finalJoinString) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < array.length; i++) {
            builder.append(array[i]);

            if(i == array.length - 2) {
                builder.append(finalJoinString);
            } else if(i < array.length - 2) {
                builder.append(joinString);
            }
        }

        return builder.toString();
    }

    /**
     * Joins a collection with the specified join string
     *
     * @param collection the array to join
     * @param joinString the string to join the collection with
     *
     * @return a string containing the joined collection
     */
    public static String joinCollection(Collection<?> collection, String joinString) {
        return joinCollection(collection, joinString, joinString);
    }

    /**
     * Joins an collection with the specified join string
     *
     * @param collection the array to join
     * @param joinString the string to join the collection with
     * @param finalJoinString the string to join at the final join
     *
     * @return a string containing the joined collection
     */
    public static String joinCollection(Collection<?> collection, String joinString, String finalJoinString) {
        return joinArray(collection.toArray(), joinString, finalJoinString);
    }

}
