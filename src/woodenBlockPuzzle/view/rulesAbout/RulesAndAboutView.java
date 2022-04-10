package woodenBlockPuzzle.view.rulesAbout;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * In deze klasse wordt het hulpscherm aangemaakt
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class RulesAndAboutView extends VBox {
    Label lblGoal;
    Label lblGoalDescription;
    Label lblHowToPlay;
    Label lblhowToPlayDescription;
    Label lblMadeBy;

    public RulesAndAboutView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Initialiseert alle nodes.
     */
    private void initialiseNodes(){
        lblGoal = new Label("The goal of the game: ");
        lblGoal.setId("goalTitle");
        lblGoalDescription = new Label("Wooden Block Puzzle is a simple yet addictive puzzle game. Drag and drop the wooden blocks to complete a vertical or horizontal line. Once a line is formed, it will disappear freeing up space for other blocks.");
        lblGoalDescription.setId("goalDescription");
        lblHowToPlay = new Label("How to play: ");
        lblHowToPlay.setId("howToPlayTitle");
        lblhowToPlayDescription = new Label("Once you've logged into a game, several wooden blocks of various shapes appear. Drag and drop one piece at a time onto the puzzle grid with a goal to clear horizontal rows and vertical columns  as you continue. The more rows/columns you clear, the higher your score climbs.");
        lblhowToPlayDescription.setId("howToPlayDescription");
        lblMadeBy = new Label("Made by Michel Matthe");
        lblMadeBy.setId("madeBy");
    }

    /**
     * In deze methode wordt de layout van de nodes toegewezen
     */
    private void layoutNodes(){
        lblGoalDescription.setWrapText(true);
        lblhowToPlayDescription.setWrapText(true);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        lblMadeBy.setPadding(new Insets(10));

        this.getChildren().addAll(lblGoal,lblGoalDescription,lblHowToPlay,lblhowToPlayDescription,lblMadeBy);
    }
}
