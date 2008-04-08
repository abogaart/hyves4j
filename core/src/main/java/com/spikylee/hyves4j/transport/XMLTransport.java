package com.spikylee.hyves4j.transport;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.method.H4jMethod;
import com.spikylee.hyves4j.response.H4jResponse;
import com.spikylee.hyves4j.response.XMLResponse;

public class XMLTransport extends H4jTransport {

    public XMLTransport(H4jClient client) {
        super(client);
    }

    @Override
    protected H4jResponse createResponse(H4jMethod<?> method) {
        return new XMLResponse(method);
    }
    
    @Override
    protected String getResponseFormat() {
        return XMLResponse.RESPONSE_FORMAT;
    }
}
