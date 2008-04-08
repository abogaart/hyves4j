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
