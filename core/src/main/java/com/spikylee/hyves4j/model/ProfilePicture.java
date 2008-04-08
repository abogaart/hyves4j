package com.spikylee.hyves4j.model;

import java.io.Serializable;

public class ProfilePicture implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String mediaId;
    private String userId;
    private String title;
    private String description;
    private String mediaType;
    
    private Image image;
    private Image fullscreenImage;
    private Image smallIcon;
    private Image mediumIcon;
    private Image largeIcon;
    
    public String getMediaId() {
        return mediaId;
    }
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getMediaType() {
        return mediaType;
    }
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public Image getFullscreenImage() {
        return fullscreenImage;
    }
    public void setFullscreenImage(Image fullscreenImage) {
        this.fullscreenImage = fullscreenImage;
    }
    public Image getSmallIcon() {
        return smallIcon;
    }
    public void setSmallIcon(Image smallIcon) {
        this.smallIcon = smallIcon;
    }
    public Image getMediumIcon() {
        return mediumIcon;
    }
    public void setMediumIcon(Image mediumIcon) {
        this.mediumIcon = mediumIcon;
    }
    public Image getLargeIcon() {
        return largeIcon;
    }
    public void setLargeIcon(Image largeIcon) {
        this.largeIcon = largeIcon;
    }

}
