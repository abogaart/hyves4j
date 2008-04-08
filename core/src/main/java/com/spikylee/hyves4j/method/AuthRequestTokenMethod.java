package com.spikylee.hyves4j.method;


public class AuthRequestTokenMethod extends H4jMethod<Object> {
    
    public AuthRequestTokenMethod() {
        super();
        setName("auth.requesttoken");
        setSuccessKey("auth_requesttoken_result");
    }
    
	@Override
	public Object getResult() {
		return null;
	}

}


