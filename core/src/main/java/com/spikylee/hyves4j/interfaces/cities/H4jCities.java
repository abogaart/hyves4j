package com.spikylee.hyves4j.interfaces.cities;

import java.util.List;

import com.spikylee.hyves4j.H4jException;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.interfaces.H4jInterface;
import com.spikylee.hyves4j.model.City;
import com.spikylee.hyves4j.transport.H4jTransport;

public class H4jCities extends H4jInterface {

    public H4jCities(H4jClient client, H4jTransport transport) {
        super(client, transport);
    }
    
    public City get(String cityIds) throws H4jException {
        CitiesGetMethod method = new CitiesGetMethod(cityIds);
        transport.sendMethod(method);
        List<City> cities = method.getResult();
        return cities.get(0);
    }

}
