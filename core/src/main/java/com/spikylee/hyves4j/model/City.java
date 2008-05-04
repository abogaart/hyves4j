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

import org.apache.commons.lang.builder.ToStringBuilder;

public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String cityId;
    private String regionId;
    private String countryId;
    
    private String name;
    private String cityTabId;
    private String url;
    
    public String getCityId() {
        return cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    public String getRegionId() {
        return regionId;
    }
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
    public String getCountryId() {
        return countryId;
    }
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCityTabId() {
        return cityTabId;
    }
    public void setCityTabId(String cityTabId) {
        this.cityTabId = cityTabId;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
        append("cityId", cityId).
        append("regionId", regionId).
        append("countryId", countryId).
        append("name", name).
        append("cityTabId", cityTabId).
        append("url", url).
        toString();
    }
    
}
