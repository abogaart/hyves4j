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

package com.spikylee.hyves4j.response;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.spikylee.hyves4j.Hyves4j;
import com.spikylee.hyves4j.method.H4jMethod;
import com.spikylee.hyves4j.util.XMLUtil;

public class XMLResponse extends H4jResponse {

    public static final String RESPONSE_FORMAT = "xml";
    
    final Logger logger = LoggerFactory.getLogger(XMLResponse.class);
    
    private Document doc;
    
    public XMLResponse(H4jMethod<?> method) {
        super(method);
    }

    @Override
    public Document getPayloadAsDOM() {
        return doc;
    }
    
    @Override
	public Collection<Node> getPayloadAsCollection() {
		return XMLUtil.getChildElements(doc.getDocumentElement());
	}
    
    public void parse(Document document) {
        Element rootElement = document.getDocumentElement();
        rootElement.normalize();

        if(logger.isDebugEnabled())  {
        	logger.debug("Parsing result from method " + getMethod().getName());
        	if(Hyves4j.DEBUG_RESPONSE_XML)
        	    logger.debug(XMLUtil.prettyPrintXML(rootElement));
        }
        
        if (rootElement.getTagName().equals(getMethod().getSuccessKey())) {
            doc = document;
            //payload = XMLUtil.getChildElements(rootElement);
        } else {
        
            //Collection<Node> payload = XMLUtil.getChildElements(rootElement);
            
            if (rootElement.getTagName().equals(getMethod().getFailKey())) {
                errorCode = "Hyves error code " + XMLUtil.getChildValue(rootElement, "error_code");
                errorMessage = XMLUtil.getChildValue(rootElement, "error_message");
            } else {
                errorCode = "Hyves4j response parsing error, success and/or fail keys didn't match. (Check method)" + XMLUtil.getChildValue(rootElement, "error_code");
            }
            
            Element req = XMLUtil.getChild(rootElement,"request_parameters");
            if(req != null) {
                errorMessage += "\n\n**** Request parameters ****\n";
                
                Collection<Node> requestParams = XMLUtil.getChildElements(req);
                Iterator<Node> it = requestParams.iterator();
                while(it.hasNext()) {
                    Element element = (Element)it.next();
                    String key = XMLUtil.getChildValue(element, "requestkey");
                    String value = XMLUtil.getChildValue(element, "requestvalue");
                    if(key != null && key.equals("oauth_consumer_key")) {
                        value = "**********************************************".substring(value.length());
                    } 
                    errorMessage += key + " : " + value + "\n";
                }
            }
            errorMessage += "\nOriginal response xml\n";
            StringBuffer sb = new StringBuffer();
            XMLUtil.prettyPrintXML(rootElement, sb, 0);
            errorMessage += sb.toString();
            
            logger.error(errorCode);
            logger.error(errorMessage);
        }
    }
    
    public String getParameter(String name) {
        return XMLUtil.getValue(XMLUtil.getChild(getPayloadAsCollection(), name));
    }

}
