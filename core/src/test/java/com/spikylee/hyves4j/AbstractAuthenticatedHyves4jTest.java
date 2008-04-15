/*
 * Copyright 2008 Arthur Bogaart <spikylee at gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
