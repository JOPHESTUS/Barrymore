package me.kieranwallbanks.barrymore.theme;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import me.kieranwallbanks.barrymore.Barrymore;
import org.pircbotx.User;

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
                if(entry.getName().matches(".*\\.theme\\.properties")) {
                    Properties properties = new Properties();
                    properties.load(zip.getInputStream(entry));

                    Theme theme = new Theme(entry.getName().replace(".theme.properties", ""), properties);

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
        Theme theme = themeMap.get(barrymore.getUsersRecord(user).getTheme());
        return theme == null ? themeMap.get("british butler") : theme;
    }

}
