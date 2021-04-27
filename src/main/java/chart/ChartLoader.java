package chart;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartLoader implements Initializable {

    private static double daySec = 60*60*24;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openDataSeries();
        Main.run();
        closeDataSeries();
    }

    // Data
    protected static void addDataA(double t, double x) {
        seriesA.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
    }

    protected static void addDataA(double t, double x, double y) {
        seriesA.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
        seriesA2.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), y));
    }

    protected static void addDataA(double t, double x, double y, double z, double a, double b, double c) {
        seriesA.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
        seriesA2.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), y));
        seriesA3.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), z));
        seriesA4.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), a));
        seriesA5.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), b));
        seriesA6.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), c));
    }


    // javaFX
    private static XYChart.Series seriesA;
    private static XYChart.Series seriesA2;
    private static XYChart.Series seriesA3;
    private static XYChart.Series seriesA4;
    private static XYChart.Series seriesA5;
    private static XYChart.Series seriesA6;
    @FXML
    private LineChart<?, ?> LineChartA;


    /**
     * initializes the series receiving the chart coordinates
     */
     private void openDataSeries() {
         seriesA = new XYChart.Series();
         seriesA2 = new XYChart.Series();
         seriesA3 = new XYChart.Series();
         seriesA4 = new XYChart.Series();
         seriesA5 = new XYChart.Series();
         seriesA6 = new XYChart.Series();
     }

    /**
     * closes those series for them to be displayed on the chart
     */
    private void closeDataSeries() {
        LineChartA.getData().addAll(seriesA);
        LineChartA.getData().addAll(seriesA2);
        LineChartA.getData().addAll(seriesA3);
        LineChartA.getData().addAll(seriesA4);
        LineChartA.getData().addAll(seriesA5);
        LineChartA.getData().addAll(seriesA6);
    }

}
