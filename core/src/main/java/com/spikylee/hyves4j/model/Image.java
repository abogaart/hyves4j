package com.spikylee.hyves4j.model;

import java.io.Serializable;

public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int width;
    private int height;
    private String source;
    
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    
}
