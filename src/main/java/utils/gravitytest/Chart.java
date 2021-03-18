package utils.gravitytest;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class Chart implements Initializable {

    private static double daySec = 24.0 * 60 * 60;;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GravityTest.initSystem();
        openDataSeries();
        GravityTest.run();
        closeDataSeries();
    }

    // Data
   protected static void addDataA(double t, double x, double y, double z) {
        seriesA.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
        seriesA2.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), y));
        seriesA3.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), z));
    }

    protected static void addDataB(double t,  double x, double y, double z) {
        seriesB.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
        seriesB2.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), y));
        seriesB3.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), z));
    }

    protected static void addDataC(double t,  double x, double y, double z) {
        seriesC.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
        seriesC2.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), y));
        seriesC3.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), z));
    }

    protected static void addDataD(double t,  double x, double y, double z) {
        seriesD.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
        seriesD2.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), y));
        seriesD3.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), z));
    }

    protected static void addDataE(double t,  double x, double y, double z) {
        seriesE.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
        seriesE2.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), y));
        seriesE3.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), z));
    }

    protected static void addDataF(double t,  double x) {
        seriesF.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
     //   seriesF2.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), y));
      //  seriesF3.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), z));
    }

    // javaFX
    private static XYChart.Series seriesA;
    private static XYChart.Series seriesA2;
    private static XYChart.Series seriesA3;
    private static XYChart.Series seriesB;
    private static XYChart.Series seriesB2;
    private static XYChart.Series seriesB3;
    private static XYChart.Series seriesC;
    private static XYChart.Series seriesC2;
    private static XYChart.Series seriesC3;
    private static XYChart.Series seriesD;
    private static XYChart.Series seriesD2;
    private static XYChart.Series seriesD3;
    private static XYChart.Series seriesE;
    private static XYChart.Series seriesE2;
    private static XYChart.Series seriesE3;
    private static XYChart.Series seriesF;
    private static XYChart.Series seriesF2;
    private static XYChart.Series seriesF3;
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
        seriesA2 = new XYChart.Series();
        seriesB2 = new XYChart.Series();
        seriesC2 = new XYChart.Series();
        seriesD2 = new XYChart.Series();
        seriesE2 = new XYChart.Series();
        seriesF2 = new XYChart.Series();
        seriesA3 = new XYChart.Series();
        seriesB3 = new XYChart.Series();
        seriesC3 = new XYChart.Series();
        seriesD3 = new XYChart.Series();
        seriesE3 = new XYChart.Series();
        seriesF3 = new XYChart.Series();
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
        LineChartA.getData().addAll(seriesA2);
        LineChartB.getData().addAll(seriesB2);
        LineChartC.getData().addAll(seriesC2);
        LineChartD.getData().addAll(seriesD2);
        LineChartE.getData().addAll(seriesE2);
        LineChartF.getData().addAll(seriesF2);
        LineChartA.getData().addAll(seriesA3);
        LineChartB.getData().addAll(seriesB3);
        LineChartC.getData().addAll(seriesC3);
        LineChartD.getData().addAll(seriesD3);
        LineChartE.getData().addAll(seriesE3);
        LineChartF.getData().addAll(seriesF3);
    }

}
