package com.spikylee.hyves4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.oauth.OAuthConsumer;

public class ConsumerTest extends AbstractHyves4jTest {
	
	private Properties consumerProperties;
	
	public void setUp() {
		super.setUp();
	    InputStream in = null;
	    try {
	        in = getClass().getResourceAsStream(consumerPropertiesLocation);
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
		consumer = Hyves4j.Consumers.getConsumer("hyves", consumerPropertiesURL);
		
		assertNotNull(consumer);
		assertEquals(consumer.callbackURL, consumerProperties.get("hyves.callbackURL"));
		assertEquals(consumer.consumerKey, consumerProperties.get("hyves.consumerKey"));
		assertEquals(consumer.consumerSecret, consumerProperties.get("hyves.consumerSecret"));
		
		assertEquals(consumer.serviceProvider.accessTokenURL, consumerProperties.get("hyves.serviceProvider.accessTokenURL"));
		assertEquals(consumer.serviceProvider.requestTokenURL, consumerProperties.get("hyves.serviceProvider.requestTokenURL"));
		assertEquals(consumer.serviceProvider.userAuthorizationURL, consumerProperties.get("hyves.serviceProvider.userAuthorizationURL"));

		consumer = Hyves4j.Consumers.getConsumer("hyves2", consumerPropertiesURL);
		
		assertNotNull(consumer);
		assertEquals(consumer.callbackURL, consumerProperties.get("hyves2.callbackURL"));
		assertEquals(consumer.consumerKey, consumerProperties.get("hyves2.consumerKey"));
		assertEquals(consumer.consumerSecret, consumerProperties.get("hyves2.consumerSecret"));
		
		assertEquals(consumer.serviceProvider.accessTokenURL, consumerProperties.get("hyves2.serviceProvider.accessTokenURL"));
		assertEquals(consumer.serviceProvider.requestTokenURL, consumerProperties.get("hyves2.serviceProvider.requestTokenURL"));
		assertEquals(consumer.serviceProvider.userAuthorizationURL, consumerProperties.get("hyves2.serviceProvider.userAuthorizationURL"));

	}
}
