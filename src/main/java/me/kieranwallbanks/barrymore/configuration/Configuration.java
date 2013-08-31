package me.kieranwallbanks.barrymore.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import me.kieranwallbanks.barrymore.Barrymore;

/**
 * Class managing default configuration for {@link Barrymore}
 */
public class Configuration {
    private Barrymore barrymore;

    public String MySQL_Username, MySQL_Password, MySQL_Database, MySQL_Hostname;
    public String IRC_Nickname, IRC_DefChannel, IRC_DefServer, IRC_NickServPass;
    public SortedSet<String> USERS_Admins;

    /**
     * Constructs a new instance of {@link Configuration}
     *
     * @param instance an instance of {@link Barrymore}
     */
    public Configuration(Barrymore instance) {
        barrymore = instance;

        File configurationFile = new File(barrymore.getDataFolder(), "defaults.properties");
        Properties configurationProperties = new Properties();

        if(!configurationFile.exists()) {
            try {
                Files.copy(Barrymore.class.getResourceAsStream("/defaults.properties"), configurationFile.toPath());
            } catch (IOException e) {
                e.printStackTrace(); // TODO better exception handling. Maybe email me?
                System.exit(1);
            }
        }

        try {
            configurationProperties.load(new FileInputStream(configurationFile));
        } catch (IOException e) { /* The file will exist */ }

        MySQL_Username = configurationProperties.getProperty("MySQL_Username");
        MySQL_Password = configurationProperties.getProperty("MySQL_Password");
        MySQL_Database = configurationProperties.getProperty("MySQL_Database");
        MySQL_Hostname = configurationProperties.getProperty("MySQL_Hostname");

        IRC_Nickname = configurationProperties.getProperty("IRC_Nickname");
        IRC_NickServPass = configurationProperties.getProperty("IRC_NickServPass");
        IRC_DefChannel = configurationProperties.getProperty("IRC_DefChannel");
        IRC_DefServer = configurationProperties.getProperty("IRC_DefServer");

        USERS_Admins = new TreeSet<String>(Arrays.asList(configurationProperties.getProperty("USERS_Admin").split(",")));
    }

}
