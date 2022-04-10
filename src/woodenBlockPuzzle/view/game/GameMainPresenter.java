package woodenBlockPuzzle.view.game;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import woodenBlockPuzzle.model.Puzzelstuk;
import woodenBlockPuzzle.model.Spel;
import woodenBlockPuzzle.model.Spelbord;
import woodenBlockPuzzle.view.gameOver.GameOverPresenter;
import woodenBlockPuzzle.view.gameOver.GameOverView;
import woodenBlockPuzzle.view.login.LoginPresenter;
import woodenBlockPuzzle.view.login.LoginView;
import woodenBlockPuzzle.view.rulesAbout.RulesAndAboutView;
import woodenBlockPuzzle.view.statistics.StatisticsPresenter;
import woodenBlockPuzzle.view.statistics.StatisticsView;
import woodenBlockPuzzle.view.settings.SettingsPresenter;
import woodenBlockPuzzle.view.settings.SettingsView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * De klasse legt de link tussen de het hoofdspeelscherm (bestaande uit de {@code GameMainView},
 * {@code PiecesView} en de {@code ScoreView}) en de modelklasse. In de presenter worden alle eventhandlers uitgewerkt.
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class GameMainPresenter {

    private final Spel model;
    private final GameMainView view;
    private final Media breakSound;
    private final Media dropSound;
    private int selectedPieceNr;

    public static final int SETTINGS_STAGE_WIDTH = 300;
    public static final int SETTINGS_STAGE_HEIGHT = 200;
    public static final int LOGIN_STAGE_WIDTH = 450;
    public static final int LOGIN_STAGE_HEIGHT = 350;

    public static final int RULES_STAGE_WIDTH = 700;
    public static final int RULES_STAGE_HEIGHT = 600;

    public GameMainPresenter(Spel model, GameMainView view) {
        this.model = model;
        this.view = view;
        this.addEventHandlers();
        this.updateView();

        File soundFile = new File("resources/break_sound.mp3");
        breakSound = new Media(soundFile.toURI().toString());
        soundFile = new File("resources/drop_sound2.mp3");
        dropSound = new Media(soundFile.toURI().toString());
    }

    /**
     * Update het hoofdspeelscherm (de {@code GameMainView}).
     */
    private void updateView(){
        // draw board
        int[][] board = model.getBoard();
        fillBoard(board);

        //draw pieces
        Color pieceColor = model.getFillColor();
        ArrayList<Puzzelstuk> pieces = model.getTePlaatsenPuzzelstukken();
        int SPACING = model.getSpacing();
        for (int i = 0; i < pieces.size(); i++) {
            view.getPiecesView().drawPiece(pieces.get(i).getCoordinaten(), i+1, pieceColor, SPACING);
        }
        // clear empty pieces
        for (int i = 3; pieces.size() < i; i--) {
            view.getPiecesView().clearPiece(i);
        }
        // show high-score, current score and current player
        view.getScoreView().setCurrentScore(model.getHuidigeScore());
        view.getScoreView().setHighScore(model.getHighScore());
        view.getScoreView().setCurrentPlayer(model.getUserName());
    }

    /**
     * Handelt alle menu- en dragevents af.
     */
    private void addEventHandlers() {
        addDragEventHandlers();
        addMenuEventHandlers();
    }

    /**
     * Handelt alle dragevents af.
     */
    private void addDragEventHandlers(){
        //1) voeg OnDragDetected EventHandler toe aan de source (piece canvas):
        EventHandler<MouseEvent> dragDetected = event -> {
            Canvas source = (Canvas) event.getSource();
            //Het canvas wordt in het DragBoard gestopt tijdens de transfer
            Dragboard dragboard = source.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            selectedPieceNr = view.getPiecesView().getPieceNr(source); // saves which piece got dragged

            // zet canvas om in image en zet het in de dragboard
            WritableImage wim = new WritableImage(180, 180);
            source.snapshot(null, wim);
            content.putImage(wim);
            dragboard.setContent(content);
            event.consume();
        };
        makePiecesDraggable(dragDetected);

        //2) voeg OnDragOver EventHandler toe aan het target (canvas die het spelbord voorstelt)
        //   dit zorgt ervoor dat het target de puzzelstuk kan accepteren.
        Canvas target = view.getCanvas();
        target.setOnDragOver(event -> {
            if (event.getGestureSource() != target && event.getDragboard().hasImage()
                    && model.kanGeplaatstworden(event.getX(), event.getY(), selectedPieceNr)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        //3) voeg OnDragDropped EventHandler toe aan target
        //   deze wordt uitgevoerd zodra de drop gebeurt.
        //   We spelen een geluidje en geven door dat de drop gelukt is
        target.setOnDragDropped(event -> {
            boolean rowsOrColsRemoved; // geeft aan of er rijen of kolommen verwijderd waren bij het plaatsen van het puzzelstuk
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                success = true;
                rowsOrColsRemoved = model.doeZet(event.getX(), event.getY(), selectedPieceNr);
                // play sound if not muted
                if (!view.getScoreView().getBtnMute().isSelected()) {
                    Media sound;
                    sound = (rowsOrColsRemoved ? breakSound : dropSound);
                    new MediaPlayer(sound).play();
                }
                if (! model.isZetMogelijk()){
                    showGameOver();
                }
            }
            updateView();
            event.setDropCompleted(success);
            event.consume();
        });

        //4) voeg OnDragDone EventHandlers toe aan sources
        //   we verwijderen de image van de imageview
        EventHandler<DragEvent> dragDone = event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                int size = model.getTePlaatsenPuzzelstukken().size();

                // maakt alle 3 canvassen (terug) draggable
                if (size == 3) {
                    makePiecesDraggable(dragDetected);
                }
                // maakt de lege canvassen NIET draggable
                else {
                    for (int i = 3; i != size; i--){
                        Canvas canvas = view.getPiecesView().canvasNrToCanvas(i);
                        canvas.setOnDragDetected(null);
                    }
                }
            }
            event.consume();
        };
        view.getPiecesView().getCvPiece1().setOnDragDone(dragDone);
        view.getPiecesView().getCvPiece2().setOnDragDone(dragDone);
        view.getPiecesView().getCvPiece3().setOnDragDone(dragDone);
    }

    /**
     * Toont het gameoverscherm (de {@code GameOverView}).
     */
    private void showGameOver() {
        GameOverView gameOverView = new GameOverView();
        new GameOverPresenter(model, gameOverView);
        view.getScene().setRoot(gameOverView);
        Stage stage = (Stage) gameOverView.getScene().getWindow();
        stage.getScene().getStylesheets().add("/css/basis.css");
        stage.getScene().getStylesheets().add("/css/gameoverscherm.css");
        stage.setTitle("Game over");
        stage.setWidth(500);
        stage.setHeight(400);
    }

    /**
     * Handelt de MenuEvents af.
     */
    private void addMenuEventHandlers(){
        view.getMiExit().setOnAction(event -> {
            try {
                model.clearGame(); // saves the data + clears it afterwards
            }
            catch (IOException exc){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error loading data from file");
                alert.setContentText(exc.getMessage());
                alert.showAndWait();
            }
            Platform.exit();
        });

        view.getMiSettings().setOnAction(event -> {
            SettingsView settingsView = new SettingsView();
            settingsView.getStylesheets().add("/css/basis.css");
            new SettingsPresenter(model, settingsView);
            Stage settingsStage = new Stage();
            settingsStage.initOwner(view.getScene().getWindow());
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(settingsView);
            settingsStage.setScene(scene);
            settingsStage.setX(view.getScene().getWindow().getX());
            settingsStage.setY(view.getScene().getWindow().getY() + 100);
            settingsStage.setWidth(SETTINGS_STAGE_WIDTH);
            settingsStage.setHeight(SETTINGS_STAGE_HEIGHT);
            settingsStage.setResizable(false);
            settingsStage.setTitle("Settings");
            settingsStage.showAndWait();
            updateView(); // update board with selected color
        });

        view.getMiHighScores().setOnAction(actionEvent -> {
            StatisticsView statisticsView = new StatisticsView();
            new StatisticsPresenter(model, statisticsView);
            Stage stage = new Stage();
            stage.initOwner(view.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(statisticsView);
            stage.setScene(scene);
            stage.setTitle("Player Stats");
            stage.setResizable(false);

            stage.showAndWait();
        });

        view.getMiBackToLogin().setOnAction(actionEvent -> {
            try {
                model.clearGame();
                model.start();
            }
            catch (IOException exc){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error loading data from file");
                alert.setContentText(exc.getMessage());
                alert.showAndWait();
            }
            LoginView loginView = new LoginView();
            new LoginPresenter(model, loginView);
            view.getScene().setRoot(loginView);
            loginView.getScene().getWindow().sizeToScene();
            Stage stage = (Stage) loginView.getScene().getWindow();
            stage.setHeight(LOGIN_STAGE_HEIGHT);
            stage.setWidth(LOGIN_STAGE_WIDTH);
            stage.setTitle("Login page");
        });

        view.getMiAboutRules().setOnAction(actionEvent -> {
            RulesAndAboutView rulesAndAboutView = new RulesAndAboutView();
            Stage rulesAndAboutStage = new Stage();
            rulesAndAboutStage.initOwner(view.getScene().getWindow());
            rulesAndAboutStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(rulesAndAboutView);
            rulesAndAboutStage.setScene(scene);
            rulesAndAboutStage.getScene().getStylesheets().add("/css/basis.css");
            rulesAndAboutStage.getScene().getStylesheets().add("/css/aboutscherm.css");
            rulesAndAboutStage.setTitle("About info and instructions");
            rulesAndAboutStage.setWidth(RULES_STAGE_WIDTH);
            rulesAndAboutStage.setHeight(RULES_STAGE_HEIGHT);
            rulesAndAboutStage.setResizable(false);
            rulesAndAboutStage.showAndWait();
        });
    }

    /** Kleurt de gevulde vakjes op het spelbord met de {@code fillColor} kleur
     *
     * @param board het spelbord
     */
    void fillBoard(int[][] board) {
        int boardSize = model.getDimensie();
        Color fillColor = model.getFillColor();
        int VOL = Spelbord.getVol();
        int SPACING = model.getSpacing();
        for (int row = 0; row < boardSize; row++){
            for (int col = 0; col < boardSize; col++){
                view.fillSquare(row, col, board[row][col] == VOL, fillColor, SPACING);
            }
        }
    }

    /** Maakt de puzzelstukken sleepbaar
     *
     * @param dragDetected Eventhandler die een Puzzelstuk draggable maakt
     */
    void makePiecesDraggable(EventHandler<MouseEvent> dragDetected){
        view.getPiecesView().getCvPiece1().setOnDragDetected(dragDetected);
        view.getPiecesView().getCvPiece2().setOnDragDetected(dragDetected);
        view.getPiecesView().getCvPiece3().setOnDragDetected(dragDetected);
    }

}
