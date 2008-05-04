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

import java.util.List;

import com.spikylee.hyves4j.H4jException;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.interfaces.H4jInterface;
import com.spikylee.hyves4j.method.UsersGetByUsernameMethod;
import com.spikylee.hyves4j.method.UsersGetMethod;
import com.spikylee.hyves4j.model.User;
import com.spikylee.hyves4j.transport.H4jTransport;

public class H4jUsers extends H4jInterface {

    public H4jUsers(H4jClient client, H4jTransport transport) {
        super(client, transport);
    }

    public List<User> get(String userIds, boolean includeProfilePicture, boolean includeCityName,
            boolean includeCountryName, boolean includeWhiteSpaces, boolean includeOnMyMind) throws H4jException {

        UsersGetMethod method = new UsersGetMethod(userIds);
        method.setIncludeProfilePicture(includeProfilePicture);
        method.setIncludeCityName(includeCityName);
        method.setIncludeCountryName(includeCountryName);
        method.setIncludeWhiteSpaces(includeWhiteSpaces);
        method.setIncludeOnMyMind(includeOnMyMind);

        transport.executeMethod(method);
        List<User> users = method.getResult();
        return users;
    }

    public List<User> get(String userIds) throws H4jException {
        return get(userIds, false, false, false, false, false);
    }

    /**
     * Get a User by the username.
     * 
     * @param username
     *            The username
     * @return The User object
     * @throws Hyves4jTransportException
     * @throws H4jException
     */
    public User getByUsername(String username) throws H4jException {
        UsersGetByUsernameMethod method = new UsersGetByUsernameMethod();
        method.setParameters("username", username);
        transport.executeMethod(method);
        User user = method.getResult();

        return user;
    }

}
