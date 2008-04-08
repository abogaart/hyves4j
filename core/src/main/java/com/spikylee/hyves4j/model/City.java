package com.spikylee.hyves4j.model;

import java.io.Serializable;

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
    
}