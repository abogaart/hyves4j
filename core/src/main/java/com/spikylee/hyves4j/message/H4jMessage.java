package com.spikylee.hyves4j.message;

import java.util.ArrayList;
import java.util.List;

import net.oauth.OAuthMessage;
import net.oauth.OAuth.Parameter;

import com.spikylee.hyves4j.Hyves4j;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.method.H4jMethod;

public class H4jMessage{
    private OAuthMessage message;
    private List<Parameter> parameters;
    private H4jMethod<?> method;

    public H4jMessage(H4jMethod<?> method, H4jClient client) {
        this.method = method;
        parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("ha_fancylayout", Boolean.toString(client.getConfig().isFancyLayout())));
        parameters.add(new Parameter("ha_format", method.getResponseFormat()));
        parameters.add(new Parameter("ha_method", method.getName()));
        parameters.add(new Parameter("ha_version", Hyves4j.HYVES_API_VERSION));
        if(!"".equals(method.getResponseFields())) {
            parameters.add(new Parameter("ha_responsefields", method.getResponseFields()));
        }
        for (Parameter parameter : method.getParameters()) {
            this.parameters.add(parameter);
        }
        message = new OAuthMessage("get", client.getAccessor().requestToken, parameters);
        
    }

    public OAuthMessage getMessage() {
        return message;
    }

    public H4jMethod<?> getMethod() {
        return method;
    }

}
