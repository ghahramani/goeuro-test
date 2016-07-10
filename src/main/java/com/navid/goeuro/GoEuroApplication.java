package com.navid.goeuro;

import com.navid.goeuro.config.ExitException;
import com.navid.goeuro.config.GoEuroConfig;
import com.navid.goeuro.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@SpringBootApplication
@EnableConfigurationProperties(GoEuroConfig.class)
public class GoEuroApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(GoEuroApplication.class);

    private CityService service;

    public static void main(String[] args) {
        new SpringApplicationBuilder(GoEuroApplication.class)
                .web(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Inject
    public void setService(CityService service) {
        this.service = service;
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            logger.error("You should enter the city name");
            return;
        }

        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new ExitException();
        }

        String city = args[0];
        String fileName = null;
        try {
            fileName = service.saveCities(city);
            logger.info("The file is ready with name: {}.csv", fileName);
        } catch (Exception e) {
            logger.error("Unable to get cities: {}", e.getMessage());
            System.exit(1);
        }
    }
}
