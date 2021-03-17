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
        moon = GravityTest.moon;
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
        seriesEX.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), earth.getPosition().getX() - sun.getPosition().getX()));
        seriesEY.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), earth.getPosition().getY() - sun.getPosition().getY()));
    }

    private static void addSunData() {
        seriesSX.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), sun.getPosition().getX()));
        seriesSY.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), sun.getPosition().getY()));
    }

    //  the coordinates of the moon that are being added are relative to the earth
    private static void addMoonData() {
        seriesMX.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), moon.getPosition().getX() - earth.getPosition().getX()));
        seriesMY.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), moon.getPosition().getY() - earth.getPosition().getY()));
    }

   protected static void addData(double t, double data) {
        seriesEX.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), data));
    }





    // javaFX
    private static XYChart.Series seriesEX;
    private static XYChart.Series seriesEY;
    private static XYChart.Series seriesSX;
    private static XYChart.Series seriesSY;
    private static XYChart.Series seriesMX;
    private static XYChart.Series seriesMY;
    @FXML
    private LineChart<?, ?> LineChartEarthX;
    @FXML
    private LineChart<?, ?> LineChartEarthY;
    @FXML
    private LineChart<?, ?> LineChartSunX;
    @FXML
    private LineChart<?, ?> LineChartSunY;
    @FXML
    private LineChart<?, ?> LineChartMoonX;
    @FXML
    private LineChart<?, ?> LineChartMoonY;

    /**
     * initializes the series receiving the chart coordinates
     */
    private void openDataSeries() {
        seriesEX = new XYChart.Series();
        seriesEY = new XYChart.Series();
        seriesSX = new XYChart.Series();
        seriesSY = new XYChart.Series();
        seriesMX = new XYChart.Series();
        seriesMY = new XYChart.Series();
    }

    /**
     * closes those series for them to be displayed on the chart
     */
    private void closeDataSeries() {
        LineChartEarthX.getData().addAll(seriesEX);
        LineChartEarthY.getData().addAll(seriesEY);
        LineChartSunX.getData().addAll(seriesSX);
        LineChartSunY.getData().addAll(seriesSY);
        LineChartMoonX.getData().addAll(seriesMX);
        LineChartMoonY.getData().addAll(seriesMY);
    }

}
