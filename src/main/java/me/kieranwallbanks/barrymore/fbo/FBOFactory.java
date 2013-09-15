package me.kieranwallbanks.barrymore.fbo;

import me.kieranwallbanks.barrymore.fbo.exception.IncorrectLoginDetailsException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Awesome stuff with FBO. <br />
 * STUFF: API LEVEL IS 3.
 */
public class FBOFactory {
    protected final XmlRpcClient client;
    protected final XmlRpcCommonsTransportFactory factory;
    protected final HttpClient httpClient;

    private final Map<String, FBOUser> userMap = new HashMap<String, FBOUser>();

    /**
     * Constructs a new {@link FBOFactory}
     */
    public FBOFactory() {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        try {
            config.setServerURL(new URL("http://forums.bukkit.org/mobiquo/mobiquo.php"));
        } catch (MalformedURLException e) {/* Won't happen */}

        client = new XmlRpcClient();
        factory = new XmlRpcCommonsTransportFactory(client);
        httpClient = new HttpClient();

        factory.setHttpClient(httpClient);

        client.setConfig(config);
        client.setTransportFactory(factory);
    }

    /**
     * Gets a {@link FBOUser}, logging them in if necessary
     *
     * @param username their username
     * @param password their password
     *
     * @return the user
     *
     * @throws IncorrectLoginDetailsException if the login details were incorrect
     * @throws XmlRpcException if an error occurred with the xml stuff
     */
    public FBOUser getUser(String username, String password) throws IncorrectLoginDetailsException, XmlRpcException {
        if(userMap.containsKey(username)) {
            return userMap.get(username);
        }

        httpClient.getState().clear();

        HashMap<String, Object> hashMap = (HashMap<String, Object>) client.execute("login", new Object[]{username.getBytes(), password.getBytes()});

        if(hashMap.get("result").equals(false)) {
            throw new IncorrectLoginDetailsException();
        }

        userMap.put(username, new FBOUser(this, httpClient));

        return userMap.get(username);
    }

}
