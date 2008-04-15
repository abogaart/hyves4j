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
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import junit.framework.TestCase;

public abstract class AbstractHyves4jTest extends TestCase {
	
	protected String 	consumerPropertiesLocation = "/consumer.properties";
	protected URL 		consumerPropertiesURL;
	
	protected Properties properties = null;
	
	public void setUp() {
    	consumerPropertiesURL = AbstractHyves4jTest.class.getResource(consumerPropertiesLocation);
        InputStream in = null;
        try {
            in = AbstractHyves4jTest.class.getResourceAsStream("/setup.properties");
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
