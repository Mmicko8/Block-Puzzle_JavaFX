package woodenBlockPuzzle.view.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * In deze klasse wordt de view die de scores en spelergegevens bevat opgesteld
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class ScoreView extends GridPane {

    ToggleButton btnMute;

    Label lblHighScore;
    Label lblCurrentScore;
    Label lblCurrentPlayer;

    TextField tfHighScore;
    TextField tfCurrentScore;
    TextField tfCurrentPlayer;


    public ScoreView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    /**
     * Initialiseert alle nodes.
     */
    private void initialiseNodes() {
        btnMute = new ToggleButton("MUTE");
        btnMute.setSelected(false);

        lblHighScore = new Label("High-Score");
        lblCurrentScore = new Label("Current Score");
        lblCurrentPlayer = new Label("Current Player");

        tfHighScore = new TextField();
        tfCurrentScore = new TextField();
        tfCurrentPlayer = new TextField();

        tfHighScore.setDisable(true);
        tfCurrentPlayer.setDisable(true);
        tfCurrentScore.setDisable(true);
    }

    /**
     * In deze methode wordt de layout van de nodes bepaald
     */
    private void layoutNodes() {
        this.add(btnMute,1,0);
        this.add(lblHighScore, 0, 1);
        this.add(tfHighScore, 1, 1);
        this.add(lblCurrentScore, 0, 2);
        this.add(tfCurrentScore, 1, 2);
        this.add(lblCurrentPlayer, 0, 3);
        this.add(tfCurrentPlayer, 1, 3);


        btnMute.setPrefSize(150, 20);

        ColumnConstraints column1 = new ColumnConstraints(200);
        ColumnConstraints column2 = new ColumnConstraints(150);
        this.getColumnConstraints().addAll(column1, column2);

//        this.setGridLinesVisible(true);
        this.setPadding(new Insets(20));
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);

    }

    ToggleButton getBtnMute() {
        return btnMute;
    }

    void setCurrentScore(int score){
        tfCurrentScore.setText(String.valueOf(score));
    }

    void setHighScore(int score){
        tfHighScore.setText(String.valueOf(score));
    }

    void setCurrentPlayer(String player){
        tfCurrentPlayer.setText(player);
    }
}
