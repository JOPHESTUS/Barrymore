package me.kieranwallbanks.barrymore;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.kieranwallbanks.barrymore.command.CommandListener;
import me.kieranwallbanks.barrymore.configuration.Configuration;
import me.kieranwallbanks.barrymore.mysql.MySQL;
import me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord;
import me.kieranwallbanks.barrymore.theme.ThemeManager;
import org.jooq.DSLContext;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static me.kieranwallbanks.barrymore.mysql.Tables.USERS;

public class Barrymore {
    private static Barrymore INSTANCE;

    private final Configuration config;
    private final DSLContext context;
    private final PircBotX bot;
    private final ThemeManager themeManager;
    private final CommandListener commandListener;

    private boolean openForRegistration = true;
    private final Map<String, UsersRecord> registeredUsers = new HashMap<String, UsersRecord>();

    public static void main(String[] args) {
        INSTANCE = new Barrymore();
    }

    public Barrymore() {
        config = new Configuration(this);
        context = MySQL.getContext(config);
        bot = new PircBotX();
        themeManager = new ThemeManager(this);
        commandListener = new CommandListener(this);

        try {
            bot.setName(config.IRC_Nickname);
            bot.setLogin("Barrymore");
            bot.identify(config.IRC_NickServPass);
            bot.setVerbose(true);
            bot.setCapEnabled(true);
            bot.connect(config.IRC_DefServer);
            bot.joinChannel(config.IRC_DefChannel);
            bot.getListenerManager().addListener(commandListener);
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
     * Gets the MySQL context for use with jOOQ
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
     * Gets the {@link ThemeManager} currently in use
     *
     * @return the ThemeManager
     */
    public ThemeManager getThemeManager() {
        return themeManager;
    }

    /**
     * Gets the {@link CommandListener} currently in use
     *
     * @return what do you think
     */
    public CommandListener getCommandListener() {
        return commandListener;
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

    /**
     * Gets the {@link UsersRecord} linked with the associated user
     *
     * @param user the user
     *
     * @return the UsersRecord or null if not found
     */
    public UsersRecord getUsersRecord(User user) {
        return registeredUsers.get(user.getNick());
    }

    /**
     * Gets a {@link Collection} of {@link UsersRecord} for all registered users
     *
     * @return the collection
     */
    public Collection<UsersRecord> getUsers() {
        return registeredUsers.values();
    }

}