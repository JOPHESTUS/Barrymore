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
    public String EXIT()                  { return get("Exit"); }
    public String AUTH_NEEDED()           { return get("AuthNeeded"); }
    public String AUTH_ATTEMPTING()       { return get("AuthAttempting"); }
    public String AUTH_FAILED()           { return get("AuthFailed"); }

    // START COMMAND
    public String COMMAND_CONFIRMATION()  { return get("CommandConfirmation"); }
    public String COMMAND_REJECTION()     { return get("CommandRejection"); }
    public String COMMAND_CONFUSION()     { return get("CommandConfusion"); }
    public String COMMAND_NOT_FOUND()     { return get("CommandNotFound");}
    public String NO_PERMISSION_MESSAGE() { return get("NoPermissionMessage"); }

    // START LIST THEMES
    public String THEME_LIST()            { return get("ThemeList"); }

    // START THEME CHANGE
    public String THEME_CHANGER_CHECK()   { return get("ThemeChangeCheck"); }
    public String THEME_DOESNT_EXIST()    { return get("ThemeDoesntExist"); }
    public String THEME_CHANGED()         { return get("ThemeChanged"); }

    // START RC CHANGE
    public String RC_CHANGE_CHECK()       { return get("RCChangeCheck"); }
    public String RC_CHANGED()            { return get("RCChanged"); }

    // START PRIVATE
    private String get(String string) {
        return properties.getProperty(string, "Sorry, I couldn't find this string in the theme " + name + ". Either update the theme or go and bug the authors (" + getAuthors() + ").");
    }

    @Override
    public String toString() {
        return getName();
    }

}
