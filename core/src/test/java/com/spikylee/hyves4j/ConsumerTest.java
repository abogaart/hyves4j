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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.oauth.ConsumerProperties;
import net.oauth.OAuthConsumer;

public class ConsumerTest extends AbstractHyves4jTest {

    private Properties consumerProperties;

    @Override
    public void setUp() {
        super.setUp();
        InputStream in = null;
        try {
            in = ConsumerTest.class.getResourceAsStream(consumerPropertiesLocation);
            consumerProperties = new Properties();
            consumerProperties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void testNewConsumer() {
        OAuthConsumer consumer;
        Properties properties;
        try {
            properties = ConsumerProperties.getProperties(consumerPropertiesURL);
            consumer = Hyves4j.Consumers.getConsumer("hyves", properties);

            assertNotNull(consumer);
            assertEquals(consumer.callbackURL, consumerProperties.get("hyves.callbackURL"));
            assertEquals(consumer.consumerKey, consumerProperties.get("hyves.consumerKey"));
            assertEquals(consumer.consumerSecret, consumerProperties.get("hyves.consumerSecret"));

            assertEquals(consumer.serviceProvider.accessTokenURL, consumerProperties
                    .get("hyves.serviceProvider.accessTokenURL"));
            assertEquals(consumer.serviceProvider.requestTokenURL, consumerProperties
                    .get("hyves.serviceProvider.requestTokenURL"));
            assertEquals(consumer.serviceProvider.userAuthorizationURL, consumerProperties
                    .get("hyves.serviceProvider.userAuthorizationURL"));

            consumer = Hyves4j.Consumers.getConsumer("hyves2", properties);

            assertNotNull(consumer);
            assertEquals(consumer.callbackURL, consumerProperties.get("hyves2.callbackURL"));
            assertEquals(consumer.consumerKey, consumerProperties.get("hyves2.consumerKey"));
            assertEquals(consumer.consumerSecret, consumerProperties.get("hyves2.consumerSecret"));

            assertEquals(consumer.serviceProvider.accessTokenURL, consumerProperties
                    .get("hyves2.serviceProvider.accessTokenURL"));
            assertEquals(consumer.serviceProvider.requestTokenURL, consumerProperties
                    .get("hyves2.serviceProvider.requestTokenURL"));
            assertEquals(consumer.serviceProvider.userAuthorizationURL, consumerProperties
                    .get("hyves2.serviceProvider.userAuthorizationURL"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
