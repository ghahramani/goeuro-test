package com.navid.goeuro.service;

import com.navid.goeuro.GoEuroApplication;
import com.navid.goeuro.config.GoEuroConfig;
import com.navid.goeuro.domain.City;
import com.navid.goeuro.domain.Geo;
import com.navid.goeuro.service.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Developed by Navid Ghahremani (ghahramani.navid@gmail.com)
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GoEuroApplication.class)
@WebAppConfiguration
@IntegrationTest
public class CityServiceTest {

    private final String cityName = "Berlin";
    private final long ID = 1L;
    private CityService service;
    private RestTemplate template;
    private GoEuroConfig config;

    @Inject
    public void setService(CityService service) {
        this.service = service;
    }

    @Inject
    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    @Inject
    public void setConfig(GoEuroConfig config) {
        this.config = config;
    }

    @Before
    public void setUp() throws IOException {
        service.setRestTemplate(template);
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(template);

        City city = new City();
        city.setId(ID);
        city.setName(cityName);
        city.setType("location");
        city.setGeoPosition(new Geo().setLatitude(52.52437).setLongitude(13.41053));

        Set<City> cities = Collections.singleton(city);
        mockServer.expect(requestTo(config.getEndpoint().getCityFull(cityName)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                JsonUtil.convertObjectToJsonBytes(cities),
                                MediaType.APPLICATION_JSON
                        )
                );
    }

    @Test
    public void getCities() throws Exception {
        Set<City> cities = service.getCities(cityName);

        if (cities.isEmpty()) {
            throw new Exception("The retrieved cities should have at least one item");
        }

        City next = cities.iterator().next();

        if (!next.getId().equals(ID)) {
            throw new Exception("The retrieved city id is wrong");
        }
    }

}