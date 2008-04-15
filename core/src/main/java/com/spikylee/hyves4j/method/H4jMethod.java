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

import java.util.ArrayList;
import java.util.List;

import net.oauth.OAuth.Parameter;

import com.spikylee.hyves4j.H4jException;
import com.spikylee.hyves4j.response.H4jResponse;

public abstract class H4jMethod<T> {
    private String name;
    private String responseFormat;
    private List<Parameter> parameters;
    private List<String> requiredParameters = new ArrayList<String>();
    private List<String> haResponseFields = new ArrayList<String>();
    
    private String successKey;
    private String failKey = "error_result";
    
    protected H4jResponse response;
    
    protected void addRequiredParameter(String name) {
        if(!requiredParameters.contains(name))
            requiredParameters.add(name);
    }
    
    public void validateParameters() throws H4jException {
        int found = 0;
        for(Parameter param : parameters) {
            if(requiredParameters.contains(param.getKey()))
                found++;
        }
        if(found < requiredParameters.size()) {
            StringBuffer sb = new StringBuffer();
            for(String key : requiredParameters) {
                if(sb.length() > 0)
                    sb.append(", ");
                sb.append(key);
            }
            throw new H4jException("Missing required method parameters", "Required parameters are " + sb.toString());
        }
    }
    
    public String getResponseFields() {
        StringBuffer buf = new StringBuffer();
        for(String str : haResponseFields) {
            if(buf.length() > 0)
                buf.append(",");
            buf.append(str);
        }
        return buf.toString();
    }
    
    protected void addResponseField(String name) {
        if(!haResponseFields.contains(name)){
            haResponseFields.add(name);
        }
    }
    
    public String getSuccessKey() {
        return successKey;
    }
    
    protected void setSuccessKey(String successKey) {
        this.successKey = successKey;
    }
    
    public String getFailKey() {
        return failKey;
    }
    protected void setFailKey(String failKey) {
        this.failKey = failKey;
    }
    
    public String getName() {
        return name;
    }
    
    protected void setName(String name) {
        this.name = name;
    }
    
    public List<Parameter> getParameters() {
        if(parameters == null)
            parameters = new ArrayList<Parameter>();
        return parameters;
    }
    
    public H4jMethod<T> setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }
    
    public H4jMethod<T> setParameters(String... parameters) {
        List<Parameter> localParams = getParameters();
        for (int p = 0; p + 1 < parameters.length; p += 2) {
            localParams.add(new Parameter(parameters[p],
                    parameters[p + 1]));
        }
        return this;
    }
    
    public void setResponse(H4jResponse response) {
        this.response = response;
    }
    
    public String getResponseFormat() {
        return responseFormat;
    }
    
    public void setResponseFormat(String responseFormat) {
        this.responseFormat = responseFormat;
    }
    
	public abstract T getResult();
	
}