package com.spikylee.hyves4j.interfaces.users;

import java.util.List;

import com.spikylee.hyves4j.H4jException;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.interfaces.H4jInterface;
import com.spikylee.hyves4j.model.User;
import com.spikylee.hyves4j.transport.H4jTransport;

public class H4jUsers extends H4jInterface {

    public H4jUsers(H4jClient client, H4jTransport transport) {
        super(client, transport);
    }

    public List<User> get(String userIds, boolean includeProfilePicture, boolean includeCityName,
            boolean includeCountryName, boolean includeWhiteSpaces, boolean includeOnMyMind) throws H4jException {
        UsersGetMethod method = new UsersGetMethod(userIds);
        if(includeProfilePicture)
            method.includeProfilePicture();
        if(includeCityName)
            method.includeCityName();
        if(includeCountryName)
            method.includeCountryName();
        if(includeWhiteSpaces)
            method.includeWhiteSpaces();
        if(includeOnMyMind)
            method.includeOnMyMind();
        
        transport.sendMethod(method);
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
        transport.sendMethod(method);
        User user = method.getResult();

        return user;
    }

}
