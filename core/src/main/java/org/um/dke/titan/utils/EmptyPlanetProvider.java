package org.um.dke.titan.utils;

import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.Planet;

import java.util.HashMap;
import java.util.Map;

public class EmptyPlanetProvider {

    public static Map<String, Planet> get() {
        Map<String, Planet> map = new HashMap();

        map.put("Sun", new Planet("Sun", 1.988500e30f, 696340e3f));
        map.put("Mercury", new Planet("Mercury", 3.302e23f, 2439.7f));
        map.put("Venus", new Planet("Venus", 4.8685e24f, 6051.8f));
        map.put("Earth", new Planet("Earth", 5.97219e24f, 6371e3f,
                (Map<String, Moon>) new HashMap<String, Moon>().put("Moon", new Moon("Moon", 7.349e22f, 1737.4e3f))));
        map.put("Mars", new Planet("Mars", 6.4171e23f, 3389.5f));
        map.put("Jupiter", new Planet("Jupiter", 1.89813e27f, 69911e3f));
        map.put("Saturn", new Planet("Saturn", 5.6834e26f, 58232e3f,
                (Map<String, Moon>) new HashMap<String, Moon>().put("Titan", new Moon("Titan", 1.34553e23f, 2575.5e3f))));
        map.put("Uranus", new Planet("Uranus", 8.6813e25f, 25362f));
        map.put("Neptune", new Planet("Neptune", 1.02413e26f, 24622f));

        return map;
    }

}
