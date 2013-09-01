package me.kieranwallbanks.barrymore.theme;

import java.util.Properties;

/**
 * Represents a loaded theme file
 */
public class Theme {
    private String name;
    private Properties properties;

    /**
     * Constructs a new theme
     *
     * @param name the name of the theme
     * @param properties the loaded theme properties file
     */
    public Theme(String name, Properties properties) {
        this.name = name;
        this.properties = properties;
    }

    // START THEME INFORMATION
    public String getName() {
        return name;
    }

    public String getAuthors() {
        return properties.getProperty("Authors", "Unknown Author(s)");
    }

    // START GENERAL
    public String EXIT() { return get("Exit"); }

    // START COMMAND
    public String COMMAND_NOT_FOUND() { return get("CommandNotFound");}
    public String NO_PERMISSION_MESSAGE() { return get("NoPermissionMessage"); }

    // START PRIVATE
    private String get(String string) {
        return properties.getProperty(string, "Sorry, I couldn't find this string in the theme " + name + ". Either update the theme or go and bug the authors (" + getAuthors() + ").");
    }

}
