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

import com.spikylee.hyves4j.interfaces.cities.H4jCities;
import com.spikylee.hyves4j.interfaces.countries.H4jCountries;
import com.spikylee.hyves4j.interfaces.users.H4jUsers;
import com.spikylee.hyves4j.model.City;
import com.spikylee.hyves4j.model.Country;
import com.spikylee.hyves4j.model.User;

public class AuthenticatedTest extends AbstractAuthenticatedHyves4jTest {
    
    private H4jUsers users;
    private H4jCities cities;
    
    @Override
    public void setUp() {
        super.setUp();
        users = h4j.getUsers();
        cities = h4j.getCities();
        assertNotNull(users);
        assertNotNull(cities);
    }
	
	public void testGetByUsernameMethod() {
        try {
            User user = users.getByUsername(getTestUserName());
            assertNotNull(user);
            assertNotNull(user.getUserId());
            assertEquals(user.getUserId(), getTestUserId());
        } catch (H4jException e) {
            fail(e);
        }
    }
	
	public void testUsersGetMethodSimple() {
        try {
            List<User> usersList = users.get(getTestUserId());
            assertNotNull(usersList);
            User usr = usersList.get(0);
            assertNotNull(usr.getUserId());
            assertEquals(usr.getUserId(), getTestUserId());
        } catch (H4jException e) {
            fail(e);
        }
	}

   public void testUsersGetMethodExtended() {
        try {
            List<User> usersList = users.get(getTestUserId(), true, true, true, true, true);
            assertNotNull(usersList);
            User usr = usersList.get(0);
            assertEquals(usr.getUserId(), getTestUserId());
            assertNotNull(usr.getProfilePicture());
            assertEquals(usr.getProfilePicture().getUserId(), getTestUserId());
            assertNotNull(usr.getCity().getName());
            assertNotNull(usr.getCountry().getName());
            assertNotNull(usr.getOnMyMind());
        } catch (H4jException e) {
            fail(e);
        }
    }
   
   public void testCitiesGetMethod() {
       try {
           City city = cities.getCity(properties.getProperty("cityid"));
           assertNotNull(city);
           assertEquals(properties.getProperty("cityid"), city.getCityId());
           assertEquals(properties.getProperty("cityname"), city.getName());
       } catch (H4jException e) {
           fail(e);
       }
   }

   public void testCountriesGetMethod() {
       H4jCountries countries = h4j.getCountries();
       try {
           Country country = countries.getCountry(properties.getProperty("countryid"));
           assertNotNull(country);
           assertEquals(properties.getProperty("countryid"), country.getCountryId());
           assertEquals(properties.getProperty("countryname"), country.getName());
       } catch (H4jException e) {
           fail(e);
       }
   }

}
