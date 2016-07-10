package com.navid.goeuro.service;

import com.navid.goeuro.config.GoEuroConfig;
import com.navid.goeuro.domain.City;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Developed by Navid Ghahremani (ghahramani.navid@gmail.com)
 */
@Service
public class CityService {

    private static final String NEW_LINE_SEPARATOR = "\r\n";
    private static final Object[] FILE_HEADER = {"_id", "name", "type", "latitude", "longitude"};

    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    private RestTemplate restTemplate;
    private GoEuroConfig config;

    @Inject
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Inject
    public void setConfig(GoEuroConfig config) {
        this.config = config;
    }

    public String saveCities(String name) throws IOException {
        Set<City> cities = getCities(name);

        if (cities.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "No cities");
        }

        String fileName = UUID.randomUUID().toString();
        writeToCsv(fileName, cities);
        return fileName;
    }

    public Set<City> getCities(String name) {
        String url = config.getEndpoint().getCityFull(name);
        ParameterizedTypeReference<Set<City>> reference = new ParameterizedTypeReference<Set<City>>() {
        };

        ResponseEntity<Set<City>> request = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                reference
        );

        if (!request.getStatusCode().is2xxSuccessful()) {
            logger.error(
                    "Error occurred during getting cities: Status: {}, Headers: {}",
                    request.getStatusCode(),
                    request.getHeaders()
            );

            throw new RestClientException(request.getStatusCode().getReasonPhrase());
        }

        return request.getBody();
    }

    public void writeToCsv(String fileName, Set<City> cities) throws IOException {

        final FileWriter writer = new FileWriter(fileName + ".csv");

        //Create the CSVFormat object with "\r\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.RFC4180.withRecordSeparator(NEW_LINE_SEPARATOR);

        final CSVPrinter printer = new CSVPrinter(writer, csvFileFormat);

        //Create CSV file header
        printer.printRecord(FILE_HEADER);

        //Write a new city object list to the CSV file
        for (City city : cities) {
            List<Object> row = new ArrayList<>();
            row.add(city.getId());
            row.add(city.getName());
            row.add(city.getType());
            row.add(city.getGeoPosition().getLatitude());
            row.add(city.getGeoPosition().getLongitude());
            printer.printRecord(row);
        }

        writer.flush();
        writer.close();
        printer.close();

        logger.debug("Saving to CSV file is finished");
    }
}
