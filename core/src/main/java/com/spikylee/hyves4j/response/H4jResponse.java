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
