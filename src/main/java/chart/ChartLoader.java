package chart;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartLoader implements Initializable {

    private static double daySec = 24.0 * 60 * 60;;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openDataSeries();
        Main.run();
        closeDataSeries();
    }

    // Data
   protected static void addDataA(double t, double x, double y, double z) {
        seriesA.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), x));
        seriesA2.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), y));
        seriesA3.getData().addAll(new XYChart.Data(String.valueOf(t / daySec), z));
    }


    // javaFX
    private static XYChart.Series seriesA;
    private static XYChart.Series seriesA2;
    private static XYChart.Series seriesA3;
    @FXML
    private LineChart<?, ?> LineChartA;


    /**
     * initializes the series receiving the chart coordinates
     */
    private void openDataSeries() {
        seriesA = new XYChart.Series();
        seriesA2 = new XYChart.Series();
        seriesA3 = new XYChart.Series();
    }

    /**
     * closes those series for them to be displayed on the chart
     */
    private void closeDataSeries() {
        LineChartA.getData().addAll(seriesA);
        LineChartA.getData().addAll(seriesA2);
        LineChartA.getData().addAll(seriesA3);
    }

}
