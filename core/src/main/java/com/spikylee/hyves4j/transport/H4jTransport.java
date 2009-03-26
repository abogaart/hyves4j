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

package com.spikylee.hyves4j.transport;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.spikylee.hyves4j.H4jException;
import com.spikylee.hyves4j.Hyves4j;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.message.H4jMessage;
import com.spikylee.hyves4j.method.H4jMethod;
import com.spikylee.hyves4j.response.H4jResponse;

public abstract class H4jTransport {

    private static final String XML_ERROR_RESPONSE = "<?xml version";

    private static final String HTTP_REQUEST = "HTTP request";

    private static final String HTTP_STATUS = "HTTP status";

    final Logger log = LoggerFactory.getLogger(H4jTransport.class);

    private H4jClient client;
    private DocumentBuilder builder;

    public H4jTransport(H4jClient client) {
        this.client = client;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("Creation of new Hyves4jTransport failed.", e);
            throw new RuntimeException("Creation of new Hyves4jTransport failed.", e);
        }
    }

    public H4jMethod<?> executeMethod(H4jMethod<?> method) throws H4jException {
        H4jMessage msg = createMessage(method);
        H4jResponse resp = sendMessage(msg);
        method.setResponse(resp);
        return method;
    }

    public H4jMessage createMessage(H4jMethod<?> method) {
        method.setResponseFormat(getResponseFormat());
        return new H4jMessage(method, client);
    }

    public H4jResponse sendMessage(H4jMessage message) throws H4jException {
        OAuthMessage result;
        String responseBody = null;

        // make sure all required parameters are set
        message.getMethod().validateParameters();
        try {
            if (log.isDebugEnabled()) {
                log.debug("About to send " + message.getMessage().getParameters());
            }
            result = Hyves4j.HTTP_CLIENT.invoke(client.getAccessor(), Hyves4j.HYVES_API, message.getMessage()
                    .getParameters());

            responseBody = result.readBodyAsString();

        } catch (OAuthProblemException oax) {
            Integer status = (Integer) oax.getParameters().get(HTTP_STATUS);
            String request = (String) oax.getParameters().get(HTTP_REQUEST);
            responseBody = XML_ERROR_RESPONSE + "=" + (String) oax.getParameters().get(XML_ERROR_RESPONSE);
            log.error("Got status " + status + " on " + request, oax);
        } catch (Exception e) {
            throw new H4jException("An net error occured while the message was sent.", e);
        }
        Document document = null;
        try {
            document = builder.parse(new InputSource(new StringReader(responseBody)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        H4jResponse response = createResponse(message.getMethod());
        response.parse(document);
        if (response.isError()) {
            throw new H4jException(response.getErrorCode(), response.getErrorMessage());
        }
        return response;
    }

    protected abstract H4jResponse createResponse(H4jMethod<?> method);

    protected abstract String getResponseFormat();

}
