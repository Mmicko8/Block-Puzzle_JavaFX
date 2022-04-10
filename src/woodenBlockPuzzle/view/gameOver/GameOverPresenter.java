package woodenBlockPuzzle.view.gameOver;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import woodenBlockPuzzle.model.Spel;
import woodenBlockPuzzle.view.game.GameMainPresenter;
import woodenBlockPuzzle.view.game.GameMainView;
import woodenBlockPuzzle.view.login.LoginPresenter;
import woodenBlockPuzzle.view.login.LoginView;

import java.io.IOException;

import static woodenBlockPuzzle.view.game.GameMainPresenter.LOGIN_STAGE_HEIGHT;
import static woodenBlockPuzzle.view.game.GameMainPresenter.LOGIN_STAGE_WIDTH;

/**
 * Deze klasse legt de link tussen het model en de {@code GameOverView} en handelt alle events voor het Game Over scherm af
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class GameOverPresenter {
    private final Spel model;
    private final GameOverView view;

    public GameOverPresenter(Spel model, GameOverView view) {
        this.model = model;
        this.view = view;
        this.addEventHandlers();
        this.updateView();
    }

    /**
     * Handelt alle non-window events af.
     */
    private void addEventHandlers(){
        view.getBtnExitGame().setOnAction(event -> {
            try {
                model.clearGame();
            } catch (IOException exc) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error loading data from file");
                alert.setContentText(exc.getMessage());
                alert.showAndWait();
            }
            Platform.exit();
        });

        view.getBtnBackToLogin().setOnAction(event -> {
            restartModel();
            LoginView loginView = new LoginView();
            new LoginPresenter(model, loginView);
            view.getScene().setRoot(loginView);
            loginView.getScene().getWindow().sizeToScene();
            Stage stage = (Stage) loginView.getScene().getWindow();
            stage.setHeight(LOGIN_STAGE_HEIGHT);
            stage.setWidth(LOGIN_STAGE_WIDTH);
            stage.setTitle("Login page");
        });

        view.getBtnPlayAgain().setOnAction(event -> {
            restartModel();
            GameMainView gameView = new GameMainView();
            view.getScene().setRoot(gameView);
            gameView.getScene().getWindow().sizeToScene();
            new GameMainPresenter(model, gameView);
            Stage stage = (Stage) gameView.getScene().getWindow();
            stage.setTitle("Wooden Block Puzzle: Game screen");
        });
    }

    /**
     * Reset het spel.
     */
    private void restartModel() {
        try {
            model.clearGame();
            model.start();
        }
        catch (IOException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading data from file");
            alert.setContentText(exc.getMessage());
            alert.showAndWait();
        }
    }

    private void updateView(){

    }
}
