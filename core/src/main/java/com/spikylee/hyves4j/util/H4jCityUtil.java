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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.spikylee.hyves4j.model.City;

public class H4jCityUtil {
    
    final static Logger logger = LoggerFactory.getLogger(H4jCityUtil.class);
    
    public static List<City> createCities(Collection<Node> nodes) {
        List<City> cities = new ArrayList<City>();
        for(Node node : nodes) {
            if(node.getNodeName().equals("city")) {
                cities.add(H4jCityUtil.createCity(node));
            }
        }
        return cities;
    }
    
    public static City createCity(Node node) {
        return createCity(node, "");
    }
    
    public static City createCity(Node node, String prefix) {
        JXPathUtil jxpath = new JXPathUtil(node);
        City city = new City();
        city.setCityId(jxpath.getStringValue(prefix + "/cityid"));
        city.setRegionId(jxpath.getStringValue(prefix + "/regionid"));
        city.setCountryId(jxpath.getStringValue(prefix + "/countryid"));
        String cityName = jxpath.getStringValue(prefix + "/cityname");
        if(cityName == null)
            cityName = jxpath.getStringValue(prefix + "/name");
        city.setName(cityName);
        city.setCityTabId(jxpath.getStringValue(prefix + "/citytabid"));
        city.setUrl(jxpath.getStringValue(prefix + "/url"));
        
        if(logger.isDebugEnabled()) {
            logger.debug("City created: " + city);
        }
        return city;
    }
}
