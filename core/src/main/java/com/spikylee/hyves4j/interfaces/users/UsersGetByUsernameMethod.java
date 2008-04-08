package com.spikylee.hyves4j.interfaces.users;

import org.w3c.dom.Node;

import com.spikylee.hyves4j.method.H4jMethod;
import com.spikylee.hyves4j.model.User;
import com.spikylee.hyves4j.util.H4jUserUtil;

public class UsersGetByUsernameMethod extends H4jMethod<User> {

    public UsersGetByUsernameMethod(String userids) {
        this();
        setParameters("userid", userids);
    }
    
    public UsersGetByUsernameMethod() {
        setName("users.getByUsername");
        setSuccessKey("users_getByUsername_result");
    }

	@Override
	public User getResult() {
    	if(response.getPayload() != null) {
    		for(Node node : response.getPayload()) {
    			User user  = H4jUserUtil.createUser(node);
    			return user;
    		}
    	}
		return null;
	}

}
