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

package com.spikylee.hyves4j;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.RedirectException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.client.config.H4jClientConfig;
import com.spikylee.hyves4j.interfaces.auth.H4jAuth;

public class AuthenticatedClientTest extends AbstractHyves4jTest {
    
    private H4jClient client = null;
    
    public void testAuthenticatedClient() {
        H4jClientConfig config = new H4jClientConfig("hyves", consumerPropertiesURL);
        config.setAccessToken(properties.getProperty("accesstoken"));
        config.setTokenSecret(properties.getProperty("tokensecret"));
        config.addMethod("users.get");
        
        try {
            client = Hyves4j.createAuthenticatedClient(config);
        } catch (H4jException e) {
            e.printStackTrace();
        } catch (RedirectException e) {
            System.err.println("go to " + e.getMessage());
        }
        assertNotNull(client);
        assertNotNull(client.getAccessor());
        assertNotNull(client.getAccessor().getAccessToken());
        assertNotNull(client.getAccessor().getTokenSecret());
        assertNull(client.getAccessor().getRequestToken());
        assertEquals(properties.getProperty("accesstoken"), client.getAccessor().getAccessToken());
        assertEquals(properties.getProperty("tokensecret"), client.getAccessor().getTokenSecret());
        
        Hyves4j h4j = new Hyves4j(client);
        assertNotNull(h4j);
    }
    
    public void testNewAuthenticatedClient() {
        H4jClientConfig config = new H4jClientConfig("hyves", consumerPropertiesURL);
        config.addMethod("auth.revokeSelf");

        try {
            client = Hyves4j.createAuthenticatedClient(config);
        } catch (H4jException e) {
            e.printStackTrace();
        } catch (RedirectException e) {
            String oauthToken = getOauthToken(e.getMessage());
            config.setAccessToken(oauthToken);
            try {
                client = Hyves4j.createAuthenticatedClient(config);
            } catch (RedirectException e1) {
                e1.printStackTrace();
                fail("Shouldn't be redirected anymore.");
            } catch (H4jException e1) {
                e1.printStackTrace();
            }
        }
        
        assertNotNull(client);
        assertNotNull(client.getAccessor().getAccessToken());
        assertNotNull(client.getAccessor().getTokenSecret());
        
        //now revoke again
        H4jAuth auth = new Hyves4j(client).getAuth();
        try {
            int numberDeleted= auth.revokeSelf();
            assertEquals(1, numberDeleted);
        } catch (H4jException e) {
            e.printStackTrace();
        }
    }
    
    
    private String getOauthToken(String url) {
        final WebClient webClient = new WebClient();

        // Get the first page
        try {
            HtmlPage page1 = (HtmlPage) webClient.getPage(url);
            
            // Get the form that we are dealing with and within that form, 
            // find the submit button and the field that we want to change.
            final HtmlForm form = page1.getFormByName("loginform");

            form.getInputByName("login_username").setValueAttribute("spikylee");
            form.getInputByName("login_password").setValueAttribute("karnemelk7337");
            final HtmlPage page2 = (HtmlPage)form.getInputByName("btnLogin").click();
            
            final HtmlForm form2 = page2.getForms().get(1);
            HtmlInput field = null;
            NodeList list = form2.getElementsByTagName("input");
            for(int i=0; i<list.getLength();i++) {
                Node node = list.item(i);
                String debug = node.getAttributes().getNamedItem("value").getNodeValue();
                if(debug.equals("Opslaan en ga verder naar partner-site")) {
                    field = (HtmlInput) node;
                }
            }
            if (field != null) {
                final HtmlPage page3 = (HtmlPage)field.click();
                URL callbackUrl = page3.getWebResponse().getUrl();
                String q = callbackUrl.getQuery();
                StringTokenizer tokenizer = new StringTokenizer(q, "&");
                while(tokenizer.hasMoreTokens()) {
                    String tkn = tokenizer.nextToken();
                    if(tkn.indexOf("oauth_token") > -1) {
                        String oauthToken = tkn.substring("oauth_token=".length());
                        return oauthToken;
                    }
                }
            }
            
        } catch (FailingHttpStatusCodeException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

}
