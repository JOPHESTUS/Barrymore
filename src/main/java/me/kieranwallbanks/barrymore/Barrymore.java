package me.kieranwallbanks.barrymore;

import me.kieranwallbanks.barrymore.command.CommandListener;
import me.kieranwallbanks.barrymore.configuration.Configuration;
import me.kieranwallbanks.barrymore.dbo.DBOFactory;
import me.kieranwallbanks.barrymore.fbo.FBOFactory;
import me.kieranwallbanks.barrymore.mysql.MySQL;
import me.kieranwallbanks.barrymore.registration.RegistrationChecker;
import me.kieranwallbanks.barrymore.registration.defaults.RCAllowAll;
import me.kieranwallbanks.barrymore.task.BTimerTask;
import me.kieranwallbanks.barrymore.theme.ThemeManager;
import me.kieranwallbanks.barrymore.user.UserManager;
import me.kieranwallbanks.barrymore.util.ReflectionsUtilities;
import org.jooq.DSLContext;
import org.pircbotx.PircBotX;

import java.io.File;
import java.util.Timer;

public class Barrymore {
    private static Barrymore INSTANCE;

    private final Configuration config;
    private final DSLContext context;
    private final PircBotX bot;
    private final ThemeManager themeManager;
    private final CommandListener commandListener;
    private final UserManager userManager;
    private final FBOFactory fboFactory;
    private final DBOFactory dboFactory;
    private final Timer timer = new Timer();

    private RegistrationChecker registrationChecker = new RCAllowAll();

    public static void main(String[] args) {
        INSTANCE = new Barrymore();
    }

    public Barrymore() {
        config = new Configuration(this);
        context = MySQL.getContext(config);
        bot = new PircBotX();
        themeManager = new ThemeManager(this);
        userManager = new UserManager(this);
        commandListener = new CommandListener(this);
        fboFactory = new FBOFactory();
        dboFactory = new DBOFactory();

        try {
            bot.setName(config.IRC_Nickname);
            bot.setLogin("Barrymore");
            bot.identify(config.IRC_NickServPass);
            bot.setVerbose(false);
            bot.setCapEnabled(true);
            bot.connect(config.IRC_DefServer);
            bot.joinChannel(config.IRC_DefChannel);
            bot.getListenerManager().addListener(commandListener);
        } catch (Exception e) {
            e.printStackTrace(); // TODO better exception handling. Maybe email me?
            System.exit(1);
        }

        userManager.reloadUsers();

        try {
            for(Class<? extends BTimerTask> clazz : ReflectionsUtilities.getSubtypesOf(BTimerTask.class, "me.kieranwallbanks.barrymore.task.defaults", ClassLoader.getSystemClassLoader())) {
                BTimerTask task = clazz.newInstance();
                timer.scheduleAtFixedRate(task, 60000, 60000);
            }
        } catch (Exception e) {
            e.printStackTrace(); // TODO better exception handling. Maybe email me?
            System.exit(1);
        }
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
     * Gets the {@link UserManager} currently in use
     *
     * @return sigh...
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * I'm not even going to bother
     *
     * @return double sigh
     */
    public RegistrationChecker getRegistrationChecker() {
        return registrationChecker;
    }

    /**
     * Sets the {@link RegistrationChecker}
     *
     * @param checker the checker
     */
    public void setRegistrationChecker(RegistrationChecker checker) {
        registrationChecker = checker;
    }

    /**
     * Gets the {@link FBOFactory} currently in use
     *
     * @return the fbo factory
     */
    public FBOFactory getFBOFactory() {
        return fboFactory;
    }

    /**
     * Gets the {@link DBOFactory} currently in use
     *
     * @return the factory
     */
    public DBOFactory getDBOFactory() {
        return dboFactory;
    }

}
