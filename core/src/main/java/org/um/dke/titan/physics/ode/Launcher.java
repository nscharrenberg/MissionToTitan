package org.um.dke.titan.physics.ode;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.physics.LanderSimulator;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;
import org.um.dke.titan.utils.Window;

public class Launcher {
    public static void main(String[] a){
        // TODO: put in realistic starting parameters
        PlanetState y0 = new PlanetState(new Vector3D(0, 100000, 0), new Vector3D(900, 0, 0));
        double tf = 5000, dt = 0.01;
        LanderSimulator ls = new LanderSimulator(y0, tf, dt);
        double[] ts = ls.getTs();
        PlanetState[] landerArray = ls.getLanderArray();
        double[] y = new double[ts.length];
        double[] x = new double[ts.length];
        for(int i = 0; i < y.length; i++){
            y[i] = landerArray[i].getPosition().getY();
            x[i] = landerArray[i].getPosition().getX();
        }
        System.out.println("READY");
        Window w = new Window(1200, 600, ts, x, y);
    }
}
