package woodenBlockPuzzle.view.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import woodenBlockPuzzle.model.Spel;
import woodenBlockPuzzle.view.game.GameMainPresenter;
import woodenBlockPuzzle.view.game.GameMainView;

import java.io.IOException;

/**
 * Deze klasse legt de link tussen de model klassen en de LoginView en handelt de events van deze view af
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class LoginPresenter {
    private final Spel model;
    private final LoginView view;

    public LoginPresenter(Spel model, LoginView view) {
        this.model = model;
        this.view = view;
        this.addEventHandlers();
    }

    /**
     * Handelt alle non-window events af.
     */
    private void addEventHandlers() {
        view.getBtnLogin().setOnAction(event -> {
            try {
                String username = view.getTfUsername().getText();
                String password = view.getTfPassword().getText();
                model.logIn(username, password);

                GameMainView gameView = new GameMainView();
                new GameMainPresenter(model, gameView);
                view.getScene().setRoot(gameView);
                gameView.getScene().getWindow().sizeToScene();
                Stage stage = (Stage) gameView.getScene().getWindow();
                stage.setTitle("Wooden Block Puzzle: Game screen");

            }
            catch (IllegalArgumentException exc){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unable to login:");
                alert.setContentText(exc.getMessage());
                alert.showAndWait();
            }
        });

        view.getBtnSignUp().setOnAction(event -> {
            try {
                String username = view.getTfUsername().getText();
                String password = view.getTfPassword().getText();
                model.signUp(username, password);

                GameMainView gameView = new GameMainView();
                new GameMainPresenter(model, gameView);
                view.getScene().setRoot(gameView);
                gameView.getScene().getWindow().sizeToScene();

                Stage stage = (Stage) gameView.getScene().getWindow();
                stage.setTitle("Wooden Block Puzzle: Game screen");
            }
            catch (IllegalArgumentException exc){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unable to sign up:");
                alert.setContentText(exc.getMessage());
                alert.showAndWait();
            }
        });
    }

    /**
     * Handelt alle Windowevents af.
     */
    public void addWindowEventHandlers () {
        view.getScene().getWindow().setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Are you sure you want to end the game?");
//                alert.setContentText("Are you sure?");
            alert.setTitle("Attention!");
            alert.getButtonTypes().clear();
            ButtonType no = new ButtonType("No");
            ButtonType yes = new ButtonType("Yes");
            alert.getButtonTypes().addAll(no, yes);
            alert.showAndWait();
            if (alert.getResult() == null || alert.getResult().equals(no)) {
                event.consume();
            }
            try {
                model.clearGame(); // saving before game shuts down
            }
            catch (IOException exc) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error loading data from file");
                alert2.setContentText(exc.getMessage());
                alert2.showAndWait();
            }
        });
    }

}
