package com.navid.goeuro.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Developed by Navid Ghahremani (ghahramani.navid@gmail.com)
 */
public class Geo {

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public Geo setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Geo setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .toString();
    }
}
