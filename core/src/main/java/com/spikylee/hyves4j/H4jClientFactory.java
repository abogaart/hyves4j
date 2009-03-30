package com.spikylee.hyves4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import net.oauth.OAuthConsumer;

import org.apache.commons.httpclient.RedirectException;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.client.config.H4jClientConfig;
import com.spikylee.hyves4j.interfaces.auth.H4jAuth;
import com.spikylee.hyves4j.transport.H4jTransport;
import com.spikylee.hyves4j.transport.XMLTransport;

public class H4jClientFactory {

    private static final String DEFAULT_CONSUMER = "hvyes";
    private static final String DEFAULT_PROPERTIES = "/consumer.properties";

    /*
     * Create default anonymous client. - consumer name will be "hyves" -
     */
    public static H4jClient createAnonymousClient() throws IOException {
        return createAnonymousClient(DEFAULT_CONSUMER);
    }

    public static H4jClient createAnonymousClient(String consumerName) throws IOException {
        return createClient(consumerName, loadPropertiesFromURL(DEFAULT_PROPERTIES));
    }

    public static H4jClient createAnonymousClient(String consumerName, URL propertiesFileURL) throws IOException {
        return createClient(consumerName, getProperties(propertiesFileURL));
    }

    private static H4jClient createClient(String consumerName, Properties properties) {
        H4jClientConfig config = new H4jClientConfig(consumerName);
        OAuthConsumer consumer = Hyves4j.Consumers.getConsumer(config.getConsumerName(), properties);
        return new H4jClient(config, consumer);
    }

    /**
     * Create a new authenticated client. Since this method might throw a RedirectException the user should keep a
     * reference to the ClientConfig, since it contains the tokenSecret.
     * 
     * @throws IOException
     */
    public static H4jClient createAuthenticatedClient(H4jClientConfig config, URL propertiesFileURL)
            throws H4jException, IOException, RedirectException {
        return createAuthenticatedClient(config, getProperties(propertiesFileURL));
    }

    public static H4jClient createAuthenticatedClient(H4jClientConfig config) throws H4jException, IOException,
            RedirectException {
        return createAuthenticatedClient(config, loadPropertiesFromURL(DEFAULT_PROPERTIES));
    }

    public static H4jClient createAuthenticatedClient(H4jClientConfig config, InputStream properties)
            throws RedirectException, H4jException, IOException {
        return createAuthenticatedClient(config, getProperties(properties));
    }

    public static H4jClient createAuthenticatedClient(H4jClientConfig config, Properties properties)
            throws H4jException, RedirectException {
        OAuthConsumer consumer = Hyves4j.Consumers.getConsumer(config.getConsumerName(), properties);
        H4jClient authClient = new H4jClient(config, consumer);
        H4jTransport transport = new XMLTransport(authClient);
        H4jAuth auth = new H4jAuth(authClient, transport);
        auth.getAccessToken();
        return authClient;
    }

    private static Properties loadPropertiesFromURL(String url) throws IOException {
        URL propertiesFileURL = H4jClientFactory.class.getResource(url);
        return getProperties(propertiesFileURL);
    }

    private static Properties getProperties(URL source) throws IOException {
        return getProperties(source.openStream());
    }

    private static Properties getProperties(InputStream stream) throws IOException {
        try {
            Properties p = new Properties();
            p.load(stream);
            return p;
        } finally {
            stream.close();
        }
    }

}
