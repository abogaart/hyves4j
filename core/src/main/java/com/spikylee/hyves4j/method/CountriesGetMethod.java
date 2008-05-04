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

package com.spikylee.hyves4j.method;

import java.util.List;

import com.spikylee.hyves4j.model.Country;
import com.spikylee.hyves4j.util.H4jCountryUtil;

public class CountriesGetMethod extends H4jMethod<List<Country>> {
    
    public CountriesGetMethod() {
        setName("countries.get");
        setSuccessKey("countries_get_result");
        addRequiredParameter("countryid");
    }
    
    public CountriesGetMethod(String countryIds) {
        this();
        setParameters("countryid", countryIds);
    }
    
    @Override
    public List<Country> getResult() {
        if(response.getPayloadAsCollection() != null) {
            return H4jCountryUtil.createCountries(response.getPayloadAsCollection());
        }
        return null;
    }
    
}
