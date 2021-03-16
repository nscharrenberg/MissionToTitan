package utils.gravitytest;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import domain.Planet;

import java.net.URL;
import java.util.ResourceBundle;

public class Chart implements Initializable {

    public static Planet earth;
    public static Planet moon;
    public static Planet sun;
    private static double t;
    private static double daySec;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getPlanets();
        openDataSeries();
        GravityTest.simulate();
        closeDataSeries();
    }

    /**
     * gets the planets from the solarSystem
     */
    private void getPlanets() {
        GravityTest.initSystem();
        earth = GravityTest.earth;
        sun = GravityTest.sun;
        daySec = 24.0 * 60 * 60;
    }

    /**
     *  adds the coordinates of the sun, moon and earth into datapoints
     *  and adds them to each series.
     */
    public static void addDataPoints() {
        t = GravityTest.t;
        addEarthData();
        addSunData();
        addMoonData();
    }

    // the coordinates of the earth that are being added are relative to the earth
    private static void addEarthData() {
        seriesA.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), earth.getPosition().getX() - sun.getPosition().getX()));
        seriesB.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), earth.getPosition().getY() - sun.getPosition().getY()));
    }

    private static void addSunData() {
        seriesC.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), sun.getPosition().getX()));
        seriesD.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), sun.getPosition().getY()));
    }

    //  the coordinates of the moon that are being added are relative to the earth
    private static void addMoonData() {
        seriesE.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), moon.getPosition().getX() - earth.getPosition().getX()));
        seriesF.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), moon.getPosition().getY() - earth.getPosition().getY()));
    }

   protected static void addDataA(double t, double data) {
        seriesA.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), data));
    }

    protected static void addDataB(double t, double data) {
        seriesB.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), data));
    }

    protected static void addDataC(double t, double data) {
        seriesC.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), data));
    }

    protected static void addDataD(double t, double data) {
        seriesD.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), data));
    }

    protected static void addDataE(double t, double data) {
        seriesE.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), data));
    }

    protected static void addDataF(double t, double data) {
        seriesF.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), data));
    }





    // javaFX
    private static XYChart.Series seriesA;
    private static XYChart.Series seriesB;
    private static XYChart.Series seriesC;
    private static XYChart.Series seriesD;
    private static XYChart.Series seriesE;
    private static XYChart.Series seriesF;
    @FXML
    private LineChart<?, ?> LineChartA;
    @FXML
    private LineChart<?, ?> LineChartB;
    @FXML
    private LineChart<?, ?> LineChartC;
    @FXML
    private LineChart<?, ?> LineChartD;
    @FXML
    private LineChart<?, ?> LineChartE;
    @FXML
    private LineChart<?, ?> LineChartF;

    /**
     * initializes the series receiving the chart coordinates
     */
    private void openDataSeries() {
        seriesA = new XYChart.Series();
        seriesB = new XYChart.Series();
        seriesC = new XYChart.Series();
        seriesD = new XYChart.Series();
        seriesE = new XYChart.Series();
        seriesF = new XYChart.Series();
    }

    /**
     * closes those series for them to be displayed on the chart
     */
    private void closeDataSeries() {
        LineChartA.getData().addAll(seriesA);
        LineChartB.getData().addAll(seriesB);
        LineChartC.getData().addAll(seriesC);
        LineChartD.getData().addAll(seriesD);
        LineChartE.getData().addAll(seriesE);
        LineChartF.getData().addAll(seriesF);
    }

}
