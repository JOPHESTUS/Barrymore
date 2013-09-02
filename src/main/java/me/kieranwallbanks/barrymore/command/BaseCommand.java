package me.kieranwallbanks.barrymore.command;

import me.kieranwallbanks.barrymore.Barrymore;
import org.pircbotx.User;

/**
 * A basic command interface
 */
public abstract class BaseCommand {
    private String name = "";
    private String description = "A command";
    private String confusion = "";
    private CommandCaller caller = new CommandCaller();

    public BaseCommand(Barrymore instance) {
    }

    public abstract void onCommand(User sender);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommandCaller getCaller() {
        return caller;
    }

    public void setCaller(CommandCaller caller) {
        this.caller = caller;
    }

    public String getConfusion() {
        return confusion;
    }

    public void setConfusion(String confusion) {
        this.confusion = confusion;
    }

    @Override
    public String toString() {
        return  getConfusion();
    }

}
