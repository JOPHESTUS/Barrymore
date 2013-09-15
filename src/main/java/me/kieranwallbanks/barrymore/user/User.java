package me.kieranwallbanks.barrymore.user;

import me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord;

/**
 * Represents a user
 */
public class User {
    protected final UsersRecord record;

    protected User(UsersRecord record) {
        this.record = record;
    }

    public String getIRCName() {
        return record.getIrc();
    }

    public String getTheme() {
        return record.getTheme();
    }

    public void setTheme(String theme) {
        record.setTheme(theme);
    }

    public String getFBOUsername() {
        return record.getFbo();
    }

    public String getFBOPassword() {
        return record.getFboPass();
    }

    public String getDBOAPIKey() {
        return record.getDboApi();
    }

}
