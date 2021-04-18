package Models;

import java.sql.Date;

public class Match {
    int id;
    String p1Email;
    String p2Email;
    int p1GamesWon;
    int p2GamesWon;
    Date datePlayed;
    int courtNumber;
    String venueName;

    public Match(int id, String p1Email, String p2Email, int p1GamesWon, int p2GamesWon,
                 Date datePlayed, int courtNumber, String venueName){
        this.id = id;
        this.p1Email = p1Email;
        this.p2Email = p2Email;
        this.p1GamesWon = p1GamesWon;
        this.p2GamesWon = p2GamesWon;
        this.datePlayed = datePlayed;
        this.courtNumber = courtNumber;
        this.venueName = venueName;
    }
}
