package com.spikylee.hyves4j.method;

import com.spikylee.hyves4j.util.JXPathUtil;

public class AuthRevokeSelfMethod extends H4jMethod<Integer> {
    
    public AuthRevokeSelfMethod() {
        setName("auth.revokeSelf");
        setSuccessKey("auth_revokeSelf_result");
    }
    
    @Override
    public Integer getResult() {
        if(response.getPayloadAsDOM() != null) {
            JXPathUtil jxpath = new JXPathUtil(response.getPayloadAsDOM());
            return new Integer(jxpath.getIntValue("/auth_revokeSelf_result/deletecount"));
        }
        return 0;
    }

}
