package woodenBlockPuzzle;
//import woodenBlockPuzzle.view.game.BoardPresenter;
import javafx.scene.control.Alert;
import woodenBlockPuzzle.model.Spel;
import woodenBlockPuzzle.view.login.LoginPresenter;
import woodenBlockPuzzle.view.login.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static woodenBlockPuzzle.view.game.GameMainPresenter.LOGIN_STAGE_HEIGHT;
import static woodenBlockPuzzle.view.game.GameMainPresenter.LOGIN_STAGE_WIDTH;


/**
 * Deze klasse start de applicatie op (met het loginscherm)
 * @author Michel Matthe
 * @version 1.1
 */
public class Main extends Application {

    /**
     * Deze methode start de applicatie op met een begin-stage
     */
    @Override
    public void start(Stage primaryStage){

        Spel model = new Spel();
        try {
            model.start();
        }
        catch (IOException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading data from file");
            alert.setContentText(exc.getMessage());
            alert.showAndWait();
        }
        LoginView view = new LoginView();
        LoginPresenter presenter = new LoginPresenter(model, view);

        primaryStage.setScene(new Scene(view));
        primaryStage.getScene().getStylesheets().add("/css/basis.css");
        primaryStage.setHeight(LOGIN_STAGE_HEIGHT);
        primaryStage.setWidth(LOGIN_STAGE_WIDTH);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login Page");
        presenter.addWindowEventHandlers();
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


}
