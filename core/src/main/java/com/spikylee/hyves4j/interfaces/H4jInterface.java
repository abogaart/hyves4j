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

package com.spikylee.hyves4j.interfaces;

import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.transport.H4jTransport;

public class H4jInterface {
    
	protected H4jClient client;
    protected H4jTransport transport;
    
    public H4jInterface(H4jClient client, H4jTransport transport) {
        this.client = client;
        this.transport = transport;
    }

}
