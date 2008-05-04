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

package com.spikylee.hyves4j.util;

import org.apache.commons.jxpath.JXPathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.spikylee.hyves4j.model.Image;
import com.spikylee.hyves4j.model.ProfilePicture;

public class H4jPictureUtil {
    
    final static Logger logger = LoggerFactory.getLogger(H4jPictureUtil.class);
    
    public static ProfilePicture createProfilePicture(Node node) {
        JXPathUtil jxpath = new JXPathUtil(node);
        try {
            jxpath.getContext().getValue("/profilepicture");
        } catch(JXPathException e) {
            return null;
        }
        
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setMediaId(jxpath.getStringValue("/profilepicture/mediaid"));
        profilePicture.setUserId(jxpath.getStringValue("/profilepicture/userid"));
        profilePicture.setTitle(jxpath.getStringValue("/profilepicture/title"));
        profilePicture.setDescription(jxpath.getStringValue("/profilepicture/description"));
        profilePicture.setMediaType(jxpath.getStringValue("/profilepicture/mediatype"));
        profilePicture.setImage(getImage(jxpath, "/profilepicture/image"));
        profilePicture.setFullscreenImage(getImage(jxpath, "/profilepicture/image_fullscreen"));
        profilePicture.setSmallIcon(getImage(jxpath, "/profilepicture/icon_small"));
        profilePicture.setMediumIcon(getImage(jxpath, "/profilepicture/icon_medium"));
        profilePicture.setLargeIcon(getImage(jxpath, "/profilepicture/icon_large"));
        
        if(logger.isDebugEnabled()) {
            logger.debug("ProfilePicture created: " + profilePicture);
        }
        
        return profilePicture;
    }
    
    private static Image getImage(JXPathUtil jxpath, String prefix) {
        Image img = new Image();
        img.setWidth(jxpath.getIntValue(prefix + "/width"));
        img.setHeight(jxpath.getIntValue(prefix + "/height"));
        img.setSource(jxpath.getStringValue(prefix + "/src"));
        
        if(logger.isDebugEnabled()) {
            logger.debug("Image with prefix '" + prefix + "' created: " + img);
        }
        return img;
    }

}
