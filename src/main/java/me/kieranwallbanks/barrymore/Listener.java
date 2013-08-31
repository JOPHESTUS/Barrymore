package me.kieranwallbanks.barrymore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static me.kieranwallbanks.barrymore.mysql.Tables.*;
import me.kieranwallbanks.barrymore.util.BukkitDevUtilities;
import me.kieranwallbanks.barrymore.util.EncryptionUtilities;
import me.kieranwallbanks.barrymore.util.LangUtilities;
import me.kieranwallbanks.barrymore.util.ReflectionsUtilities;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * Default listeners
 */
public class Listener extends ListenerAdapter {
    private Barrymore barrymore;
    private SortedMap<String, BaseCommand> commands = new TreeMap<String, BaseCommand>();
    private Map<String, Integer> userRegistration = new HashMap<String, Integer>();
    private Map<String, UserRegistrationInformation> userRegistrationInformation = new HashMap<String, UserRegistrationInformation>();

    /**
     * Constructs a new instance of {@link Listener}
     *
     * @param instance an instance of {@link Barrymore}
     */
    public Listener(Barrymore instance) {
        barrymore = instance;

        try {
            for(Class<? extends BaseCommand> clazz : ReflectionsUtilities.getSubtypesOf(BaseCommand.class, "me.kieranwallbanks.barrymore.command", ClassLoader.getSystemClassLoader())) {
                BaseCommand command = clazz.getConstructor(Barrymore.class).newInstance(barrymore);
                commands.put(command.getName(), command);
            }
        } catch (Exception e) {
            e.printStackTrace(); // TODO better exception handling. Maybe email me?
            System.exit(1);
        }
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        if(!barrymore.isUserRegistered(event.getUser())) {
            if(userRegistration.containsKey(event.getUser().getNick())) {
                handleRegistrationAttempt(event);
            } else if(event.getMessage().equalsIgnoreCase("register")) {  // User registration
                if(!barrymore.isOpenForRegistration()) {
                    event.getUser().sendMessage("One is not accepting new user registrations at this point in time. Please try again later.");
                } else if(userRegistration.containsKey(event.getUser().getNick())) {
                    handleRegistrationAttempt(event);
                } else {
                    event.getUser().sendMessage("Wonderful! We shall start registering you at once.");
                    event.getUser().sendMessage("One will only be able to talk to you when you have the nickname " + event.getUser().getNick() + ", is this okay?");
                    userRegistration.put(event.getUser().getNick(), 0);
                }
            } else {
                event.getUser().sendMessage("Dreadfully sorry, one is only allowed to cooperate with registered users. Type 'register' to start the registration process.");
            }

            return;
        }

        String[] arr = event.getMessage().split(" ");

        if(commands.get(arr[0]) != null) {
            commands.get(arr[0]).onCommand(event.getUser(), Arrays.copyOfRange(arr, 1, arr.length));
        } else {
            event.getUser().sendMessage("Command not found! I'll do theming of this soon.");
        }
    }

