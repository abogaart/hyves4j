package com.spikylee.hyves4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import junit.framework.TestCase;

public abstract class AbstractHyves4jTest extends TestCase {
	
	protected String 	consumerPropertiesLocation = "/consumer.properties";
	protected URL 		consumerPropertiesURL;
	
	protected Properties properties = null;
	
	public void setUp() {
    	consumerPropertiesURL = getClass().getResource(consumerPropertiesLocation);
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/setup.properties");
            properties = new Properties();
            properties.load(in);
        } catch(IOException e) {
        	e.printStackTrace();
        } finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}
