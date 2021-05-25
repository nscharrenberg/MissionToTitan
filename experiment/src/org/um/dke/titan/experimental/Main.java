package org.um.dke.titan.experimental;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.utils.FileImporter;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Vector;

public class Main {

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
        FactoryProvider.getGameRepository().load();
        StateInterface[][] timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray();
        HashMap<Integer, Vector3dInterface> earth = null;
        HashMap<Integer, Vector3dInterface> moon = null;
        HashMap<Integer, Vector3dInterface> sun = null;
        HashMap<Integer, Vector3dInterface> titan = null;
        HashMap<Integer, Vector3dInterface> saturn = null;
        HashMap<Integer, Vector3dInterface> jupiter = null;

        try {
            earth = FileImporter.importHorizon("HorizonData_Earth");
            moon = FileImporter.importHorizon("HorizonData_Moon");
            sun = FileImporter.importHorizon("HorizonData_Sun");
            titan = FileImporter.importHorizon("HorizonData_Titan");
            saturn = FileImporter.importHorizon("HorizonData_Saturn");
            jupiter = FileImporter.importHorizon("HorizonData_Jupiter");

        } catch (ParseException e) { e.printStackTrace(); }

        System.out.println("******************************** ERROR CALCULATOR********************************\n\n\n\n\n\n");

        ErrorCalc calcEarth = new ErrorCalc(earth, timeLineArray);
        ErrorCalc calcMoon = new ErrorCalc(moon, timeLineArray);
        ErrorCalc calcSun = new ErrorCalc(sun, timeLineArray);
        ErrorCalc calcTitan = new ErrorCalc(titan, timeLineArray);
        ErrorCalc calcSaturn = new ErrorCalc(saturn, timeLineArray);
        ErrorCalc calcJupiter = new ErrorCalc(jupiter, timeLineArray);

        double average =
                calcEarth.averageError(SpaceObjectEnum.EARTH.getId()) +
                calcSun.averageError(SpaceObjectEnum.SUN.getId()) +
                calcTitan.averageError(SpaceObjectEnum.TITAN.getId()) +
                calcSaturn.averageError(SpaceObjectEnum.SATURN.getId()) +
                calcJupiter.averageError(SpaceObjectEnum.JUPITER.getId());
        average = average/5;

        System.out.println("moon error: " + calcMoon.averageError(SpaceObjectEnum.MOON.getId()));

        System.out.println("average error: " + average);

    }

}