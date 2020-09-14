package com.akumaran.route.finder;

import com.akumaran.route.finder.model.CityPair;
import com.akumaran.route.finder.util.RoadMapper;
import com.akumaran.route.finder.util.Transit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class TransitApplicationTests {

    @Autowired
    RoadMapper roadMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void fileLoad() {
        Assert.assertFalse("File load failed", roadMapper.getCityMap().isEmpty());
    }

    @Test
    public void sameCity() {
        CityPair city = CityPair.build("A");
        Assert.assertTrue(Transit.traverse(city, city));
    }

    @Test
    public void neighbours() {
        CityPair cityA = roadMapper.getCity("Boston");
        CityPair cityB = roadMapper.getCity("Newark");

        Assert.assertNotNull("Invalid test data. CityPair not found: Boston", cityA);
        Assert.assertNotNull("Invalid test data. CityPair not found: Newark", cityB);

        Assert.assertTrue(Transit.traverse(cityA, cityB));
    }

    @Test
    public void distantConnected() {
        CityPair cityA = roadMapper.getCity("Boston");
        CityPair cityB = roadMapper.getCity("Philadelphia");

        Assert.assertNotNull("Invalid test data. CityPair not found: Philadelphia", cityA);
        Assert.assertNotNull("Invalid test data. CityPair not found: Boston", cityB);

        Assert.assertTrue(Transit.traverse(cityA, cityB));
    }

    @Test
    public void restConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "Boston");
        params.put("destination", "Newark");

        String body = restTemplate
                .getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("true", body);
    }

    @Test
    public void restNotConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "Boston");
        params.put("destination", "Hartford");

        String body = restTemplate
                .getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("false", body);
    }

    public static class CityTest {

        @Test
        public void buildWithNeighbours() {
            CityPair city = CityPair.build("Boston");
            city.addNearby(CityPair.build("New York"))
                .addNearby(CityPair.build("Newark"));

            Set<CityPair> nearby = city.getNearby();
            Assert.assertEquals(2, nearby.size());
            Assert.assertTrue(nearby.contains(CityPair.build("Newark")));
        }


        @Test
        public void addNearby() {
            CityPair city = CityPair.build("Boston");
            city.addNearby(CityPair.build("Newark"))
                .addNearby(CityPair.build("New York"));

            Assert.assertEquals(2, city.getNearby().size());
        }

        @Test
        public void addNearbyDuplicates() {
            CityPair city = CityPair.build("Boston");
            city.addNearby(CityPair.build("Newark"))
                .addNearby(CityPair.build("Newark"));
            Assert.assertEquals(1, city.getNearby().size());
        }
    }
}
