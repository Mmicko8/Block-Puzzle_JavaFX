package woodenBlockPuzzle.view.settings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import woodenBlockPuzzle.model.Spel;

/**
 * Deze klasse legt de link tussen het model en de SettingsView en handelt de events van deze view ook af
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class SettingsPresenter {
    private final Spel model;
    private final SettingsView view;



    public SettingsPresenter(Spel model, SettingsView view) {
        this.model = model;
        this.view = view;
        this.addEventHandlers();
    }


    /**
     * Deze methode handelt de events van de view af
     */
    private void addEventHandlers() {
        view.getRbBlack().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.setFillColor(Color.BLACK);
            }
        });

        view.getRbBlue().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.setFillColor(Color.STEELBLUE);
            }
        });

        view.getRbRed().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.setFillColor(Color.DARKRED);
            }
        });

        view.getRbGreen().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.setFillColor(Color.DARKOLIVEGREEN);
            }
        });
    }
}
