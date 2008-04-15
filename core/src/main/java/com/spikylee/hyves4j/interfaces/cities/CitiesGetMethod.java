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

package com.spikylee.hyves4j.interfaces.cities;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.spikylee.hyves4j.method.H4jMethod;
import com.spikylee.hyves4j.model.City;
import com.spikylee.hyves4j.util.H4jCityUtil;

public class CitiesGetMethod extends H4jMethod<List<City>> {

    public CitiesGetMethod() {
        setName("cities.get");
        setSuccessKey("cities_get_result");
        addRequiredParameter("cityid");
    }
    
    public CitiesGetMethod(String cityIds) {
        this();
        setParameters("cityid", cityIds);
    }
    
    @Override
    public List<City> getResult() {
        if(response.getPayload() != null) {
            List<City> cities = new ArrayList<City>();
            for(Node node : response.getPayload()) {
                City city = H4jCityUtil.createCity(node);
                cities.add(city);
            }
            return cities;
        }
        return null;
    }

}
