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

package com.spikylee.hyves4j.client.config;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.oauth.OAuth;
import net.oauth.OAuth.Parameter;

import org.apache.commons.lang.builder.ToStringBuilder;

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
    // Serializable workaround for non-serializable class OAuth.Parameter
    private Map<String, String> callbackParameters = new HashMap<String, String>();

    public H4jClientConfig() {
        String propertiesFileSource = "/consumer.properties";
        propertiesFileURL = H4jClientConfig.class
                .getResource(propertiesFileSource);
        consumerName = "hyves";
    }

    public H4jClientConfig(String consumerName, URL propertiesFileURL) {
        this.consumerName = consumerName;
        this.propertiesFileURL = propertiesFileURL;
    }

    public H4jClientConfig(String consumerName, String propertiesFileSource) {
        propertiesFileURL = H4jClientConfig.class.getClassLoader().getResource(
                propertiesFileSource);
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
        if (!methods.contains(method)) {
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
        return new ToStringBuilder(this).append("consumerName", consumerName)
                .append("requestToken", requestToken).append("accessToken",
                        accessToken).append("tokenSecret", tokenSecret).append(
                        "expirationType", expirationType).append(
                        "methods size", methods).append("propertiesFileURL",
                        propertiesFileURL).toString();
    }

    public boolean isFancyLayout() {
        return fancyLayout;
    }

    public void setFancyLayout(boolean fancyLayout) {
        this.fancyLayout = fancyLayout;
    }

    public List<Parameter> getCallbackParameters() {
        List<Parameter> list = new ArrayList<Parameter>(callbackParameters
                .size());
        for (Entry<String, String> entry : callbackParameters.entrySet()) {
            list.add(new Parameter(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    public void setCallbackParameters(List<Parameter> parameters) {
        parameters.clear();
        for (Parameter p : parameters) {
            callbackParameters.put(p.getKey(), p.getValue());
        }
    }

    public void setCallbackParameters(String... params) {
        setCallbackParameters(OAuth.newList(params));
    }

    public void addCallbackParameter(String key, String value) {
        if (!callbackParameters.containsKey(key)) {
            callbackParameters.put(key, value);
        }
    }

}
