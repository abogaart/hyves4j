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

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.spikylee.hyves4j.method.H4jMethod;
import com.spikylee.hyves4j.model.User;
import com.spikylee.hyves4j.util.H4jUserUtil;

public class UsersGetMethod extends H4jMethod<List<User>> {

    public UsersGetMethod(String userids) {
        this();
        setParameters("userid", userids);
    }
    public UsersGetMethod() {
        setName("users.get");
        setSuccessKey("users_get_result");
        addRequiredParameter("userid");
    }
	
    @Override
	public List<User> getResult() {
    	if(response.getPayload() != null) {
    		List<User> users = new ArrayList<User>();
    		for(Node node : response.getPayload()) {
    			User user  = H4jUserUtil.createUser(node);
				users.add(user);
    		}
    		return users;
    	}
		return null;
	}
    
    public void includeCityName() {
        addResponseField("cityname");
    }
    
    public void includeCountryName() {
        addResponseField("countryname");
    }

    public void includeProfilePicture() {
        addResponseField("profilepicture");
    }

    public void includeWhiteSpaces() {
        addResponseField("whitespaces");
    }

    public void includeOnMyMind() {
        addResponseField("onmymind");
    }

}
