package org.um.dke.titan.experimental;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
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
        run();
    }

    private static void run() {

        System.out.println("running");

        HashMap<Integer, Vector3dInterface> data = null;
        try { data = FileImporter.importHorizon(); } catch (ParseException e) { e.printStackTrace(); }

        ErrorCalc calc = new ErrorCalc(data,FactoryProvider.getSolarSystemRepository().getTimeLineArray());
    }

}