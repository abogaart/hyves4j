package com.spikylee.hyves4j;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.client.config.H4jClientConfig;

public abstract class AbstractAuthenticatedHyves4jTest extends AbstractHyves4jTest {
    protected Hyves4j h4j = null;

    public void setUp() {
        super.setUp();
        H4jClientConfig config = new H4jClientConfig("hyves", consumerPropertiesURL);
        config.setAccessToken(properties.getProperty("accesstoken"));
        config.setTokenSecret(properties.getProperty("tokensecret"));
        config.addMethod("users.get");

        H4jClient client = null;
        try {
            client = Hyves4j.createAuthenticatedClient(config);
        } catch (Exception e) {
            fail("Test setup failed, unable to instatiate authenticated client");
        }
        h4j = new Hyves4j(client);
    }
}
