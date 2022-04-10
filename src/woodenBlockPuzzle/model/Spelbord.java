package woodenBlockPuzzle.model;

import javafx.scene.control.ToggleButton;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/** Stelt een spelbord voor die bestaat uit N rijen en N kolommen van vakjes die opgevuld kunnen worden met puzzelstukken.
 *
 * @author Michel Matthe
 * @version 1.1
 * */
public class Spelbord {
    static final int VOL = 1;
    static final int LEEG = 0;
    int boardSize;
    int [][] bord;

    public Spelbord(int dimensie){
        this.bord = new int[dimensie][dimensie];
        boardSize = dimensie;
    }

    /**
     * Geeft de som van twee coordinaten.
     *
     * @param coord1 de eerste coordinaat
     * @param coord2 de tweede coordinaat
     * @return de som van {@code coord1} en {@code coord2}
     */
    static int[] coordinaatSom(int[] coord1, int[] coord2){
        ToggleButton btn = new ToggleButton();
        btn.isSelected();
        return new int[] {coord1[0]+coord2[0], coord1[1] + coord2[1]};
    }


    /**
     * Geeft aan of de gegeven rij vol is.
     *
     * @param rij de gegeven rij
     * @return {@code true} als {@code rij} vol is, anders {@code false}.
     */
    boolean isRijVol(int rij){
        // loopt over elke vak in de rij en returnt false als er ééntje leeg is
        for (int i = 0; i < this.bord.length; i++){
            if (this.bord[rij][i] == LEEG){
                return false;
            }
        }
        return true;
    }

    /**
     * Geeft aan of de gegeven kolom vol is.
     *
     * @param kolom de gegeven kolom
     * @return {@code true} als {@code kolom} vol is, anders {@code false}.
     */
    boolean isKolomVol(int kolom){
        // loopt over elke vak in de kolom en returnt false als er ééntje leeg is
        for (int[] rij : this.bord) {
            if (rij[kolom] == LEEG) {
                return false;
            }
        }
        return true;
    }

    /**
     * Maakt alle volle rijen en kolommen leeg.
     *
     * @return {@code true} als er rijen of kolommen zijn leeggemaakt, anders {@code false}
     */
    boolean verwijderVolleRijenEnKolommen(){
        boolean removedColsOrRows = false;
        ArrayList<Integer> rijenTeVerwijderen = new ArrayList<>();
        ArrayList<Integer> kolommenTeVerwijderen = new ArrayList<>();

        // loopt over alle rijen en voegt elke volle rij toe aan 'rijenTeVerwijderen'
        for (int row = 0; row < this.bord.length; row++){
            if (isRijVol(row)) {
                rijenTeVerwijderen.add(row);
            }
        }

        // loopt over alle kolommen en voegt elke volle kolom toe aan 'kolommenTeVerwijderen'
        for (int kolom = 0; kolom < this.bord.length; kolom++){
            if (isKolomVol(kolom)) {
                kolommenTeVerwijderen.add(kolom);
            }
        }

        // maakt alle rijen uit 'rijenTeVerwijderen' terug leeg
        for (Integer row: rijenTeVerwijderen){
            verwijderRij(row);
            removedColsOrRows = true;
        }

        // maakt alle kolommen uit 'kolommenTeVerwijderen' terug leeg
        for (Integer kolom: kolommenTeVerwijderen){
            verwijderKolom(kolom);
            removedColsOrRows = true;
        }
        return removedColsOrRows;
    }


    /**
     * Maakt de gegeven rij leeg.
     *
     * @param rij de gegeven rij
     */
    void verwijderRij(int rij){
        for (int i = 0; i < this.bord.length; i++){
            this.bord[rij][i] = LEEG;
        }
    }

    /**
     * Maakt de gegeven kolom leeg.
     *
     * @param kolom de gegeven kolom
     */
    void verwijderKolom(int kolom){
        for (int i = 0; i < this.bord.length; i++){
            this.bord[i][kolom] = LEEG;
        }
    }

    /**
     * Plaatst het gegeven puzzelstuk op het gegeven vakje van het spelbord en verwijderd volle rijen of kolommen indien
     * nodig.
     *
     * @param stuk het puzzelstuk dat geplaatst wordt op het spelbord
     * @param vak het vakje waarop het puzzelstuk geplaatst wordt
     * @return {@code true} als er rijen of kolommen zijn leeggemaakt/ verwijderd, anders {@code false}
     */
    boolean plaatsPuzzelstuk(Puzzelstuk stuk, int[] vak){
        int[] current_coordinaat;
        for (int[] coordinaat: stuk.coordinaten){
            current_coordinaat = coordinaatSom(coordinaat, vak);
            this.bord[current_coordinaat[0]][current_coordinaat[1]] = VOL;
        }
        return verwijderVolleRijenEnKolommen();
    }

    /**
     * Geeft aan of het gegeven puzzelstuk geplaatst kan worden op het gegeven vakje.
     *
     * @param stuk het puzzelstuk dat geplaatst wordt op het spelbord
     * @param vak het vakje waarop het puzzelstuk geplaatst wordt
     * @return {@code true} als {@code stuk} geplaatst kan worden op {@code vak}, anders {@code false}.
     */
    boolean kanGeplaatstworden(Puzzelstuk stuk, int[] vak){
        // loopt over alle coordinaten van het te plaatsen puzzelstuk en ziet of de vakjes waar die moeten geplaatst worden
        //   valide en lege vakjes op het spelbord zijn
        for (int[] coordinaat: stuk.coordinaten){
            if (! isLeegEnValidCoordinaat(coordinaatSom(coordinaat, vak))){
                return false;
            }
        }
        return true;
    }

    /**
     * Geeft aan of het vakje op het gegeven coordinaat leeg en valide is.
     *
     * @param coordinaat de coordinaat
     * @return {@code true} als de het spelbord leeg is op coordinaat {@code coordinaat}, anders {@code false}
     */
    boolean isLeegEnValidCoordinaat(int[] coordinaat){
        // return false als coordinaat buiten bord valt OF als het bord vol is op die coordinaat
        return coordinaat[0] >= 0 && coordinaat[0] < this.bord.length && coordinaat[1] >= 0 && coordinaat[1] < this.bord.length
                && this.bord[coordinaat[0]][coordinaat[1]] != VOL;
    }

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        // loopt over de rijen van het bord in omgekeerde volgorde en voegt de string vorm van de rij toe aan 'string'
        for (int i = this.bord.length - 1; i >= 0; i--){
            string.append(Arrays.toString(this.bord[i])).append("\n");
        }
        return string.toString();

    }

    public int[][] getBord() {
        return bord;
    }
    public static int getVol(){
        return VOL;
    }
}
