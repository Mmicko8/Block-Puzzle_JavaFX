package woodenBlockPuzzle.view.statistics;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;

/**
 * In deze klasse wordt het scherm met de grafiek van de highscores opgesteld
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class StatisticsView extends BorderPane {
    CategoryAxis xAxis;
    NumberAxis yAxis;
    BarChart<String, Number> barchart;

    public StatisticsView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    /**
     * Initialiseert alle nodes.
     */
    private void initialiseNodes() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        barchart = new BarChart<>(xAxis, yAxis);
    }

    /**
     * In deze methode wordt de layout van de nodes toegewezen
     */
    private void layoutNodes() {
        barchart.setTitle("Player Scores");
        xAxis.setLabel("Players");
        yAxis.setLabel("High-Score");
        barchart.setLegendVisible(false);
        this.setCenter(barchart);
    }

    /**
     * Tekent de grafiek van de high-scores.
     *
     * @param serie de data van high-scores
     */
    void drawChart(XYChart.Series<String, Number> serie) {
        barchart.getData().add(serie);
    }
}
