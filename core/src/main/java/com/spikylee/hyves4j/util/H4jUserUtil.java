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

import java.util.Date;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.spikylee.hyves4j.model.City;
import com.spikylee.hyves4j.model.Image;
import com.spikylee.hyves4j.model.ProfilePicture;
import com.spikylee.hyves4j.model.User;

public class H4jUserUtil {

	public static User createUser(Node node) {
	    Element userEl = (Element)node;
		User user = new User();
		user.setUserId(XMLUtil.getChildValue(userEl, "userid"));
		user.setNickName(XMLUtil.getChildValue(userEl, "nickname"));
		user.setFirstName(XMLUtil.getChildValue(userEl, "firstname"));
		user.setLastName(XMLUtil.getChildValue(userEl, "lastname"));
		user.setGender(XMLUtil.getChildValue(userEl, "gender"));

		user.getBirthday().setAge(
				XMLUtil.getChildValueAsInt(userEl, "age"));
		user.getBirthday().setDay(
				XMLUtil.getChildValueAsInt(userEl, "day"));
		user.getBirthday().setMonth(
				XMLUtil.getChildValueAsInt(userEl, "month"));
		user.getBirthday().setYear(
				XMLUtil.getChildValueAsInt(userEl, "year"));

		user.setFriendsCount(XMLUtil.getChildValueAsInt(userEl,
				"friendscount"));
		user.setUrl(XMLUtil.getChildValue(userEl, "url"));
		user.setMediaId(XMLUtil.getChildValue(userEl, "mediaid"));
		user.setCountryId(XMLUtil.getChildValue(userEl, "countryid"));

		City city = new City();
		city.setCityId(XMLUtil.getChildValue(userEl, "cityid"));
		city.setName(XMLUtil.getChildValue(userEl, "cityname"));
		user.setCity(city);

		int created = XMLUtil.getChildValueAsInt(userEl, "created");
		if (created > -1) {
			Date date = new Date(created);
			user.setCreated(date);
		}
		
		Element ppEl = XMLUtil.getChild(userEl, "profilepicture");
		if(ppEl != null) {
		    ProfilePicture profilePicture = new ProfilePicture();
		    profilePicture.setMediaId(XMLUtil.getChildValue(ppEl, "mediaid"));
		    profilePicture.setUserId(XMLUtil.getChildValue(ppEl, "userid"));
		    profilePicture.setTitle(XMLUtil.getChildValue(ppEl, "title"));
		    profilePicture.setDescription(XMLUtil.getChildValue(ppEl, "description"));
		    profilePicture.setMediaType(XMLUtil.getChildValue(ppEl, "mediatype"));
		    
		    profilePicture.setImage(getImage(XMLUtil.getChild(ppEl, "image")));
		    profilePicture.setFullscreenImage(getImage(XMLUtil.getChild(ppEl, "image_fullscreen")));
		    profilePicture.setSmallIcon(getImage(XMLUtil.getChild(ppEl, "icon_small")));
		    profilePicture.setMediumIcon(getImage(XMLUtil.getChild(ppEl, "icon_medium")));
		    profilePicture.setLargeIcon(getImage(XMLUtil.getChild(ppEl, "icon_large")));
		    user.setProfilePicture(profilePicture);
		}
		
		return user;
	}
	
	public static Image getImage(Element el) {
        Image img = new Image();
        img.setWidth(XMLUtil.getChildValueAsInt(el, "width"));
        img.setHeight(XMLUtil.getChildValueAsInt(el, "height"));
        img.setSource(XMLUtil.getChildValue(el, "src"));
        return img;
	}
}
