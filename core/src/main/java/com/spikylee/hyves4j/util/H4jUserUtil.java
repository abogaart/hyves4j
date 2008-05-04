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

package com.spikylee.hyves4j.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.spikylee.hyves4j.model.ProfilePicture;
import com.spikylee.hyves4j.model.User;

public class H4jUserUtil {

    final static Logger logger = LoggerFactory.getLogger(H4jUserUtil.class);

    public static List<User> createUsers(Collection<Node> nodes) {
        List<User> users = new ArrayList<User>();
        for (Node node : nodes) {
            User user = createUser(node);
            if (user != null)
                users.add(user);
        }
        return users;
    }

    public static User createUser(Collection<Node> nodes) {
        for (Node node : nodes) {
            if (node.getNodeName().equals("user")) {
                User user = createUser(node);
                return user;
            }
        }
        return null;
    }

    public static User createUser(Node node) {
        if (!node.getNodeName().equals("user"))
            return null;

        JXPathUtil jxpath = new JXPathUtil(node);

        User user = new User();
        user.setUserId(jxpath.getStringValue("/userid"));
        user.setNickName(jxpath.getStringValue("/nickname"));
        user.setFirstName(jxpath.getStringValue("/firstname"));
        user.setLastName(jxpath.getStringValue("/lastname"));
        user.setGender(jxpath.getStringValue("/gender"));

        user.getBirthday().setAge(jxpath.getIntValue("/birthday/age"));
        user.getBirthday().setDay(jxpath.getIntValue("/birthday/day"));
        user.getBirthday().setMonth(jxpath.getIntValue("/birthday/month"));
        user.getBirthday().setYear(jxpath.getIntValue("/birthday/year"));

        user.setFriendsCount(jxpath.getIntValue("/friendscount"));
        user.setUrl(jxpath.getStringValue("/url"));
        user.setMediaId(jxpath.getStringValue("/mediaid"));

        user.setCountry(H4jCountryUtil.createCountry(node));
        user.setCity(H4jCityUtil.createCity(node));
        
        long created = jxpath.getLongValue("/created");
        if (created > -1) {
            long timestamp = created * 1000;
            Date date = new Date(timestamp);
            user.setCreated(date);
        }

        ProfilePicture profilePicture = H4jPictureUtil.createProfilePicture(node);
        user.setProfilePicture(profilePicture);
        user.setOnMyMind(jxpath.getStringValue("onmymind"));
        
        if (logger.isDebugEnabled()) {
            logger.debug("User created: " + user);
        }
        return user;
    }

}
