package me.kieranwallbanks.barrymore.command;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a class used to determine how a command is called
 */
public class CommandCaller {
    final Set<String> startsWith = new HashSet<String>(), contains = new HashSet<String>();

    /**
     * Adds a call so that if a users enters a string that starts with the
     * given string your command will be called.
     *
     * @param string the string
     *
     * @return {@code this} for chaining
     */
    public CommandCaller startsWith(String string) {
        startsWith.add(string);
        return this;
    }

    /**
     * Adds a call so that if a users enters a string that contains the
     * given string your command will be called.
     *
     * @param string the string
     *
     * @return {@code this} for chaining
     */
    public CommandCaller contains(String string) {
        contains.add(string);
        return this;
    }

}
