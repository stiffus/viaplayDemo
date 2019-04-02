package com.viaplay.viaplay.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ArtistDescription implements Serializable {

    @JsonProperty("profile")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
