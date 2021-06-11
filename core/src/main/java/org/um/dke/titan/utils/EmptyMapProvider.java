package org.um.dke.titan.utils;

import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetRate;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;

import java.util.HashMap;
import java.util.Map;

public class EmptyMapProvider {

    public static Map<String, PlanetState> getStateMap() {
        Map<String, PlanetState> map = new HashMap<>();

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

    public static Map<String, PlanetState> getLanderStateMap() {
        Map<String, PlanetState> map = new HashMap<>();
        //map.put("Earth", new PlanetState());
        return map;
    }

    public static Map<String, PlanetRate> getRateMap() {
        Map<String, PlanetRate> map = new HashMap();

        map.put("Sun", new PlanetRate());
        map.put("Mercury", new PlanetRate());
        map.put("Venus", new PlanetRate());
        map.put("Earth", new PlanetRate());
        map.put("Moon", new PlanetRate());
        map.put("Mars", new PlanetRate());
        map.put("Jupiter", new PlanetRate());
        map.put("Saturn", new PlanetRate());
        map.put("Uranus", new PlanetRate());
        map.put("Neptune", new PlanetRate());

        return map;
    }

    public static Map<String, PlanetRate> getLanderRateMap() {
        Map<String, PlanetRate> map = new HashMap();

        //map.put("Earth",   new PlanetRate());


        return map;
    }


}
