package com.viaplay.viaplay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Image implements Serializable {

    private String image;

    @JsonProperty("front")
    private boolean frontCover;

    public Image() {
    }

    public Image(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFrontCover() {
        return frontCover;
    }

    public void setFrontCover(boolean frontCover) {
        this.frontCover = frontCover;
    }
}