    public void handleRegistrationAttempt(PrivateMessageEvent event) throws Exception {
        switch(userRegistration.get(event.getUser().getNick()) == null ? 0 : userRegistration.get(event.getUser().getNick())) {
            case 0: // Checking nickname
                String message0 = event.getMessage().toLowerCase();
                if(LangUtilities.isAffirmative(message0)) {
                    event.getUser().sendMessage("That is great. Next up, what is your username for bukkit.org?");
                    userRegistration.put(event.getUser().getNick(), 1);
                } else if(LangUtilities.isDissenting(message0)) {
                    event.getUser().sendMessage("That is a shame. Could you change your nickname to the one you desire me to talk to, then come back to me? Cheerio!");
                    userRegistration.remove(event.getUser().getNick());
                } else {
                    event.getUser().sendMessage("Sorry, one didn't quite understand that. Could you repeat it?");
                }
                break;
            case 1:
                event.getUser().sendMessage(event.getMessage() + ". Has one got that correct?");
                userRegistration.put(event.getUser().getNick(), 2);
                userRegistrationInformation.put(event.getUser().getNick(), new UserRegistrationInformation());
                userRegistrationInformation.get(event.getUser().getNick()).FBO = event.getMessage();
                break;
            case 2:
                String message2 = event.getMessage().toLowerCase();
                if(LangUtilities.isAffirmative(message2)) {
                    event.getUser().sendMessage("One would just like to notify you that all of the following information you give me will be encrypted with a master password and stored securely.");
                    event.getUser().sendMessage("You may give me your desired master password at the end of this session.");
                    event.getUser().sendMessage("Jolly good! Onwards and upwards... One now requires your password for bukkit.org.");
                    userRegistration.put(event.getUser().getNick(), 3);
                } else if(LangUtilities.isDissenting(message2)) {
                    event.getUser().sendMessage("That is a shame. What is your username for bukkit.org?");
                    userRegistration.put(event.getUser().getNick(), 1);
                } else {
                    event.getUser().sendMessage("Sorry, one didn't quite understand that. Could you repeat it?");
                }
                break;
            case 3:
                userRegistrationInformation.get(event.getUser().getNick()).FBO_PASS = event.getMessage();
                event.getUser().sendMessage("Spiffing! We are half way there. What is your Bukkit Dev API key? You can find this information at http://dev.bukkit.org/home/api-key");
                userRegistration.put(event.getUser().getNick(), 4);
                break;
            case 4:
                userRegistrationInformation.get(event.getUser().getNick()).DBO_API = event.getMessage();
                event.getUser().sendMessage(event.getMessage() + ". Was that correct?");
                userRegistration.put(event.getUser().getNick(), 5);
                break;
            case 5:
                String message5 = event.getMessage().toLowerCase();
                if(LangUtilities.isAffirmative(message5)) {
                    event.getUser().sendMessage("Wonderful. So you are " + BukkitDevUtilities.getUsernameFromAPIKey(userRegistrationInformation.get(event.getUser().getNick()).DBO_API) + ". Is that correct?");
                    userRegistration.put(event.getUser().getNick(), 6);
                } else if(LangUtilities.isDissenting(message5)) {
                    event.getUser().sendMessage("That is a shame. What is your Bukkit Dev API key?");
                    userRegistration.put(event.getUser().getNick(), 5);
                } else {
                    event.getUser().sendMessage("Sorry, one didn't quite understand that. Could you repeat it?");
                }
                break;
            case 6:
                String message6 = event.getMessage().toLowerCase();
                if(LangUtilities.isAffirmative(message6)) {
                    event.getUser().sendMessage("Excellent! All one requires now is a master password that one will use to encrypt all other passwords and your API key.");
                    event.getUser().sendMessage("To be more precise, your master password will be stored as a SHA-512 hash.");
                    event.getUser().sendMessage("One will encrypt your passwords using PBE with MD5 and DES with your master password hash as the salt.");
                    event.getUser().sendMessage("Your master password will not be stored anywhere but in your brain.");
                    event.getUser().sendMessage("If you require more information, please do not hesitate to contact Kezz101.");
                    event.getUser().sendMessage("What would you like your master password to be? I will only ask you once, so please validate that it is correct.");
                    userRegistration.put(event.getUser().getNick(), 7);
                } else if(LangUtilities.isDissenting(message6)) {
                    event.getUser().sendMessage("Drats! One must have got something wrong. Awfully sorry. What is your Bukkit Dev API key?");
                    userRegistration.put(event.getUser().getNick(), 4);
                } else {
                    event.getUser().sendMessage("Sorry, one didn't quite understand that. Could you repeat it?");
                }
                break;
            case 7:
                userRegistration.put(event.getUser().getNick(), 8);
                userRegistrationInformation.get(event.getUser().getNick()).MASTER_PASS = event.getMessage();
                event.getUser().sendMessage("You have now completed your registration! I will start storing the required information.");
                event.getUser().sendMessage("Please do not talk to me during this time. One needs to concentrate!");

                UserRegistrationInformation info = userRegistrationInformation.get(event.getUser().getNick());
                String masterPass = EncryptionUtilities.sha512(info.MASTER_PASS.getBytes("UTF-8"));
                String fboPass = EncryptionUtilities.encrypt(info.FBO_PASS.getBytes("UTF-8"), event.getUser().getNick().getBytes("UTF-8"), masterPass);
                String dboAPI = EncryptionUtilities.encrypt(info.DBO_API.getBytes("UTF-8"), event.getUser().getNick().getBytes("UTF-8"), masterPass);

                barrymore.getMySQLContext().insertInto(USERS, USERS.IRC, USERS.FBO, USERS.FBO_PASS, USERS.DBO_API, USERS.THEME).values(event.getUser().getNick(), info.FBO, fboPass, dboAPI, "british butler");
                event.getUser().sendMessage("You are now registered. Ask for help if you would like an introduction to my features.");
                userRegistration.remove(event.getUser().getNick());
                userRegistrationInformation.remove(event.getUser().getNick());
                break;
            case 8:
                event.getUser().sendMessage("One moment please, one is triflingly busy working out SHA hashes from my head.");
                break;
        }
    }

    private class UserRegistrationInformation {
        public String FBO, FBO_PASS, DBO_API, MASTER_PASS;
    }

}
