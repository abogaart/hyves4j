package com.spikylee.hyves4j.method;


public class AuthAccessTokenMethod extends H4jMethod<Object> {
    
    public AuthAccessTokenMethod() {
        super();
        setName("auth.accesstoken");
        setSuccessKey("auth_accesstoken_result");
    }

	@Override
	public Object getResult() {
		return null;
	}
}
