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

package com.spikylee.hyves4j;

import java.util.List;

import com.spikylee.hyves4j.interfaces.users.H4jUsers;
import com.spikylee.hyves4j.model.User;

public class AuthenticatedUsersTest extends AbstractAuthenticatedHyves4jTest {
    
    private H4jUsers users;
    
    @Override
    public void setUp() {
        super.setUp();
        users = h4j.getUsers();
        assertNotNull(users);
    }
	
	public void testGetByUsernameMethod() {
        try {
            User user = users.getByUsername(properties.getProperty("username"));
            assertNotNull(user);
            assertNotNull(user.getUserId());
            assertEquals(user.getUserId(), properties.getProperty("userid"));
        } catch (H4jException e) {
            fail(e.getErrorCode() + " - " + e.getErrorMessage());
        }
    }
	
	public void testUsersGetMethod() {
        try {
            List<User> usersList = users.get(properties.getProperty("userid"));
            assertNotNull(usersList);
            User usr = usersList.get(0);
            assertNotNull(usr.getUserId());
            assertEquals(usr.getUserId(), properties.getProperty("userid"));
        } catch (H4jException e) {
            fail(e.getErrorCode() + " - " + e.getErrorMessage());
        }
	}

}
