package Models;

import java.sql.Date;

public class PlayedMatch implements Model{
    int id;
    String p1Email;
    String p2Email;
    int p1GamesWon;
    int p2GamesWon;
    Date datePlayed;
    int courtNumber;
    String venueName;
    String leagueName;
    int leagueYear;

    public PlayedMatch(int id, String p1Email, String p2Email, int p1GamesWon, int p2GamesWon, Date datePlayed, int courtNumber,
                       String venueName, String leagueName, int leagueYear){
        this.id = id;
        this.p1Email = p1Email;
        this.p2Email = p2Email;
        this.p1GamesWon = p1GamesWon;
        this.p2GamesWon = p2GamesWon;
        this.datePlayed = datePlayed;
        this.courtNumber = courtNumber;
        this.venueName = venueName;
        this.leagueName = leagueName;
        this.leagueYear = leagueYear;
    }

    @Override
    public String[] toStringArray() {
        return new String[]{id + "", p1Email, p2Email, p1GamesWon + "", p2GamesWon + "", datePlayed.toString(), courtNumber + "",
        venueName, leagueName, leagueYear + ""};
    }
}
