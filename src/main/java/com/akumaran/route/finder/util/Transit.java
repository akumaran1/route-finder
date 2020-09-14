package com.akumaran.route.finder.util;

import com.akumaran.route.finder.model.CityPair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class Transit {

    private static final Log LOG = LogFactory.getLog(Transit.class);

    private Transit() { }

    /**
     * Find if destination city is reachable from origin. Will visit all the cities
     * on the connectedCities list which is built by collecting all the neighbours of a visited place
     * @param origin the origin
     * @param destination the destination
     * @return true if cities are connected
     */
    public static boolean traverse(CityPair origin, CityPair destination) {

        if(origin == null || destination == null) return false;

        LOG.info("Origin: " + origin.getName() + ", destination: " + destination.getName());

        if (origin.equals(destination)) return true;

        if (origin.getNearby().contains(destination)) return true;

        /*
         * The origin city was already visited since we have started from it
         */
        Set<CityPair> visitedCity = new HashSet<>(Collections.singleton(origin));

        /*
         * Put all the neighboring cities into a connectedCities list
         */
        Deque<CityPair> connectedCities = new ArrayDeque<>(origin.getNearby());


        while (!connectedCities.isEmpty()) {


            CityPair city = connectedCities.getLast();

            if (city.equals(destination)) return true;

            // remove the city from the connectedCities list

            // first time visit?
            if (!visitedCity.contains(city)) {

                visitedCity.add(city);

                // add neighbours to the connectedCities list and
                // remove already visited cities from the list
                connectedCities.addAll(city.getNearby());
                connectedCities.removeAll(visitedCity);

                LOG.info("Visiting: ["
                        + city.getName()
                        + "] , neighbours: ["
                        + (city.prettyPrint())
                        + "], connectedCities: ["
                        + connectedCities.toString()
                        + "]");
            } else {
                // the city has been visited, so remove it from the connectedCities list
                connectedCities.removeAll(Collections.singleton(city));
            }
        }

        return false;
    }
}

