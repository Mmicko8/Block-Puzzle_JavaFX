package woodenBlockPuzzle.model;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/** Stelt een spelsessie voor.
 *
 * @author Michel Matthe
 * @version 1.1
 * */
public class  Spel {
    final int SPACING = 60;
    final int DIMENSIE = 10; // dimensie van het spelbord (bv een 10x10 bord)
    final int ENCRYPTION_SHIFT = 60;
    ArrayList<Puzzelstuk> puzzelstukkenEasy;
    ArrayList<Puzzelstuk> puzzelstukkenMedium;
    ArrayList<Puzzelstuk> puzzelstukkenHard;
    ArrayList<Puzzelstuk> tePlaatsenPuzzelstukken = new ArrayList<>();
    Speler huidigeSpeler;
    int huidigeScore = 0;
    String huidigeMoeilijkheid = "easy";
    ArrayList<Speler> spelersLijst = new ArrayList<>(); //een lijst met de gegevens van alle spelers
    Spelbord spelbord = new Spelbord(DIMENSIE);
    String saveFilePathStr = "resources" + File.separator + "playerInfo.txt";
    Color fillColor;

    /**
     * Initialiseert en laad de nodige data zodat het spel kan starten.
     *
     * @throws IOException indien het laden van de spelers's data niet lukt
     */
    public void start() throws IOException {
        fillColor = Color.BLACK;
        this.initialiseerPuzzelstukken();          // deze methode wordt gebruikt puur voor leesbaarheid
        this.genereerTePlaatsenPuzzelstukken();    // de 3 mogelijke puzzelstukken waaruit de gebruiker kan kiezen genereren
        loadPlayersFromFile();
    }

    /**
     * Verwijdered en slaat data op zodat het spel kan afsluiten of terug opnieuw opstarten.
     *
     * @throws IOException indien het opslaan van de spelers's data niet lukt
     */
    public void clearGame() throws IOException {
        savePlayersToFile();
        spelersLijst.clear();
        spelbord = new Spelbord(DIMENSIE);
        huidigeScore = 0;
        adjustMoeilijkheid();
    }

    /**Slaat de username, password and high-score van elke speler op in de {@code playerinfo.txt} file.
     *
     * @throws IOException indien het opslaan van de spelers's data niet lukt
     */
    private void savePlayersToFile() throws IOException {
        try (Formatter formatter=new Formatter(saveFilePathStr)) {
            String naam;
            String passwoord;
            String highScore;
            for (Speler speler: spelersLijst) {
                naam = encrypt(speler.getGebruikersnaam());
                passwoord = encrypt(speler.getWachtwoord());
                highScore = encrypt(String.valueOf(speler.getHighscore()));
                formatter.format("%s;%s;%s\n", naam, passwoord, highScore); // save username password and highscore separated by a ';'
            }
        } catch (IOException ioe) {
            throw new IOException("An error occured trying to save data from file.");
        }
    }

    /**
     * Returns de geencrypteerde versie van de meegegeven string.
     *
     * @param str de String die geencrypteerd wordt
     * @return de geencrypteerde String van {@code str}
     */
    private String encrypt(String str) {
        StringBuilder strEncrypted = new StringBuilder();
        int ascii;
        char shiftedChar;
        for (int i = 0; i < str.length(); i++){
            ascii = (int) str.charAt(i);
            shiftedChar = (char) (ascii + ENCRYPTION_SHIFT);
            strEncrypted.append(shiftedChar);
        }
        return strEncrypted.toString();
    }

    /**
     * Laadt de spelers's data.
     *
     * @throws IOException indien het laden van de spelers's data niet lukt
     */
    private void loadPlayersFromFile() throws IOException {
        Path saveFile = Paths.get(saveFilePathStr);
        if (Files.exists(saveFile)){
            try {
                Scanner fileScanner = new Scanner(saveFile);
                Speler speler;
                while (fileScanner.hasNext()) {
                    String textLine = fileScanner.nextLine(); // read next line from file
                    String[] textLineSplit = textLine.split(";"); // split line with ';' as separator
                    String naam = decrypt(textLineSplit[0]);
                    String password = decrypt(textLineSplit[1]);
                    String highscore = decrypt(textLineSplit[2]);

                    speler = new Speler(naam, password, Integer.parseInt(highscore)); //add player to list of players
                    spelersLijst.add(speler);
                }
            } catch (IOException ioe){
                throw new IOException("An error occured trying to load data from file.");
            }
        }
    }

