package woodenBlockPuzzle.view.statistics;

import javafx.scene.chart.XYChart;
import woodenBlockPuzzle.model.Spel;
import woodenBlockPuzzle.model.Speler;

import java.util.ArrayList;

/**
 * Deze klasse legt de link tussen het model en de StatisticsView en handelt ook de events van de view af
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class StatisticsPresenter {
    private final Spel model;
    private final StatisticsView view;

    public StatisticsPresenter(Spel model, StatisticsView view) {
        this.model = model;
        this.view = view;
        this.updateView();
    }

    /**
     * Update de view.
     */
    private void updateView() {
        ArrayList<Speler> spelersLijst = model.getSpelersLijst();
        XYChart.Series<String,Number> serie = new XYChart.Series<>();
        String naam;
        int score;

        for (Speler speler : spelersLijst) {
            naam = speler.getGebruikersnaam();
            score = speler.getHighscore();
            serie.getData().add(new XYChart.Data<>(naam, score));
        }
        view.drawChart(serie);
    }
}
