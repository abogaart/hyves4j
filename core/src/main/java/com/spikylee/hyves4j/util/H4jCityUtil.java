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
