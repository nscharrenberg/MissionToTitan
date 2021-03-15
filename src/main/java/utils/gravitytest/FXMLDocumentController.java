package utils.gravitytest;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import domain.MovingObject;
import domain.Planet;
import domain.Vector3D;
import interfaces.Vector3dInterface;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    // javaFx
    @FXML
    private LineChart<?, ?> LineChartEarthX;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private LineChart<?, ?> LineChartEarthY;
    @FXML
    private CategoryAxis x1;
    @FXML
    private NumberAxis y1;
    @FXML
    private LineChart<?, ?> LineChartSunX;
    @FXML
    private CategoryAxis x2;
    @FXML
    private NumberAxis y2;
    @FXML
    private LineChart<?, ?> LineChartSunY;
    @FXML
    private CategoryAxis x21;
    @FXML
    private NumberAxis y21;
    @FXML
    private LineChart<?, ?> LineChartMoonX;
    @FXML
    private CategoryAxis x3;
    @FXML
    private NumberAxis y3;
    @FXML
    private LineChart<?, ?> LineChartMoonY;
    @FXML
    private CategoryAxis x31;
    @FXML
    private NumberAxis y31;
    private static XYChart.Series seriesEX;
    private static XYChart.Series seriesEY;
    private static XYChart.Series seriesSX;
    private static XYChart.Series seriesSY;
    private static XYChart.Series seriesMX;
    private static XYChart.Series seriesMY;

    public static final double G = 6.67408e-11; // Gravitational Constant
    public static double dt; // delta time
    public static double t;
    public static Planet earth;
    public static Planet moon;
    public static Planet sun;
    public static double daySec = 60*24*60; // total seconds in a day

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seriesEX = new XYChart.Series();
        seriesEY = new XYChart.Series();
        seriesSX = new XYChart.Series();
        seriesSY = new XYChart.Series();
        seriesMX = new XYChart.Series();
        seriesMY = new XYChart.Series();
        simulate();
        LineChartEarthX.getData().addAll(seriesEX);
        LineChartEarthY.getData().addAll(seriesEY);
        LineChartSunX.getData().addAll(seriesSX);
        LineChartSunY.getData().addAll(seriesSY);
        LineChartMoonX.getData().addAll(seriesMX);
        LineChartMoonY.getData().addAll(seriesMY);
    }

    public void simulate() {
        earth = new Planet(5.97219e24, new Vector3D(-1.471922101663588e11,-2.860995816266412e10,8.278183193596080e6), new Vector3D(5.427193405797901e3,-2.931056622265021e4,6.575428158157592e-1), "earth");
        sun = new Planet(1.988500e30, new Vector3D(6.806783239281648e8,1.080005533878725e9,6.564012751690170e6), new Vector3D(1.420511669610689e1, -4.954714716629277e0,3.994237625449041e1),"sun");
        moon = new Planet(7.349e22, new Vector3D(-1.472343904597218e11, -2.822578361503422e10, 1.052790970065631e7), new Vector3D(4.433121605215677e3, -2.948453614110320e4, 8.896598225322805e1), "moon");

        t = 0; // start time
        dt = 5*daySec;

        System.out.println("starting");
        // gameLoop
        while(t <100*365*daySec) {
            newtonsLaw(earth, sun);
            newtonsLaw(moon,sun);

            seriesEX.getData().addAll(new XYChart.Data(String.valueOf(t / (24.0 * 60 * 60)), earth.getPosition().getX()));
            seriesEY.getData().addAll(new XYChart.Data(String.valueOf(t / (24.0 * 60 * 60)), earth.getPosition().getY()));

            seriesSX.getData().addAll(new XYChart.Data(String.valueOf(t / (24.0 * 60 * 60)), sun.getPosition().getX()));
            seriesSY.getData().addAll(new XYChart.Data(String.valueOf(t / (24.0 * 60 * 60)), sun.getPosition().getY()));

            seriesMX.getData().addAll(new XYChart.Data(String.valueOf(t / (24.0 * 60 * 60)), moon.getPosition().getX()));
            seriesMY.getData().addAll(new XYChart.Data(String.valueOf(t / (24.0 * 60 * 60)), moon.getPosition().getY()));
            System.out.println("done adding t: " + t + ". progress = " + t/(365*24*60*60));

            t += dt;
        }
        System.out.println("done");
    }

    // calculates the force on all x,y,z axes of a at its current position
    public void newtonsLaw(MovingObject a, MovingObject b) {

        // calculating gravitational force on a
        Vector3D r = (Vector3D) b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * a.getMass() * b.getMass(); // G * Mi * Mj
        double modr3 = Math.pow(r.norm(),3); // ||xi - xj||^3
        Vector3dInterface force = r.mul(gravConst/modr3); // full formula together

        a.setForce(force);
        b.setForce(force.mul(-1)); // force on b is the inverted force on a

        updateAcceleration(a,b);
        updateVelocities(a,b);
        updatePositions(a,b);

    }

    /**
     * updates the current acceleration of a and b determined by the gravitational force
     * each MovingObject is experiencing
     */
    public static void updateAcceleration (MovingObject a, MovingObject b) {
        a.setAcceleration(a.getForce().mul(1/a.getMass()));
        b.setAcceleration(b.getForce().mul(1/b.getMass()));
    }

    /**
     * updates the current Velocities of a and b determined by it's current
     * acceleration
     */
    public static void updateVelocities(MovingObject a, MovingObject b) {
        a.setVelocity(a.getVelocity().add(a.getAcceleration().mul(dt)));
        b.setVelocity(b.getVelocity().add(b.getAcceleration().mul(dt)));
    }

    /**
     * updates the current position of a and b determined by it's
     * current velocity
     */
    public static void updatePositions(MovingObject a, MovingObject b) {
        a.setPosition(a.getPosition().add(a.getVelocity().mul(dt)));
        b.setPosition(b.getPosition().add(b.getVelocity().mul(dt)));
    }
}