    /**
     * Returns de decrypted versie van de meegegeven string.
     *
     * @param str de String die gedecrypt wordt
     * @return de decrypted String van {@code str}
     */
    private String decrypt(String str) {
        StringBuilder strDecrypted = new StringBuilder();
        int ascii;
        char shiftedChar;
        for (int i = 0; i < str.length(); i++){
            ascii = (int) str.charAt(i);
            shiftedChar = (char) (ascii - ENCRYPTION_SHIFT);
            strDecrypted.append(shiftedChar);
        }
        return strDecrypted.toString();
    }

    /**
     * Update de high-score van de huidige speler indien nodig.
     */
    private void updateHighscoreHuidigeSpeler(){
        // vervangt de oude score met de huidige score indien de huidige score groter is
        if (huidigeScore > huidigeSpeler.getHighscore()){
            huidigeSpeler.setHighscore(huidigeScore);
        }
    }

    /**
     * Initialiseert de lijsten van puzzelstukken.
     */
    private void initialiseerPuzzelstukken(){
        // voegt alle mogelijke puzzelstukken toe aan de lijst puzzelstukkenEasy
        puzzelstukkenEasy = new ArrayList<>(Arrays.asList(Puzzelstuk.values()));
        // voegt extra "gemakkelijke" puzzelstukken toe
        puzzelstukkenEasy.addAll(Arrays.asList(Puzzelstuk.VAK, Puzzelstuk.VAK, Puzzelstuk.VAK, Puzzelstuk.VAK, Puzzelstuk.I2, Puzzelstuk.I2,
                Puzzelstuk.I2VLAK, Puzzelstuk.I2VLAK, Puzzelstuk.VIERKANT2, Puzzelstuk.VIERKANT2, Puzzelstuk.I3, Puzzelstuk.I3VLAK));

        // voegt alle mogelijke puzzelstukken toe aan de lijst puzzelstukkenMedium
        puzzelstukkenMedium = new ArrayList<>(Arrays.asList(Puzzelstuk.values()));
        // voegt extra "gemiddelde moeilijkheid" puzzelstukken toe
        puzzelstukkenMedium.addAll(Arrays.asList(Puzzelstuk.VAK, Puzzelstuk.VAK, Puzzelstuk.VAK, Puzzelstuk.I2, Puzzelstuk.I2VLAK, Puzzelstuk.I3,
                Puzzelstuk.I3VLAK, Puzzelstuk.I3, Puzzelstuk.I3VLAK,Puzzelstuk.T_BOVEN, Puzzelstuk.T_ONDER, Puzzelstuk.T_RECHTS,
                Puzzelstuk.T_LINKS, Puzzelstuk.LKLEIN1, Puzzelstuk.LKLEIN2, Puzzelstuk.LKLEIN3, Puzzelstuk.LKLEIN4,
                Puzzelstuk.VIERKANT2, Puzzelstuk.VIERKANT2, Puzzelstuk.VIERKANT3));

        // voegt alle mogelijke puzzelstukken toe aan de lijst puzzelstukkenHard
        puzzelstukkenHard = new ArrayList<>(Arrays.asList(Puzzelstuk.values()));
        // voegt extra "moeilijke" puzzelstukken toe
        puzzelstukkenHard.addAll(Arrays.asList(Puzzelstuk.VAK, Puzzelstuk.VAK, Puzzelstuk.I3, Puzzelstuk.I3VLAK,
                Puzzelstuk.LKLEIN1, Puzzelstuk.LKLEIN2, Puzzelstuk.LKLEIN3, Puzzelstuk.LKLEIN4,Puzzelstuk.T_BOVEN,
                Puzzelstuk.T_ONDER, Puzzelstuk.T_RECHTS, Puzzelstuk.T_LINKS, Puzzelstuk.LGROOT1, Puzzelstuk.LGROOT2,
                Puzzelstuk.LGROOT3, Puzzelstuk.LGROOT4, Puzzelstuk.VIERKANT3, Puzzelstuk.VIERKANT3));
    }

