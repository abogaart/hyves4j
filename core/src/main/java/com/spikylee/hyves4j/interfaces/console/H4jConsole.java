package com.spikylee.hyves4j.interfaces.console;

import java.util.List;

import net.oauth.OAuth.Parameter;

import com.spikylee.hyves4j.H4jException;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.interfaces.H4jInterface;
import com.spikylee.hyves4j.method.ConsoleMethod;
import com.spikylee.hyves4j.transport.H4jTransport;

public class H4jConsole extends H4jInterface {

	public H4jConsole(H4jClient client, H4jTransport transport) {
		super(client, transport);
	}
	
	public String execute(String haMethod, String responseFields, List<Parameter> params) throws H4jException {
	    ConsoleMethod method = new ConsoleMethod(haMethod);
        if (responseFields != null && !responseFields.equals("")) {
            method.setResponseFields(responseFields);
        }
        method.setParameters(params);
        transport.sendMethod(method);
        return method.getResult();
	}

}
