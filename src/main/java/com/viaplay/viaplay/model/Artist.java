package com.viaplay.viaplay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist implements Serializable {

    @JsonProperty("id")
    private String artistId;
    @JsonProperty("name")
    private String artistName;

    @JsonProperty("relations")
    private List<Relation> relations;

    @JsonProperty("release-groups")
    private List<ReleaseGroup> releaseGroups;

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String id) {
        this.artistId = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String name) {
        this.artistName = name;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public List<ReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }
}
