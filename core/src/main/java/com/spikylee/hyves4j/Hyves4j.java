package com.spikylee.hyves4j;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import net.oauth.ConsumerProperties;
import net.oauth.OAuth;
import net.oauth.OAuthConsumer;
import net.oauth.OAuth.Parameter;
import net.oauth.client.HttpClientPool;
import net.oauth.client.OAuthClient;
import net.oauth.client.OAuthHttpClient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.RedirectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.client.config.H4jClientConfig;
import com.spikylee.hyves4j.interfaces.cities.H4jCities;
import com.spikylee.hyves4j.interfaces.console.H4jConsole;
import com.spikylee.hyves4j.interfaces.users.H4jUsers;
import com.spikylee.hyves4j.message.H4jMessage;
import com.spikylee.hyves4j.method.AuthAccessTokenMethod;
import com.spikylee.hyves4j.method.AuthRequestTokenMethod;
import com.spikylee.hyves4j.response.H4jResponse;
import com.spikylee.hyves4j.transport.H4jTransport;
import com.spikylee.hyves4j.transport.XMLTransport;

/**
 * Main entry point for the Hyves4j API. This class sets up transport/response
 * and returns interfaces to the Hyves API.
 * 
 * @author adcb
 */

public class Hyves4j {
    final Logger logger = LoggerFactory.getLogger(Hyves4j.class);

    public static final String HYVES_API = "http://data.hyves-api.nl/";
    public static final String HYVES_API_VERSION = "1.0";
    public static final String HYVES_RESPONSE_FORMAT = "xml";

    private H4jClient client;
    private H4jTransport transport;

    public Hyves4j() {
        this(createAnonymousClient());
    }

    public Hyves4j(H4jClient client) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating new Hyves4j:");
            logger.debug("Config is " + client.getConfig().toString());
            logger.debug("Consumer is " + client.getConsumer().toString());
            logger.debug("Accessor is " + client.getAccessor().toString());
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

    /* 
     * Create client helpers 
     */
    public static H4jClient createAnonymousClient() {
        return new H4jClient(new H4jClientConfig());
    }

    public static H4jClient createAnonymousClient(String consumerName) {
        H4jClientConfig config = new H4jClientConfig();
        config.setConsumerName(consumerName);
        return new H4jClient(config);
    }

    public static H4jClient createAnonymousClient(String consumerName, URL propertiesFileURL) {
        H4jClientConfig config = new H4jClientConfig(consumerName, propertiesFileURL);
        return new H4jClient(config);
    }

    /**
     * Create a new authenticated client. Since this method might throw an
     * RedirectException the user should always keep a reference to the
     * ClientConfig, since it contains the tokenSecret.
     */
    public static H4jClient createAuthenticatedClient(H4jClientConfig config) throws H4jException, RedirectException {
        H4jClient authClient = new H4jClient(config);
        H4jTransport transport = new XMLTransport(authClient);
        H4jAccessor accessor = authClient.getAccessor();
        if (accessor.getAccessToken() != null && accessor.getTokenSecret() != null) {
            if (accessor.getRequestToken() != null) {
                // request was made, user accepted, now get access token
                H4jMessage message = transport.createMessage(new AuthAccessTokenMethod());
                H4jResponse response = null;
                try {
                    response = transport.sendMessage(message);
                    accessor.setRequestToken(null);
                    accessor.setAccessToken(response.getParameter("oauth_token"));
                    accessor.setTokenSecret(response.getParameter("oauth_token_secret"));

                } catch (H4jException e) {
                    throw new RuntimeException(e);
                }
            }
            return authClient;
        } else {
            // reset accesstoken & secret
            authClient.getAccessor().setRequestToken(null);
            authClient.getAccessor().setAccessToken(null);
            authClient.getAccessor().setTokenSecret(null);

            // get a new request token!
            String methods = "";
            for (Iterator<String> it = config.getMethods().iterator(); it.hasNext();) {
                methods += it.next();
                if (it.hasNext())
                    methods += ",";
            }
            H4jMessage message = transport.createMessage(new AuthRequestTokenMethod().setParameters("methods", methods,
                    "expirationtype", config.getExpirationType()));
            H4jResponse response = transport.sendMessage(message);

            authClient.getAccessor().setRequestToken(response.getParameter("oauth_token"));
            authClient.getAccessor().setTokenSecret(response.getParameter("oauth_token_secret"));
            if (authClient.getAccessor().getRequestToken() == null) {
                H4jException ex = new H4jException("-1", "No requestToken was returned");
                throw ex;
            }
            if (authClient.getAccessor().getTokenSecret() == null) {
                H4jException ex = new H4jException("-1", "No tokenSecret was returned");
                throw ex;
            }

            String consumerName = (String) authClient.getConfig().getConsumerName();
            String authorizationURL = authClient.getAccessor().consumer.serviceProvider.userAuthorizationURL;
            String callbackURL = authClient.getAccessor().consumer.callbackURL;

            List<Parameter> params = config.getCallbackParameters();
            params.add(new Parameter("consumer", consumerName));

            String authorizeURL;
            try {
                authorizeURL = OAuth.addParameters(authorizationURL, "oauth_token", authClient.getAccessor()
                        .getRequestToken(), "oauth_callback", OAuth.addParameters(callbackURL, params));
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
            throw new RedirectException(authorizeURL);

        }
    }

    /**
     * Simple http-pool, simply creates a new instance every time. Copied this
     * from an example, some extra work for a more efficient solution would is
     * needed
     */
    public static final OAuthClient HTTP_CLIENT = new OAuthHttpClient(new HttpClientPool() {
        public HttpClient getHttpClient(URL server) {
            return new HttpClient();
        }
    });

    /**
     * Pool of consumer configurations
     */
    public static class Consumers {
        private static Properties consumerProperties = null;
        private static ConsumerProperties consumers = null;

        public static OAuthConsumer getConsumer(String name, URL propertiesFileURL) {

            OAuthConsumer consumer = null;
            try {
                synchronized (Hyves4j.Consumers.class) {
                    if (consumers == null) {
                        consumerProperties = ConsumerProperties.getProperties(propertiesFileURL);
                        consumers = new ConsumerProperties(consumerProperties);
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
