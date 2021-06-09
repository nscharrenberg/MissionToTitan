package org.um.dke.titan.utils;

import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.physics.ode.functions.planetfunction.PlanetRate;
import org.um.dke.titan.physics.ode.functions.planetfunction.PlanetState;
import org.um.dke.titan.physics.ode.functions.planetfunction.SystemState;

import java.util.HashMap;
import java.util.Map;

public class EmptyMapProvider {

    public static Map<String, PlanetState> getStateMap() {
        Map<String, PlanetState> map = new HashMap<>();

//        map.put("Sun", new Planet("Sun", 1.988500e30f, 696340e3f));
//        map.put("Mercury", new Planet("Mercury", 3.302e23f, 2439.7f));
//        map.put("Venus", new Planet("Venus", 4.8685e24f, 6051.8f));
//        map.put("Earth", new Planet("Earth", 5.97219e24f, 6371e3f,
//                (Map<String, Moon>) new HashMap<String, Moon>().put("Moon", new Moon("Moon", 7.349e22f, 1737.4e3f))));
//        map.put("Mars", new Planet("Mars", 6.4171e23f, 3389.5f));
//        map.put("Jupiter", new Planet("Jupiter", 1.89813e27f, 69911e3f));
//        map.put("Saturn", new Planet("Saturn", 5.6834e26f, 58232e3f,
//                (Map<String, Moon>) new HashMap<String, Moon>().put("Titan", new Moon("Titan", 1.34553e23f, 2575.5e3f))));
//        map.put("Uranus", new Planet("Uranus", 8.6813e25f, 25362f));
//        map.put("Neptune", new Planet("Neptune", 1.02413e26f, 24622f));

        map.put("Sun", new PlanetState());
        map.put("Mercury", new PlanetState());
        map.put("Venus", new PlanetState());
        map.put("Earth", new PlanetState());
        map.put("Moon", new PlanetState());
        map.put("Mars", new PlanetState());
        map.put("Jupiter", new PlanetState());
        map.put("Saturn", new PlanetState());
        map.put("Uranus", new PlanetState());
        map.put("Neptune", new PlanetState());

        return map;
    }

    public static Map<String, PlanetRate> getRateMap() {
        Map<String, PlanetRate> map = new HashMap();

        map.put("Sun",     new PlanetRate());
        map.put("Mercury", new PlanetRate());
        map.put("Venus",   new PlanetRate());
        map.put("Earth",   new PlanetRate());
        map.put("Moon",    new PlanetRate());
        map.put("Mars",    new PlanetRate());
        map.put("Jupiter", new PlanetRate());
        map.put("Saturn",  new PlanetRate());
        map.put("Uranus",  new PlanetRate());
        map.put("Neptune", new PlanetRate());

        return map;
    }

}
