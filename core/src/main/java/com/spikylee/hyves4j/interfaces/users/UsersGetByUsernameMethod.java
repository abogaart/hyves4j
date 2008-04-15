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
