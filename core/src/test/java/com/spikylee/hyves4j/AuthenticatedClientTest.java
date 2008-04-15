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
        assertNotNull(client);
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
