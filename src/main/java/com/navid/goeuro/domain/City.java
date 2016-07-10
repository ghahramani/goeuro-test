package com.navid.goeuro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Developed by Navid Ghahremani (ghahramani.navid@gmail.com)
 */

public class City {
    @JsonProperty(value = "_id")
    private Long id;

    private String name;

    private String type;

    @JsonProperty(value = "geo_position")
    private Geo geoPosition;

    public Long getId() {
        return id;
    }

    public City setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public City setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public City setType(String type) {
        this.type = type;
        return this;
    }

    public Geo getGeoPosition() {
        return geoPosition;
    }

    public City setGeoPosition(Geo geoPosition) {
        this.geoPosition = geoPosition;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("type", type)
                .append("geoPosition", geoPosition)
                .toString();
    }
}
