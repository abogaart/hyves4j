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

package com.spikylee.hyves4j.method;

import java.util.StringTokenizer;

import org.w3c.dom.Node;

import com.spikylee.hyves4j.util.XMLUtil;

public class ConsoleMethod extends H4jMethod<String> {
	
	public ConsoleMethod(String methodName) {
		setName(methodName);
		String successKey = methodName.replaceAll("\\.", "_");
		setSuccessKey(successKey + "_result");
	}
	
	@Override
	public String getResult() {
		StringBuffer result = new StringBuffer();
		if(response.getPayload() != null) {
    		for(Node node : response.getPayload()) {
    			result.append(XMLUtil.prettyPrintXML(node));
    		}
	    }
		return result.toString();
	}
	
	public void setResponseFields(String input){
	    if(input == null || input.length() == 0)
	        return;
	    StringTokenizer token = new StringTokenizer(input, ",");
	    while(token.hasMoreTokens()) {
	        addResponseField(token.nextToken());
	    }
	}
}
