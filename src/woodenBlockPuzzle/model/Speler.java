package woodenBlockPuzzle.model;

/** Stelt een speler voor met bepaalde inloggegevens en een high-score.
 *
 * @author Michel Matthe
 * @version 1.1
 * */
public class Speler {
    private final String gebruikersnaam;
    private final String wachtwoord;
    private int highscore;

    public Speler(String gebruikersnaam, String wachtwoord, int highscore) {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.highscore = highscore;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    @Override
    public String toString(){
        return "GN: " + gebruikersnaam + " | WW: " + wachtwoord + " | HS: " + highscore;
    }
}
