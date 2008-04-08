package com.spikylee.hyves4j.client.config;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.oauth.OAuth;
import net.oauth.OAuth.Parameter;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.spikylee.hyves4j.Hyves4j;

public class H4jClientConfig implements Serializable {
    private static final long serialVersionUID = 2738064941695337596L;
    
    private URL propertiesFileURL;
    private String consumerName;
    
    private String requestToken;
    private String accessToken; 
    private String tokenSecret;
    
    private String expirationType = "user";
    
    private boolean fancyLayout = false;
    
    private List<String> methods = new ArrayList<String>();
    private List<Parameter> callbackParameters = new ArrayList<Parameter>();
    
    public H4jClientConfig() {
        String propertiesFileSource = "/consumer.properties";
        propertiesFileURL = H4jClientConfig.class.getResource(propertiesFileSource);
        consumerName = "hyves";
    }
    
    public H4jClientConfig(String consumerName, URL propertiesFileURL) {
        this.consumerName = consumerName;
        this.propertiesFileURL = propertiesFileURL;
    }
    
    public H4jClientConfig(String consumerName, String propertiesFileSource){
        propertiesFileURL = H4jClientConfig.class.getClassLoader().getResource(propertiesFileSource);
    }

    public URL getPropertiesFileURL() {
        return propertiesFileURL;
    }

    public void setPropertiesFileURL(URL propertiesFileURL) {
        this.propertiesFileURL = propertiesFileURL;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
    
    public void addMethod(String method) {
        if(!methods.contains(method)) {
            methods.add(method);
        }
    }
    
    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public String getExpirationType() {
        return expirationType;
    }

    public void setExpirationType(String expirationType) {
        this.expirationType = expirationType;
    }
    
    @Override
    public String toString() {
    	return new ToStringBuilder(this).
    	append("consumerName", consumerName).
        append("requestToken", requestToken).
        append("accessToken", accessToken).
        append("tokenSecret", tokenSecret).
        append("expirationType", expirationType).
        append("methods size", methods).
        append("propertiesFileURL", propertiesFileURL).
    	toString();
    }

    public boolean isFancyLayout() {
        return fancyLayout;
    }

    public void setFancyLayout(boolean fancyLayout) {
        this.fancyLayout = fancyLayout;
    }

    public List<Parameter> getCallbackParameters() {
        return callbackParameters;
    }

    public void setCallbackParameters(List<Parameter> callbackParameters) {
        this.callbackParameters = callbackParameters;
    }

    public void setCallbackParameters(String... params) {
        callbackParameters = OAuth.newList(params);
    }

    public void addCallbackParameter(String key, String value) {
        Parameter param = new Parameter(key, value);
        if(!callbackParameters.contains(param)) {
            callbackParameters.add(param);
        }
    }
    
}