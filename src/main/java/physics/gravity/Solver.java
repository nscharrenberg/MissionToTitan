package physics.gravity;

import interfaces.DifferentialEquationFunctionInterface;
import interfaces.SolverInterface;
import interfaces.StateInterface;

public class Solver implements SolverInterface {
    @Override
    public StateInterface[] solve(DifferentialEquationFunctionInterface f, StateInterface y0, double[] ts) {
        return new StateInterface[0];
    }

    @Override
    public StateInterface[] solve(DifferentialEquationFunctionInterface f, StateInterface y0, double tf, double h) {
        return new StateInterface[0];
    }

    @Override
    public StateInterface step(DifferentialEquationFunctionInterface f, double t, StateInterface y, double h) {
        return null;
    }
}
