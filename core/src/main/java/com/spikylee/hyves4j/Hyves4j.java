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
import java.util.Properties;

import net.oauth.ConsumerProperties;
import net.oauth.OAuthConsumer;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient4.HttpClient4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.interfaces.auth.H4jAuth;
import com.spikylee.hyves4j.interfaces.cities.H4jCities;
import com.spikylee.hyves4j.interfaces.console.H4jConsole;
import com.spikylee.hyves4j.interfaces.countries.H4jCountries;
import com.spikylee.hyves4j.interfaces.users.H4jUsers;
import com.spikylee.hyves4j.transport.H4jTransport;
import com.spikylee.hyves4j.transport.XMLTransport;

/**
 * Main entry point for the Hyves4j API. This class sets up transport/response and returns interfaces to the Hyves API.
 * 
 * @author Arthur Bogaart
 */

public class Hyves4j {
    final static Logger logger = LoggerFactory.getLogger(Hyves4j.class);

    public static final String HYVES_API = "http://data.hyves-api.nl/";
    public static final String HYVES_API_VERSION = "1.0";
    public static final String HYVES_RESPONSE_FORMAT = "xml";

    public static final boolean DEBUG_RESPONSE_XML = false;

    private H4jClient client;
    private H4jTransport transport;

    public Hyves4j() throws IOException {
        this(H4jClientFactory.createAnonymousClient());
    }

    public Hyves4j(H4jClient client) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating new Hyves4j:\n - Config is " + client.getConfig().toString() + "\n - Consumer is "
                    + client.getConsumer().toString() + "\n - Accessor is " + client.getAccessor().toString());
        }
        this.client = client;
        transport = new XMLTransport(client);
    }

    /*
     * Hyves-api interface getters
     */

    public H4jUsers getUsers() {
        return new H4jUsers(client, transport);
    }

    public H4jConsole getConsole() {
        return new H4jConsole(client, transport);
    }

    public H4jCities getCities() {
        return new H4jCities(client, transport);
    }

    public H4jCountries getCountries() {
        return new H4jCountries(client, transport);
    }

    public H4jAuth getAuth() {
        return new H4jAuth(client, transport);
    }

    /**
     * Simple http-client pool.
     */
    public static final OAuthClient HTTP_CLIENT = new OAuthClient(new HttpClient4());

    /**
     * Pool of consumer configurations
     */
    public static class Consumers {
        private static ConsumerProperties consumers = null;

        public static OAuthConsumer getConsumer(String name, Properties properties) {

            OAuthConsumer consumer = null;
            try {
                synchronized (Hyves4j.Consumers.class) {
                    if (consumers == null) {
                        consumers = new ConsumerProperties(properties);
                    }
                }
                consumer = consumers.getConsumer(name);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            return consumer;
        }
    }
}
