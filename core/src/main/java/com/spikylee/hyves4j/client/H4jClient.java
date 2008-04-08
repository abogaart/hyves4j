package com.spikylee.hyves4j.client;

import net.oauth.OAuthConsumer;

import com.spikylee.hyves4j.H4jAccessor;
import com.spikylee.hyves4j.Hyves4j;
import com.spikylee.hyves4j.client.config.H4jClientConfig;

public class H4jClient {

	private H4jClientConfig config;
	private OAuthConsumer consumer;
	private H4jAccessor accessor;

	public H4jClient(H4jClientConfig config) {
		this.config = config;
		consumer = Hyves4j.Consumers.getConsumer(config.getConsumerName(),
				config.getPropertiesFileURL());
        accessor = new H4jAccessor(getConsumer(), getConfig());
        if(getConfig().getRequestToken() != null)
        	accessor.requestToken = getConfig().getRequestToken();
        if(getConfig().getAccessToken() != null)
        	accessor.accessToken = getConfig().getAccessToken();
        if(getConfig().getTokenSecret() != null)
        	accessor.tokenSecret = getConfig().getTokenSecret();
	}

	public OAuthConsumer getConsumer() {
		return consumer;
	}

	public H4jAccessor getAccessor() {
		return accessor;
	}

	public H4jClientConfig getConfig() {
		return config;
	}

}
