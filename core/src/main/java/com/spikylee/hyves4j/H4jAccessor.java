package com.spikylee.hyves4j;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.spikylee.hyves4j.client.config.H4jClientConfig;

public class H4jAccessor extends OAuthAccessor {
    
	private static final long serialVersionUID = 1L;

	private H4jClientConfig config;
    
    public H4jAccessor(OAuthConsumer consumer, H4jClientConfig config) {
        super(consumer);
        this.config = config;
        if(config.getAccessToken()!=null) {
            accessToken = config.getAccessToken();
        }
        if(config.getRequestToken() != null) {
            requestToken = config.getRequestToken();
        }
        if(config.getTokenSecret() != null) {
            tokenSecret = config.getTokenSecret();
        }
    }
    
    public String getRequestToken() {
        return requestToken;
    }
    
    public void setRequestToken(String value) {
        requestToken = value;
        config.setRequestToken(value);
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String value) {
        accessToken = value;
        config.setAccessToken(value);
    }
    
    public String getTokenSecret() {
        return tokenSecret;
    }
    
    public void setTokenSecret(String value) {
        tokenSecret = value;
        config.setTokenSecret(value);
    }
    
    public String toString() {
        return new ToStringBuilder(this).
          append("requestToken", requestToken).
          append("accessToken", accessToken).
          append("tokenSecret", tokenSecret).
          toString();
      }

}
