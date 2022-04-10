package woodenBlockPuzzle.view.gameOver;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * In deze klasse wordt het game over scherm opgesteld
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class GameOverView extends VBox {
    private Label lblGameOver;
    private Label lblBetterLuckNextTime;
    private Button btnExitGame;
    private Button btnBackToLogin;
    private Button btnPlayAgain;

    public GameOverView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    /**
     * Initialiseert alle nodes.
     */
    private void initialiseNodes (){
        lblGameOver = new Label("Game Over!");
        lblGameOver.setId("gameover");
        lblBetterLuckNextTime = new Label("Better luck next time...");
        lblBetterLuckNextTime.setId("betterlucknexttime");
        btnPlayAgain = new Button("Play again");
        btnBackToLogin = new Button("Back to login");
        btnExitGame = new Button("Exit game");
    }

    /**
     * In deze methode wordt de layout van de nodes toegewezen
     */
    private void layoutNodes (){
        getChildren().addAll(lblGameOver,lblBetterLuckNextTime,btnPlayAgain,btnBackToLogin,btnExitGame);
        setSpacing(20);
        setAlignment(Pos.CENTER);
    }

    Button getBtnExitGame() {
        return btnExitGame;
    }

    Button getBtnBackToLogin (){
        return btnBackToLogin;
    }

    Button getBtnPlayAgain () {
        return btnPlayAgain;
    }
}
