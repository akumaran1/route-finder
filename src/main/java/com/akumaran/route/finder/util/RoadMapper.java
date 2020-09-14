package com.akumaran.route.finder.util;

import com.akumaran.route.finder.model.CityPair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Build a road map from the text file. Ensure names are not case sensitive.
 * The application will fail fast if data file is not readable or invalid
 * White spaces and empty lines ignored
 */
@Component
public class RoadMapper {

    private final Log LOG = LogFactory.getLog(RoadMapper.class);

    private Map<String, CityPair> cityMap = new HashMap<>();

    @Value("${data.file:classpath:city.txt}")
    private String CITIES;

    @Autowired
    private ResourceLoader resourceLoader;


    public Map<String, CityPair> getCityMap() {
        return cityMap;
    }

    @PostConstruct
    private void read() throws IOException {

        LOG.info("Reading data from the file..");

        Resource resource = resourceLoader.getResource(CITIES);

        InputStream is;

        if (!resource.exists()) {
            // file on the filesystem path
            is = new FileInputStream(new File(CITIES));
        } else {
            // file is a classpath resource
            is = resource.getInputStream();
        }

        Scanner scanner = new Scanner(is);

        while (scanner.hasNext()) {

            String line = scanner.nextLine();
            if (StringUtils.isEmpty(line)) continue;

            LOG.info(line);

            String[] split = line.split(",");
            String cityAKey = split[0].trim();
            String cityBKey = split[1].trim();

            if (!cityAKey.equals(cityBKey)) {
                CityPair cityPairOne = cityMap.getOrDefault(cityAKey, CityPair.build(cityAKey));
                CityPair cityPairTwo = cityMap.getOrDefault(cityBKey, CityPair.build(cityBKey));

                cityPairOne.addNearby(cityPairTwo);
                cityPairTwo.addNearby(cityPairOne);

                cityMap.put(cityPairOne.getName(), cityPairOne);
                cityMap.put(cityPairTwo.getName(), cityPairTwo);
            }
        }

        LOG.info("Map: " + cityMap);
    }

    public CityPair getCity(String name) {
        return cityMap.get(name);
    }

}
