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
