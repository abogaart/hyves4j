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

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.spikylee.hyves4j.model.City;

public class H4jCityUtil {
    
    public static City createCity(Node node) {
        Element el = (Element)node;
        City city = new City();
        city.setCityId(XMLUtil.getChildValue(el, "cityid"));
        city.setRegionId(XMLUtil.getChildValue(el, "regionid"));
        city.setCountryId(XMLUtil.getChildValue(el, "countryid"));
        city.setName(XMLUtil.getChildValue(el, "name"));
        city.setCityTabId(XMLUtil.getChildValue(el, "citytabid"));
        city.setUrl(XMLUtil.getChildValue(el, "url"));
                
        return city;
    }
}
