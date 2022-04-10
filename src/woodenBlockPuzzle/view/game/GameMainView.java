package woodenBlockPuzzle.view.game;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * In deze klasse wordt het hoofdspeelscherm opgesteld.
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class GameMainView extends BorderPane {
    private ScoreView scoreView;
    private PiecesView piecesView;

    private Canvas canvas;

    private MenuItem miExit;
    private MenuItem miHighScores;
    private MenuItem miSettings;
    private MenuItem miBackToLogin;
    private MenuItem miAboutRules;

    public GameMainView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    /**
     * Initialiseert alle nodes.
     */
    private void initialiseNodes() {
        scoreView = new ScoreView();
        piecesView = new PiecesView();
        int MEASUREMENT = 600;
        canvas = new Canvas(MEASUREMENT, MEASUREMENT);

        miExit = new MenuItem("Exit");
        miHighScores = new MenuItem("High-scores");
        miSettings = new MenuItem("Settings");
        miBackToLogin = new MenuItem("Back to login");
        miAboutRules = new MenuItem("View info and instructions");
    }

    /**
     * In deze methode krijgen de verschillende nodes hun layout.
     */
    private void layoutNodes() {
        this.setRight(scoreView);
        this.setBottom(piecesView);
        this.setLeft(canvas);

        this.setPadding(new Insets(0, 20, 20, 20));

        Menu menuGame = new Menu("Game",null, miHighScores, miSettings, miExit);
        Menu menuLogin = new Menu("Log In",null,miBackToLogin);
        Menu menuAboutRules = new Menu("About info/how to play",null,miAboutRules);
        MenuBar menuBar = new MenuBar(menuLogin,menuGame, menuAboutRules);
        setTop(menuBar);
    }

    /**
     * Kleurt op het spelbord het vakje op rij {@code row} en kolom {@code col} met kleur {@code color} indien deze gevuld is.
     *
     * @param row de rij index van het vakje
     * @param col de kolom index van het vakje
     * @param filled toont aan of het vakje wel of niet gevuld is
     * @param color de kleur waarmee het vakje gekleurt wordt
     * @param SPACING de breedte/ hoogte van een vakje op het spelrooster
     */
    void fillSquare(int row, int col, boolean filled, Color color, int SPACING) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
//        double SPACING = 60;
        double x = SPACING * row; // x-coordinate of upper-left corner of vakje
        double y = SPACING * col; // y-coordinate of upper-left corner of vakje
        gc.setFill(filled ? color : Color.WHITE); // chosen color if filled, white if empty
        gc.fillRect(y, x, SPACING, SPACING); // fill vakje

        // draw border to separate the individual vakjes
        gc.setStroke(filled ? Color.WHITE : Color.BLACK); // black if filled, white if empty
        gc.strokeRect(y, x, SPACING, SPACING);
    }


    PiecesView getPiecesView(){
        return piecesView;
    }

    Canvas getCanvas(){
        return canvas;
    }

    ScoreView getScoreView(){
        return scoreView;
    }

    MenuItem getMiExit() {
        return miExit;
    }

    MenuItem getMiHighScores() {
        return miHighScores;
    }

    MenuItem getMiSettings() {
        return miSettings;
    }

    MenuItem getMiBackToLogin(){ return miBackToLogin;}

    MenuItem getMiAboutRules(){
        return miAboutRules;
    }
}
