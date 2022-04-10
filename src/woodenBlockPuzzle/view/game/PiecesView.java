package woodenBlockPuzzle.view.game;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * In deze klasse wordt de view met de drie verschillende canvassen voor de puzzelstukken opgesteld
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class PiecesView extends HBox {
    final double MIDDLE_COORDINATE = 60; // x en y coordinate of the left upper corner of the center vakje
//    final int SPACING = 60; // width and height of a vakje
    final int CANVAS_SIZE = 180;
    Canvas cvPiece1;
    Canvas cvPiece2;
    Canvas cvPiece3;

    public PiecesView(){
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Initialiseert alle nodes.
     */
    private void initialiseNodes() {
        cvPiece1 = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        cvPiece2 = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        cvPiece3 = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
    }

    /**
     * In deze methode krijgen de nodes hun layout toegekend
     */
    private void layoutNodes() {
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.getChildren().addAll(cvPiece1, cvPiece2, cvPiece3);
    }

    /**
     * Tekent en kleurt puzzelstuk nummer {@code canvasNr} met kleur {@code color}.
     *
     * @param coordinates de coordinaten van het puzzelstuk op de canvas
     * @param canvasNr de nummer van het puzzelstuk/ canvas in kwestie
     * @param color de kleur waarmee het puzzelstuk gekleurt wordt
     * @param SPACING de breedte/ hoogte van een vakje op het spelrooster
     */
    void drawPiece (int[][] coordinates, int canvasNr, Color color, int SPACING) {
        Canvas canvas = canvasNrToCanvas(canvasNr);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE); // clears the the board
        for (int[] coordinate : coordinates) {
            double x = MIDDLE_COORDINATE + SPACING * coordinate[0]; // x-coordinate
            double y = MIDDLE_COORDINATE + SPACING * coordinate[1]; // y-coordinate

            gc.setFill(color);
            gc.fillRect(y, x, SPACING, SPACING); // vakje inkleuren

            gc.setStroke(Color.WHITE);
            gc.strokeRect(y, x, SPACING, SPACING); // witte rand rond vakje

        }
    }

    /**
     * Returns het Puzzelstuk/ {@code Canvas} object dat overeen komt met het meegegeven puzzelstuk/ {@code Canvas} nummer.
     *
     * @param canvasNr nummer van het puzzelstuk/ {@code Canvas} object
     * @return het overeenkomende Puzzelstuk/ {@code Canvas} object
     */
    Canvas canvasNrToCanvas(int canvasNr) {
        Canvas canvas;
        switch (canvasNr){
            case 1:
                canvas = cvPiece1; break;
            case 2:
                canvas = cvPiece2; break;
            default:
                canvas = cvPiece3; break;
        }
        return canvas;
    }

    /**
     * Verwijderd het puzzelstuk dat overeen komt met het meegegeven puzzelstuk/ {@code Canvas} nummer.
     *
     * @param canvasNr de nummer van het puzzelstuk/ {@code Canvas}
     */
    void clearPiece(int canvasNr) {
        GraphicsContext gc = canvasNrToCanvas(canvasNr).getGraphicsContext2D();
        gc.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
    }

    /**
     * Returns het puzzelstuk/ {@code Canvas} nummer dat overeen komt met het meegegeven puzzelstuk/ {@code Canvas} object.
     *
     * @param canvas het puzzelstuk/ {@code Canvas} object
     * @return nummer van het puzzelstuk/ {@code Canvas} object
     */
    int getPieceNr(Canvas canvas) {
        if (cvPiece1 == canvas) {
            return 1;
        }
        else if (cvPiece2 == canvas) {
            return 2;
        }
        else {
            return 3;
        }
    }

    Canvas getCvPiece1() {
        return cvPiece1;
    }

    Canvas getCvPiece2() {
        return cvPiece2;
    }

    Canvas getCvPiece3() {
        return cvPiece3;
    }
}
