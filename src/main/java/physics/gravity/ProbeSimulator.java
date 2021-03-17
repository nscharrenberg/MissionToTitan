package physics.gravity;

import domain.Vector3D;
import interfaces.ProbeSimulatorInterface;
import interfaces.Vector3dInterface;

import static java.lang.Math.round;

public class ProbeSimulator implements ProbeSimulatorInterface {
    private Vector3dInterface v;
    private Vector3dInterface a;
    private Vector3dInterface p;

    private Vector3dInterface[] traj;

    private double mass;
    private double launch_angle;
    private double latitude;
    private double longitude;
    final private double escape_velocity = 11000;

    public ProbeSimulator(double mass, Vector3dInterface v, Vector3dInterface a, Vector3dInterface p){

    this.v = v;
    this.p = p;
    this.a = a;
    this.mass = mass;

 }
    @Override
    /*
     * Simulate the solar system, including a probe fired from Earth at 00:00h on 1 April 2020.
     *
     * @param   p0      the starting position of the probe, relative to the earth's position.
     * @param   v0      the starting velocity of the probe, relative to the earth's velocity.
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time.
     * @return  an array of size ts.length giving the position of the probe at each time stated,
     *          taken relative to the Solar System barycentre.
     */
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {

        return new Vector3dInterface[0];
    }

    @Override
    /*
     * Simulate the solar system with steps of an equal size.
     * The final step may have a smaller size, if the step-size does not exactly divide the solution time range.
     *
     * @param   tf      the final time of the evolution.
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 giving the position of the probe at each time stated,
     *          taken relative to the Solar System barycentre
     */
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        traj = new Vector3dInterface[(int) (round(tf/h)+1)];
        // TODO: call simulation of the solar system passing a,v,p of the probe + time t0
        return new Vector3dInterface[0];
    }

    public Vector3dInterface target(double t ) {
        double titan_diameter = 5149400;
    return new Vector3D(1,3,5); //placeholder
    }

    public Vector3dInterface lanchBase(Vector3dInterface p) {

        double radius = 6378000;

        double x = Math.random();
        double y = Math.random();
        double z = Math.random();

        double sum = p.getX()*p.getX() + p.getY()*p.getY() +  p.getZ()*p.getZ();
        double t = Math.sqrt(sum);
        x = x*t; y = y*t; z = y*t;

        if(x == 0 && y == 0 && z == 0){
            while(x == 0 && y == 0 && z == 0) {
                x = Math.random();
                y = Math.random();
                z = Math.random();
            }
        }
        x = x*radius;
        y= y*radius;
        z = z * radius;

        Vector3D launch_position = new Vector3D(x, y, z);

        return  launch_position;
    }
}
