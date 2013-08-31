package me.kieranwallbanks.barrymore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.kieranwallbanks.barrymore.configuration.Configuration;
import me.kieranwallbanks.barrymore.mysql.MySQL;
import me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord;
import me.kieranwallbanks.barrymore.util.ReflectionsUtilities;
import org.jooq.DSLContext;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static me.kieranwallbanks.barrymore.mysql.Tables.USERS;

public class Barrymore {
    private static Barrymore INSTANCE;

    private Configuration config;
    private DSLContext context;
    private PircBotX bot;

    private boolean openForRegistration = true;
    private Map<String, UsersRecord> registeredUsers = new HashMap<String, UsersRecord>();
    private Map<String, Theme> themeMap = new HashMap<String, Theme>();

    public static void main(String[] args) {
        INSTANCE = new Barrymore();
    }

    public Barrymore() {
        config = new Configuration(this);
        context = MySQL.getContext(config);
        bot = new PircBotX();

        try {
            bot.setName(config.IRC_Nickname);
            bot.setLogin("Barrymore");
            bot.identify(config.IRC_NickServPass);
            bot.setVerbose(true);
            bot.setCapEnabled(true);
            bot.connect(config.IRC_DefServer);
            bot.joinChannel(config.IRC_DefChannel);
            bot.getListenerManager().addListener(new Listener(this));
        } catch (Exception e) {
            e.printStackTrace(); // TODO better exception handling. Maybe email me?
            System.exit(1);
        }

        try {
            for(Class<? extends Theme> clazz : ReflectionsUtilities.getSubtypesOf(Theme.class, "me.kieranwallbanks.barrymore.theme", ClassLoader.getSystemClassLoader())) {
                Theme theme = clazz.newInstance();
                themeMap.put(theme.getName(), theme);
            }
        } catch (Exception e) {
            e.printStackTrace(); // TODO better exception handling. Maybe email me?
            System.exit(1);
        }

        reloadUsers();
    }

    /**
     * Gets the folder the jar is located in
     *
     * @return the folder
     */
    public File getDataFolder() {
        File file = new File(System.getProperty("user.home"), "Barrymore");

        if(!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    /**
     * Gets the current configuration
     *
     * @return the {@link Configuration}
     */
    public Configuration getConfiguration() {
        return config;
    }

    /**
     * Gets the MySQL context for use ith jOOQ
     *
     * @return the {@link DSLContext}
     */
    public DSLContext getMySQLContext() {
        return context;
    }

    /**
     * Gets the {@link PircBotX} bot currently in use
     *
     * @return the bot
     */
    public PircBotX getBot() {
        return bot;
    }

    /**
     * Gets the theme from it's name
     *
     * @param theme the theme name
     *
     * @return the theme
     */
    public Theme getTheme(String theme) {
        return themeMap.containsKey(theme.toLowerCase().trim()) ? themeMap.get(theme.toLowerCase().trim()) : themeMap.get("british butler");
    }

    /**
     * Checks if a user is a registered user
     *
     * @param user the user
     *
     * @return {@code true} if they are a registered user
     */
    public boolean isUserRegistered(User user) {
        return registeredUsers.containsKey(user.getNick());
    }

    /**
     * Gets if Barrymore is open for registration
     *
     * @return {@code true} if Barrymore is open for registration
     */
    public boolean isOpenForRegistration() {
        return openForRegistration;
    }

    /**
     * Sets whether Barrymore is open for registration
     *
     * @param openForRegistration {@code true} if Barrymore is open for registration
     */
    public void setOpenForRegistration(boolean openForRegistration) {
        this.openForRegistration = openForRegistration;
    }

    /**
     * Reloads the registered user map
     */
    public void reloadUsers() {
        registeredUsers.clear();

        for(UsersRecord record : context.select().from(USERS).fetchInto(UsersRecord.class)) {
            registeredUsers.put(record.getIrc(), record);
        }
    }

}