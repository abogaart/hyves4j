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