    /**Vult {@code tePlaatsenPuzzelStukken} met drie willekeurig geselecteerde {@code Puzzelstuk}'en. */
    private void genereerTePlaatsenPuzzelstukken() {
        tePlaatsenPuzzelstukken.clear();
        ArrayList<Puzzelstuk> lijst;

        //aanvankelijk van de huidige moeilijkheidsgraad een bepaalde verzameling van puzzelstukken nemen
        switch(huidigeMoeilijkheid){
            case "easy":
                lijst = puzzelstukkenEasy; break;
            case "medium":
                lijst = puzzelstukkenMedium; break;
            default:
                lijst = puzzelstukkenHard; break;
        }
        Random rnd = new Random();

        for (int i = 0; i < 3; i++) { //deze code driemaal uitvoeren
            int index  =  rnd.nextInt(lijst.size()); //een random index maken
            tePlaatsenPuzzelstukken.add(lijst.get(index)); //een random puzzelstuk uit de ArrayList nemen
        }
    }

    /**
     * Geeft aan of het mogelijk is om een van de te plaatsen puzzelstukken te plaatsen op het spelbord.
     *
     * @return {@code true} als het mogelijk is om een van de {@code PuzzelStuk}'en uit {@code tePlaatsenPuzzelstukken}
     * te plaatsen op het {@code spelbord}, anders {@code false}
     */
    public boolean isZetMogelijk(){
        for (Puzzelstuk puzzelstuk : this.tePlaatsenPuzzelstukken){
            // loopt over elk vakje op het spelbord
            for (int rij = 0; rij < DIMENSIE; rij++){
                for (int kol = 0; kol < DIMENSIE; kol++){
                    // kijkt na of het puzzelstuk geplaatst kan worden op het vakje, indien wel dan return true
                    if (spelbord.kanGeplaatstworden(puzzelstuk, new int[] {rij, kol})){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Plaatst het puzzelstuk op het gegeven coordinaat.
     *
     * @param screenCoordX de x-coordinaat van het scherm tov de upper-left hoek van het bord
     * @param screenCoordY de y-coordinaat van het scherm tov de upper-left hoek van het bord
     * @param pieceNr het nummer van het puzzelstuk
     * @return {@code true} als er rijen of kolommen zijn verwijderd na het plaatsen van het puzzelstuk, anders {@code false}
     */
    public boolean doeZet(double screenCoordX, double screenCoordY, int pieceNr) {
        boolean rijenOfKolommenVerwijderd;
        int index = pieceNr - 1; // puzzelstuknr = 1 -> index 0 van tePlaatsenPuzzelstukken
        // plaatst de gekozen puzzelstuk op het gekozen vakje en slaagt op of er rijen of kolommen waren verwijderd
        int[] coordinates = new int[] {coordinateToIndex(screenCoordY), coordinateToIndex(screenCoordX)};
        rijenOfKolommenVerwijderd = spelbord.plaatsPuzzelstuk(tePlaatsenPuzzelstukken.get(index), coordinates);

        // verhoogt de huidige score met de score-waarde van het geplaatse puzzelstuk
        huidigeScore += tePlaatsenPuzzelstukken.get(index).score;

        adjustMoeilijkheid(); // verhoogt de moeilijkheid indien de score boven een bepaalde threshold komt

        tePlaatsenPuzzelstukken.remove(index); // verwijder de geplaatste puzzelstuk uit de rij van de te plaatsen puzzelstukken

        // als de rij van te plaatsen puzzelstukken leeg is dan wordt er opnieuw eentje gegenereerd
        if (tePlaatsenPuzzelstukken.size() == 0){
            genereerTePlaatsenPuzzelstukken();
        }
        updateHighscoreHuidigeSpeler(); // update high score van huidige speler

        return rijenOfKolommenVerwijderd;
    }

    /**
     * Geeft van de gegeven scherm-coordinaat de corresponderende rij/ kolom index
     *
     * @param screenCoord de x-/ y-coordinaat van het scherm tov de upper-left hoek van het bord
     * @return de corresponderende rij/ kolom index
     */
    private int coordinateToIndex(double screenCoord) {
        int result = 0;
        for (int i = 0; i < DIMENSIE; i++) {
            if (SPACING * i < screenCoord && screenCoord <= SPACING * (i + 1)) {
                result = i;
            }
        }
        return result;
    }

    /**
     * Geeft aan of het puzzelstuk geplaatst kan worden op de gegeven coordinaat.
     *
     * @param screenCoordX de x-coordinaat van het scherm tov de upper-left hoek van het bord
     * @param screenCoordY de y-coordinaat van het scherm tov de upper-left hoek van het bord
     * @param pieceNr het nummer van het puzzelstuk
     * @return {@code true} als de puzzelstuk geplaatst kan worden op de gegeven coordinaat, anders {@code false}
     */
    public boolean kanGeplaatstworden(double screenCoordX, double screenCoordY, int pieceNr){
        int [] coordinate = {coordinateToIndex(screenCoordY), coordinateToIndex(screenCoordX)}; // screen coordinates -> [row, col]
        Puzzelstuk piece = tePlaatsenPuzzelstukken.get(pieceNr-1); //
        return spelbord.kanGeplaatstworden(piece, coordinate);
    }

    /**
     * Veranderd de huidige moeilijkheid als de huidige score een bepaalde threshold overschrijdt.
     */
    void adjustMoeilijkheid(){
        if (huidigeScore < 50) {
            huidigeMoeilijkheid = "easy";
        }
        else if (huidigeScore < 100){
            huidigeMoeilijkheid = "medium";
        }
        else {
            huidigeMoeilijkheid = "hard";
        }
    }

    /**
     * Logt de speler in indien de correcte gegevens worden meegegeven.
     *
     * @param username de username van de speler
     * @param password het wachtwoord van de speler
     * @throws IllegalArgumentException als het wachtwoord verkeerd is of de username niet bestaat
     */
    public void logIn(String username, String password) throws IllegalArgumentException{
        for (Speler speler: spelersLijst) {
            if (speler.getGebruikersnaam().equals(username)){
                if (! speler.getWachtwoord().equals(password)){
                    throw new IllegalArgumentException("Incorrect password");
                }
                else {
                    huidigeSpeler = speler;
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Username doesn't exist");
    }

    /**
     * Maakt een nieuwe speler aan met de meegegeven username en wachtwoord.
     *
     * @param username de username van de speler
     * @param password het wachtwoord van de speler
     * @throws IllegalArgumentException als de {@code username} al bestaat, de {@code username} en/ of het {@code password} illegale karakters
     * bevatten of korter dan 4 karakters of langer dan 25 karakters zijn
     */
    public void signUp(String username, String password) throws IllegalArgumentException{
        // check if username and password are valid
        if (isNotValid(username)){
            throw new IllegalArgumentException("Invalid username! \nUsername can contain only letters and numbers, " +
                                                    "it must be at least 2 characters long and at most 25 characters long.");
        }
        else if (isNotValid(password)){
            throw new IllegalArgumentException("Invalid password! \nPassword can contain only letters and numbers, " +
                                                    "it must be at least 2 characters long and at most 25 characters long.");
        }

        // check if username already exists
        for (Speler speler: spelersLijst) {
            if (speler.getGebruikersnaam().equals(username)){
                throw new IllegalArgumentException("Username already exists");
            }
        }

        Speler speler = new Speler(username, password, 0);
        huidigeSpeler = speler;
        spelersLijst.add(speler);
    }

    /**
     * Geeft aan of de username of wachtwoord valide zijn.
     *
     * @param str de username of wachtwoord
     * @return {@code true} als {@code str} NIET valide is, anders {@code false}
     */
    boolean isNotValid(String str) {
        return ! (str.matches("[a-zA-Z0-9 ]*") && (str.length() > 1 && str.length() < 25));
    }

    public void setFillColor(Color color) {
        fillColor = color;
    }

    @Override
    public String toString(){
        return "Speler: " + huidigeSpeler.getGebruikersnaam() + " | Score: " + huidigeScore + " | Moeilijkheid: " + huidigeMoeilijkheid;
    }

    public ArrayList<Puzzelstuk> getTePlaatsenPuzzelstukken() {
        return tePlaatsenPuzzelstukken;
    }

    public int[][] getBoard() {
        return spelbord.getBord();
    }

    public int getHuidigeScore(){
        return huidigeScore;
    }

    public int getHighScore(){
        return huidigeSpeler.getHighscore();
    }

    public String getUserName(){
        return huidigeSpeler.getGebruikersnaam();
    }

    public Color getFillColor(){
        return fillColor;
    }

    public ArrayList<Speler> getSpelersLijst() {
        return spelersLijst;
    }

    public int getDimensie() {
        return DIMENSIE;
    }

    public int getSpacing() {
        return SPACING;
    }
}
