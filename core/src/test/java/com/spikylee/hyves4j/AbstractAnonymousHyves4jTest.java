package com.spikylee.hyves4j;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.client.config.H4jClientConfig;

public abstract class AbstractAnonymousHyves4jTest extends AbstractHyves4jTest {
    protected Hyves4j h4j = null;

    public void setUp() {
        super.setUp();
        H4jClientConfig config = new H4jClientConfig("hyves", consumerPropertiesURL);
        H4jClient client = new H4jClient(config);
        h4j = new Hyves4j(client);
    }
}
