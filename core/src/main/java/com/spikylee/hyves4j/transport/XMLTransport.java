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

package com.spikylee.hyves4j.transport;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.method.H4jMethod;
import com.spikylee.hyves4j.response.H4jResponse;
import com.spikylee.hyves4j.response.XMLResponse;

public class XMLTransport extends H4jTransport {

    public XMLTransport(H4jClient client) {
        super(client);
    }

    @Override
    protected H4jResponse createResponse(H4jMethod<?> method) {
        return new XMLResponse(method);
    }
    
    @Override
    protected String getResponseFormat() {
        return XMLResponse.RESPONSE_FORMAT;
    }
}
