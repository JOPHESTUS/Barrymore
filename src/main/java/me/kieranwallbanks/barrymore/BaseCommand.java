package me.kieranwallbanks.barrymore;

import org.pircbotx.User;

/**
 * A basic command interface

 */
public abstract class BaseCommand {
    private String name = "", description = "A command", usage = "";

    public BaseCommand(Barrymore instance) {
    }

    public abstract void onCommand(User sender, String[] args);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
