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
