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
