package com.spikylee.hyves4j;

import org.apache.commons.httpclient.RedirectException;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.client.config.H4jClientConfig;

public class AuthenticatedClientTest extends AbstractHyves4jTest {
    
    public void testNewAuthenticatedClient() {
        H4jClientConfig config = new H4jClientConfig("hyves", consumerPropertiesURL);
        config.setAccessToken(properties.getProperty("accesstoken"));
        config.setTokenSecret(properties.getProperty("tokensecret"));
        config.addMethod("users.get");
        
        H4jClient client = null;
        try {
            client = Hyves4j.createAuthenticatedClient(config);
        } catch (H4jException e) {
            e.printStackTrace();
        } catch (RedirectException e) {
            System.err.println("go to " + e.getMessage());
        }
        assertNotNull(client.getAccessor());
        assertNotNull(client.getAccessor().getAccessToken());
        assertNotNull(client.getAccessor().getTokenSecret());
        assertNull(client.getAccessor().getRequestToken());
        assertEquals(properties.getProperty("accesstoken"), client.getAccessor().getAccessToken());
        assertEquals(properties.getProperty("tokensecret"), client.getAccessor().getTokenSecret());
        
        Hyves4j h4j = new Hyves4j(client);
        assertNotNull(h4j);
    }

}
