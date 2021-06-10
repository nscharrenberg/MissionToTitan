package org.um.dke.titan.physics.ode.functions.mathfunctions;

import org.um.dke.titan.interfaces.StateInterface;

import org.um.dke.titan.physics.ode.solvers.ODESolver;
import org.um.dke.titan.physics.ode.solvers.ODESolverR4;

import java.text.DecimalFormat;

public class MainTest {

    public static void main(String[] args) {
        ODETestFunction f = new ODETestFunction();
        ODESolver solver = new ODESolver();
        ODESolverR4 rkSolver = new ODESolverR4();

        double h = 0.1;
        double tf = 100;

        StateInterface[] states = solver.solve(f,new State(0, 0),tf, h);
        StateInterface[] statesRK = rkSolver.solve(f, new State(0,0),tf , h);

        System.out.println(arrayToString(states, h));
        System.out.println(arrayToString(statesRK, h));

        System.out.println("absolute error = " + getAbsError(states, tf));
        System.out.println("absolute rk error = " + getAbsError(statesRK, tf));
    }


    public static double getAbsError(StateInterface[] array, double tf) {
        return Math.abs(((State)array[array.length-1]).getPosition() - tf*tf);
    }

    public static String arrayToString(StateInterface[] array, double h) {
        String string = "";

        DecimalFormat df = new DecimalFormat("#.0");

        for(int i = 0; i < array.length; i=i+10) {
            string += "[Euler]  " + "[" + df.format(i*h)+"] : [" + ((State)array[i]).getPosition() + "]\n";
        }

        return string;
    }
}
