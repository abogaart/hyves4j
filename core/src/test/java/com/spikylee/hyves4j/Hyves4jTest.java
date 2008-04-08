package com.spikylee.hyves4j;

import com.spikylee.hyves4j.client.H4jClient;



public class Hyves4jTest extends AbstractHyves4jTest {
	
	public void testPublicHyves4j () {
		Hyves4j h4j = null;
		H4jClient client = Hyves4j.createAnonymousClient("hyves", consumerPropertiesURL);
		h4j = new Hyves4j(client);
		assertNotNull(h4j);
	}
	
}
