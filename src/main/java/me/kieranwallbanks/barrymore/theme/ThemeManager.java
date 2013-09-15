package me.kieranwallbanks.barrymore.theme;

import me.kieranwallbanks.barrymore.Barrymore;
import org.pircbotx.User;

import java.io.File;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Class that manages themes.
 * Jeeze, these javadocs don't get much more descriptive than this!
 */
public class ThemeManager {
    private final Barrymore barrymore;
    private final SortedMap<String, Theme> themeMap = new TreeMap<String, Theme>();

    /**
     * Constructs a new {@link ThemeManager}
     *
     * @param instance an instance of {@link Barrymore}
     */
    public ThemeManager(Barrymore instance) {
        barrymore = instance;

        try {
            File jar = new File(Barrymore.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            ZipFile zip = new ZipFile(jar);
            Enumeration<? extends ZipEntry> entries = zip.entries();

            while(entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if(entry.getName().matches("themes/.*\\.theme\\.properties")) {
                    Properties properties = new Properties();
                    properties.load(zip.getInputStream(entry));

                    Theme theme = new Theme(entry.getName().replace("themes/", "").replace(".theme.properties", "").replace("_", " "), properties);

                    themeMap.put(theme.getName(), theme);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // TODO better exception handling. Email me?
            System.exit(1);
        }
    }

    /**
     * Gets the theme a user has chosen to use.
     * This function will never return {@code null}.
     *
     * @param user the user
     *
     * @return the theme
     */
    public Theme getUsersTheme(User user) {
        Theme theme = themeMap.get(barrymore.getUserManager().getUser(user.getNick()).getTheme());
        return theme == null ? themeMap.get("british butler") : theme;
    }

    /**
     * Gets the theme a user has chosen to use.
     * This function will never return {@code null}.
     *
     * @param user the user
     *
     * @return the theme
     */
    public Theme getUsersTheme(me.kieranwallbanks.barrymore.user.User user) {
        Theme theme = themeMap.get(user.getTheme());
        return theme == null ? themeMap.get("british butler") : theme;
    }

    /**
     * Gets a {@link Theme} by its name
     *
     * @param name the theme name
     *
     * @return the theme or {@code null} if not found
     */
    public Theme getThemeByName(String name) {
        return themeMap.get(name);
    }

    /**
     * Gets all loaded {@link Theme}s
     *
     * @return the themes
     */
    public Collection<Theme> getThemes() {
        return themeMap.values();
    }

}
