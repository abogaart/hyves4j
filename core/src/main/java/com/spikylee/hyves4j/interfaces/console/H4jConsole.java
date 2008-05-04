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
        transport.executeMethod(method);
        return method.getResult();
	}

}
