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

import com.spikylee.hyves4j.model.Country;

public class H4jCountryUtil {
    
    final static Logger logger = LoggerFactory.getLogger(H4jCountryUtil.class);
    
    public static List<Country> createCountries(Collection<Node> nodes) {
        List<Country> countries = new ArrayList<Country>();
        for(Node node : nodes) {
            if(node.getNodeName().equals("country")) {
                countries.add(createCountry(node));
            }
        }
        return countries;

    }
    
    public static Country createCountry(Node node) {
        return createCountry(node, "");
    }
        
    public static Country createCountry(Node node, String prefix) {
        JXPathUtil jxpath = new JXPathUtil(node);
        Country country = new Country();
        country.setCountryId(jxpath.getStringValue(prefix + "/countryid"));
        String countryName = jxpath.getStringValue(prefix + "/countryname");
        if(countryName == null) {
            countryName = jxpath.getStringValue(prefix + "/name");
        }
        country.setName(countryName);

        if(logger.isDebugEnabled()) {
            logger.debug("Country created: " + country);
        }

        return country;

    }
}
