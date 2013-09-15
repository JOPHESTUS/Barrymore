package me.kieranwallbanks.barrymore.fbo;

import org.apache.commons.httpclient.HttpClient;
import org.apache.xmlrpc.XmlRpcException;

import java.util.Map;

/**
 * A user of FBO
 */
public class FBOUser {
    private final FBOFactory factory;
    private final HttpClient httpClient;

    public FBOUser(FBOFactory fboFactory, HttpClient client) {
        factory = fboFactory;
        httpClient = client;
    }

    public int getUnreadMessages() throws XmlRpcException {
        factory.factory.setHttpClient(httpClient);

        Map<String, Integer> map = (Map<String, Integer>) factory.client.execute("get_inbox_stat", new Object[0]);

        return map.get("inbox_unread_count");
    }

}
