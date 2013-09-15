package me.kieranwallbanks.barrymore.user;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.command.InstantCommandListener;
import me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord;
import me.kieranwallbanks.barrymore.theme.Theme;

import java.util.*;

import static me.kieranwallbanks.barrymore.mysql.Tables.USERS;

/**
 * Class that manager users.
 */
public class UserManager {
    private final Barrymore barrymore;
    private final Comparator<User> comparator = new UserComparator();
    private final SortedSet<User> users = new TreeSet<User>(comparator);
    private final SortedMap<String, DecryptedUser> decryptedUsers = new TreeMap<String, DecryptedUser>();
    private final Set<String> awaitingConfirmation = new HashSet<String>();

    public UserManager(Barrymore instance) {
        barrymore = instance;
    }

    /**
     * Reloads users.
     */
    public void reloadUsers() {
        users.clear();

        for(UsersRecord user : barrymore.getMySQLContext().select().from(USERS).fetchInto(UsersRecord.class)) {
            users.add(new User(user));
        }
    }

    /**
     * Gets all users
     *
     * @return all users
     */
    public Collection<User> getUsers() {
        return users;
    }

    /**
     * Gets a user by their irc name
     *
     * @param ircName the irc name
     *
     * @return the user or null if not found
     */
    public User getUser(String ircName) {
        for(User user : users) {
            if(user.getIRCName().equals(ircName)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Gets a {@link DecryptedUser} from a normal {@link User}, decrypting if necessary.
     *
     * @param user the user or {@code null} if we are awaiting a master password.
     *             Do not send the user a message stating that they need to decrypt their details.
     *             This function manages all of that.
     *
     * @return
     */
    public DecryptedUser decryptUser(final User user) {
        if(decryptedUsers.containsKey(user.getIRCName())) {
            return decryptedUsers.get(user.getIRCName());
        }

        final Theme theme = barrymore.getThemeManager().getUsersTheme(user);
        final org.pircbotx.User pUser = barrymore.getBot().getUser(user.getIRCName());

        pUser.sendMessage(theme.AUTH_NEEDED());

        if(!awaitingConfirmation.contains(user.getIRCName())) {
            barrymore.getCommandListener().registerInstantCommandListener(pUser, new AuthenticationInstantListener(user, pUser));
        }

        return null;
    }

    private final class UserComparator implements Comparator<User> {

        @Override
        public int compare(User o1, User o2) {
            return o1.getIRCName().compareTo(o2.getIRCName());
        }

    }

    private final class AuthenticationInstantListener implements InstantCommandListener {
        private final User kUser;
        private final org.pircbotx.User pUser;
        private final Theme theme;

        public AuthenticationInstantListener(User kUser, final org.pircbotx.User pUser) {
            this.kUser = kUser;
            this.pUser = pUser;
            theme = barrymore.getThemeManager().getUsersTheme(pUser);
        }

        @Override
        public void onPrivateMessage(String message) {
            pUser.sendMessage(theme.AUTH_ATTEMPTING());
            try {
                DecryptedUser decryptedUser = new DecryptedUser(kUser.record, message);
                barrymore.getFBOFactory().getUser(decryptedUser.getDecryptedFboUsername(), decryptedUser.getDecryptedFboPassword());
                awaitingConfirmation.remove(decryptedUser.getIRCName());
            } catch (Exception e){
                pUser.sendMessage(theme.AUTH_FAILED());
                barrymore.getCommandListener().registerInstantCommandListener(pUser, new AuthenticationInstantListener(kUser, pUser));
            }
        }

    }

}
