package com.akumaran.route.finder;

import com.akumaran.route.finder.model.CityPair;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CityPairTest {
    

    @Test
    public void buildWithNeighbours() {
        CityPair city = CityPair.build("Boston");
        city.addNearby(CityPair.build("Newark"))
                .addNearby(CityPair.build("New York"));

        Set<CityPair> nearby = city.getNearby();
        assertEquals(2, nearby.size());
        assertTrue(nearby.contains(CityPair.build("Newark")));
    }


    @Test
    public void addNearby() {
        CityPair city = CityPair.build("Boston");
        city.addNearby(CityPair.build("Newark"))
                .addNearby(CityPair.build("New York"));

        assertEquals(2, city.getNearby().size());
    }

    @Test
    public void addNearbyDuplicates() {
        CityPair city = CityPair.build("Boston");
        city.addNearby(CityPair.build("Newark"))
                .addNearby(CityPair.build("Newark"))
                .addNearby(CityPair.build("  Newark"))
                .addNearby(CityPair.build("  Newark "));

        assertEquals(1, city.getNearby().size());
    }


}