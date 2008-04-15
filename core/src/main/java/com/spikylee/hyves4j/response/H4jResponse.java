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

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.spikylee.hyves4j.method.H4jMethod;


public abstract class H4jResponse {
    
    protected String errorCode;
    protected String errorMessage;
    private H4jMethod<?> method;
    
    public H4jResponse(H4jMethod<?> method) {
        this.method = method;
    }
    
    public boolean isError() {
        return errorCode != null;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public H4jMethod<?> getMethod() {
        return method;
    }
    
    public abstract String getParameter(String name);
    
    public abstract Collection<Node> getPayload();
    
    public abstract void parse(Document document);

}
