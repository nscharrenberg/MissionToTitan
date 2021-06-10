package org.um.dke.titan.error;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.um.dke.titan.error.game.Game;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.utils.FileImporter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    static HashMap<Integer, Vector3dInterface> earth = null;
    static HashMap<Integer, Vector3dInterface> moon = null;
    static HashMap<Integer, Vector3dInterface> sun = null;
    static HashMap<Integer, Vector3dInterface> titan = null;
    static HashMap<Integer, Vector3dInterface> saturn = null;
    static HashMap<Integer, Vector3dInterface> jupiter = null;


    static ErrorCalc calcEarth;
    static ErrorCalc calcMoon;
    static ErrorCalc calcSun;
    static ErrorCalc calcTitan;
    static ErrorCalc calcSaturn;
    static ErrorCalc calcJupiter;

    static StateInterface[] timeLineArray;

    static double tf = 365 * 24 * 60*60;
    static double dt = 50;

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Mission To  Titan";
        config.resizable = false;
        config.vSyncEnabled = true;
        config.width = 1260;
        config.height = 720;
        config.foregroundFPS = 120;
        new LwjglApplication(new Game(false), config);
    }

    public static void run() {
        System.out.println("******************************** ERROR CALCULATOR********************************\n\n\n\n\n\n");
        importData();

        System.out.println("dt: " + dt);
        euler();
    }

    public static void euler() {
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(FactoryProvider.getSolver(), tf, dt);

        List<Double> list = getAverage();

        double total = 0;

        for (int i = 0; i < list.size(); i++)
            total += list.get(i);

        double average = total/list.size();

        System.out.println("euler | + average error : " + average );
        System.out.println("earth: " + list.get(0));
        System.out.println("sun: " + list.get(1));
        System.out.println("titan: " + list.get(2));
        System.out.println("saturn: " + list.get(3));
        System.out.println("jupiter: " + list.get(4));
        System.out.println("moon: " + list.get(5));
        System.out.println();

    }

    public static void importData() {
        try {
            earth = FileImporter.importHorizon("HorizonData_Earth");
            moon = FileImporter.importHorizon("HorizonData_Moon");
            sun = FileImporter.importHorizon("HorizonData_Sun");
            titan = FileImporter.importHorizon("HorizonData_Titan");
            saturn = FileImporter.importHorizon("HorizonData_Saturn");
            jupiter = FileImporter.importHorizon("HorizonData_Jupiter");
        } catch (ParseException e) { e.printStackTrace(); }
    }

    public static List<Double> getAverage() {
        calcEarth = new ErrorCalc(earth, timeLineArray);
        calcMoon = new ErrorCalc(moon, timeLineArray);
        calcSun = new ErrorCalc(sun, timeLineArray);
        calcTitan = new ErrorCalc(titan, timeLineArray);
        calcSaturn = new ErrorCalc(saturn, timeLineArray);
        calcJupiter = new ErrorCalc(jupiter, timeLineArray);

        List<Double> list = new ArrayList<>();

        list.add(calcEarth.averageError("Earth"));
        list.add(calcSun.averageError("Sun"));
        list.add(calcTitan.averageError("Titan"));
        list.add(calcSaturn.averageError("Saturn"));
        list.add(calcJupiter.averageError("Jupiter"));
        list.add(calcMoon.averageError("Moon"));
        return list;
    }

}