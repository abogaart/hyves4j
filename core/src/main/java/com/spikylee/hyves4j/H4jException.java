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

public class H4jException extends Exception {
  
    private static final long serialVersionUID = 7958091410349084831L;
    
    private String errorCode;
    private String errorMessage;

    public H4jException(String errorCode, String errorMessage) {
        super(errorCode + ": " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public H4jException(String string, Exception e) {
        super(string, e);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
