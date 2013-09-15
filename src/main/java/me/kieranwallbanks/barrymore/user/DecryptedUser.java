package me.kieranwallbanks.barrymore.user;

import me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord;
import me.kieranwallbanks.barrymore.util.EncryptionUtilities;

/**
 * Represents a decrypted user
 */
public class DecryptedUser extends User {
    private final String fbo, fboPass, dboApi;

    protected DecryptedUser(UsersRecord record, String masterPassword) throws Exception {
        super(record);

        fbo = EncryptionUtilities.decrypt(getFBOUsername(), getIRCName().getBytes(), masterPassword);
        fboPass = EncryptionUtilities.decrypt(getFBOPassword(), getIRCName().getBytes(), masterPassword);
        dboApi = EncryptionUtilities.decrypt(getDBOAPIKey(), getIRCName().getBytes(), masterPassword);
    }

    public String getDecryptedFboUsername() {
        return fbo;
    }

    public String getDecryptedFboPassword() {
        return fboPass;
    }

    public String getDecryptedDboApiKey() {
        return dboApi;
    }

}
