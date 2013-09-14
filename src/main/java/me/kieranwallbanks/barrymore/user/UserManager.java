package me.kieranwallbanks.barrymore.user;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import static me.kieranwallbanks.barrymore.mysql.Tables.USERS;

/**
 * Class that manager users.
 */
public class UserManager {
    private final Barrymore barrymore;
    private final SortedSet<User> users = new TreeSet<User>(new Comparator<User>() {

        @Override
        public int compare(User o1, User o2) {
            return o1.getIRCName().compareTo(o2.getIRCName());
        }

    });

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

}
