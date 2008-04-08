package com.spikylee.hyves4j.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.transport.H4jTransport;

public class H4jInterface {
    
	protected H4jClient client;
    protected H4jTransport transport;
    
    public H4jInterface(H4jClient client, H4jTransport transport) {
        this.client = client;
        this.transport = transport;
    }

}
