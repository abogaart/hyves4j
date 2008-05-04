package com.spikylee.hyves4j.interfaces.countries;

import java.util.List;

import com.spikylee.hyves4j.H4jException;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.interfaces.H4jInterface;
import com.spikylee.hyves4j.method.CountriesGetMethod;
import com.spikylee.hyves4j.model.Country;
import com.spikylee.hyves4j.transport.H4jTransport;

public class H4jCountries extends H4jInterface {

    public H4jCountries(H4jClient client, H4jTransport transport) {
        super(client, transport);
    }

    public Country getCountry(String ids) throws H4jException {
        List<Country> countries = get(ids);
        if (countries == null)
            return null;
        else
            return countries.get(0);
    }

    public List<Country> get(String countryIds) throws H4jException {
        CountriesGetMethod method = new CountriesGetMethod(countryIds);
        transport.executeMethod(method);
        return method.getResult();
    }

}
