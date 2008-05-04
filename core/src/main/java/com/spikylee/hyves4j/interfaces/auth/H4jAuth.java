package com.spikylee.hyves4j.interfaces.auth;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.oauth.OAuth;
import net.oauth.OAuth.Parameter;

import org.apache.commons.httpclient.RedirectException;

import com.spikylee.hyves4j.H4jAccessor;
import com.spikylee.hyves4j.H4jException;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.interfaces.H4jInterface;
import com.spikylee.hyves4j.message.H4jMessage;
import com.spikylee.hyves4j.method.AuthAccessTokenMethod;
import com.spikylee.hyves4j.method.AuthRequestTokenMethod;
import com.spikylee.hyves4j.method.RevokeSelfMethod;
import com.spikylee.hyves4j.response.H4jResponse;
import com.spikylee.hyves4j.transport.H4jTransport;

public class H4jAuth extends H4jInterface {

    public H4jAuth(H4jClient client, H4jTransport transport) {
        super(client, transport);
    }
    
    public void getAccessToken() throws H4jException, RedirectException {
        H4jAccessor accessor = client.getAccessor();
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
            //return authClient;
        } else {
            // reset accesstoken & secret
            client.getAccessor().setRequestToken(null);
            client.getAccessor().setAccessToken(null);
            client.getAccessor().setTokenSecret(null);

            // get a new request token!
            getRequestToken();
            
            String consumerName = (String) client.getConfig().getConsumerName();
            String authorizationURL = client.getAccessor().consumer.serviceProvider.userAuthorizationURL;
            String callbackURL = client.getAccessor().consumer.callbackURL;

            List<Parameter> params = client.getConfig().getCallbackParameters();
            params.add(new Parameter("consumer", consumerName));

            String authorizeURL;
            try {
                authorizeURL = OAuth.addParameters(authorizationURL, "oauth_token", client.getAccessor()
                        .getRequestToken(), "oauth_callback", OAuth.addParameters(callbackURL, params));
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
            throw new RedirectException(authorizeURL);
        }
    }
    
    public void getRequestToken() throws H4jException {
        String methods = "";
        for (Iterator<String> it = client.getConfig().getMethods().iterator(); it.hasNext();) {
            methods += it.next();
            if (it.hasNext())
                methods += ",";
        }

        H4jMessage message = transport.createMessage(new AuthRequestTokenMethod().setParameters("methods", methods,
                "expirationtype", client.getConfig().getExpirationType()));
        H4jResponse response = transport.sendMessage(message);

        client.getAccessor().setRequestToken(response.getParameter("oauth_token"));
        client.getAccessor().setTokenSecret(response.getParameter("oauth_token_secret"));

        if (client.getAccessor().getRequestToken() == null) {
            throw new H4jException("-1", "No requestToken was returned");
        }
        if (client.getAccessor().getTokenSecret() == null) {
            throw new H4jException("-1", "No tokenSecret was returned");
        }

    }
    
    public Integer revokeSelf() throws H4jException {
        RevokeSelfMethod method = new RevokeSelfMethod();
        transport.executeMethod(method);
        return method.getResult();
    }

}
