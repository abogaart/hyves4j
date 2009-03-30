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

package com.spikylee.hyves4j;

import java.io.IOException;

import com.spikylee.hyves4j.client.H4jClient;

public abstract class AbstractAnonymousHyves4jTest extends AbstractHyves4jTest {
    protected Hyves4j h4j = null;

    @Override
    public void setUp() {
        super.setUp();

        H4jClient client;
        try {
            client = H4jClientFactory.createAnonymousClient("hyves", consumerPropertiesURL);
            h4j = new Hyves4j(client);
        } catch (IOException e) {
            throw new IllegalStateException("Couln't setup h4j", e);
        }
    }
}
