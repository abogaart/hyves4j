package com.spikylee.hyves4j;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;

//TODO: write AuthAccessor test
public class AccessorTest extends AbstractHyves4jTest {
	
	private OAuthConsumer consumer;
	
	public void setUp() {
		super.setUp();
		consumer = Hyves4j.Consumers.getConsumer("hyves", consumerPropertiesURL);
	}
	
	public void testPublicAccessor() {
	    
		OAuthAccessor accessor = Hyves4j.createAnonymousClient("hyves", consumerPropertiesURL).getAccessor();
		assertNotNull(accessor);
		assertEquals(accessor.accessToken, null);
		assertEquals(accessor.requestToken, null);
		assertEquals(accessor.tokenSecret, null);
		
	}
	
}
