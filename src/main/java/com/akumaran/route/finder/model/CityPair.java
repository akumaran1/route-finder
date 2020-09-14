package com.akumaran.route.finder.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class represents a CityPair along with the connected neighbouring cities
 * CityPair names are not case sensitive in computations
 */
public class CityPair {
    private String name;

    private Set<CityPair> nearby = new HashSet<>();

    private CityPair(String name) {
        if(name!= null)
        this.name = name.trim();
    }

    public static CityPair build(String name) {
        return new CityPair(name);
    }

    @Override
    public String toString() {

        return "CityPair{" +
                "name='" + name + "'" +
                ", nearby='" + prettyPrint() +
                "'}";
    }

    public String prettyPrint() {
        return nearby
                .stream()
                .map(CityPair::getName)
                .collect(Collectors.joining(","));
    }

    public String getName() {
        return name;
    }

    public CityPair addNearby(CityPair city) {
        nearby.add(city);
        return this;
    }

    public Set<CityPair> getNearby() {
        return nearby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityPair)) return false;
        CityPair city = (CityPair) o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
