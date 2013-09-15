package me.kieranwallbanks.barrymore.task.defaults;

import me.kieranwallbanks.barrymore.Barrymore;
import me.kieranwallbanks.barrymore.fbo.FBOUser;
import me.kieranwallbanks.barrymore.fbo.exception.IncorrectLoginDetailsException;
import me.kieranwallbanks.barrymore.task.BTimerTask;
import me.kieranwallbanks.barrymore.theme.Theme;
import me.kieranwallbanks.barrymore.user.DecryptedUser;
import me.kieranwallbanks.barrymore.user.User;
import org.apache.xmlrpc.XmlRpcException;

public class NotificationChecker extends BTimerTask {

    public NotificationChecker(Barrymore instance) {
        super(instance);
    }

    @Override
    public void run() {
        for(User user : barrymore.getUserManager().getUsers()) {
            DecryptedUser dUser = barrymore.getUserManager().decryptUser(user);
            org.pircbotx.User pUser = barrymore.getBot().getUser(user.getIRCName());
            Theme theme = barrymore.getThemeManager().getUsersTheme(pUser);

            if(dUser == null) {
                return; // We just ignore this because decryptUser sorts out the rest
            }

            try {
                FBOUser fUser = barrymore.getFBOFactory().getUser(dUser.getDecryptedFboUsername(), dUser.getDecryptedFboPassword());
                int i = fUser.getUnreadMessages();

                if(i > 0) {
                    pUser.sendMessage(String.format(theme.NOTIFICATION_UNREAD_FBO_MAIL(), String.valueOf(i)));
                }
            } catch (IncorrectLoginDetailsException e) {
                // Right. Urm. Panic?!
            } catch (XmlRpcException e) {
                // This we can deal with. TODO email me?
            }


            int i = barrymore.getDBOFactory().getUnreadMessages(dUser.getDecryptedDboApiKey());
            if(i > 0) {
                pUser.sendMessage(String.format(theme.NOTIFICATION_UNREAD_DBO_MAIL(), String.valueOf(i)));
            }
        }
    }

}
