package com.navid.goeuro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.text.MessageFormat;

/**
 * Developed by Navid Ghahremani (ghahramani.navid@gmail.com)
 */

@ConfigurationProperties(value = "goeuro", ignoreUnknownFields = false)
public class GoEuroConfig {
    private final Endpoint endpoint = new Endpoint();

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public class Endpoint {
        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityFull(String name) {
            return MessageFormat.format(city, name);
        }
    }
}
