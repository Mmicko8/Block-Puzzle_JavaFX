package woodenBlockPuzzle.view.settings;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Deze klasse maakt het instellingen-scherm op
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class SettingsView extends GridPane {

    Label lblPickYourColor;
    Label lblBlack;
    Label lblBlue;
    Label lblRed;
    Label lblGreen;

    ToggleGroup tgColors;

    private RadioButton rbBlack;
    private RadioButton rbBlue;
    private RadioButton rbRed;
    private RadioButton rbGreen;

    public SettingsView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    /**
     * Initialiseert alle nodes.
     */
    private void initialiseNodes() {
        lblPickYourColor = new Label("Pick your color:");
        lblBlack = new Label();
        lblBlack.setId("zwarteKleurLabel");
        lblBlue = new Label();
        lblBlue.setId("blauweKleurLabel");
        lblRed = new Label();
        lblRed.setId("rodeKleurLabel");
        lblGreen = new Label();
        lblGreen.setId("groeneKleurLabel");
        tgColors = new ToggleGroup();
        rbBlack = new RadioButton("Black");
        rbBlue = new RadioButton("Blue");
        rbRed = new RadioButton("Red");
        rbGreen = new RadioButton("Green");
    }

    /**
     * Deze methode geeft de nodes hun layout
     */
    private void layoutNodes() {
        ArrayList<RadioButton> radioButtons = new ArrayList<>(Arrays.asList(rbBlack, rbBlue, rbRed, rbGreen));

        for (RadioButton rb : radioButtons) {
            rb.setToggleGroup(tgColors);
        }

        lblBlack.setPrefSize(30,60);
        lblBlue.setPrefSize(30,60);
        lblRed.setPrefSize(30,60);
        lblGreen.setPrefSize(30,60);

        this.add(lblPickYourColor,0,0,4,1);
        this.add(rbBlack,0,1);
        this.add(rbBlue,1,1);
        this.add(rbRed,2,1);
        this.add(rbGreen,3,1);
        this.add(lblBlack,0,2);
        this.add(lblBlue,1,2);
        this.add(lblRed,2,2);
        this.add(lblGreen,3,2);
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
    }

    RadioButton getRbBlack() {
        return rbBlack;
    }

    RadioButton getRbBlue() {
        return rbBlue;
    }

    RadioButton getRbRed() {
        return rbRed;
    }

    RadioButton getRbGreen() {
        return rbGreen;
    }
}
