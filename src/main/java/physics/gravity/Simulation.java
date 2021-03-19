package physics.gravity;

import domain.Vector3D;
import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import utils.WriteToFile;

import java.util.ArrayList;

public class Simulation {

    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static double dt = 0.01*daySec;
    private static StateInterface[][] timeLineArray;
	public static void main(String[] args) {
	     Simulation sim = new Simulation();   
	}
	//NON-STATIC
	private State minDistState;
	private Vector3dInterface initialVelocity, initialPosition, distanceVector, titanGoalPosition, errorVector;
	private double normErrVector, normDistanceVector;
	private Vector3dInterface unitErrVector, unitDistanceVector, unitSum;
	private Vector3dInterface sum;
	
    public Simulation() {
    	minDistState = null;
    	run();
    }
    
    
    private void run() {
    	FactoryProvider.getSolarSystemFactory().init();
        double totalTime = FactoryProvider.getSettingRepository().getYearCount() * FactoryProvider.getSettingRepository().getDayCount() * daySec;

        ODESolver odes;
        ODEFunction odef;


        odes = new ODESolver(FactoryProvider.getSolarSystemFactory());
        odef = new ODEFunction(FactoryProvider.getSolarSystemFactory());
        timeLineArray = odes.getData(odef, totalTime, dt);

        State start = (State) timeLineArray[1][0];
        State titan = (State) timeLineArray[4][0];
        System.out.println(titan.getPosition().sub(start.getPosition()).norm());
        initialPosition = start.getPosition();


        for (int i = 21256; i < 21257; i+=1) {
                State goal = (State) timeLineArray[4][i];

                titanGoalPosition = goal.getPosition();
                initialVelocity = unitVecToGoal(start.getPosition(), goal.getPosition()).mul(58000);
                
                FactoryProvider.getSolarSystemFactory().init(initialVelocity.add(start.getVelocity()));
                if (i == 21296 ) {
                    System.out.println(initialVelocity.add(start.getVelocity()));
                }
                timeLineArray = odes.getData(odef, totalTime, dt);
                printMin();
                System.out.println(".    i: " + i);

        }
        errorVector = titanGoalPosition.sub(minDistState.getPosition());
        normErrVector = errorVector.norm();
        distanceVector = titanGoalPosition.sub(initialPosition);
        normDistanceVector = distanceVector.norm();
        System.out.println("initVelo"+initialVelocity);
        unitDistanceVector = distanceVector.mul(1/normDistanceVector);
        unitErrVector = errorVector.mul(1/normErrVector);
        System.out.println("err"+unitErrVector);
        sum = (Vector3D) unitDistanceVector.mul(normDistanceVector).add(unitErrVector).mul(normErrVector);
        unitSum = sum.mul(1/sum.norm());
        sum = unitSum.mul(58500);
        System.out.println("sum"+sum+ " "+ sum.norm());
        
        for (int i = 0; i < 1000; i+=1) {
            State goal = (State) timeLineArray[4][21256];
            initialVelocity = sum;
            
            FactoryProvider.getSolarSystemFactory().init(initialVelocity.add(start.getVelocity()));
            timeLineArray = odes.getData(odef, totalTime, dt);
            
            printMin();
            errorVector = titanGoalPosition.sub(minDistState.getPosition());
            normErrVector = errorVector.norm();
            unitErrVector = errorVector.mul(1/normErrVector);
            System.out.println("err"+unitErrVector);
            sum = (Vector3D) unitDistanceVector.mul(normDistanceVector).add(unitErrVector).mul(normErrVector);
            unitSum = sum.mul(1/sum.norm());
            sum = unitSum.mul(58500);
            System.out.println("sum"+sum+ " "+sum.norm());
        }
    }

    private static Vector3dInterface unitVecToGoal(Vector3dInterface start, Vector3dInterface goal) {
        Vector3dInterface aim = goal.sub(start); // vector between earth and goal
        return aim.mul(1.0/aim.norm());
    }

    private void printMin() {
        double min = Double.MAX_VALUE;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            if (i * dt / daySec < 300) {
                State probe = (State) timeLineArray[5][i];
                State titan = (State) timeLineArray[4][i];
                if (probe.getPosition().sub(titan.getPosition()).norm() < (69911e3 + 20)) {
                    System.out.println("COLLISION");
                }
                double dist = probe.getPosition().sub(titan.getPosition()).norm() - 2575.5e3;
                if (min > dist) {
                    min = dist;
                    minDistState = probe.clone();
                    if (dist < 3.535715850129074E9){
                        WriteToFile.writeToFile(dist, probe.getVelocity().toString());
                    }
                }
            }
        }
        System.out.println(min);
    }


}

