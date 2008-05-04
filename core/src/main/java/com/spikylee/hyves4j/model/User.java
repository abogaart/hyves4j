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

package com.spikylee.hyves4j.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class User implements Serializable {

	private static final long serialVersionUID = 7485144747415704864L;
	
	private String userId;
	private boolean profileVisible;
	private String nickName;
	private String firstName;
	private String lastName;
	private String gender;
	private Birthday birthday;
	private int friendsCount;
	private String url;
	private String mediaId;
	
	private Country country;
	private City city;
	private Date created;
	
	private ProfilePicture profilePicture;
	
	private String onMyMind;//dep?
	
	public boolean isProfileVisible() {
		return profileVisible;
	}

	public void setProfileVisible(boolean profileVisible) {
		this.profileVisible = profileVisible;
	}

	public Birthday getBirthday() {
		return birthday;
	}

	public void setBirthday(Birthday birthday) {
		this.birthday = birthday;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public User() {
		birthday = new Birthday();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOnMyMind() {
		return onMyMind;
	}
	public void setOnMyMind(String onMyMind) {
		this.onMyMind = onMyMind;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    final public static class Birthday implements Serializable {
        private static final long serialVersionUID = 1L;

        private int day;
        private int month;
        private int year;
        private int age;
        
        public int getDay() {
            return day;
        }
        public void setDay(int day) {
            this.day = day;
        }
        public int getMonth() {
            return month;
        }
        public void setMonth(int month) {
            this.month = month;
        }
        public int getYear() {
            return year;
        }
        public void setYear(int year) {
            this.year = year;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).
            append("day", day).
            append("month", month).
            append("year", year).
            append("age", age).
            toString();
        }
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
        append("userId", userId).
        append("profileVisible", profileVisible).
        append("nickName", nickName).
        append("firstName", firstName).
        append("lastName", lastName).
        append("gender", gender).
        append("birthday", birthday).
        append("friendsCount", friendsCount).
        append("url", url).
        append("mediaId", mediaId).
        append("country", country).
        append("city", city).
        append("created", created).
        append("profilePicture", profilePicture).
        append("onMyMind", onMyMind).
        toString();
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
