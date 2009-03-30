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

import net.oauth.OAuthAccessor;

//TODO: write AuthAccessor test
public class AccessorTest extends AbstractHyves4jTest {

    public void testPublicAccessor() {

        OAuthAccessor accessor;
        try {
            accessor = H4jClientFactory.createAnonymousClient("hyves", consumerPropertiesURL).getAccessor();
            assertNotNull(accessor);
            assertEquals(accessor.accessToken, null);
            assertEquals(accessor.requestToken, null);
            assertEquals(accessor.tokenSecret, null);
        } catch (IOException e) {
            throw new IllegalStateException("Failed", e);
        }

    }

}
