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

import com.spikylee.hyves4j.client.H4jClient;

public class Hyves4jTest extends AbstractHyves4jTest {
	
	public void testPublicHyves4j () {
		Hyves4j h4j = null;
		H4jClient client = Hyves4j.createAnonymousClient("hyves", consumerPropertiesURL);
		h4j = new Hyves4j(client);
		assertNotNull(h4j.getCities());
		assertNotNull(h4j.getConsole());
		assertNotNull(h4j.getUsers());
	}
	
}
