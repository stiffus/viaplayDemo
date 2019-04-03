package com.viaplay.viaplay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class ReleaseGroup implements Serializable {

        @JsonProperty("id")
        private String albumId;

        @JsonProperty("title")
        private String albumTitle;

        @JsonProperty("primary-type")
        private String type;


        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getAlbumTitle() {
            return albumTitle;
        }

        public void setAlbumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }